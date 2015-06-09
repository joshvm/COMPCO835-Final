package co835.vailskiwear.vendorclient.net.handler.impl;

import co835.vailskiwear.vendorclient.ClientWindow;
import co835.vailskiwear.vendorclient.VailSystemTray;
import co835.vailskiwear.vendorclient.VendorApplication;
import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.net.handler.PacketHandler;
import co835.vailskiwear.vendorclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderStateHandler implements PacketHandler{

    public void handle(final Packet pkt){
        final int orderId = pkt.readInt();
        final SalesOrder.State state = SalesOrder.State.values()[pkt.readByte()];
        final SalesOrder order = VendorApplication.getVendor().getOrder(orderId);
        if(order == null)
            return;
        order.setState(state);
        VailSystemTray.notify("Order #%d is now %s", order.getId(), state);
        ClientWindow.get().getOrdersPanel().getList().refresh();
        ClientWindow.get().getOrdersPanel().update(order);
    }
}
