public class Graph {
    public UsersContainer users;
    public EdgesContainer edges;
    private final boolean nonFriends;
    private final boolean spanningTree;
    public int size;

    public Graph(){
        users = new UsersContainer();
        edges = new EdgesContainer();
        nonFriends = true;
        spanningTree = true;
    }
    public Graph(String text, boolean nonFriends, boolean spanningTree) throws MyExceptions {
        users = new UsersContainer();
        edges = new EdgesContainer();
        this.nonFriends = nonFriends;
        this.spanningTree = spanningTree;
        processText(text);
        buildGraph();
    }
    public void addUser(String str) throws MyExceptions {
        addUserInfo(str);
        buildGraph();
    }
    public User getUser(int x, int y){
        for(User user : users.users){
            if((Math.pow((user.cords.x + 50 - x), 2) + Math.pow((user.cords.y + 50 - y), 2)) < 50 * 50)
                return user;
        }
        return null;
    }

    private void buildGraph(){
        if(nonFriends)
            addNonFriends();
        size = edges.buildEdges(users.getLength());
        edges.buildWeights();
        users.buildUsers();
        if(spanningTree)
            buildOstov();
    }
    private void addNonFriends(){
        for(int i = 0; i < users.getLength() - 1; i++){
            for(int j = i + 1; j < users.getLength(); j++){
                edges.addEdge(users.users[i], users.users[j], false);
            }
        }
    }
    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    private void addUserInfo(String str) throws MyExceptions {
        if(str.equals("")) return;
        String [] userAndFriends = str.split(":");
        if(userAndFriends.length < 2) return;
        String [] userName = userAndFriends[0].split(" ");
        if (userName.length < 3 && (isNumeric(userName[0]) || isNumeric(userName[1]))){
            throw new MyExceptions("Некорректные входные данные: отстутствие имени или фамилии пользователя " + userName[0] + " " + userName[1]);
        }
        User user = userName.length < 3 ? users.addUser(userName[0], userName[1], "")
                : users.addUser(userName[0], userName[1], userName[2]);
        String [] friends = userAndFriends[1].split(",");
        User [] userFriends = new User[friends.length];
        for(int i = 0, j = 0; i < userFriends.length; i++){
            if(friends[i].replaceAll("[^А-Яа-яA-Za-z]", "").length() < 1) continue;
            String [] friend = friends[i].split(" ");
            if (friend.length < 3){
                throw new MyExceptions("Некорректные входные данные: отстутствие имени или фамилии " + (i+1) + "-го друга пользователя " + userName[0] + " " + userName[1]);
            }
            while (friend[j].length() < 1) j++;
            String name = friend[j]; j++;
            while (friend[j].length() < 1) j++;
            String surname = friend[j];
            User newFriend = users.addUser(name, surname, "");
            boolean isFriend2Exist = false;
            for (int k = 0; k < user.friendsNumber; k++){
                if (user.friends[k].getName().equals(newFriend.getName()) && user.friends[k].getSurname().equals(newFriend.getSurname())){
                    isFriend2Exist = true;
                    break;
                }
            }
            if (!isFriend2Exist) {
                user.addFriend(newFriend);
            }
            boolean isFriend1Exist = false;
            for (int k = 0; k < newFriend.friendsNumber; k++){
                if (newFriend.friends[k].getName().equals(user.getName()) && newFriend.friends[k].getSurname().equals(user.getSurname())){
                    isFriend1Exist = true;
                    break;
                }
            }
            if (!isFriend1Exist){
                newFriend.addFriend(user);
            }
            edges.addEdge(user, newFriend, true);
            j = 0;
        }

    }
    private void processText(String text) throws MyExceptions {
        String [] strings = text.split("\n");
        for(String str : strings){
            addUserInfo(str);
        }
    }
    private void buildOstov(){
        UsersContainer ostovUsers = new UsersContainer();
        if(users.getLength() < 2) return;
        ostovUsers.addUser(users.users[0]);
        while(ostovUsers.getLength() < users.getLength()){
            int max = -1;
            Edge maxEdge = edges.edges[0];
            User maxUser = ostovUsers.users[0];
            for(User curr : ostovUsers.users){
                Edge currEdge = edges.findMaxEdge(curr, ostovUsers);
                if(currEdge.weight > max){
                    max = currEdge.weight;
                    maxEdge = currEdge;
                    maxUser = currEdge.connectWith(curr);
                }
            }
            if(max == -1) break;
            maxEdge.isInSpanningTree = true;
            ostovUsers.addUser(maxUser);
        }
    }
}
