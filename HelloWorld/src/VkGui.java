import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.*;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class VkGui extends JFrame {
    private final JButton buildButton = new JButton("Build Graph");
    private final JButton infoButton = new JButton("INFO");
    private final JButton addButton = new JButton("Add user");
    private final JTextArea input = new JTextArea();
    private final JLabel label1 = new JLabel("Input all users'");
    private final JLabel label2 = new JLabel(" information:");
    private final JLabel label3 = new JLabel("Input info about new user");
    private final JCheckBox checkBoxOstov = new JCheckBox("Paint Ostov",false);
    private final JCheckBox checkBoxNonFriends = new JCheckBox("Paint Non Friends",false);
    private final JRadioButton radioButton1 = new JRadioButton("From file");
    private final JRadioButton radioButton2 = new JRadioButton("From keyboard");
    private final JPanel infoPanel = new JPanel();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final UserFields userFields = new UserFields();
    private GraphPanel panel = new GraphPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollInfoText = new JScrollPane();


    JLabel picLabel = new JLabel();
    private Graph graph = new Graph();
    private String inputString;
    private final DragImage dragImage = new DragImage();

    static class HelpPanel extends JFrame{
        HelpPanel(){
            super("INFO");
            this.setBounds(700,200, 500, 700);
            this.setVisible(true);
            this.setLayout(null);
            JTextArea info = new JTextArea();
            JLabel picture = new JLabel();
            info.setText("\tHello, this is high level program made by Luxury Genius Black Team " +
                    "subdivision of Black Lives Matter movement.\n\n" +
                    "It is our statement and direct message to everyone who is against our rights being heard.\n\n" +
                    "Main purpose of this IT product is to show all naive people how Social Networks really affect their lives. " +
                    "Simple graph visualisation can demonstrate your whole being.\n" +
                    "If you are ready to touch your fragile little inner world, read the instruction below.\n\n" +
                    "1)Choose input method(from file or from keyboard)\n" +
                    "2)Input data about Social Network users in such form" +
                    "\tNAME SURNAME AGE: FRIEND1_NAME FRIEND1_SURNAME, FRIEND2_NAME ...\n" +
                    "3)Press button BUILD GRAPH\n" +
                    "4)If you forgot somebody, you can add him or her by using field in right upper corner\n" +
                    "5)Now you can see nearly everything but if you want to go further press PAINT OSTOV\n" +
                    "6)Your life has changed forever");
            info.setLineWrap(true);
            info.setWrapStyleWord(true);
            info.setFont(new Font("Dialog", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBounds(0, 0, 485, 300);

            try {
                BufferedImage myPicture = ImageIO.read(new File("Power.png"));
                Image image = myPicture.getScaledInstance(400,400, 300);
                picture = new JLabel(new ImageIcon(image));
                picture.setBounds(45,300, 400, 400);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.getLayeredPane().add(picture, JLayeredPane.POPUP_LAYER);
            this.getLayeredPane().add(scrollPane, JLayeredPane.POPUP_LAYER);
            this.setAlwaysOnTop(true);
        }
    }
    static class DragImage{
        public int x;
        public int y;
    }
    static class UserFields{
        private final JLabel namel = new JLabel("Name");
        private final JLabel surnamel = new JLabel("Surname");
        private final JLabel agel = new JLabel("Age");
        private final JLabel friendsl = new JLabel("Friends");

        private final JTextField name = new JTextField();
        private final JTextField surname = new JTextField();
        private final JTextField age = new JTextField();
        private final JTextField friends = new JTextField();
    }
    public VkGui(){
        super("VK visualisation");
        this.setBounds(100, 100, 1430, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAllForms();
        new HelpPanel();
        Container container = this.getContentPane();
        container.setLayout(null);
        container.add(radioButton1);
        container.add(radioButton2);
        container.add(label1);
        container.add(label2);
        container.add(label3);
        container.add(input);
        container.add(buildButton);
        container.add(checkBoxOstov);
        container.add(checkBoxNonFriends);
        container.add(userFields.name);
        container.add(userFields.surname);
        container.add(userFields.age);
        container.add(userFields.friends);
        container.add(userFields.namel);
        container.add(userFields.surnamel);
        container.add(userFields.agel);
        container.add(userFields.friendsl);
        container.add(addButton);
        container.add(infoButton);
        container.add(scrollPane);
        container.add(layeredPane);
    }
    class mouserMotionListener implements MouseMotionListener{
        public void mouseDragged(MouseEvent e){
            int dx = dragImage.x - e.getLocationOnScreen().x;
            int dy = dragImage.y - e.getLocationOnScreen().y;
            scrollPane.getHorizontalScrollBar().setValue(dx);
            scrollPane.getVerticalScrollBar().setValue(dy);
        }
        public void mouseMoved(MouseEvent e){

        }
    }
    class mouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            User user = graph.getUser((int)(e.getX()/panel.zoomFactor),(int)((e.getY()- 100)/panel.zoomFactor) );
            try {
                showInfo(user, e.getX(), e.getY());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        public void mousePressed(MouseEvent e){
            dragImage.x =  e.getLocationOnScreen().x + scrollPane.getHorizontalScrollBar().getValue();
            dragImage.y =  e.getLocationOnScreen().y + scrollPane.getVerticalScrollBar().getValue();
        }
        public void mouseReleased(MouseEvent e){

        }
        public void mouseExited (MouseEvent e) {

        }
        public void mouseEntered (MouseEvent e){

        }
        public void showInfo(User user, int x, int y) throws IOException {
            infoPanel.remove(scrollInfoText);
            infoPanel.remove(picLabel);
            layeredPane.remove(infoPanel);
            layeredPane.repaint();
            if(user == null) return;
            infoPanel.setVisible(true);
            String picture = user.getName() + " " + user.getSurname() + ".png";
            if(new File(picture).exists()){
                BufferedImage myPicture = ImageIO.read(new File(picture));
                picLabel = new JLabel(new ImageIcon(myPicture));
                picLabel.setBounds(0,110, 300, 380);
            }
            else{
                picLabel = new JLabel("Picture not found");
                picLabel.setBounds(0,110, 300, 20);
            }

            StringBuilder text = new StringBuilder("Пользователь: " + user.getName()
                    + " " + user.getSurname() + "\nВозраст: " + user.getAge() + "\nДрузья: ");
            for(User user1 : user.friends){
                text.append(user1.getName());
                text.append(" ");
                text.append(user1.getSurname());
                text.append(", ");
            }
            String str = text.toString();
            str = str.substring(0, text.length() - 2);
            JTextArea infoText = new JTextArea(str);
            infoText.setLineWrap(true);
            infoText.setWrapStyleWord(true);
            infoPanel.setLayout(null);
            infoPanel.setBounds(x - scrollPane.getHorizontalScrollBar().getValue(),
                    y - scrollPane.getVerticalScrollBar().getValue(), 300, 500);
            infoText.setBounds(0,10,300,100);
            infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
            scrollInfoText = new JScrollPane(infoText,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollInfoText.setBounds(0,10,300,100);
            infoPanel.add(scrollInfoText);
            infoPanel.add(picLabel);
            layeredPane.add(infoPanel, new Integer(10));
            //SwingUtilities.updateComponentTreeUI(VkGui.this);

        }
    }
    class GraphPanel extends JPanel{
        public double zoomFactor = 1;
        public boolean zoomed = true;
        public AffineTransform at;
        Graphics2D g2;
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            panel.setPreferredSize(new Dimension((int)(graph.size * 3 * panel.zoomFactor),
                    (int)(graph.size * 3 * panel.zoomFactor) + 500));
            g2=(Graphics2D)g;
            if (zoomed) {
                scrollPane.getVerticalScrollBar().setValue(0);
                scrollPane.getHorizontalScrollBar().setValue(0);
                at = g2.getTransform();
                at.scale(zoomFactor, zoomFactor);
                zoomed = false;
            }
            g2.transform(at);
            g2.setColor(Color.BLACK);
            for(User user : graph.users.users){
                user.drawUser(g2);
            }
            for(Edge edge : graph.edges.edges){
                edge.drawEdge(g2);
            }
        }
        public void setZoomFactor(double factor){
            if(factor<this.zoomFactor && factor > 0.3){
                this.zoomFactor=this.zoomFactor/1.1;
            }
            else if(factor>this.zoomFactor && zoomFactor < 3){
                this.zoomFactor=factor;
            }
            this.zoomed =true;
        }
        public double getZoomFactor() {
            return zoomFactor;
        }
    }
    public String readFile(){
        StringBuilder text = new StringBuilder();
        try(FileReader reader = new FileReader("tests.txt"))
        {
            int c;
            while((c = reader.read()) != -1){
                text.append ((char) c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return  text.toString();
    }
    public void addUser(String str){
        graph.addUser(str);
        paintGraph();
    }
    public void buildGraph(){
        panel.zoomFactor = 1;
        panel.zoomed = true;
        inputString = radioButton1.isSelected() ? readFile() : input.getText();
        infoPanel.setVisible(false);
        paintGraph();
    }
    public void paintGraph(){
        graph = new Graph(inputString, checkBoxNonFriends.isSelected(), checkBoxOstov.isSelected());
        layeredPane.remove(scrollPane);
        panel = new GraphPanel();
        panel.addMouseMotionListener(new mouserMotionListener());
        panel.addMouseListener(new mouseClickListener());
        panel.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                panel.setZoomFactor(1.1 * panel.getZoomFactor());
                panel.revalidate();
                panel.repaint();
                SwingUtilities.updateComponentTreeUI(this);
            }
            if (e.getWheelRotation() > 0) {
                panel.setZoomFactor(panel.getZoomFactor() / 1.1);
                panel.revalidate();
                panel.repaint();
                SwingUtilities.updateComponentTreeUI(this);
            }
            infoPanel.setVisible(false);
        });
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0,0, 1400, 700);
        scrollPane.getHorizontalScrollBar().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                infoPanel.setVisible(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        scrollPane.getVerticalScrollBar().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                infoPanel.setVisible(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        layeredPane.add(scrollPane, new Integer(5));
        SwingUtilities.updateComponentTreeUI(this);
    }
    public void setAllForms(){
        infoButton.setBounds(1300, 5, 100, 50);
        label1.setBounds(25,45,80,10);
        label2.setBounds(25,57,80,10);
        label3.setBounds(1020,20,200,15);
        input.setBounds(110,30,450,50);
        input.setBackground(Color.LIGHT_GRAY);
        buildButton.setBounds(580,30,100,50);
        checkBoxNonFriends.setBounds(130,5,120,20);
        checkBoxOstov.setBounds(250,5,100,20);
        radioButton1.setBounds(350,5,80,20);
        radioButton2.setBounds(440,5,100,20);
        userFields.name.setBounds(780, 60, 100, 20);
        userFields.surname.setBounds(910, 60, 100, 20);
        userFields.age.setBounds(1040, 60, 100, 20);
        userFields.friends.setBounds(1170, 60, 100, 20);
        userFields.namel.setBounds(780, 45, 100, 12);
        userFields.surnamel.setBounds(910, 45, 100, 12);
        userFields.agel.setBounds(1040, 45, 100, 12);
        userFields.friendsl.setBounds(1170, 45, 100, 12);
        addButton.setBounds(1300, 60, 100, 20);
        layeredPane.setBounds(1,95, 1400, 700);

        buildButton.addActionListener(e -> buildGraph());
        addButton.addActionListener(e -> {
            String name = userFields.name.getText();
            String surname = userFields.surname.getText();
            String friends = userFields.friends.getText();
            String age = userFields.age.getText();
            addUser(name + " " + surname + " " + age + ": " + friends);
        });
        infoButton.addActionListener(e -> new HelpPanel());
        checkBoxOstov.addActionListener(e -> paintGraph());
        checkBoxNonFriends.addActionListener(e -> paintGraph());
        radioButton1.addActionListener(e -> {
            input.setEnabled(false);
            input.setBackground(Color.LIGHT_GRAY);
        });
        radioButton2.addActionListener(e -> {
            input.setEnabled(true);
            input.setBackground(Color.WHITE);
        });


        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        radioButton1.setSelected(true);
        checkBoxOstov.setSelected(true);
        checkBoxNonFriends.setSelected(true);
        input.setEnabled(false);
    }
}
