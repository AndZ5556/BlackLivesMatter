import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.*;
import java.io.FileReader;
import java.io.IOException;
import  javax.swing.*;

public class VkGui extends JFrame {
    private final JButton buildButton = new JButton("Build Graph");
    private final JButton addButton = new JButton("Add user");
    private final JTextArea input = new JTextArea();
    private final JLabel label1 = new JLabel("Input all users'");
    private final JLabel label2 = new JLabel(" information:");
    private final JLabel label3 = new JLabel("Input info about new user");
    private final JCheckBox checkBoxOstov = new JCheckBox("Paint Ostov",false);
    private final JCheckBox checkBoxNonFriends = new JCheckBox("Paint Non Friends",false);
    private final JRadioButton radioButton1 = new JRadioButton("From file");
    private final JRadioButton radioButton2 = new JRadioButton("From keyboard");
    private JPanel infoPanel = new JPanel();
    private GraphPanel panel = new GraphPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private JScrollPane scrollInfoText = new JScrollPane();
    private UserFields userFields = new UserFields();
    private JPanel warningPanel = new JPanel();
    JLabel picLabel = new JLabel();
    private Graph graph = new Graph();

    class UserFields{
        private JLabel namel = new JLabel("Name");
        private JLabel surnamel = new JLabel("Surname");
        private JLabel agel = new JLabel("Age");
        private JLabel friendsl = new JLabel("Friends");

        private JTextField name = new JTextField();
        private JTextField surname = new JTextField();
        private JTextField age = new JTextField();
        private JTextField friends = new JTextField();
        private JButton addButton = new JButton();
    }


    public VkGui(){
        super("VK visualisation");
        this.setBounds(100, 100, 1430, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAllForms();

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
        //buildGraph();
    }
    class mouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            User user = graph.getUser(e.getX(),e.getY());
            //System.out.println(getX() + " " + getY());
            try {
                showInfo(user);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        public void mousePressed(MouseEvent e){
            return;
        }
        public void mouseReleased(MouseEvent e){
            return;
        }
        public void mouseExited (MouseEvent e) {
            return;
        }
        public void mouseEntered (MouseEvent e){
            return;
        }
        public void showInfo(User user) throws IOException {
            infoPanel.remove(scrollInfoText);
            infoPanel.remove(picLabel);
            panel.remove(infoPanel);
            SwingUtilities.updateComponentTreeUI(VkGui.this);
            if(user == null) return;
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


            String text = "Пользователь: " + user.getName() + " " + user.getSurname() + "\nВозраст: " + user.getAge() + "\nДрузья: ";
            for(User user1 : user.friends){
                text += user1.getName() + " " + user1.getSurname();
                text += ", ";
            }
            text = text.substring(0, text.length() - 2);
            JTextArea infoText = new JTextArea(text);
            infoText.setLineWrap(true);
            infoText.setWrapStyleWord(true);
            infoPanel.setLayout(null);
            infoPanel.setBounds(user.cords.x+110, user.cords.y, 300, 500);
            infoText.setBounds(0,10,300,100);
            infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
            scrollInfoText = new JScrollPane(infoText,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollInfoText.setBounds(0,10,300,100);
            infoPanel.add(scrollInfoText);
            infoPanel.add(picLabel);
            panel.add(infoPanel);
            SwingUtilities.updateComponentTreeUI(VkGui.this);
        }
    }
    class GraphPanel extends JPanel{
        public double zoomFactor = 1;
        public boolean zoomer = false;
        public AffineTransform at;

        Graphics2D g2;
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g2=(Graphics2D)g;
            at = g2.getTransform();
            if (zoomer == true) {
                at.scale(zoomFactor, zoomFactor);
                zoomer = false;
                g2.transform(at);
            }


            g2.setColor(Color.BLACK);
            for(User user : graph.users.users){
                user.drawUser(g2);
            }
            for(Edge edge : graph.edges.edges){
                edge.drawEdge(g2);
            }
        }
        public void setZoomFactor(double factor){

            if(factor<this.zoomFactor){
                this.zoomFactor=this.zoomFactor/1.1;
            }
            else{
                this.zoomFactor=factor;
            }
            this.zoomer=true;
        }
        public double getZoomFactor() {
            return zoomFactor;
        }
    }

    public String readFile(){
        String text = "";
        try(FileReader reader = new FileReader("tests.txt"))
        {
            int c;
            while((c = reader.read()) != -1){
                text += (char) c;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return  text;
    }
    public void addUser(String str) {
        try {
            graph.addUser(str);
        } catch (MyExceptions myEx) {
            JDialog dialog = createDialog("Ошибка", true);
            JLabel errLab = new JLabel(myEx.getMessage());
            dialog.add(errLab);
            dialog.setVisible(true);
        }
        paintGraph();
    }
    private JDialog createDialog(String title, boolean modal)
    {
        JDialog dialog = new JDialog(this, title, modal);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(600, 120);
        return dialog;
    }
    public void buildGraph() {
        String text = radioButton1.isSelected() ? readFile() : input.getText();
        try {
            graph = new Graph(text, checkBoxNonFriends.isSelected(), checkBoxOstov.isSelected());
        }
        catch (MyExceptions myEx){
            JDialog dialog = createDialog("Ошибка", true);
            JLabel errLab = new JLabel(myEx.getMessage());
            dialog.add(errLab);
            dialog.setVisible(true);
        }
        paintGraph();
    }
    public void paintGraph(){
        getContentPane().remove(scrollPane);
        panel = new GraphPanel();

        panel.setPreferredSize(new Dimension(graph.size * 3, graph.size * 3));
        System.out.println(panel.getSize());
        //panel.setBounds(1,95, 1400, 900);
        panel.setLayout(null);
        panel.addMouseListener(new mouseClickListener());
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    panel.setZoomFactor(1.1 * panel.getZoomFactor());
                    panel.revalidate();
                    panel.repaint();
                }
                if (e.getWheelRotation() > 0) {
                    panel.setZoomFactor(panel.getZoomFactor() / 1.1);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(1,95, 1400, 700);

        getContentPane().add(scrollPane);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setAllForms(){
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

        buildButton.addActionListener(e -> buildGraph());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userFields.name.getText();
                String surname = userFields.surname.getText();
                System.out.println(name.length());
                if (name.length() == 0 || surname.length() == 0){
                    JDialog dialog = createDialog("Ошибка", true);
                    JLabel errLab = new JLabel("Некорректные входные данные: отутствие имени или фамилии пользователя");
                    dialog.add(errLab);
                    dialog.setVisible(true);
                    return;
                }
                String friends = userFields.friends.getText();
                String age = userFields.age.getText();
                addUser(name + " " + surname + ": " + friends);
            }
        });
        checkBoxOstov.addActionListener(e -> buildGraph());
        checkBoxNonFriends.addActionListener(e -> buildGraph());
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
