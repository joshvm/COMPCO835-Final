package co835.vailskiwear.adminclient.ui.order;

import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.ui.invoice.InvoicePanel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class OrderPanel extends JPanel {

    private final OrderInfoPanel infoPanel;
    private final ItemsTable itemsTable;
    private final InvoicePanel invoicePanel;

    public OrderPanel(){
        super(new BorderLayout());

        infoPanel = new OrderInfoPanel();

        itemsTable = new ItemsTable();

        invoicePanel = new InvoicePanel();

        final JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(infoPanel);
        north.add(itemsTable);

        final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, north, invoicePanel);
        split.setResizeWeight(1);

        add(split, BorderLayout.CENTER);
    }

    public void update(final SalesOrder order){
        infoPanel.update(order);
        itemsTable.update(order);
        invoicePanel.update(order);
    }
}
