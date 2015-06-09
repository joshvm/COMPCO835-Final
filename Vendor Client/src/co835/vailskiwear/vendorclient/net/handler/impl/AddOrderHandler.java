package co835.vailskiwear.vendorclient.net.handler.impl;

import co835.vailskiwear.vendorclient.ClientWindow;
import co835.vailskiwear.vendorclient.VendorApplication;
import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.net.handler.PacketHandler;
import co835.vailskiwear.vendorclient.net.packet.Packet;

import java.util.Date;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddOrderHandler implements PacketHandler{

    public void handle(final Packet pkt){
        final int orderId = pkt.readInt();
        final Date date = new Date(pkt.readLong());
        final SalesOrder.State state = SalesOrder.State.values()[pkt.readByte()];
        final SalesOrder order = new SalesOrder(orderId, date, state);
        final int items = pkt.readByte();
        for(int i = 0; i < items; i++){
            final int id = pkt.readShort();
            final double price = pkt.readFloat();
            final int quantity = pkt.readInt();
            final SalesOrder.Item item = new SalesOrder.Item(id, price, quantity);
            order.getItems().add(item);
        }
        VendorApplication.getVendor().addOrder(order);
        ClientWindow.get().getOrdersPanel().add(order);
    }
}
