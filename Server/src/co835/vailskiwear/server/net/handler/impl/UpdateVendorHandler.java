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
public class UpdateVendorHandler extends ProfilePacketHandler<Admin> {

    private static final int INVALID_VENDOR = 0;
    private static final int INVALID_FIELD = 1;
    private static final int INVALID_FIELD_VALUE = 2;
    private static final int ERROR = 3;
    private static final int SUCCESS = 4;

    private static final int PASS = 0;
    private static final int EMAIL = 1;
    private static final int PHONE_NUMBER = 2;
    private static final int BILLING_ADDRESS = 3;
    private static final int SHIPPING_ADDRESS = 4;

    public void handle(final Admin admin, final Packet pkt){
        final int vendorId = pkt.readInt();
        final int id = pkt.readByte();
        final String value = pkt.readString();
        final Vendor vendor = VendorManager.getInstance().get(vendorId);
        if(vendor == null){
            admin.send(Packets.updateVendorResponse(INVALID_VENDOR));
            return;
        }
        if(value.isEmpty()){
            admin.send(Packets.updateVendorResponse(INVALID_FIELD_VALUE));
            return;
        }
        boolean success;
        switch(id){
            case PASS:
                final String pass = vendor.getPass();
                vendor.setPass(value);
                if(!(success = VendorManager.getInstance().save()))
                    vendor.setPass(pass);
                break;
            case EMAIL:
                final String email = vendor.getEmail();
                vendor.setEmail(value);
                if(!(success = VendorManager.getInstance().save()))
                    vendor.setEmail(email);
                break;
            case PHONE_NUMBER:
                final String phoneNumber = vendor.getPhoneNumber();
                vendor.setPhoneNumber(value);
                if(!(success = VendorManager.getInstance().save()))
                    vendor.setPhoneNumber(phoneNumber);
                break;
            case BILLING_ADDRESS:
                final String billingAddress = vendor.getBillingAddress();
                vendor.setBillingAddress(value);
                if(!(success = VendorManager.getInstance().save()))
                    vendor.setBillingAddress(billingAddress);
                break;
            case SHIPPING_ADDRESS:
                final String shippingAddress = vendor.getShippingAddress();
                vendor.setShippingAddress(value);
                if(!(success = VendorManager.getInstance().save()))
                    vendor.setShippingAddress(shippingAddress);
                break;
            default:
                admin.send(Packets.updateVendorResponse(INVALID_FIELD));
                return;
        }
        if(!success){
            admin.send(Packets.updateVendorResponse(ERROR));
            return;
        }
        admin.send(Packets.updateVendorResponse(SUCCESS));
        vendor.send(Packets.updateVendor(id, value));
        for(final Admin a : AdminManager.getInstance().get())
            a.send(Packets.updateVendor(vendorId, id, value));
    }
}
