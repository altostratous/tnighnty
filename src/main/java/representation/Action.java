package representation;

public class Action {

    enum ActionName {FIRE,RIGHT,LEFT, AHEAD, BACK};
    // want an instance variable "action"
    ActionName action;
    double QValue = 0;

    public Action (String name) {

        switch (name) {
            case "fire":
                action = ActionName.FIRE;
                break;
            case "right":
                action = ActionName.RIGHT;
                break;
            case "left":
                action = ActionName.LEFT;
                break;
            case "ahead":
                action = ActionName.AHEAD;
                break;
            case "back":
                action = ActionName.BACK;
                break;
        }
    }
    public static void main(String[] args) {
        Action action = new Action("right");
        System.out.println(action.action);
    }


}
