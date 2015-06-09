package co835.vailskiwear.vendorclient;

import co835.vailskiwear.vendorclient.net.Connection;
import co835.vailskiwear.vendorclient.ui.order.AddOrderForm;
import co835.vailskiwear.vendorclient.ui.order.OrdersPanel;
import co835.vailskiwear.vendorclient.ui.vendor.VendorInfoPanel;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ClientWindow extends JFrame implements ActionListener{

    private static ClientWindow instance;

    private final JButton addOrderButton;
    private final JButton logoutButton;

    private final VendorInfoPanel vendorInfoPanel;
    private final OrdersPanel ordersPanel;

    public ClientWindow(){
        super("Vail Skiwear - Vendor Client");
        setIconImage(Res.LOGO.getImage());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(final WindowEvent e){
                        Connection.disconnect();
                    }
                }
        );
        setLayout(new BorderLayout());

        addOrderButton = new JButton("Add Order", Res.ORDER_ADD_32);
        addOrderButton.setHorizontalTextPosition(JLabel.CENTER);
        addOrderButton.setVerticalTextPosition(JLabel.BOTTOM);
        addOrderButton.addActionListener(this);

        logoutButton = new JButton("Logout", Res.LOGOUT_32);
        logoutButton.setHorizontalTextPosition(JLabel.CENTER);
        logoutButton.setVerticalTextPosition(JLabel.BOTTOM);
        logoutButton.addActionListener(this);

        final JLabel creditsLabel = new JLabel("<html>Developed By: Josh Maione (000320309)<br><center>Tested By: Jesse Hilton (000322425)</center></html>", JLabel.CENTER);
        creditsLabel.setFont(creditsLabel.getFont().deriveFont(20f));

        final JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(addOrderButton);
        top.add(Box.createHorizontalGlue());
        top.add(creditsLabel);
        top.add(Box.createHorizontalGlue());
        top.add(logoutButton);

        vendorInfoPanel = new VendorInfoPanel();

        ordersPanel = new OrdersPanel();

        final JPanel container = new JPanel(new BorderLayout());
        container.add(vendorInfoPanel, BorderLayout.NORTH);
        container.add(ordersPanel, BorderLayout.CENTER);

        final JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(5, 5, 5, 5));
        center.add(top, BorderLayout.NORTH);
        center.add(container, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        setSize(790, 970);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(source.equals(addOrderButton)){
            AddOrderForm.get().info("", null);
            AddOrderForm.open();
        }else if(source.equals(logoutButton)){
            Connection.disconnect();
        }
    }

    public VendorInfoPanel getVendorInfoPanel(){
        return vendorInfoPanel;
    }

    public OrdersPanel getOrdersPanel(){
        return ordersPanel;
    }

    public static void init(){
        instance = new ClientWindow();
    }

    public static ClientWindow get(){
        return instance;
    }

    public static void open(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        instance.setVisible(true);
                    }
                }
        );
    }

    public static void close(){
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run(){
                        instance.setVisible(false);
                    }
                }
        );
    }

}
