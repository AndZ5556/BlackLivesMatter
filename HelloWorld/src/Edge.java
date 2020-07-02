import java.awt.*;

public class Edge {
    public boolean isFriends;
    public boolean isInSpanningTree;
    public User user1;
    public User user2;
    public int weight;
    public Cords cords;
    public static class Cords{
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int xText;
        public int yText;
    }
    public Edge(User u1, User u2, boolean isFriends){
        this.isInSpanningTree = false;
        this.isFriends = isFriends;
        user1 = u1;
        user2 = u2;
        weight = 0;
        cords = new Cords();
    }
    public Edge(){
        user1 = new User();
        user2 = new User();
        this.isInSpanningTree = false;
        this.isFriends = false;
        weight = -1;
        cords = new Cords();
    }
    public boolean equals(Edge edge){
        return ((this.user1.equals(edge.user1)  && this.user2.equals(edge.user2))
                || (this.user2.equals(edge.user1) && this.user1.equals(edge.user2)));
    }
    public void drawEdge(Graphics2D g2){
        if(!isFriends) {
            float[] dashl = {5,5};
            BasicStroke pen = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,10, dashl,0);
            g2.setStroke(pen);
        }
        else {
            BasicStroke pen = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,0);
            g2.setStroke(pen);
        }
        if(isInSpanningTree)
            g2.setColor(Color.RED);
        else
            g2.setColor(Color.BLACK);
        g2.drawLine(cords.x1, cords.y1, cords.x2, cords.y2);
        g2.drawString(Integer.toString(weight), cords.xText, cords.yText);
    }
    public User connectWith(User user){
        if(user.equals(user1))
            return user2;
        if(user.equals(user2))
            return user1;
        System.out.println(user1.getSurname() + " " + user2.getSurname() + " " + user.getSurname());
        return null;
    }
}
