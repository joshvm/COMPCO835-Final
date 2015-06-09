package co835.vailskiwear.vendorclient.ui.order;

import co835.vailskiwear.vendorclient.model.order.SalesOrder;
import co835.vailskiwear.vendorclient.model.profile.Vendor;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Component;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderList extends JPanel {

    private final DefaultListModel<SalesOrder> model;
    private final JList<SalesOrder> list;

    public OrderList(){
        super(new BorderLayout());
        setBorder(new TitledBorder("0 Sale Orders"));

        model = new DefaultListModel<>();

        list = new JList<>(model);
        list.setCellRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(final JList list, final Object o, final int i, final boolean s, final boolean f){
                        final Component comp = super.getListCellRendererComponent(list, o, i, s, f);
                        if(o == null)
                            return comp;
                        final SalesOrder order = (SalesOrder) o;
                        final JLabel label = (JLabel) comp;
                        label.setIcon(order.getState().getIcon());
                        label.setText(String.format("#%d - %s", order.getId(), order.getDate()));
                        return label;
                    }
                }
        );

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void refresh(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        list.repaint();
                    }
                }
        );
    }

    public void addListSelectionListener(final ListSelectionListener listener){
        list.addListSelectionListener(listener);
    }

    public SalesOrder getSelected(){
        return list.getSelectedValue();
    }

    public void update(final Vendor vendor){
        clear();
        if(vendor != null)
            for(final SalesOrder order : vendor.getOrders())
                add(order);
    }

    private void updateBorder(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        setBorder(new TitledBorder(String.format("%,d Order%s", model.size(), model.size() != 1 ? "s" : "")));
                        revalidate();
                        repaint();
                    }
                }
        );
    }

    public void add(final SalesOrder order){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.addElement(order);
                        list.repaint();
                        updateBorder();
                    }
                }
        );
    }

    public void remove(final SalesOrder order){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.removeElement(order);
                        list.repaint();
                        updateBorder();
                    }
                }
        );
    }

    public void clear(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        model.clear();
                        list.repaint();
                        updateBorder();
                    }
                }
        );
    }
}
