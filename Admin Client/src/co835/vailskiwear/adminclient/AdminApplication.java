package co835.vailskiwear.adminclient;

import co835.vailskiwear.adminclient.model.itemdef.ItemDefManager;
import co835.vailskiwear.adminclient.model.profile.Admin;
import co835.vailskiwear.adminclient.ui.itemdef.ItemsManagerWindow;
import co835.vailskiwear.adminclient.ui.vendor.AddVendorForm;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;
import org.pushingpixels.substance.api.skin.SkinInfo;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AdminApplication {

    private static Admin admin;

    public static void main(String[] args){
        final KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(
                new KeyEventDispatcher() {

                    private final SkinInfo[] skins = SubstanceLookAndFeel.getAllSkins().values().toArray(new SkinInfo[0]);
                    private int i;

                    public boolean dispatchKeyEvent(KeyEvent e){
                        if(e.getID() != KeyEvent.KEY_PRESSED)
                            return false;
                        if(e.getKeyCode() == KeyEvent.VK_LEFT){
                            if(i < 0)
                                i = skins.length-1;
                            final SkinInfo info = skins[i--];
                            SwingUtilities.invokeLater(
                                    new Runnable(){
                                        public void run(){
                                            SubstanceLookAndFeel.setSkin(info.getClassName());
                                        }
                                    }
                            );
                        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                            if(i > skins.length-1)
                                i = 0;
                            final SkinInfo info = skins[i++];
                            SwingUtilities.invokeLater(
                                    new Runnable(){
                                        public void run(){
                                            SubstanceLookAndFeel.setSkin(info.getClassName());
                                        }
                                    }
                            );
                        }
                        return false;
                    }
                }
        );
        ItemDefManager.load();
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        JFrame.setDefaultLookAndFeelDecorated(true);
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        SubstanceLookAndFeel.setSkin(new BusinessBlackSteelSkin());
                        ItemsManagerWindow.init();
                        AddVendorForm.init();
                        ClientWindow.init();
                        LoginWindow.init();
                        LoginWindow.open();
                    }
                }
        );
    }

    public static Admin getAdmin(){
        return admin;
    }

    public static void setAdmin(final Admin admin){
        AdminApplication.admin = admin;
        ClientWindow.get().setTitle(String.format("Vail Skiwear - Admin Client - Logged In As %s (ID: %d)", admin.getUser(), admin.getId()));
    }
}
