package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import co835.vailskiwear.server.net.handler.ProfilePacketHandler;
import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.packet.Packets;

import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class RemoveVendorHandler extends ProfilePacketHandler<Admin> {

    private static final int INVALID_VENDOR = 1;
    private static final int ERROR = 2;
    private static final int SUCCESS = 3;

    public void handle(final Admin admin, final Packet pkt){
        final int vendorId = pkt.readInt();
        final Vendor vendor = VendorManager.getInstance().get(vendorId);
        if(vendor == null){
            admin.send(Packets.removeVendorResponse(INVALID_VENDOR));
            return;
        }
        final List<SalesOrder> orders = vendor.getOrders();
        for(final SalesOrder order : orders){
            SalesOrderManager.getInstance().remove(order, false);
            final Invoice invoice = order.getInvoice();
            if(invoice != null)
                InvoiceManager.getInstance().remove(invoice, false);
        }
        if(!SalesOrderManager.getInstance().save() || !InvoiceManager.getInstance().save()){
            for(final SalesOrder order : orders){
                SalesOrderManager.getInstance().add(order, false);
                final Invoice invoice = order.getInvoice();
                if(invoice != null)
                    InvoiceManager.getInstance().add(invoice, false);
            }
            SalesOrderManager.getInstance().save();
            InvoiceManager.getInstance().save();
            admin.send(Packets.removeVendorResponse(ERROR));
            return;
        }
        if(!VendorManager.getInstance().remove(vendor, true)){
            admin.send(Packets.removeVendorResponse(ERROR));
            return;
        }
        admin.send(Packets.removeVendorResponse(SUCCESS));
        vendor.disconnect();
        for(final Admin a : AdminManager.getInstance().get())
            a.send(Packets.removeVendor(vendor));
    }
}
