import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowEvent;

public class UsersList {
    static private InfoDialog dialog = new InfoDialog();
    static private JScrollPane listScroll = new JScrollPane();
    static private JList<String> list = new JList<>();
    static private JFrame frameMain;

    static class SelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) return;
            dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            dialog = new InfoDialog(list.getSelectedValue(), frameMain.getX() + 100, frameMain.getY() + 127, 315, 600);
        }
    }

    static public void update(JFrame frame, String inputString) {
        frameMain = frame;
        inputString = inputString.replaceAll("[ ]+", " ");
        frame.getContentPane().remove(listScroll);
        list = new JList<>(inputString.split(" "));
        list.setBounds(0, 95, 100, 660);
        list.addListSelectionListener(new SelectionListener());
        listScroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScroll.setBounds(0, 95, 100, 660);
        frame.getContentPane().add(listScroll);
        listScroll.revalidate();
        listScroll.repaint();
    }
}
