package co835.vailskiwear.adminclient.net.handler.impl;

import co835.vailskiwear.adminclient.ClientWindow;
import co835.vailskiwear.adminclient.model.profile.Vendor;
import co835.vailskiwear.adminclient.model.profile.VendorManager;
import co835.vailskiwear.adminclient.net.handler.PacketHandler;
import co835.vailskiwear.adminclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddVendorHandler implements PacketHandler{

    public void handle(final Packet pkt){
        final int id = pkt.readInt();
        final String user = pkt.readString();
        final String pass = pkt.readString();
        final String email = pkt.readString();
        final String phoneNumber = pkt.readString();
        final String billingAddress = pkt.readString();
        final String shippingAddress = pkt.readString();
        final Vendor vendor = new Vendor(id, user, pass, email, phoneNumber, billingAddress, shippingAddress);
        VendorManager.add(vendor);
        ClientWindow.get().getVendorsList().add(vendor);
    }
}
