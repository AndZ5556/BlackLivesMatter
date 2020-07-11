class GraphTest {
    @org.junit.jupiter.api.Test
    void addUser() {
        try {
            Graph graph = new Graph();
            graph.addUser("blabla");
        } catch (MyExceptions myExceptions) {
            System.out.println("OK");
        }
    }

    @org.junit.jupiter.api.Test
    void processText() {
        try {
            Graph graph = new Graph();
            graph.processText("234fwe133");
        } catch (MyExceptions myExceptions) {
            System.out.println("OK");
        }
    }
}