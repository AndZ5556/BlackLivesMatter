public class EdgesContainer {
    public Edge[] edges;
    private int length;

    public EdgesContainer() {
        length = 0;
        edges = new Edge[0];
    }

    public void addEdge(User u1, User u2) {
        Edge newEdge = new Edge(u1, u2);
        if (find(newEdge))
            return;
        length++;
        Edge[] newArr = new Edge[length];
        System.arraycopy(edges, 0, newArr, 0, edges.length);
        newArr[length - 1] = newEdge;
        edges = newArr;
    }

    public boolean find(Edge edge) {
        for (Edge value : edges) {
            if (value.equals(edge))
                return true;
        }
        return false;
    }

    public int buildEdges(int vertexNum) {
        int R = (int) (vertexNum * 200 / 2 / Math.PI) * 2 - 50;
        for (Edge edge : edges) {
            edge.cords.x1 = (int) (100 + R + R * Math.cos(2 * Math.PI * edge.user1.getID() / vertexNum));
            edge.cords.y1 = (int) (100 + R + R * Math.sin(2 * Math.PI * edge.user1.getID() / vertexNum));
            edge.cords.x2 = (int) (100 + R + R * Math.cos(2 * Math.PI * edge.user2.getID() / vertexNum));
            edge.cords.y2 = (int) (100 + R + R * Math.sin(2 * Math.PI * edge.user2.getID() / vertexNum));
            edge.cords.xText = edge.cords.x1 + (edge.cords.x2 - edge.cords.x1) / 3;
            edge.cords.yText = edge.cords.y1 + (edge.cords.y2 - edge.cords.y1) / 3;
        }
        return R;
    }

    public void buildWeights() {
        for (Edge edge : edges) {
            edge.weight = 0;
            for (int i = 0; i < edge.user1.friendsNumber; i++) {
                for (int j = 0; j < edge.user2.friendsNumber; j++) {
                    if (edge.user1.friends[i].equals(edge.user2.friends[j])) {
                        edge.weight++;
                    }
                }
            }
        }
    }

    public Edge findMaxEdge(User user, UsersContainer spanningTree, boolean nonFriends) {
        Edge maxEdge = new Edge();
        int max = -1;
        for (Edge edge : edges) {
            if (!nonFriends && !edge.isFriends) continue;
            if ((edge.user1.equals(user) && edge.weight > max && spanningTree.notInContainer(edge.user2))
                    || (edge.user2.equals(user) && edge.weight > max && spanningTree.notInContainer(edge.user1))) {
                max = edge.weight;
                maxEdge = edge;
            }
        }
        return maxEdge;
    }
}
