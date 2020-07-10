import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserFriends() {
        String ID = "135927919";
        User user = new User();
        String[] line = User.getUserFriends(ID);
        for (String str : line){
            try{
                Double.parseDouble(str);
            }
            catch (NumberFormatException e) {
                System.out.println("Incorrect ID");
                assertFalse(true);
            }
        }
    }

    @Test
    void getUserInfo() {
            try{
                User user = new User("fwer423ew", 2);
                String[] line = user.getUserInfo(user.VkID);
            }
            catch (MyExceptions e) {
                System.out.println("Incorrect ID");
                assertFalse(true);
            }
    }

}