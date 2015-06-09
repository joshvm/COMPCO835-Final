package co835.vailskiwear.server.net.packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public enum Opcode {

    RESPONSE(0, 2),
    ADMIN_LOGIN(-1, 0),
    VENDOR_LOGIN(-1, 0),
    ADMIN_INIT(0, -2),
    VENDOR_INIT(0, -2),
    ADD_ORDER(-2, -2),
    REMOVE_ORDER(4, 4),
    ORDER_STATE(5, 5),
    ADD_INVOICE(0, 12),
    ADD_VENDOR(-2, -2),
    REMOVE_VENDOR(4, 4),
    UPDATE_VENDOR(-2, -2);

    private final int incomingLength;
    private final int outgoingLength;

    private Opcode(final int incomingLength, final int outgoingLength){
        this.incomingLength = incomingLength;
        this.outgoingLength = outgoingLength;
    }

    public int getValue(){
        return ordinal();
    }

    public int getIncomingLength(){
        return incomingLength;
    }

    public int getOutgoingLength(){
        return outgoingLength;
    }

    public static Opcode get(final int i){
        return values()[i];
    }
}
