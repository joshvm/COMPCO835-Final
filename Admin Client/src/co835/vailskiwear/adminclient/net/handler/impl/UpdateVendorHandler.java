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
public class UpdateVendorHandler implements PacketHandler{

    public static final int PASS = 0;
    public static final int EMAIL = 1;
    public static final int PHONE_NUMBER = 2;
    public static final int BILLING_ADDRESS = 3;
    public static final int SHIPPING_ADDRESS = 4;

    public void handle(final Packet pkt){
        final int vendorId = pkt.readInt();
        final int id = pkt.readByte();
        final String value = pkt.readString();
        final Vendor vendor = VendorManager.get(vendorId);
        if(vendor == null)
            return;
        switch(id){
            case PASS:
                vendor.setPass(value);
                break;
            case EMAIL:
                vendor.setEmail(value);
                break;
            case PHONE_NUMBER:
                vendor.setPhoneNumber(value);
                break;
            case BILLING_ADDRESS:
                vendor.setBillingAddress(value);
                break;
            case SHIPPING_ADDRESS:
                vendor.setShippingAddress(value);
                break;
        }
        if(vendorId == ClientWindow.get().getVendorInfoPanel().getCurrentVendorId())
            ClientWindow.get().getVendorInfoPanel().update(vendor);
    }
}
