package co835.vailskiwear.vendorclient.net.handler.impl;

import co835.vailskiwear.vendorclient.ClientWindow;
import co835.vailskiwear.vendorclient.VailSystemTray;
import co835.vailskiwear.vendorclient.VendorApplication;
import co835.vailskiwear.vendorclient.model.profile.Vendor;
import co835.vailskiwear.vendorclient.net.handler.PacketHandler;
import co835.vailskiwear.vendorclient.net.packet.Packet;
import co835.vailskiwear.vendorclient.ui.util.UIUtils;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class UpdateVendorHandler implements PacketHandler{

    private static final int PASS = 0;
    private static final int EMAIL = 1;
    private static final int PHONE_NUMBER = 2;
    private static final int BILLING_ADDRESS = 3;
    private static final int SHIPPING_ADDRESS = 4;

    public void handle(final Packet pkt){
        final int id = pkt.readByte();
        final String value = pkt.readString();
        final Vendor vendor = VendorApplication.getVendor();
        switch(id){
            case PASS:
                UIUtils.info("Password Change", "Your password has been changed!");
                break;
            case EMAIL:
                vendor.setEmail(value);
                VailSystemTray.notify("Email changed: %s", value);
                break;
            case PHONE_NUMBER:
                vendor.setPhoneNumber(value);
                VailSystemTray.notify("Phone Number changed: %s", value);
                break;
            case BILLING_ADDRESS:
                vendor.setBillingAddress(value);
                VailSystemTray.notify("Billing address changed: %s", value);
                break;
            case SHIPPING_ADDRESS:
                vendor.setShippingAddress(value);
                VailSystemTray.notify("Shipping address changed: %s", value);
                break;
        }
        ClientWindow.get().getVendorInfoPanel().update(vendor);
    }
}
