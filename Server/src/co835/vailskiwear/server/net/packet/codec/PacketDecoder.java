package co835.vailskiwear.server.net.packet.codec;

import co835.vailskiwear.server.net.packet.Opcode;
import co835.vailskiwear.server.net.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class PacketDecoder extends ByteToMessageDecoder{

    public void decode(final ChannelHandlerContext ctx, final ByteBuf buf, final List<Object> out){
        while(buf.readableBytes() > 0){
            final int idx = buf.readerIndex();
            final Opcode opcode = Opcode.get(buf.readByte());
            if(opcode == null)
                continue;
            int length = opcode.getIncomingLength();
            switch(length){
                case -2:
                    if(buf.readableBytes() >= 2)
                        length = buf.readShort();
                    break;
                case -1:
                    if(buf.readableBytes() >= 1)
                        length = buf.readByte();
                    break;
            }
            if(length < 0 || buf.readableBytes() < length){
                buf.readerIndex(idx);
                break;
            }
            out.add(new Packet(opcode, buf.readBytes(length)));
        }
    }
}
