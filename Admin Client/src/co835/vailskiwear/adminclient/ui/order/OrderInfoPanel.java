package co835.vailskiwear.adminclient.ui.order;

import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.net.Connection;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;
import res.Res;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderInfoPanel extends JPanel implements ActionListener, ItemListener{

    private final JTextField idBox;
    private final JTextField dateBox;
    private final JComboBox<SalesOrder.State> stateBox;

    private final JButton cancelButton;

    public OrderInfoPanel(){
        super(new BorderLayout());
        setBorder(new TitledBorder("Order Info"));

        idBox = new JTextField();
        idBox.setEditable(false);

        dateBox = new JTextField();
        dateBox.setEditable(false);

        stateBox = new JComboBox<>(SalesOrder.State.values());
        stateBox.setRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(final JList l, final Object o, final int i, final boolean s, final boolean f){
                        final Component comp = super.getListCellRendererComponent(l, o, i, s, f);
                        if(o == null)
                            return comp;
                        final SalesOrder.State state = (SalesOrder.State) o;
                        final JLabel label = (JLabel) comp;
                        label.setIcon(state.getIcon());
                        return label;
                    }
                }
        );
        stateBox.setEnabled(false);
        stateBox.addItemListener(this);

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
        container.add(new LabelledComponent("State", 100, stateBox));
        container.add(Box.createVerticalStrut(2));
        container.add(new LabelledComponent("Actions", 100, actions));

        add(container, BorderLayout.CENTER);
    }

    public void itemStateChanged(final ItemEvent e){
        final Object source = e.getSource();
        if(!source.equals(stateBox) || e.getStateChange() != ItemEvent.SELECTED)
            return;
        try{
            final int orderId = Integer.parseInt(idBox.getText());
            final SalesOrder.State state = (SalesOrder.State) stateBox.getSelectedItem();
            Connection.orderState(orderId, state.ordinal());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void actionPerformed(final ActionEvent e){
        final Object source = e.getSource();
        if(source.equals(cancelButton)){
            try{
                final int orderId = Integer.parseInt(idBox.getText());
                Connection.removeOrder(orderId);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void update(final SalesOrder order){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        cancelButton.setEnabled(order != null);
                        cancelButton.repaint();
                        idBox.setText(order == null ? "" : Integer.toString(order.getId()));
                        idBox.repaint();
                        dateBox.setText(order == null ? "" : order.getDate().toString());
                        dateBox.repaint();
                        stateBox.removeItemListener(OrderInfoPanel.this);
                        stateBox.setEnabled(order != null);
                        if(order != null)
                            stateBox.setSelectedItem(order.getState());
                        stateBox.repaint();
                        stateBox.addItemListener(OrderInfoPanel.this);
                    }
                }
        );
    }
}
