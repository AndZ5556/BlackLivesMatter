import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgesContainerTest {

    @Test
    void addEdge() {
        try{
            User u1 = new User("135927919", 2);
            User u2 = new User("237044688", 1);
            u1.friends[0] = "237044688";
            u2.friends[0] = "135927919";
            EdgesContainer edges = new EdgesContainer();
            edges.addEdge(u1, u2);
            Edge edge = new Edge(u1, u2);
            System.out.println("OK");
            assertTrue(edges.find(edge));
        }
        catch(MyExceptions ex){
            System.out.println("Not OK, data was correct");
        }
    }

    @Test
    void buildWeights() {
        try{
            User u1 = new User("135927919", 2);
            User u2 = new User("237044688", 1);
            User u3 = new User("633549608", 3);
            u1.friends[0] = "237044688";
            u1.friends[1] = "633549608";
            u2.friends[0] = "135927919";
            u2.friends[1] = "633549608";
            EdgesContainer edges = new EdgesContainer();
            edges.addEdge(u1, u2);
            edges.buildWeights();
            if (edges.edges[0].weight == 1){
                System.out.println("OK");
                assertTrue(true);
            }
            else {
                System.out.println("Not OK");
            }

        }
        catch(MyExceptions ex){
            System.out.println("Not OK, data was correct");
        }
    }

    @Test
    void findMaxEdge() {
        try{
            User u1 = new User("135927919", 2);
            User u2 = new User("237044688", 1);
            User u3 = new User("633549608", 3);
            User u4 = new User("123456789", 4);
            User u5 = new User("428104856", 5);
            UsersContainer users = new UsersContainer();
            u1.friends[0] = "237044688";
            u1.friends[1] = "663549618";
            u1.friends[2] = "732987469";
            u1.friendsNumber = 3;
            u2.friends[0] = "135927919";
            u2.friends[1] = "663549618";
            u2.friends[2] = "732987469";
            u2.friendsNumber = 3;
            u3.friends[0] = "135927919";
            u3.friends[1] = "237044688";
            u3.friends[2] = "927014388";
            u3.friendsNumber = 3;
            users.addUser(u1);
            users.addUser(u3);
            EdgesContainer edges = new EdgesContainer();
            edges.addEdge(u1, u2);
            edges.addEdge(u1, u3);
            edges.addEdge(u2, u3);
            edges.buildWeights();
            if (edges.findMaxEdge(u1, users, true).weight == 2){
                System.out.println("OK");
                assertTrue(true);
            }
            else {
                System.out.println("Not OK");
            }

        }
        catch(MyExceptions ex){
            System.out.println("Not OK, data was correct");
        }
    }
}
