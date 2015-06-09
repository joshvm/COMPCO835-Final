package co835.vailskiwear.server.net.packet.codec;

import co835.vailskiwear.server.net.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class PacketEncoder extends MessageToByteEncoder<Packet>{

    public void encode(final ChannelHandlerContext ctx, final Packet packet, final ByteBuf out){
        out.writeBytes(packet.getPayload());
    }
}
