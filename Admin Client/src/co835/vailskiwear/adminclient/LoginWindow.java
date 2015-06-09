package co835.vailskiwear.adminclient;

import co835.vailskiwear.adminclient.net.Connection;
import co835.vailskiwear.adminclient.net.util.LoginConstants;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class LoginWindow extends JFrame implements ActionListener{

    private static LoginWindow instance;

    private final JTextField userBox;
    private final JPasswordField passBox;

    private final JButton loginButton;
    private final JLabel infoLabel;

    public LoginWindow(){
        super("Vail Skiwear - Admin Login");
        setIconImage(Res.LOGO.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        final JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(5, 5, 5, 5));

        final JLabel logoLabel = new JLabel("<html>Developed By: Josh Maione (000320309)<br><center>Tested By: Jesse Hilton (000322425)</center></html>", Res.LOGO, JLabel.CENTER);
        logoLabel.setHorizontalTextPosition(JLabel.CENTER);
        logoLabel.setVerticalTextPosition(JLabel.BOTTOM);

        userBox = new JTextField();

        passBox = new JPasswordField();

        final JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.add(new LabelledComponent("Username", 70, userBox));
        fields.add(new LabelledComponent("Password", 70, passBox));

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        infoLabel = new JLabel("", JLabel.CENTER);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(infoLabel);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(loginButton);

        container.add(logoLabel, BorderLayout.NORTH);
        container.add(fields, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);

        add(container, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public void handleResponse(final int response){
        switch(response){
            case LoginConstants.INVALID_USER:
                info("Invalid username", Color.RED);
                break;
            case LoginConstants.INVALID_PASS:
                info("Invalid password", Color.RED);
                break;
            case LoginConstants.SUCCESS:
                info("Success. Loading...", Color.GREEN);
                break;
            case LoginConstants.ALREADY_LOGGED_IN:
                info("You are already logged in!", Color.RED);
                break;
        }
    }

    public void info(final String text, final Color color){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        infoLabel.setText(text);
                        infoLabel.setForeground(color);
                        infoLabel.revalidate();
                        infoLabel.repaint();
                    }
                }
        );
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(!source.equals(loginButton))
            return;
        enableForm(false);
        info("Processing...", null);
        final String user = userBox.getText().trim();
        final String pass = new String(passBox.getPassword()).trim();
        if(user.isEmpty())
            info("Enter your username", Color.RED);
        else if(pass.isEmpty())
            info("Enter your password", Color.RED);
        else if(!Connection.login(user, pass))
            info("Error sending login request", Color.RED);
        enableForm(true);
    }

    private void enableForm(final boolean enabled){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        userBox.setEditable(enabled);
                        passBox.setEditable(enabled);
                        loginButton.setEnabled(enabled);
                    }
                }
        );
    }

    public static void init(){
        instance = new LoginWindow();
    }

    public static LoginWindow get(){
        return instance;
    }

    public static void open(){
        SwingUtilities.invokeLater(
                new Runnable() {
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
