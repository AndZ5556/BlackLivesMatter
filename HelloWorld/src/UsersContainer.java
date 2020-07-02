public class UsersContainer {
    public User [] users;
    private int length;
    public UsersContainer(){
        length = 0;
        users = new User[0];
    }
    public User addUser(String name, String surname, String age){
        User newUser;
        name = name.replaceAll("[^А-Яа-яA-Za-z]", "");
        surname = surname.replaceAll("[^А-Яа-яA-Za-z]", "");
        if((newUser = getUser(name, surname)) != null){
            if(newUser.getAge().equals("")) newUser.setAge(age);
            return newUser;
        }

        newUser = new User(name, surname, age, length);
        length++;
        User [] newArr = new User [length];
        System.arraycopy(users, 0, newArr, 0, users.length);
        newArr[length - 1] = newUser;
        users = newArr;
        return newUser;
    }
    public void addUser(User user){
        if(notInContainer(user)){
            length++;
            User [] newArr = new User [length];
            System.arraycopy(users, 0, newArr, 0, users.length);
            newArr[length - 1] = user;
            users = newArr;
        }
    }
    public User getUser(String name, String surname){
        for(User user : users){
            if(name.equals(user.getName()) && surname.equals(user.getSurname()))
                return user;
        }
        return null;
    }
    public int getLength(){
        return length;
    }
    public boolean notInContainer(User user){
        for(User curr : users){
            if(user.equals(curr))
                return false;
        }
        return true;
    }
    public void buildUsers(){
        int R = (int)(length * 200 / 2 / Math.PI) * 2;
        for(User user : users){
            user.cords.x = (int)(R + R * Math.cos(2 * Math.PI * user.getID()/length));
            user.cords.y = (int)(R + R * Math.sin(2 * Math.PI * user.getID()/length));
        }
    }
}
