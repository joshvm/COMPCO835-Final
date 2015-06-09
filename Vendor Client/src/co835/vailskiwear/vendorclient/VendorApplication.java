package co835.vailskiwear.vendorclient;

import co835.vailskiwear.vendorclient.model.itemdef.ItemDefManager;
import co835.vailskiwear.vendorclient.model.profile.Vendor;
import co835.vailskiwear.vendorclient.ui.order.AddOrderForm;
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
public class VendorApplication {

    private static Vendor vendor;

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
                                i = skins.length - 1;
                            final SkinInfo info = skins[i--];
                            SwingUtilities.invokeLater(
                                    new Runnable() {
                                        public void run(){
                                            SubstanceLookAndFeel.setSkin(info.getClassName());
                                        }
                                    }
                            );
                        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                            if(i > skins.length - 1)
                                i = 0;
                            final SkinInfo info = skins[i++];
                            SwingUtilities.invokeLater(
                                    new Runnable() {
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
                        VailSystemTray.init();
                        AddOrderForm.init();
                        ClientWindow.init();
                        LoginWindow.init();
                        LoginWindow.open();
                    }
                }
        );
    }

    public static Vendor getVendor(){
        return vendor;
    }

    public static void setProfile(final Vendor vendor){
        VendorApplication.vendor = vendor;
        ClientWindow.get().setTitle(String.format("Vail Skiwear - Vendor Client - Logged In As %s (ID: %d)", vendor.getUser(), vendor.getId()));
        ClientWindow.get().getVendorInfoPanel().update(vendor);
        ClientWindow.get().getOrdersPanel().update(vendor);
    }
}
