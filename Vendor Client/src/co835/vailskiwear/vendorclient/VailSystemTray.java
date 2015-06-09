package co835.vailskiwear.vendorclient;

import res.Res;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VailSystemTray {

    private static TrayIcon icon;

    public static void init(){
        if(!SystemTray.isSupported())
            return;
        icon = new TrayIcon(Res.LOGO.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH), "Vail Skiwear");
        try{
            SystemTray.getSystemTray().add(icon);
        }catch(AWTException e){
            e.printStackTrace();
        }
    }

    public static void notify(final String format, final Object... args){
        if(icon != null)
            icon.displayMessage("Vail Skiwear", String.format(format, args), TrayIcon.MessageType.INFO);
    }
}
