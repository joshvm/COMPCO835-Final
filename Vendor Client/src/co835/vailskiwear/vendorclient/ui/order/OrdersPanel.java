package co835.vailskiwear.vendorclient.ui.order;

import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.model.profile.Vendor;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrdersPanel extends JPanel implements ListSelectionListener{

    private final OrderList list;
    private final OrderPanel panel;

    public OrdersPanel(){
        super(new BorderLayout());

        list = new OrderList();
        list.addListSelectionListener(this);

        panel = new OrderPanel();

        add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, panel));
    }

    public OrderList getList(){
        return list;
    }

    public void update(final Vendor vendor){
        list.update(vendor);
        panel.update((SalesOrder)null);
    }

    public void update(final SalesOrder order){
        final SalesOrder selected = list.getSelected();
        if(selected != null && selected.equals(order)){
            list.refresh();
            panel.update(order);
        }
    }

    public void add(final SalesOrder order){
        list.add(order);
    }

    public void remove(final SalesOrder order){
        final SalesOrder selected = list.getSelected();
        list.remove(order);
        if(selected != null && selected.equals(order))
            panel.update((SalesOrder)null);
    }

    public void clear(){
        list.clear();
        panel.update((SalesOrder)null);
    }

    public void valueChanged(final ListSelectionEvent e){
        final SalesOrder selected = list.getSelected();
        panel.update(selected);
    }
}
