package co835.vailskiwear.vendorclient.ui.vendor;

import co835.vailskiwear.vendorclient.model.profile.Vendor;
import co835.vailskiwear.vendorclient.ui.util.LabelledComponent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VendorInfoPanel extends JPanel {

    private static final int WIDTH = 150;

    private final JTextField userBox;
    private final JTextField emailBox;
    private final JTextField phoneNumberBox;
    private final JTextArea billingAddressBox;
    private final JTextArea shippingAddressBox;

    public VendorInfoPanel(){
        super(new BorderLayout());
        setBorder(new TitledBorder("Vendor Information"));

        userBox = new JTextField();
        userBox.setEditable(false);

        emailBox = new JTextField();
        emailBox.setEditable(false);

        phoneNumberBox = new JTextField();
        phoneNumberBox.setEditable(false);

        billingAddressBox = new JTextArea();
        billingAddressBox.setRows(3);
        billingAddressBox.setLineWrap(true);
        billingAddressBox.setWrapStyleWord(true);
        billingAddressBox.setEditable(false);

        shippingAddressBox = new JTextArea();
        shippingAddressBox.setRows(3);
        shippingAddressBox.setLineWrap(true);
        shippingAddressBox.setWrapStyleWord(true);
        shippingAddressBox.setEditable(false);

        final JPanel container = new JPanel();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new LabelledComponent("Username", WIDTH, userBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Email", WIDTH, emailBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Phone Number", WIDTH, phoneNumberBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Billing Address", WIDTH, new JScrollPane(billingAddressBox)));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Shipping Address", WIDTH, new JScrollPane(shippingAddressBox)));

        add(container, BorderLayout.CENTER);
    }

    public void update(final Vendor vendor){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        userBox.setText(vendor == null ? "" : vendor.getUser());
                        userBox.repaint();
                        emailBox.setText(vendor == null ? "" : vendor.getEmail());
                        emailBox.repaint();
                        phoneNumberBox.setText(vendor == null ? "" : vendor.getPhoneNumber());
                        phoneNumberBox.repaint();
                        billingAddressBox.setText(vendor == null ? "" : vendor.getBillingAddress());
                        billingAddressBox.repaint();
                        shippingAddressBox.setText(vendor == null ? "" : vendor.getShippingAddress());
                        shippingAddressBox.repaint();
                    }
                }
        );
    }
}
