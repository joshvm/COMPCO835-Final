package co835.vailskiwear.adminclient;

import co835.vailskiwear.adminclient.model.profile.Vendor;
import co835.vailskiwear.adminclient.net.Connection;
import co835.vailskiwear.adminclient.ui.itemdef.ItemsManagerWindow;
import co835.vailskiwear.adminclient.ui.order.OrdersPanel;
import co835.vailskiwear.adminclient.ui.vendor.AddVendorForm;
import co835.vailskiwear.adminclient.ui.vendor.VendorInfoPanel;
import co835.vailskiwear.adminclient.ui.vendor.VendorsList;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    private final JButton addVendorButton;
    private final JButton itemsButton;
    private final JButton logoutButton;

    private final VendorsList vendorsList;
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

        final JLabel creditsLabel = new JLabel("<html>Developed By: Josh Maione (000320309)<br><center>Tested By: Jesse Hilton (000322425)</center></html>", JLabel.CENTER);
        creditsLabel.setFont(creditsLabel.getFont().deriveFont(20f));

        addVendorButton = new JButton("Add Vendor", Res.PROFILE_ADD_32);
        addVendorButton.setHorizontalTextPosition(JLabel.CENTER);
        addVendorButton.setVerticalTextPosition(JLabel.BOTTOM);
        addVendorButton.addActionListener(this);

        itemsButton = new JButton("Items", Res.PRODUCT_32);
        itemsButton.setHorizontalTextPosition(JLabel.CENTER);
        itemsButton.setVerticalTextPosition(JLabel.BOTTOM);
        itemsButton.addActionListener(this);

        logoutButton = new JButton("Logout", Res.LOGOUT_32);
        logoutButton.setHorizontalTextPosition(JLabel.CENTER);
        logoutButton.setVerticalTextPosition(JLabel.BOTTOM);
        logoutButton.addActionListener(this);

        final JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(addVendorButton);
        top.add(Box.createHorizontalStrut(2));
        top.add(itemsButton);
        top.add(Box.createHorizontalGlue());
        top.add(creditsLabel);
        top.add(Box.createHorizontalGlue());
        top.add(logoutButton);

        vendorInfoPanel = new VendorInfoPanel();

        ordersPanel = new OrdersPanel();

        final JPanel container = new JPanel(new BorderLayout());
        container.add(vendorInfoPanel, BorderLayout.NORTH);
        container.add(ordersPanel, BorderLayout.CENTER);

        vendorsList = new VendorsList();
        vendorsList.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(final ListSelectionEvent e){
                        final Vendor vendor = vendorsList.getSelected();
                        vendorInfoPanel.update(vendor);
                        ordersPanel.update(vendor);
                    }
                }
        );

        final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vendorsList, container);
        split.setResizeWeight(1);

        final JPanel center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(5, 5, 5, 5));
        center.add(top, BorderLayout.NORTH);
        center.add(split, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        setSize(900, 970);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(source.equals(addVendorButton)){
            AddVendorForm.get().info("", null);
            AddVendorForm.open();
        }else if(source.equals(itemsButton)){
            ItemsManagerWindow.open();
        }else if(source.equals(logoutButton)){
            Connection.disconnect();
        }
    }

    public VendorsList getVendorsList(){
        return vendorsList;
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
