package co835.vailskiwear.server.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class PacketBuilder {

    private final Opcode opcode;
    private final ByteBuf buf;

    public PacketBuilder(final Opcode opcode){
        this.opcode = opcode;

        buf = Unpooled.buffer();
    }

    public PacketBuilder writeByte(final int b){
        buf.writeByte(b);
        return this;
    }

    public PacketBuilder writeShort(final int s){
        buf.writeShort(s);
        return this;
    }

    public PacketBuilder writeInt(final int i){
        buf.writeInt(i);
        return this;
    }

    public PacketBuilder writeFloat(final double d){
        buf.writeFloat((float)d);
        return this;
    }

    public PacketBuilder writeLong(final long l){
        buf.writeLong(l);
        return this;
    }

    public PacketBuilder writeString(final String s){
        writeShort(s.length());
        for(final char c : s.toCharArray())
            writeByte((byte)c);
        return this;
    }

    public Packet build(){
        final int extra = opcode.getOutgoingLength() < 0 ? Math.abs(opcode.getOutgoingLength()) : 0;
        final ByteBuf out = Unpooled.buffer(1 + extra + buf.writerIndex());
        out.writeByte(opcode.getValue());
        switch(opcode.getOutgoingLength()){
            case -2:
                out.writeShort(buf.writerIndex());
                break;
            case -1:
                out.writeByte(buf.writerIndex());
                break;
        }
        out.writeBytes(buf, 0, buf.writerIndex());
        return new Packet(opcode, out);
    }
}
