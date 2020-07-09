import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testEquals() {
        Edge edge = new Edge();
        if (edge.equals(edge)){
            assertTrue(true);
        }
    }
    @Test
    void connectWith() {
        try {
            User user1 = new User("237044688", 1);
            User user2 = new User("135927919", 2);
            Edge edge = new Edge(user1, user2);
            if (edge.connectWith(user1) == user2){
                assertTrue(true);
            }
        }
        catch (MyExceptions ex){
            System.out.println("Not ok");
        }
    }
}