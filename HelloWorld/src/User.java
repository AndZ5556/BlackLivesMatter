import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringJoiner;

public class User {
    private final String name;
    private final String surname;
    private final int ID;
    public final String VkID;
    private final String age;
    private final String photo;
    public int friendsNumber;
    public String [] friends;
    public String friendsList;
    public User.Cords cords = new Cords();
    public static class Cords{
        public int x;
        public int y;
    }

    public User(){
        this.name = "";
        this.surname = "";
        this.age = "";
        this.ID = -1;
        this.VkID = "1";
        this.photo = "https://vk.com/images/camera_400.png";
    }
    public User(String VkID, int ID) throws MyExceptions {
        String [] info = getUserInfo(VkID);
        String [] friendsInfo = getUserFriends(VkID);
        this.name = info[0];
        this.surname = info[1];
        this.age = info[2];
        this.photo = info[3].replaceAll("\\\\", "");
        this.ID = ID;
        this.VkID = VkID;
        this.friendsNumber = friendsInfo.length;
        this.friends = friendsInfo;
    }
    static public String [] getUserFriends(String VkID){
        StringBuilder text = new StringBuilder();
        try(FileReader reader = new FileReader(".idea/secret.txt"))
        {
            int c;
            while((c = reader.read()) != -1){
                text.append ((char) c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        String TOKEN = text.toString();
        String url = "https://api.vk.com/method/" +
                "friends.get" +
                "?user_id=" + VkID +
                "&access_token=" + TOKEN +
                "&v=5.52";
        String line = "";
        try {
            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.split("\\[")[1].split("]")[0].split(",");
    }
    static public String getUserFriendsList(String VkID){
        StringBuilder text = new StringBuilder();
        try(FileReader reader = new FileReader(".idea/secret.txt"))
        {
            int c;
            while((c = reader.read()) != -1){
                text.append ((char) c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        String TOKEN = text.toString();
        String url = "https://api.vk.com/method/" +
                "friends.get" +
                "?user_id=" + VkID +
                "&access_token=" + TOKEN +
                "&fields=first_name,last_name"+
                "&v=5.52";
        String line = "";
        try {
            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = line.replaceAll("\\d","").replaceAll("\\[]", "");
        StringJoiner join = new StringJoiner(", ");
        String [] friendsInfo = line.split("\\[")[1].split("]")[0].split("},\\{");
        for(String str : friendsInfo){
            str = str.split("\"")[5] + " " + str.split("\"")[9];
            join.add(str);
        }
        return join.toString();
    }
    static public String [] getUserInfo(String VkID) throws MyExceptions {
        StringBuilder text = new StringBuilder();
        try(FileReader reader = new FileReader(".idea/secret.txt"))
        {
            int c;
            while((c = reader.read()) != -1){
                text.append ((char) c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        String TOKEN = text.toString();
        String[] info = new String [4];
        String url = "https://api.vk.com/method/" +
                "users.get" +
                "?user_id=" + VkID +
                "&access_token=" + TOKEN +
                "&fields=photo_400_orig,bdate"+
                "&v=5.52";
        String line = "";
        try {
            URL url2 = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            info[0] = line.split("\"")[7];
            info[1] = line.split("\"")[11];
            info[2] = line.split("\"")[15];
            info[3] = line.split("\"")[19];
        }
        catch (ArrayIndexOutOfBoundsException e){
            if(e.getMessage().equals("19")){
                info[3] = info[2];
                info[2] = "Not specified";
            }
            else
                throw new MyExceptions("Error has occurred with ID " + VkID);
        }
        return info;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getAge(){return age;}
    public String getPhoto(){return photo;}
    public int getID(){
        return ID;
    }
    public boolean equals(User u){
        return (u.getName().equals(this.name) && u.getSurname().equals(this.surname));
    }
    public boolean areFriends(User u){
        for (String id : u.friends)
            if(VkID.equals(id))
                return true;
        return false;
    }
    public void drawUser(Graphics2D g2){
        g2.drawOval(cords.x,cords.y, 100,100);
        g2.drawString(name, cords.x + 20,cords.y + 40);
        g2.drawString(surname, cords.x + 20,cords.y + 55);
    }
}
