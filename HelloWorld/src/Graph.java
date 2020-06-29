public class Graph {
    public UsersContainer users;
    public EdgesContainer edges;
    private boolean nonFriends;
    private boolean ostov;
    public int size;

    public Graph(){
        users = new UsersContainer();
        edges = new EdgesContainer();
        nonFriends = true;
        ostov = true;
    }
    public Graph(String text, boolean nonFriends, boolean ostov){
        users = new UsersContainer();
        edges = new EdgesContainer();
        this.nonFriends = nonFriends;
        this.ostov = ostov;
        processText(text);
        buildGraph();
    }
    public void addUser(String str){
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
        if(ostov)
            buildOstov();
    }
    private void addNonFriends(){
        for(int i = 0; i < users.getLength() - 1; i++){
            for(int j = i + 1; j < users.getLength(); j++){
                edges.addEdge(users.users[i], users.users[j], false);
            }
        }
    }
    private void addUserInfo(String str){
        if(str == "") return;
        String [] userAndFriends = str.split(":");
        if(userAndFriends.length < 2) return;
        String [] userName = userAndFriends[0].split(" ");
        User user = userName.length < 3 ? users.addUser(userName[0], userName[1], "")
                : users.addUser(userName[0], userName[1], userName[2]);
        String [] friends = userAndFriends[1].split(",");
        User [] userFriends = new User[friends.length];
        for(int i = 0, j = 0; i < userFriends.length; i++){
            if(friends[i].replaceAll("[^А-Яа-яA-Za-z0-9]", "").length() < 1) continue;
            String [] friend = friends[i].split(" ");
            while (friend[j].length() < 1) j++;
            String name = friend[j]; j++;
            while (friend[j].length() < 1) j++;
            String surname = friend[j]; j++;
            User newFriend = users.addUser(name, surname, "");
            user.addFriend(newFriend);
            edges.addEdge(user, newFriend, true);
            j = 0;
        }

    }
    private void processText(String text){
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
            maxEdge.isInOstov = true;
            ostovUsers.addUser(maxUser);
        }
    }
}
