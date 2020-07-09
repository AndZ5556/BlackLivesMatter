public class Graph {
    public UsersContainer users;
    public EdgesContainer edges;
    public final boolean nonFriends;
    public final boolean spanningTree;

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
        public void addUser(String VkID) throws MyExceptions {
            try{
                Double.parseDouble(VkID);
            }
            catch (NumberFormatException e) {
                throw new MyExceptions("Incorrect ID format, only integer value");
            }
            users.addUser(VkID);
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
            addEdges();
            size = edges.buildEdges(users.getLength());
            edges.buildWeights();
            users.buildUsers();
            if(spanningTree)
            buildSpanningTree();
        }
            private void addEdges(){
                for(int i = 0; i < users.getLength() - 1; i++){
                    for(int j = i + 1; j < users.getLength(); j++){
                        edges.addEdge(users.users[i], users.users[j]);
                    }
                }
            }

            public void processText(String text) throws MyExceptions {
                if(text == null) return;
                String [] strings = text.split(" ");
                for(String str : strings){
                    try{
                        Double.parseDouble(str);
                    }
                    catch (NumberFormatException e) {
                        throw new MyExceptions("Incorrect ID format, only integer value");
                    }
                    users.addUser(str);
                }
            }
                private void buildSpanningTree(){
                    UsersContainer ostovUsers = new UsersContainer();
                    if(users.getLength() < 2) return;
                    ostovUsers.addUser(users.users[0]);
                    while(ostovUsers.getLength() < users.getLength()){
                        int max = -1;
                        Edge maxEdge = edges.edges[0];
                        User maxUser = ostovUsers.users[0];
                        for(User curr : ostovUsers.users){
                            Edge currEdge = edges.findMaxEdge(curr, ostovUsers, nonFriends);
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