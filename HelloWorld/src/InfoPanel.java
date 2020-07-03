import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class InfoPanel extends JPanel {
    private int x;
    private int y;
    public InfoPanel(User user, int x, int y, JFrame frame, JScrollPane scrollPane){
        if(user == null) return;
        setVisible(true);
        setLayout(null);
        addPhoto(user);
        addText(user);
        addButton(user, frame);
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
            pictureLabel.setBounds(0,110, 300, 350);
            add(pictureLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addText(User user){
        String str = "User: " + user.getName()
                + " " + user.getSurname() + "\nBirthday: " + user.getAge()
                + "\nNumber of friends: " + user.friendsNumber;
        JTextArea infoText = new JTextArea(str);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setBounds(0,10,300,100);
        infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
        JScrollPane scrollInfoText = new JScrollPane(infoText,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollInfoText.setBounds(0,10,300,100);
        add(scrollInfoText);
    }
    private void addButton(User user, JFrame frame){
        JButton showFriendsButton = new JButton("Show friends");
        showFriendsButton.addActionListener(e -> {
            InfoDialog w = new InfoDialog("Sending request...",  "", 400, 1);
            StringBuilder text = new StringBuilder();
            for(String id : user.friends){
                String [] userInfo = new String[0];
                try {
                    userInfo = User.getUserInfo(id);
                } catch (MyExceptions myExceptions) {
                    new WarningDialog(frame, "Error", true, myExceptions);
                }
                text.append(userInfo[0]);
                text.append(" ");
                text.append(userInfo[1]);
                text.append(", ");
            }
            String str = text.toString();
            str = str.substring(0, str.length() - 2);
            w.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            w = new InfoDialog(user.getName() + " "+ user.getSurname() + " friend List", str, 600, 600);
            w.setAlwaysOnTop(true);
        });
        showFriendsButton.setBounds(0,460, 300, 40);
        add(showFriendsButton);
    }

}
