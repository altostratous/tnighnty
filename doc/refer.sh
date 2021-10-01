#!/bin/bash

for f in ../src/main/java/*.java ../src/main/java/**/*.java ../src/test/java/*.java ../src/test/java/**/*.java ; do
  CAPTION=`echo $f | sed  's@\.\./src/main/java/@@g'`
  CAPTION=`echo $CAPTION | sed  's@\.\./src/test/java/@@g'`
  if [ -f $f ]; then
    echo '\lstinputlisting[language=Java,caption='$CAPTION']{'$f'}'
  fi
done