import com.sun.deploy.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

public class InfoPanel extends JPanel {
    private int x;
    private int y;
    public InfoPanel(User user, int x, int y, JFrame frame, JScrollPane scrollPane){
        if(user == null) return;
        setVisible(true);
        setLayout(null);
        addPhoto(user);
        addText(user);
        this.x = x;
        this.y = y;
        refresh(scrollPane);
    }
    public InfoPanel(){
        super();
    }
    public void refresh(JScrollPane scrollPane){
        if(y < 500)
            setBounds(x - scrollPane.getHorizontalScrollBar().getValue(),
                    y - scrollPane.getVerticalScrollBar().getValue(), 300, 500);
        else
            setBounds(x - scrollPane.getHorizontalScrollBar().getValue(),
                    y - scrollPane.getVerticalScrollBar().getValue() - 500, 300, 500);
    }
    private void addPhoto(User user){
        URL url;
        try {
            url = new URL(user.getPhoto());
            BufferedImage c = ImageIO.read(url);
            JLabel pictureLabel = new JLabel(new ImageIcon(c));
            pictureLabel.setBounds(0,160, 300, 400);
            add(pictureLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addText(User user){
        String str = "User: " + user.getName()
                + " " + user.getSurname() + "\nBirthday: " + user.getAge()
                + "\nNumber of friends: " + user.friendsNumber + "\nFriends: ";
        str += User.getUserFriendsList(user.VkID);
        JTextArea infoText = new JTextArea(str);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setBounds(0,10,300,200);
        infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
        JScrollPane scrollInfoText = new JScrollPane(infoText,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollInfoText.setBounds(0,10,300,150);
        add(scrollInfoText);
    }
}
