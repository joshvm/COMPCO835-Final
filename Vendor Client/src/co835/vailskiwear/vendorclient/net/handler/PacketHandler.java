package co835.vailskiwear.vendorclient.net.handler;

import co835.vailskiwear.vendorclient.net.packet.Packet;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public interface PacketHandler {

    public void handle(final Packet pkt);
}
