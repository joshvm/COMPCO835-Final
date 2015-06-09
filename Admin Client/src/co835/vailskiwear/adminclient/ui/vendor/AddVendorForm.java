package co835.vailskiwear.adminclient.ui.vendor;

import co835.vailskiwear.adminclient.net.Connection;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AddVendorForm extends JFrame implements ActionListener{

    private static final int INVALID_USER = 0;
    private static final int INVALID_PASS = 1;
    private static final int INVALID_EMAIL = 2;
    private static final int INVALID_PHONE_NUMBER = 3;
    private static final int INVALID_BILLING_ADDRESS = 4;
    private static final int INVALID_SHIPPING_ADDRESS = 5;
    private static final int USER_TAKEN = 6;
    private static final int SUCCESS = 7;
    private static final int ERROR = 8;

    private static AddVendorForm instance;
    
    private final JTextField userBox;
    private final JTextField passBox;
    private final JTextField emailBox;
    private final JTextField phoneNumberBox;
    private final JTextArea billingAddressArea;
    private final JTextArea shippingAddressArea;
    
    private final JLabel infoLabel;
    private final JButton addButton;
    private final JButton clearButton;

    public AddVendorForm(){
        super("Vail Skiwear - Add Vendor");
        setIconImage(Res.LOGO.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        userBox = new JTextField();
        
        passBox = new JTextField();
        
        emailBox = new JTextField();
        
        phoneNumberBox = new JTextField();
        
        billingAddressArea = new JTextArea();
        billingAddressArea.setRows(3);
        billingAddressArea.setLineWrap(true);
        billingAddressArea.setWrapStyleWord(true);
        
        shippingAddressArea = new JTextArea();
        shippingAddressArea.setRows(3);
        shippingAddressArea.setLineWrap(true);
        shippingAddressArea.setWrapStyleWord(true);
        
        final JPanel fields = new JPanel();
        fields.setBorder(new TitledBorder("Vendor Information"));
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.add(new LabelledComponent("Username", 150, userBox));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Password", 150, passBox));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Email", 150, emailBox));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Phone Number", 150, phoneNumberBox));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Billing Address", 150, new JScrollPane(billingAddressArea)));
        fields.add(Box.createVerticalStrut(2));
        fields.add(new LabelledComponent("Shipping Address", 150, new JScrollPane(shippingAddressArea)));

        infoLabel = new JLabel("", JLabel.CENTER);
        
        addButton = new JButton("Add Vendor", Res.PROFILE_ADD_16);
        addButton.addActionListener(this);
        
        clearButton = new JButton("Clear", Res.CLEAR_16);
        clearButton.addActionListener(this);
        
        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(infoLabel);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(addButton);
        buttons.add(Box.createHorizontalStrut(2));
        buttons.add(clearButton);

        final JPanel center = new JPanel(new BorderLayout());
        center.add(fields, BorderLayout.CENTER);
        center.add(buttons, BorderLayout.SOUTH);

        final JLabel heading = new JLabel("Create New Vendor", JLabel.CENTER);
        heading.setFont(heading.getFont().deriveFont(30f));

        final JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.add(heading, BorderLayout.NORTH);
        container.add(center, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public void info(final String text, final Color color){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        infoLabel.setText(text);
                        infoLabel.setForeground(color);
                        infoLabel.repaint();
                    }
                }
        );
    }

    public void handleResponse(final int response){
        switch(response){
            case INVALID_USER:
                info("Invalid username", Color.RED);
                break;
            case INVALID_PASS:
                info("Invalid password", Color.RED);
                break;
            case INVALID_EMAIL:
                info("Invalid email", Color.RED);
                break;
            case INVALID_PHONE_NUMBER:
                info("Invalid phone number", Color.RED);
                break;
            case INVALID_BILLING_ADDRESS:
                info("Invalid billing address", Color.RED);
                break;
            case INVALID_SHIPPING_ADDRESS:
                info("Invalid shipping address", Color.RED);
                break;
            case USER_TAKEN:
                info("Username taken already", Color.RED);
                break;
            case SUCCESS:
                clear();
                info("Success", Color.GREEN);
                break;
            case ERROR:
                info("Error adding vendor", Color.RED);
                break;
        }
    }

    private void clear(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        info("", null);
                        userBox.setText("");
                        userBox.repaint();
                        passBox.setText("");
                        passBox.repaint();
                        emailBox.setText("");
                        emailBox.repaint();
                        phoneNumberBox.setText("");
                        phoneNumberBox.repaint();
                        billingAddressArea.setText("");
                        billingAddressArea.repaint();
                        shippingAddressArea.setText("");
                        shippingAddressArea.repaint();
                    }
                }
        );
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(source.equals(addButton)){
            final String user = userBox.getText();
            final String pass = passBox.getText();
            final String email = emailBox.getText();
            final String phoneNumber = phoneNumberBox.getText();
            final String billingAddress = billingAddressArea.getText();
            final String shippingAddress = shippingAddressArea.getText();
            if(user.isEmpty()){
                info("Enter a valid username", Color.RED);
                return;
            }
            if(pass.isEmpty()){
                info("Enter a valid password", Color.RED);
                return;
            }
            if(email.isEmpty()){
                info("Enter a valid email", Color.RED);
                return;
            }
            if(phoneNumber.isEmpty()){
                info("Enter a valid phone number", Color.RED);
                return;
            }
            if(billingAddress.isEmpty()){
                info("Enter a valid billing address", Color.RED);
                return;
            }
            if(shippingAddress.isEmpty()){
                info("Enter a valid shipping address", Color.RED);
                return;
            }
            if(!Connection.addVendor(user, pass, email, phoneNumber, billingAddress, shippingAddress))
                info("Error sending request", Color.RED);
        }else if(source.equals(clearButton)){
            clear();
        }
    }

    public static void init(){
        instance = new AddVendorForm();
    }

    public static AddVendorForm get(){
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
                new Runnable(){
                    public void run(){
                        instance.setVisible(false);
                    }
                }
        );
    }
}
