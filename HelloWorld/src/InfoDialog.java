import javax.swing.*;
import java.awt.*;

public class InfoDialog extends JFrame {
    public InfoDialog(String title, String message, int width, int height){
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JTextArea infoText = new JTextArea(message);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setFont(new Font("Dialog", Font.PLAIN, 14));
        infoText.setEditable(false);
        JScrollPane scrollInfoText = new JScrollPane(infoText,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollInfoText.setBounds(0,0,width-15,height-40);
        add(scrollInfoText);
        setVisible(true);
        this.setBounds(700, 200, width, height);
    }
}
