package co835.vailskiwear.adminclient.net.handler.impl;

import co835.vailskiwear.adminclient.ClientWindow;
import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.model.order.SalesOrderManager;
import co835.vailskiwear.adminclient.model.profile.Vendor;
import co835.vailskiwear.adminclient.net.handler.PacketHandler;
import co835.vailskiwear.adminclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderStateHandler implements PacketHandler {

    public void handle(final Packet pkt){
        final int orderId = pkt.readInt();
        final SalesOrder.State state = SalesOrder.State.values()[pkt.readByte()];
        final SalesOrder order = SalesOrderManager.get(orderId);
        if(order == null)
            return;
        order.setState(state);
        ClientWindow.get().getOrdersPanel().getList().refresh();
        final Vendor selected = ClientWindow.get().getVendorsList().getSelected();
        if(selected != null && selected.getId() == order.getVendorId())
            ClientWindow.get().getOrdersPanel().update(order);
    }
}
