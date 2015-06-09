package co835.vailskiwear.server;

import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.net.handler.PacketHandler;
import co835.vailskiwear.server.net.handler.impl.AddOrderHandler;
import co835.vailskiwear.server.net.handler.impl.AddVendorHandler;
import co835.vailskiwear.server.net.handler.impl.AdminLoginHandler;
import co835.vailskiwear.server.net.handler.impl.OrderStateHandler;
import co835.vailskiwear.server.net.handler.impl.RemoveOrderHandler;
import co835.vailskiwear.server.net.handler.impl.RemoveVendorHandler;
import co835.vailskiwear.server.net.handler.impl.UpdateVendorHandler;
import co835.vailskiwear.server.net.handler.impl.VendorLoginHandler;
import co835.vailskiwear.server.net.packet.Opcode;
import co835.vailskiwear.server.net.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter{

    private final Map<Opcode, PacketHandler> handlers;

    public ServerHandler(){
        handlers = new HashMap<>();
        handlers.put(Opcode.ADMIN_LOGIN, new AdminLoginHandler());
        handlers.put(Opcode.VENDOR_LOGIN, new VendorLoginHandler());
        handlers.put(Opcode.ADD_ORDER, new AddOrderHandler());
        handlers.put(Opcode.REMOVE_ORDER, new RemoveOrderHandler());
        handlers.put(Opcode.ORDER_STATE, new OrderStateHandler());
        handlers.put(Opcode.ADD_VENDOR, new AddVendorHandler());
        handlers.put(Opcode.REMOVE_VENDOR, new RemoveVendorHandler());
        handlers.put(Opcode.UPDATE_VENDOR, new UpdateVendorHandler());
    }

    public void handlerRemoved(final ChannelHandlerContext ctx){
        final Profile profile = ctx.attr(Profile.KEY).getAndRemove();
        if(profile == null)
            return;
        profile.ctx = null;
    }

    public void channelRead(final ChannelHandlerContext ctx, final Object msg){
        final Packet pkt = (Packet) msg;
        try{
            final PacketHandler handler = handlers.get(pkt.getOpcode());
            if(handler != null)
                handler.handle(ctx, pkt);
        }finally{
            pkt.release();
        }
    }

    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable err){
        err.printStackTrace();
        ctx.close();
    }
}
