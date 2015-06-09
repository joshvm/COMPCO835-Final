package co835.vailskiwear.server;

import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.itemdef.ItemDefManager;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.admin.AdminManager;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import co835.vailskiwear.server.net.packet.codec.PacketDecoder;
import co835.vailskiwear.server.net.packet.codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Server {

    private static final String HOST = "localhost";
    private static final int PORT = 7495;

    private static void start() throws Exception{
        final EventLoopGroup boss = new NioEventLoopGroup();
        final EventLoopGroup worker = new NioEventLoopGroup();
        final ServerBootstrap bs = new ServerBootstrap();
        bs.group(boss, worker);
        bs.channel(NioServerSocketChannel.class);
        bs.childHandler(new ChannelInitializer<SocketChannel>(){
            public void initChannel(final SocketChannel ch){
                ch.pipeline().addLast("encoder", new PacketEncoder());
                ch.pipeline().addLast("decoder", new PacketDecoder());
                ch.pipeline().addLast("handler", new ServerHandler());
            }
        });
        try{
            bs.bind(HOST, PORT).sync().channel().closeFuture().sync();
        }finally{
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        ItemDefManager.getInstance().load();
        SalesOrderManager.getInstance().load();
        InvoiceManager.getInstance().load();
        AdminManager.getInstance().load();
        VendorManager.getInstance().load();
        System.out.printf("started server on %s:%d\n", HOST, PORT);
        start();
    }
}
