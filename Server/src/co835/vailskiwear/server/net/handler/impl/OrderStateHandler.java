package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
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
public class OrderStateHandler extends ProfilePacketHandler<Admin>{

    private static final int INVALID_ORDER = 0;
    private static final int INVALID_STATE = 1;
    private static final int SUCCESS = 2;
    private static final int ERROR = 3;

    public void handle(final Admin admin, final Packet pkt){
        final int orderId = pkt.readInt();
        final int stateIdx = pkt.readByte();
        final SalesOrder order = SalesOrderManager.getInstance().get(orderId);
        if(order == null){
            admin.send(Packets.orderStateResponse(INVALID_ORDER));
            return;
        }
        final SalesOrder.State state = SalesOrder.State.values()[stateIdx];
        if(state == null || order.getState() == state){
            admin.send(Packets.orderStateResponse(INVALID_STATE));
            return;
        }
        final SalesOrder.State oldState = order.getState();
        order.setState(state);
        if(!SalesOrderManager.getInstance().save()){
            order.setState(oldState);
            admin.send(Packets.orderStateResponse(ERROR));
            return;
        }
        if(order.getState() == SalesOrder.State.APPROVED){
            final Invoice invoice = new Invoice(order.getId());
            if(!InvoiceManager.getInstance().add(invoice, true)){
                admin.send(Packets.orderStateResponse(ERROR));
                return;
            }
        }
        admin.send(Packets.orderStateResponse(SUCCESS));
        final Invoice invoice = order.getInvoice();
        for(final Admin a : AdminManager.getInstance().get()){
            a.send(Packets.orderStateUpdate(order));
            if(invoice != null)
                a.send(Packets.addInvoice(invoice));
        }
        final Vendor vendor = order.getVendor();
        vendor.send(Packets.orderStateUpdate(order));
        if(invoice != null)
            vendor.send(Packets.addInvoice(invoice));
    }
}
