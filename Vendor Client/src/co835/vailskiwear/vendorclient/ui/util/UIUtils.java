package co835.vailskiwear.vendorclient.ui.util;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class UIUtils {

    private UIUtils(){}

    private static void showMessage(final int type, final String title, final String msg){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        JOptionPane.showMessageDialog(null, msg, title, type);
                    }
                }
        );
    }

    public static void info(final String title, final String msg){
        showMessage(JOptionPane.INFORMATION_MESSAGE, title, msg);
    }

    public static void error(final String title, final String msg){
        showMessage(JOptionPane.ERROR_MESSAGE, title, msg);
    }
}
