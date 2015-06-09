package co835.vailskiwear.vendorclient.ui.order;

import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.net.Connection;
import co835.vailskiwear.vendorclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderInfoPanel extends JPanel implements ActionListener{

    private final JTextField idBox;
    private final JTextField dateBox;
    private final JLabel stateLabel;

    private final JButton cancelButton;

    public OrderInfoPanel(){
        super(new BorderLayout());
        setBorder(new TitledBorder("Order Info"));

        idBox = new JTextField();
        idBox.setEditable(false);

        dateBox = new JTextField();
        dateBox.setEditable(false);

        stateLabel = new JLabel();

        cancelButton = new JButton("Cancel", Res.ORDER_REMOVE_16);
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(this);

        final JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));
        actions.add(cancelButton);

        final JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(new LabelledComponent("ID", 100, idBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Date", 100, dateBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("State", 100, stateLabel));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Actions", 100, actions));

        add(container, BorderLayout.CENTER);
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(source.equals(cancelButton)){
            try{
                final int orderId = Integer.parseInt(idBox.getText());
                Connection.removeOrder(orderId);
            }catch(Exception ex){}
        }
    }

    public void update(final SalesOrder order){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        cancelButton.setEnabled(order != null && order.getState() == SalesOrder.State.PENDING);
                        cancelButton.setToolTipText(cancelButton.isEnabled() ? "Cancel" : "You can only cancel a sales order when it's pending");
                        cancelButton.repaint();
                        idBox.setText(order == null ? "" : Integer.toString(order.getId()));
                        idBox.repaint();
                        dateBox.setText(order == null ? "" : order.getDate().toString());
                        dateBox.repaint();
                        if(order == null){
                            stateLabel.setText("");
                            stateLabel.setIcon(null);
                        }else{
                            stateLabel.setText(order.getState().toString());
                            stateLabel.setIcon(order.getState().getIcon());
                        }
                        stateLabel.revalidate();
                        stateLabel.repaint();
                    }
                }
        );
    }
}
