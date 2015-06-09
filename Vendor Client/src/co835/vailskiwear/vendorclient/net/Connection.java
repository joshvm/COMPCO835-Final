package co835.vailskiwear.vendorclient.net;

import co835.vailskiwear.vendorclient.ClientWindow;
import co835.vailskiwear.vendorclient.LoginWindow;
import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.model.profile.Vendor;
import co835.vailskiwear.vendorclient.net.handler.PacketHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.AddInvoiceHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.AddOrderHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.InitHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.OrderStateHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.RemoveOrderHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.ResponseHandler;
import co835.vailskiwear.vendorclient.net.handler.impl.UpdateVendorHandler;
import co835.vailskiwear.vendorclient.net.packet.Opcode;
import co835.vailskiwear.vendorclient.net.packet.Packet;
import co835.vailskiwear.vendorclient.net.packet.PacketBuilder;
import co835.vailskiwear.vendorclient.ui.order.AddOrderForm;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Connection extends Thread implements Runnable{

    private static final Map<Opcode, PacketHandler> HANDLERS = new HashMap<>();

    static{
        HANDLERS.put(Opcode.RESPONSE, new ResponseHandler());
        HANDLERS.put(Opcode.VENDOR_INIT, new InitHandler());
        HANDLERS.put(Opcode.ADD_ORDER, new AddOrderHandler());
        HANDLERS.put(Opcode.ADD_INVOICE, new AddInvoiceHandler());
        HANDLERS.put(Opcode.REMOVE_ORDER, new RemoveOrderHandler());
        HANDLERS.put(Opcode.ORDER_STATE, new OrderStateHandler());
        HANDLERS.put(Opcode.UPDATE_VENDOR, new UpdateVendorHandler());
    }

    public static final String HOST = "localhost";
    public static final int PORT = 7495;

    private static Connection instance;

    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;

    private Connection(final Socket socket) throws Exception{
        this.socket = socket;

        out = new DataOutputStream(socket.getOutputStream());
        out.flush();

        in = new DataInputStream(socket.getInputStream());

        setPriority(MAX_PRIORITY);
        start();
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    private void close(){
        try{
            socket.close();
            in.close();
            out.close();
        }catch(Exception ex){}
    }

    public boolean send(final Packet pkt){
        try{
            out.write(pkt.getBytes());
            out.flush();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void run(){
        while(isConnected()){
            try{
                final Opcode opcode = Opcode.get(in.readByte());
                int length = opcode.getIncomingLength();
                switch(opcode.getIncomingLength()){
                    case -2:
                        if(in.available() >= 2)
                            length = in.readShort();
                        break;
                    case -1:
                        if(in.available() >= 1)
                            length = in.readByte();
                        break;
                }
                if(length < 0)
                    break;
                System.out.println("opcode: " + opcode);
                System.out.println("length: " + length);
                System.out.println("available: " + in.available());
                final byte[] bytes = new byte[length];
                in.readFully(bytes);
                final ByteBuffer buffer = ByteBuffer.wrap(bytes);
                final Packet packet = new Packet(opcode, buffer);
                final PacketHandler handler = HANDLERS.get(packet.getOpcode());
                if(handler != null){
                    System.out.println("handler: " + handler.getClass());
                    handler.handle(packet);
                }
            }catch(Exception ex){
                ex.printStackTrace();
                break;
            }
        }
        instance = null;
        AddOrderForm.close();
        ClientWindow.get().getVendorInfoPanel().update((Vendor) null);
        ClientWindow.get().getOrdersPanel().clear();
        ClientWindow.close();
        LoginWindow.get().info("You have been disconnected", Color.RED);
        LoginWindow.open();
    }

    private boolean doLogin(final String user, final String pass){
        return send(
                new PacketBuilder(Opcode.VENDOR_LOGIN)
                        .writeString(user)
                        .writeString(pass)
                        .build()
        );
    }

    private boolean doAddOrder(final List<SalesOrder.Item> items){
        final PacketBuilder bldr = new PacketBuilder(Opcode.ADD_ORDER);
        bldr.writeShort(items.size());
        for(final SalesOrder.Item item : items)
            bldr.writeShort(item.getId()).writeInt(item.getQuantity());
        return send(bldr.build());
    }

    private boolean doRemoveOrder(final int orderId){
        return send(
                new PacketBuilder(Opcode.REMOVE_ORDER)
                    .writeInt(orderId)
                    .build()
        );
    }

    private static Connection create(){
        try{
            return new Connection(new Socket(HOST, PORT));
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void disconnect(){
        if(instance != null)
            instance.close();
    }

    public static boolean connect(){
        return (instance = create()) != null;
    }

    public static boolean login(final String user, final String pass){
        if(instance == null)
            connect();
        return instance != null && instance.doLogin(user, pass);
    }

    public static boolean removeOrder(final int orderId){
        if(instance == null)
            connect();
        return instance != null && instance.doRemoveOrder(orderId);
    }

    public static boolean addOrder(final List<SalesOrder.Item> items){
        if(instance == null)
            connect();
        return instance != null && instance.doAddOrder(items);
    }
}
