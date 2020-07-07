import javax.swing.*;

public class VK {

    public static void main(String[] args) throws ClassNotFoundException,  InstantiationException, IllegalAccessException, ClassCastException, UnsupportedLookAndFeelException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        VkGui myGui = new VkGui();
        myGui.setVisible(true);
     }
}

