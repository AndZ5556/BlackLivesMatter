import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HelpPanel extends JFrame {
    HelpPanel(){
        super("HELP");
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
        info.setEditable(false);
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