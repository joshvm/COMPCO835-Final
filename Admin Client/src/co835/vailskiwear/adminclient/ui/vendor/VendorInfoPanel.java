package co835.vailskiwear.adminclient.ui.vendor;

import co835.vailskiwear.adminclient.model.profile.Vendor;
import co835.vailskiwear.adminclient.net.Connection;
import co835.vailskiwear.adminclient.net.handler.impl.UpdateVendorHandler;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VendorInfoPanel extends JPanel implements ActionListener{

    private final JTextField idBox;
    private final JTextField userBox;
    private final JTextField passBox;
    private final JButton editPassButton;
    private final JTextField emailBox;
    private final JButton editEmailButton;
    private final JTextField phoneNumberBox;
    private final JButton editPhoneNumberButton;
    private final JTextArea billingAddressArea;
    private final JButton editBillingAddressButton;
    private final JTextArea shippingAddressArea;
    private final JButton editShippingAddressButton;

    private final JButton removeButton;

    public VendorInfoPanel(){
        super(new BorderLayout());
        setBorder(new TitledBorder("Vendor Info"));

        idBox = new JTextField();
        idBox.setEditable(false);

        userBox = new JTextField();
        userBox.setEditable(false);

        passBox = new JTextField();
        passBox.setEditable(false);

        editPassButton = new JButton(Res.PROFILE_EDIT_16);
        editPassButton.setEnabled(false);
        editPassButton.addActionListener(this);

        emailBox = new JTextField();
        emailBox.setEditable(false);

        editEmailButton = new JButton(Res.PROFILE_EDIT_16);
        editEmailButton.setEnabled(false);
        editEmailButton.addActionListener(this);

        phoneNumberBox = new JTextField();
        phoneNumberBox.setEditable(false);

        editPhoneNumberButton = new JButton(Res.PROFILE_EDIT_16);
        editPhoneNumberButton.setEnabled(false);
        editPhoneNumberButton.addActionListener(this);

        billingAddressArea = new JTextArea();
        billingAddressArea.setRows(3);
        billingAddressArea.setLineWrap(true);
        billingAddressArea.setWrapStyleWord(true);
        billingAddressArea.setEditable(false);

        editBillingAddressButton = new JButton(Res.PROFILE_EDIT_16);
        editBillingAddressButton.setEnabled(false);
        editBillingAddressButton.addActionListener(this);

        shippingAddressArea = new JTextArea();
        shippingAddressArea.setRows(3);
        shippingAddressArea.setLineWrap(true);
        shippingAddressArea.setWrapStyleWord(true);
        shippingAddressArea.setEditable(false);

        editShippingAddressButton = new JButton(Res.PROFILE_EDIT_16);
        editShippingAddressButton.setEnabled(false);
        editShippingAddressButton.addActionListener(this);

        removeButton = new JButton("Remove", Res.PROFILE_REMOVE_16);
        removeButton.setEnabled(false);
        removeButton.addActionListener(this);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(removeButton);

        final JPanel container = new JPanel();
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new LabelledComponent("ID", 150, idBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Username", 150, userBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Password", 150, passBox, editPassButton));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Email", 150, emailBox, editEmailButton));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Phone Number", 150, phoneNumberBox, editPhoneNumberButton));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Billing Address", 150, new JScrollPane(billingAddressArea), editBillingAddressButton));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Shipping Address", 150, new JScrollPane(shippingAddressArea), editShippingAddressButton));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Actions", 150, buttons));

        add(container, BorderLayout.CENTER);
    }

    public int getCurrentVendorId(){
        try{
            return Integer.parseInt(idBox.getText());
        }catch(Exception ex){
            return -1;
        }
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        final int vendorId = getCurrentVendorId();
        if(vendorId == -1)
            return;
        if(source.equals(editPassButton)){
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            final String value = JOptionPane.showInputDialog(null, "Enter new password");
                            if(value == null || value.isEmpty())
                                return;
                            Connection.updateVendor(vendorId, UpdateVendorHandler.PASS, value);
                        }
                    }
            );
        }else if(source.equals(editEmailButton)){
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            final String value = JOptionPane.showInputDialog(null, "Enter new email");
                            if(value == null || value.isEmpty())
                                return;
                            Connection.updateVendor(vendorId, UpdateVendorHandler.EMAIL, value);
                        }
                    }
            );
        }else if(source.equals(editPhoneNumberButton)){
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            final String value = JOptionPane.showInputDialog(null, "Enter new phone number");
                            if(value == null || value.isEmpty())
                                return;
                            Connection.updateVendor(vendorId, UpdateVendorHandler.PHONE_NUMBER, value);
                        }
                    }
            );
        }else if(source.equals(editBillingAddressButton)){
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            final String value = JOptionPane.showInputDialog(null, "Enter new billing address");
                            if(value == null || value.isEmpty())
                                return;
                            Connection.updateVendor(vendorId, UpdateVendorHandler.BILLING_ADDRESS, value);
                        }
                    }
            );
        }else if(source.equals(editShippingAddressButton)){
            SwingUtilities.invokeLater(
                    new Runnable(){
                        public void run(){
                            final String value = JOptionPane.showInputDialog(null, "Enter new shipping address");
                            if(value == null || value.isEmpty())
                                return;
                            Connection.updateVendor(vendorId, UpdateVendorHandler.SHIPPING_ADDRESS, value);
                        }
                    }
            );
        }else if(source.equals(removeButton)){
            Connection.removeVendor(vendorId);
        }
    }

    public void update(final Vendor vendor){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        idBox.setText(vendor == null ? "" : Integer.toString(vendor.getId()));
                        idBox.repaint();
                        userBox.setText(vendor == null ? "" : vendor.getUser());
                        userBox.repaint();
                        passBox.setText(vendor == null ? "" : vendor.getPass());
                        passBox.repaint();
                        editPassButton.setEnabled(vendor != null);
                        editPassButton.repaint();
                        emailBox.setText(vendor == null ? "" : vendor.getEmail());
                        emailBox.repaint();
                        editEmailButton.setEnabled(vendor != null);
                        editEmailButton.repaint();
                        phoneNumberBox.setText(vendor == null ? "" : vendor.getPhoneNumber());
                        phoneNumberBox.repaint();
                        editPhoneNumberButton.setEnabled(vendor != null);
                        editPhoneNumberButton.repaint();
                        billingAddressArea.setText(vendor == null ? "" : vendor.getBillingAddress());
                        billingAddressArea.repaint();
                        editBillingAddressButton.setEnabled(vendor != null);
                        editBillingAddressButton.repaint();
                        shippingAddressArea.setText(vendor == null ? "" : vendor.getShippingAddress());
                        shippingAddressArea.repaint();
                        editShippingAddressButton.setEnabled(vendor != null);
                        editShippingAddressButton.repaint();
                        removeButton.setEnabled(vendor != null);
                        removeButton.repaint();
                    }
                }
        );
    }
}
