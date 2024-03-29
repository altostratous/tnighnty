package nn;

import autograd.IVariable;
import autograd.Operation;
import autograd.Parameter;
import dataset.DataPoint;
import dataset.IDataSet;
import jdk.jshell.spi.ExecutionControl;
import optimization.ILoss;
import optimization.IOptimizer;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class Model {
    private final Parameter[] input;
    private final IVariable[] output;

    public Model(Parameter[] input, IVariable[] output) {
        this.input = input;
        this.output = output;
    }


    public double[] evaluate(double[] input) {
        var result = new double[output.length];
        for (int i = 0; i < input.length; i++) {
            this.input[i].setValue(input[i]);
        }
        for (int i = 0; i < output.length; i++) {
            result[i] = output[i].evaluate();
        }
        return result;
    }

    public Parameter[] getParameters() {
        HashSet<Parameter> result = new HashSet<>();
        for (IVariable o :
                this.output) {
            result.addAll(Arrays.asList(o.getParameters()));
        }
        return result.toArray(new Parameter[0]);
    }

    public Parameter[] getTrainableParameters() {
        var results = new HashSet<Parameter>();
        for (Parameter p :
                getParameters()) {
            if (p.isTrainable()) {
                results.add(p);
            }
        }
        for (Parameter p : input) {
            results.remove(p);
        }

        return results.stream().sorted(Comparator.comparing(Parameter::getParameterId)).toArray(Parameter[]::new);
    }

    public IVariable[] getOutput() {
        return output;
    }

    public double fit(IDataSet dataSet, IOptimizer optimizer, ILoss loss, int epochs, double lossLimit) throws ExecutionControl.NotImplementedException {
        return fit(dataSet, optimizer, loss, epochs, lossLimit, (epoch, l) -> {
        });
    }

    public double fit(IDataSet dataSet, IOptimizer optimizer, ILoss loss, int epochs, double lossLimit, IFitCallback callback) throws ExecutionControl.NotImplementedException {
        return fit(dataSet, optimizer, loss, epochs, lossLimit, callback, true);
    }
    public double fit(IDataSet dataSet, IOptimizer optimizer, ILoss loss, int epochs, double lossLimit, IFitCallback callback, boolean online) throws ExecutionControl.NotImplementedException {
        var parameters = getTrainableParameters();
        Map<Integer, List<Parameter>> layeredParameters = layerParameters(parameters);
        if (epochs < 1) {
            throw new IllegalArgumentException("At least one epochs required.");
        }
        double totalLoss = 0;
        for (int i = 0; i < epochs; i++) {
            totalLoss = 0;
            dataSet.reset();
            DataPoint dataPoint;
            while ((dataPoint = dataSet.next()) != null) {
                setInput(dataPoint.getX());
                loss.setDesired(dataPoint.getY());
                totalLoss += loss.evaluate();
                if (online) {
                    for (Integer j : layeredParameters.keySet().stream().sorted().collect(Collectors.toList())) {
                        Parameter[] layerParameters = layeredParameters.get(j).toArray(new Parameter[0]);
                        loss.backward(layerParameters, 1.);
                        optimizer.update(layerParameters);
                    }
                } else {
                    loss.backward(parameters, 1.);
                }
            }
            callback.collect(i, totalLoss);
            if (totalLoss < lossLimit) {
                break;
            }
            if (!online) {
                optimizer.update(parameters);
            }
        }
        return totalLoss;
    }

    private Map<Integer, List<Parameter>> layerParameters(Parameter[] parameters) {
        setLayers(getOutput(), 0);
        return Arrays.stream(parameters).collect(Collectors.groupingBy(Parameter::getLayer));

    }

    private void setLayers(IVariable[] outputs, int layer) {
        if (outputs.length == 0) return;
        HashSet<IVariable> nextOutput = new HashSet<>();
        for (IVariable i : outputs) {
            if (i instanceof Parameter) {
                ((Parameter) i).setLayer(layer);
            }
            if (i instanceof Operation) {
                nextOutput.addAll(Arrays.asList(((Operation) i).getOperands()));
            }
        }
        setLayers(nextOutput.toArray(new IVariable[0]), layer + 1);
    }

    private void setInput(double[] x) {
        for (int i = 0; i < input.length; i++) {
            input[i].setValue(x[i]);
        }
    }
}
