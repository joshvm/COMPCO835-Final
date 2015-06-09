package co835.vailskiwear.server.net.packet;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.net.util.ResponseConstants;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class Packets implements ResponseConstants{

    private Packets(){}

    public static Packet response(final int id, final int response){
        return new PacketBuilder(Opcode.RESPONSE)
                .writeByte(id)
                .writeByte(response)
                .build();
    }

    public static Packet loginResponse(final int response){
        return response(LOGIN, response);
    }

    public static Packet addOrderResponse(final int response){
        return response(ADD_ORDER, response);
    }

    public static Packet removeOrderResponse(final int response){
        return response(REMOVE_ORDER, response);
    }

    public static Packet orderStateResponse(final int response){
        return response(ORDER_STATE, response);
    }

    public static Packet addVendorResponse(final int response){
        return response(ADD_VENDOR, response);
    }

    public static Packet removeVendorResponse(final int response){
        return response(REMOVE_VENDOR, response);
    }

    public static Packet updateVendorResponse(final int response){
        return response(UPDATE_VENDOR, response);
    }

    public static Packet updateVendor(final int vendorId, final int id, final String value){
        final PacketBuilder bldr = new PacketBuilder(Opcode.UPDATE_VENDOR);
        if(vendorId != -1)
            bldr.writeInt(vendorId);
        return bldr.writeByte(id)
                .writeString(value)
                .build();
    }

    public static Packet updateVendor(final int id, final String value){
        return updateVendor(-1, id, value);
    }

    public static Packet adminInit(final Admin admin){
        return new PacketBuilder(Opcode.ADMIN_INIT)
                .writeInt(admin.getId())
                .writeString(admin.getUser())
                .writeString(admin.getEmail())
                .writeString(admin.getPhoneNumber())
                .build();
    }

    public static Packet vendorInit(final Vendor vendor){
        return new PacketBuilder(Opcode.VENDOR_INIT)
                .writeInt(vendor.getId())
                .writeString(vendor.getUser())
                .writeString(vendor.getEmail())
                .writeString(vendor.getPhoneNumber())
                .writeString(vendor.getBillingAddress())
                .writeString(vendor.getShippingAddress())
                .build();
    }

    public static Packet addVendor(final Vendor vendor){
        return new PacketBuilder(Opcode.ADD_VENDOR)
                .writeInt(vendor.getId())
                .writeString(vendor.getUser())
                .writeString(vendor.getPass())
                .writeString(vendor.getEmail())
                .writeString(vendor.getPhoneNumber())
                .writeString(vendor.getBillingAddress())
                .writeString(vendor.getShippingAddress())
                .build();
    }

    public static Packet removeVendor(final Vendor vendor){
        return new PacketBuilder(Opcode.REMOVE_VENDOR)
                .writeInt(vendor.getId())
                .build();
    }

    public static Packet addOrder(final SalesOrder order, final boolean includeVendor){
        final PacketBuilder bldr = new PacketBuilder(Opcode.ADD_ORDER)
                .writeInt(order.getId())
                .writeLong(order.getDate().getTime())
                .writeByte(order.getState().ordinal());
        if(includeVendor)
            bldr.writeInt(order.getVendorId());
        bldr.writeByte(order.getItems().size());
        for(final SalesOrder.Item i : order.getItems())
            bldr.writeShort(i.getId())
                    .writeFloat(i.getPrice())
                    .writeInt(i.getQuantity());
        return bldr.build();
    }

    public static Packet removeOrder(final SalesOrder order){
        return new PacketBuilder(Opcode.REMOVE_ORDER)
                .writeInt(order.getId())
                .build();
    }

    public static Packet orderStateUpdate(final SalesOrder order){
        return new PacketBuilder(Opcode.ORDER_STATE)
                .writeInt(order.getId())
                .writeByte(order.getState().ordinal())
                .build();
    }

    public static Packet addInvoice(final Invoice invoice){
        return new PacketBuilder(Opcode.ADD_INVOICE)
                .writeInt(invoice.getId())
                .writeLong(invoice.getDate().getTime())
                .build();
    }
}
