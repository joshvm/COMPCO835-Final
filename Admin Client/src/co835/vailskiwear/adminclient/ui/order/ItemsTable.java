package co835.vailskiwear.adminclient.ui.order;

import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.ui.util.LabelledComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.text.DecimalFormat;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemsTable extends JPanel{

    private final ItemsTableModel model;
    private final JTable table;

    private final JTextField totalBox;

    public ItemsTable(){
        super(new BorderLayout());
        setBorder(new TitledBorder("0 Items"));

        model = new ItemsTableModel();

        table = new JTable(model);
        final DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(String.class);
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, renderer);

        totalBox = new JTextField();
        totalBox.setEditable(false);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(new LabelledComponent("Total Price", 100, totalBox), BorderLayout.SOUTH);
    }

    public void update(final SalesOrder order){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        totalBox.setText(order == null ? "" : DecimalFormat.getCurrencyInstance().format(order.getTotalPrice()));
                        totalBox.repaint();
                        model.clear();
                        if(order != null)
                            for(final SalesOrder.Item item : order.getItems())
                                model.add(item);
                        table.repaint();
                        setBorder(new TitledBorder(String.format("%,d Item%s", model.size(), model.size() != 1 ? "s" : "")));
                        revalidate();
                        repaint();
                    }
                }
        );
    }

}
