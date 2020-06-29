import java.awt.*;

public class User {
    private final String name;
    private final String surname;
    private final int ID;
    private String age;
    public int friendsNumber;
    public User [] friends;
    public User.Cords cords;
    public class Cords{
        public int x;
        public int y;
    }

    public User(String name, String surname, String age, int ID){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.ID = ID;
        friendsNumber = 0;
        friends = new User[0];
        cords = new User.Cords();
    }
    public User(){
        this.name = "";
        this.surname = "";
        this.age = "";
        this.ID = -1;
        friendsNumber = 0;
        friends = new User[0];
        cords = new User.Cords();
    }
    public void addFriend(User user)
    {
        friendsNumber++;
        User [] newArr = new User [friendsNumber];
        for(int i = 0; i < friends.length; i++)
            newArr[i] = friends[i];
        newArr[friendsNumber - 1] = user;
        friends = newArr;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getAge(){return age;}
    public void setAge(String age){this.age = age;}
    public int getID(){
        return ID;
    }
    public boolean equals(User u){
        return (u.getName().equals(this.name) && u.getSurname().equals(this.surname));
    }
    public void drawUser(Graphics2D g2){
        g2.drawOval(cords.x,cords.y, 100,100);
        g2.drawString(name, cords.x + 20,cords.y + 40);
        g2.drawString(surname, cords.x + 20,cords.y + 55);
    }
}
