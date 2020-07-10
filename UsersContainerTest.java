import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersContainerTest {

    @Test
    void addUser() {
        try{
            User user = new User("4234", 3);
            UsersContainer users = new UsersContainer();
            users.addUser(user.VkID);
        }
        catch (MyExceptions ex){
            System.out.println("Ok");
        }
    }

    @Test
    void testAddUser() {
        try{
            User user = new User("135927919", 1);
            UsersContainer users = new UsersContainer();
            users.addUser(user);
            User user2 = user;
            assertEquals(user2, users.users[0]);
        }
        catch (MyExceptions ex){
            System.out.println("Not ok, data was correct");
        }
    }
}