package co835.vailskiwear.server.net.handler.impl;

import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.model.profile.admin.Admin;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
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
public class AdminLoginHandler implements PacketHandler, LoginConstants{

    public void handle(final ChannelHandlerContext ctx, final Packet pkt){
        final String user = pkt.readString();
        final String pass = pkt.readString();
        final Admin admin = AdminManager.getInstance().get(user);
        if(admin == null){
            ctx.writeAndFlush(Packets.loginResponse(INVALID_USER));
            return;
        }
        if(!admin.getPass().equals(pass)){
            ctx.writeAndFlush(Packets.loginResponse(INVALID_PASS));
            return;
        }
        if(admin.isOnline()){
            ctx.writeAndFlush(Packets.loginResponse(ALREADY_LOGGED_IN));
            return;
        }
        ctx.writeAndFlush(Packets.loginResponse(SUCCESS));
        ctx.attr(Profile.KEY).set(admin);
        admin.ctx = ctx;
        admin.sendInit();
        admin.sendVendors();
        admin.sendOrders();
        admin.sendInvoices();
    }
}
