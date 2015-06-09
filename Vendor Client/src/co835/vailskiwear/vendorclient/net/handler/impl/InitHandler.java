package co835.vailskiwear.vendorclient.net.handler.impl;

import co835.vailskiwear.vendorclient.ClientWindow;
import co835.vailskiwear.vendorclient.LoginWindow;
import co835.vailskiwear.vendorclient.VendorApplication;
import co835.vailskiwear.vendorclient.model.profile.Vendor;
import co835.vailskiwear.vendorclient.net.handler.PacketHandler;
import co835.vailskiwear.vendorclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class InitHandler implements PacketHandler{

    public void handle(final Packet pkt){
        final int id = pkt.readInt();
        final String user = pkt.readString();
        final String email = pkt.readString();
        final String phoneNumber = pkt.readString();
        final String billingAddress = pkt.readString();
        final String shippingAddress = pkt.readString();
        final Vendor vendor = new Vendor(id, user, email, phoneNumber, billingAddress, shippingAddress);
        LoginWindow.close();
        ClientWindow.open();
        VendorApplication.setProfile(vendor);
    }
}
