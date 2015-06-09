package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import co835.vailskiwear.server.net.handler.PacketHandler;
import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.packet.Packets;
import co835.vailskiwear.server.net.util.LoginConstants;
import io.netty.channel.ChannelHandlerContext;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VendorLoginHandler implements PacketHandler, LoginConstants{

    public void handle(final ChannelHandlerContext ctx, final Packet pkt){
        final String user = pkt.readString();
        final String pass = pkt.readString();
        final Vendor vendor = VendorManager.getInstance().get(user);
        if(vendor == null){
            ctx.writeAndFlush(Packets.loginResponse(INVALID_USER));
            return;
        }
        if(!vendor.getPass().equals(pass)){
            ctx.writeAndFlush(Packets.loginResponse(INVALID_PASS));
            return;
        }
        if(vendor.isOnline()){
            ctx.writeAndFlush(Packets.loginResponse(ALREADY_LOGGED_IN));
            return;
        }
        ctx.writeAndFlush(Packets.loginResponse(SUCCESS));
        ctx.attr(Profile.KEY).set(vendor);
        vendor.ctx = ctx;
        vendor.sendInit();
        vendor.sendOrders();
        vendor.sendInvoices();
    }
}
