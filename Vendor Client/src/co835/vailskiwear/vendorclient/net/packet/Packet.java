package co835.vailskiwear.vendorclient.net.packet;

import java.nio.ByteBuffer;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Packet {

    private final Opcode opcode;
    private final ByteBuffer buf;

    public Packet(final Opcode opcode, final ByteBuffer buf){
        this.opcode = opcode;
        this.buf = buf;
    }

    public Opcode getOpcode(){
        return opcode;
    }

    public byte[] getBytes(){
        return buf.array();
    }

    public int readByte(){
        return buf.get();
    }

    public int readShort(){
        return buf.getShort();
    }

    public int readInt(){
        return buf.getInt();
    }

    public double readFloat(){
        return buf.getFloat();
    }

    public long readLong(){
        return buf.getLong();
    }

    public String readString(){
        final int length = readShort();
        final StringBuilder bldr = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            bldr.append((char)readByte());
        return bldr.toString();
    }

}
