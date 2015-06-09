package co835.vailskiwear.server.net.handler;

import co835.vailskiwear.server.net.packet.Packet;
import co835.vailskiwear.server.net.util.ResponseConstants;
import io.netty.channel.ChannelHandlerContext;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public interface PacketHandler extends ResponseConstants{

    public void handle(final ChannelHandlerContext ctx, final Packet pkt);
}
