public class UsersContainer {
    public User[] users;
    private int length;

    public UsersContainer() {
        length = 0;
        users = new User[0];
    }

    public void addUser(String VkID) throws MyExceptions {
        if (notInContainer(VkID)) {
            length++;
            User[] newArr = new User[length];
            System.arraycopy(users, 0, newArr, 0, users.length);
            newArr[length - 1] = new User(VkID, length);
            users = newArr;
        }
    }

    public void addUser(User user) {
        if (notInContainer(user)) {
            length++;
            User[] newArr = new User[length];
            System.arraycopy(users, 0, newArr, 0, users.length);
            newArr[length - 1] = user;
            users = newArr;
        }
    }

    public int getLength() {
        return length;
    }

    public boolean notInContainer(User user) {
        for (User curr : users) {
            if (user.equals(curr))
                return false;
        }
        return true;
    }

    public boolean notInContainer(String VkID) {
        for (User curr : users) {
            if (curr.VkID.equals(VkID))
                return false;
        }
        return true;
    }

    public void buildUsers() {
        int R = (int) (length * 200 / 2 / Math.PI) * 2;
        for (User user : users) {
            user.cords.x = (int) (R + R * Math.cos(2 * Math.PI * user.getID() / length));
            user.cords.y = (int) (R + R * Math.sin(2 * Math.PI * user.getID() / length));
        }
    }
}
