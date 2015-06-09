package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.itemdef.ItemDef;
import co835.vailskiwear.server.model.itemdef.ItemDefManager;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.net.handler.ProfilePacketHandler;
import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.packet.Packets;

import java.util.ArrayList;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddOrderHandler extends ProfilePacketHandler<Vendor> {

    private static final int INVALID_ITEM_COUNT = 0;
    private static final int INVALID_ITEM = 1;
    private static final int INVALID_QUANTITY = 2;
    private static final int SUCCESS = 3;
    private static final int ERROR = 4;

    public void handle(final Vendor vendor, final Packet pkt){
        final int itemCount = pkt.readShort();
        if(itemCount == 0){
            vendor.send(Packets.addOrderResponse(INVALID_ITEM_COUNT));
            return;
        }
        final List<SalesOrder.Item> items = new ArrayList<>(itemCount);
        for(int i = 0; i < itemCount; i++){
            final int id = pkt.readShort();
            final int quantity = pkt.readInt();
            final ItemDef def = ItemDefManager.getInstance().get(id);
            if(def == null){
                vendor.send(Packets.addOrderResponse(INVALID_ITEM));
                return;
            }
            if(quantity < 1){
                vendor.send(Packets.addOrderResponse(INVALID_QUANTITY));
                return;
            }
            final SalesOrder.Item item = new SalesOrder.Item(id, def.getPrice(), quantity);
            items.add(item);
        }
        final SalesOrder order = new SalesOrder(vendor.getId());
        order.getItems().addAll(items);
        if(!SalesOrderManager.getInstance().add(order, true)){
            vendor.send(Packets.addOrderResponse(ERROR));
            return;
        }
        vendor.send(Packets.addOrderResponse(SUCCESS));
        vendor.send(Packets.addOrder(order, false));
        for(final Admin admin : AdminManager.getInstance().get())
            admin.send(Packets.addOrder(order, true));
    }
}
