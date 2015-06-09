package co835.vailskiwear.vendorclient.net.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class PacketBuilder {
    
    private final Opcode opcode;
    private final DataOutputStream dos;
    private final ByteArrayOutputStream baos;
    
    public PacketBuilder(final Opcode opcode){
        this.opcode = opcode;
        
        baos = new ByteArrayOutputStream();
        dos = new DataOutputStream(baos);
    }
    
    public PacketBuilder writeByte(final int b){
        try{
            dos.writeByte(b);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public PacketBuilder writeShort(final int s){
        try{
            dos.writeShort(s);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public PacketBuilder writeInt(final int i){
        try{
            dos.writeInt(i);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return this;
    }
    
    public PacketBuilder writeString(final String s){
        writeShort(s.length());
        for(final char c : s.toCharArray())
            writeByte((byte)c);
        return this;
    }

    public Packet build(){
        final byte[] bytes = baos.toByteArray();
        final int outLength = opcode.getOutgoingLength();
        final int extra = outLength < 0 ? Math.abs(outLength) : 0;
        final ByteBuffer buf = ByteBuffer.allocate(1 + extra + bytes.length);
        buf.put((byte)opcode.getValue());
        switch(outLength){
            case -2:
                buf.putShort((short)bytes.length);
                break;
            case -1:
                buf.put((byte)bytes.length);
                break;
        }
        buf.put(bytes);
        return new Packet(opcode, buf);
    }
}
