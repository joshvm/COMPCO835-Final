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
public class RemoveOrderHandler implements PacketHandler{

    public void handle(final Packet pkt){
        final int orderId = pkt.readInt();
        final SalesOrder order = VendorApplication.getVendor().getOrder(orderId);
        if(order == null)
            return;
        VailSystemTray.notify("Order #%d has been removed", order.getId());
        VendorApplication.getVendor().removeOrder(order);
        ClientWindow.get().getOrdersPanel().remove(order);
    }
}
