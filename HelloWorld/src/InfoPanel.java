import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class InfoPanel extends JPanel {
    private int x;
    private int y;

    public InfoPanel(String VkID, int x, int y, JScrollPane scrollPane) {
        setVisible(true);
        setLayout(null);
        addInfo(VkID);
        this.x = x;
        this.y = y;
        refresh(scrollPane);
    }

    public InfoPanel() {
        super();
    }

    public void refresh(JScrollPane scrollPane) {
        if (y < 500)
            setBounds(x - scrollPane.getHorizontalScrollBar().getValue(),
                    y - scrollPane.getVerticalScrollBar().getValue(), 300, 500);
        else
            setBounds(x - scrollPane.getHorizontalScrollBar().getValue(),
                    y - scrollPane.getVerticalScrollBar().getValue() - 500, 300, 500);
    }

    private void addInfo(String VkID) {
        try {
            String[] userInfo = User.getUserInfo(VkID);
            URL url;
            try {
                url = new URL(userInfo[3].replaceAll("\\\\", ""));
                BufferedImage c = ImageIO.read(url);
                JLabel pictureLabel = new JLabel(new ImageIcon(c));
                pictureLabel.setBounds(0, 160, 300, 400);
                add(pictureLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String str = "User: " + userInfo[0]
                    + " " + userInfo[1] + "\nBirthday: " + userInfo[2]
                    + "\nNumber of friends: ";
            String friends = User.getUserFriendsList(VkID);
            if (friends.split(",").length > 1)
                str += friends.split(",").length + "\nFriends: " + friends;
            else
                str += "UNKNOWN" + "\nFriends: " + friends;
            JTextArea infoText = new JTextArea(str);
            infoText.setLineWrap(true);
            infoText.setWrapStyleWord(true);
            infoText.setBounds(0, 10, 300, 200);
            infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
            JScrollPane scrollInfoText = new JScrollPane(infoText,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollInfoText.setBounds(0, 10, 300, 150);
            add(scrollInfoText);
        } catch (MyExceptions myExceptions) {
            myExceptions.printStackTrace();
        }
    }
}
