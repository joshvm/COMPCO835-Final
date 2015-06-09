package co835.vailskiwear.adminclient.net.handler.impl;

import co835.vailskiwear.adminclient.ClientWindow;
import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.model.order.SalesOrderManager;
import co835.vailskiwear.adminclient.model.profile.Vendor;
import co835.vailskiwear.adminclient.net.handler.PacketHandler;
import co835.vailskiwear.adminclient.net.packet.Packet;

import java.util.Date;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddOrderHandler implements PacketHandler {

    public void handle(final Packet pkt){
        final int orderId = pkt.readInt();
        final Date date = new Date(pkt.readLong());
        final SalesOrder.State state = SalesOrder.State.values()[pkt.readByte()];
        final int vendorId = pkt.readInt();
        final SalesOrder order = new SalesOrder(orderId, vendorId, date, state);
        final int items = pkt.readByte();
        for(int i = 0; i < items; i++){
            final int id = pkt.readShort();
            final double price = pkt.readFloat();
            final int quantity = pkt.readInt();
            final SalesOrder.Item item = new SalesOrder.Item(id, price, quantity);
            order.getItems().add(item);
        }
        SalesOrderManager.add(order);
        final Vendor selected = ClientWindow.get().getVendorsList().getSelected();
        if(selected != null && selected.getId() == vendorId)
            ClientWindow.get().getOrdersPanel().add(order);
    }
}
