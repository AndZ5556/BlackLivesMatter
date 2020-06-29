public class UsersContainer {
    public User [] users;
    private int length;
    public UsersContainer(){
        length = 0;
        users = new User[0];
    }

    public User addUser(String name, String surname, String age)
    {
        User newUser;
        name = name.replaceAll("[^А-Яа-яA-Za-z0-9]", "");
        surname = surname.replaceAll("[^А-Яа-яA-Za-z0-9]", "");
        if((newUser = getUser(name, surname)) != null){
            if(newUser.getAge() == "") newUser.setAge(age);
            return newUser;
        }

        newUser = new User(name, surname, age, length);
        length++;
        User [] newArr = new User [length];
        for(int i = 0; i < users.length; i++)
            newArr[i] = users[i];
        newArr[length - 1] = newUser;
        users = newArr;
        return newUser;
    }

    public void addUser(User user){
        if(!find(user)){
            length++;
            User [] newArr = new User [length];
            for(int i = 0; i < users.length; i++)
                newArr[i] = users[i];
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
    public boolean find(User user){
        for(int i = 0; i < length; i++){
            if(user.equals(users[i]))
                return true;
        }
        return false;
    }

    public void buildUsers(){
        int R = (int)(length * 200 / 2 / Math.PI) * 2;
        for(User user : users){
            user.cords.x = (int)(R + R * Math.cos(2 * Math.PI * user.getID()/length));
            user.cords.y = (int)(R + R * Math.sin(2 * Math.PI * user.getID()/length));
        }
    }
}
