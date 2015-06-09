package co835.vailskiwear.adminclient.net.handler.impl;

import co835.vailskiwear.adminclient.AdminApplication;
import co835.vailskiwear.adminclient.ClientWindow;
import co835.vailskiwear.adminclient.LoginWindow;
import co835.vailskiwear.adminclient.model.profile.Admin;
import co835.vailskiwear.adminclient.net.handler.PacketHandler;
import co835.vailskiwear.adminclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class InitHandler implements PacketHandler {

    public void handle(final Packet pkt){
        final int id = pkt.readInt();
        final String user = pkt.readString();
        final String email = pkt.readString();
        final String phoneNumber = pkt.readString();
        final Admin admin = new Admin(id, user, email, phoneNumber);
        LoginWindow.close();
        ClientWindow.open();
        AdminApplication.setAdmin(admin);
    }
}
