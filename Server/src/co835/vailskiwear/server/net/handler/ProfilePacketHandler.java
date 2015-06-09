package co835.vailskiwear.server.net.handler;

import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.net.packet.Packet;
import io.netty.channel.ChannelHandlerContext;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public abstract class ProfilePacketHandler<T extends Profile> implements PacketHandler{

    public void handle(final ChannelHandlerContext ctx, final Packet pkt){
        final Profile profile = ctx.attr(Profile.KEY).get();
        if(profile != null)
            handle((T)profile, pkt);
    }

    public abstract void handle(final T profile, final Packet pkt);
}
