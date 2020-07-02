import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JCheckBox checkBoxSpanningTree = new JCheckBox("Spanning Tree",false);
    private final JCheckBox checkBoxNonFriends = new JCheckBox("Paint Non Friends",false);
    private final JRadioButton radioButton1 = new JRadioButton("From file");
    private final JRadioButton radioButton2 = new JRadioButton("From keyboard");
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final UserFields userFields = new UserFields();
    private final GraphPanel graphPanel = new GraphPanel();
    private String inputString;

    static class UserFields{
        private final JLabel nameLabel = new JLabel("Name");
        private final JLabel surnameLabel = new JLabel("Surname");
        private final JLabel ageLabel = new JLabel("Age");
        private final JLabel friendsLabel = new JLabel("Friends");

        private final JTextField name = new JTextField();
        private final JTextField surname = new JTextField();
        private final JTextField age = new JTextField();
        private final JTextField friends = new JTextField();
    }

    class BuildButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            inputString = radioButton1.isSelected() ? readFile() : input.getText();
            graphPanel.paint(VkGui.this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected());
        }
        private String readFile(){
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
    }
    class AddButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userFields.name.getText();
            String surname = userFields.surname.getText();
            System.out.println(name.length());
            if (name.length() == 0 || surname.length() == 0){
                new WarningDialog(VkGui.this, "Ошибка", true, "Некорректные входные данные: отутствие имени или фамилии пользователя");
                return;
            }
            String friends = userFields.friends.getText();
            String age = userFields.age.getText();
            graphPanel.addUser(name + " " + surname + " " + age +": " + friends);
            graphPanel.paint(VkGui.this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected());
        }
    }

    public VkGui(){
        super("VK visualisation");
        this.setBounds(100, 100, 1430, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAllFormsBounds();
        setAllButtonsAndCheckBoxes();
        addAllForms();
        new HelpPanel();
    }
    private void setAllButtonsAndCheckBoxes(){
        buildButton.addActionListener(new BuildButtonActionListener());
        addButton.addActionListener(new AddButtonActionListener());
        infoButton.addActionListener(e -> new HelpPanel());
        checkBoxSpanningTree.addActionListener(e -> graphPanel.paint(this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected()));
        checkBoxNonFriends.addActionListener(e -> graphPanel.paint(this, inputString, checkBoxNonFriends.isSelected(), checkBoxSpanningTree.isSelected()));
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
        checkBoxSpanningTree.setSelected(true);
        checkBoxNonFriends.setSelected(true);
    }
    private void setAllFormsBounds(){
        infoButton.setBounds(1300, 5, 100, 50);
        label1.setBounds(25,45,80,10);
        label2.setBounds(25,57,80,10);
        label3.setBounds(1020,20,200,15);
        input.setBounds(110,30,450,50);
        input.setBackground(Color.LIGHT_GRAY);
        buildButton.setBounds(580,30,100,50);
        checkBoxNonFriends.setBounds(130,5,120,20);
        checkBoxSpanningTree.setBounds(250,5,100,20);
        radioButton1.setBounds(350,5,80,20);
        radioButton2.setBounds(440,5,100,20);
        userFields.name.setBounds(780, 60, 100, 20);
        userFields.surname.setBounds(910, 60, 100, 20);
        userFields.age.setBounds(1040, 60, 100, 20);
        userFields.friends.setBounds(1170, 60, 100, 20);
        userFields.nameLabel.setBounds(780, 45, 100, 12);
        userFields.surnameLabel.setBounds(910, 45, 100, 12);
        userFields.ageLabel.setBounds(1040, 45, 100, 12);
        userFields.friendsLabel.setBounds(1170, 45, 100, 12);
        addButton.setBounds(1300, 60, 100, 20);
        layeredPane.setBounds(1,95, 1400, 700);
        input.setEnabled(false);
    }
    private void addAllForms(){
        Container container = this.getContentPane();
        container.setLayout(null);
        container.add(radioButton1);
        container.add(radioButton2);
        container.add(label1);
        container.add(label2);
        container.add(label3);
        container.add(input);
        container.add(buildButton);
        container.add(checkBoxSpanningTree);
        container.add(checkBoxNonFriends);
        container.add(userFields.name);
        container.add(userFields.surname);
        container.add(userFields.age);
        container.add(userFields.friends);
        container.add(userFields.nameLabel);
        container.add(userFields.surnameLabel);
        container.add(userFields.ageLabel);
        container.add(userFields.friendsLabel);
        container.add(addButton);
        container.add(infoButton);
        container.add(layeredPane);
    }
    @Override
    public JLayeredPane getLayeredPane(){
        return layeredPane;
    }
}
