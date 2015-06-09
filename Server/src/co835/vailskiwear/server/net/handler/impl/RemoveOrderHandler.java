package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.net.handler.ProfilePacketHandler;
import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.packet.Packets;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class RemoveOrderHandler extends ProfilePacketHandler{

    private static final int INVALID_ORDER = 0;
    private static final int INVALID_STATE = 1;
    private static final int SUCCESS = 3;
    private static final int ERROR = 4;

    public void handle(final Profile profile, final Packet pkt){
        if(profile instanceof Admin)
            handleAdmin((Admin)profile, pkt);
        else
            handleVendor((Vendor)profile, pkt);
    }

    private void handleAdmin(final Admin admin, final Packet pkt){
        final int orderId = pkt.readInt();
        final SalesOrder order = SalesOrderManager.getInstance().get(orderId);
        if(order == null || order.getVendorId() != admin.getId()){
            admin.send(Packets.removeOrderResponse(INVALID_ORDER));
            return;
        }
        final Invoice invoice = order.getInvoice();
        if(invoice != null && !InvoiceManager.getInstance().remove(invoice, true)){
            admin.send(Packets.removeOrderResponse(ERROR));
            return;
        }
        if(!SalesOrderManager.getInstance().remove(order, true)){
            admin.send(Packets.removeOrderResponse(ERROR));
            return;
        }
        admin.send(Packets.removeOrderResponse(SUCCESS));
        order.getVendor().send(Packets.removeOrder(order));
        for(final Admin a : AdminManager.getInstance().get())
            a.send(Packets.removeOrder(order));
    }

    private void handleVendor(final Vendor vendor, final Packet pkt){
        final int orderId = pkt.readInt();
        final SalesOrder order = SalesOrderManager.getInstance().get(orderId);
        if(order == null || order.getVendorId() != vendor.getId()){
            vendor.send(Packets.removeOrderResponse(INVALID_ORDER));
            return;
        }
        if(order.getState() != SalesOrder.State.PENDING){
            vendor.send(Packets.removeOrderResponse(INVALID_STATE));
            return;
        }
        if(!SalesOrderManager.getInstance().remove(order, true)){
            vendor.send(Packets.removeOrderResponse(ERROR));
            return;
        }
        vendor.send(Packets.removeOrderResponse(SUCCESS));
        vendor.send(Packets.removeOrder(order));
        for(final Admin admin : AdminManager.getInstance().get())
            admin.send(Packets.removeOrder(order));
    }
}
