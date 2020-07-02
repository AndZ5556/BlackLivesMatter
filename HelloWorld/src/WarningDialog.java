import javax.swing.*;

class WarningDialog extends JDialog {
    public WarningDialog(java.awt.Frame owner, String title, boolean modal, Exception e){
        super(owner, title, modal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600,120);
        setLocationRelativeTo(null);
        add(new JLabel(e.getMessage()));
        setVisible(true);
    }
    public WarningDialog(java.awt.Frame owner, String title, boolean modal, String message){
        super(owner, title, modal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600,120);
        setLocationRelativeTo(null);
        add(new JLabel(message));
        setVisible(true);
    }
}
