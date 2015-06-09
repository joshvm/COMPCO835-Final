package co835.vailskiwear.server.net.packet;

import io.netty.buffer.ByteBuf;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Packet {

    private final Opcode opcode;
    private final ByteBuf buf;

    public Packet(final Opcode opcode, final ByteBuf buf){
        this.opcode = opcode;
        this.buf = buf;
    }

    public Opcode getOpcode(){
        return opcode;
    }

    public ByteBuf getPayload(){
        return buf;
    }

    public int readByte(){
        return buf.readByte();
    }

    public int readShort(){
        return buf.readShort();
    }

    public int readInt(){
        return buf.readInt();
    }

    public float readFloat(){
        return buf.readFloat();
    }

    public double readDouble(){
        return buf.readDouble();
    }

    public long readLong(){
        return buf.readLong();
    }

    public String readString(){
        final int length = readShort();
        final StringBuilder bldr = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            bldr.append((char)readByte());
        return bldr.toString();
    }

    public void release(){
        buf.release();
    }
}
