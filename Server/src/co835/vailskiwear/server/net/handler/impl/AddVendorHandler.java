package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import co835.vailskiwear.server.net.handler.ProfilePacketHandler;
import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.packet.Packets;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddVendorHandler extends ProfilePacketHandler<Admin> {

    private static final int INVALID_USER = 0;
    private static final int INVALID_PASS = 1;
    private static final int INVALID_EMAIL = 2;
    private static final int INVALID_PHONE_NUMBER = 3;
    private static final int INVALID_BILLING_ADDRESS = 4;
    private static final int INVALID_SHIPPING_ADDRESS = 5;
    private static final int USER_TAKEN = 6;
    private static final int SUCCESS = 7;
    private static final int ERROR = 8;

    public void handle(final Admin admin, final Packet pkt){
        final String user = pkt.readString();
        final String pass = pkt.readString();
        final String email = pkt.readString();
        final String phoneNumber = pkt.readString();
        final String billingAddress = pkt.readString();
        final String shippingAddress = pkt.readString();
        if(user.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_USER));
            return;
        }
        if(pass.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_PASS));
            return;
        }
        if(email.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_EMAIL));
            return;
        }
        if(phoneNumber.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_PHONE_NUMBER));
            return;
        }
        if(billingAddress.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_BILLING_ADDRESS));
            return;
        }
        if(shippingAddress.isEmpty()){
            admin.send(Packets.addVendorResponse(INVALID_SHIPPING_ADDRESS));
            return;
        }
        if(VendorManager.getInstance().get(user) != null){
            admin.send(Packets.addVendorResponse(USER_TAKEN));
            return;
        }
        final Vendor vendor = new Vendor(user, pass, email, phoneNumber, billingAddress, shippingAddress);
        if(!VendorManager.getInstance().add(vendor, true)){
            admin.send(Packets.addVendorResponse(ERROR));
            return;
        }
        admin.send(Packets.addVendorResponse(SUCCESS));
        for(final Admin a : AdminManager.getInstance().get())
            a.send(Packets.addVendor(vendor));
    }
}
