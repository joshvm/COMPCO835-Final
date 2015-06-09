package co835.vailskiwear.adminclient.net.handler.impl;

import co835.vailskiwear.adminclient.LoginWindow;
import co835.vailskiwear.adminclient.net.handler.PacketHandler;
import co835.vailskiwear.adminclient.net.packet.Packet;
import co835.vailskiwear.adminclient.net.util.ResponseConstants;
import co835.vailskiwear.adminclient.ui.vendor.AddVendorForm;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ResponseHandler implements PacketHandler, ResponseConstants {

    public void handle(final Packet pkt){
        final int id = pkt.readByte();
        final int response = pkt.readByte();
        switch(id){
            case LOGIN:
                LoginWindow.get().handleResponse(response);
                break;
            case ADD_ORDER:

                break;
            case REMOVE_ORDER:

                break;
            case ADD_VENDOR:
                AddVendorForm.get().handleResponse(response);
                break;
        }
    }
}
