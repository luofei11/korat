korat
=====
This is "korat-1.1" By Author Fei Luo
-----

### compile it in the command line:

    cd korat-1.1/src/Graph
    
    javac -cp ../../lib/korat-1.0.jar *.java -d ../../bin

### run it in the command line(linux/mac):

    cd bin
    
    java -cp ../lib/korat-1.0.jar:../lib/junit.jar:../lib/commons-cli-1.0.jar:../lib/alloy4viz.jar:../lib/javassist.jar: Graph.MyTest --class Graph.Graph --args 2,3,4
    
### run it in the command line(windows):

    cd bin
    
    java -cp ../lib/korat-1.0.jar;../lib/junit.jar;../lib/commons-cli-1.0.jar;../lib/alloy4viz.jar;../lib/javassist.jar; Graph.MyTest --class Graph.Graph --args 2,3,4
