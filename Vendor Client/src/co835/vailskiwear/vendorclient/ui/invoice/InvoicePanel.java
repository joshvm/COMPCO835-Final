package co835.vailskiwear.vendorclient.ui.invoice;

import co835.vailskiwear.vendorclient.model.invoice.Invoice;
import co835.vailskiwear.vendorclient.model.order.SalesOrder;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.text.DecimalFormat;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class InvoicePanel extends JPanel {

    private final JTextArea area;

    public InvoicePanel(){
        super(new BorderLayout());
        setBorder(new TitledBorder("Invoice"));

        area = new JTextArea();
        area.setRows(5);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void update(final SalesOrder order){
        final Invoice invoice = order == null ? null : order.getInvoice();
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        area.setText("");
                        if(order == null)
                            area.setText("");
                        else if(invoice == null)
                            area.setText("NO INVOICE FOUND");
                        else{
                            final String nl = System.lineSeparator();
                            area.append(String.format("SALES ORDER ID: %s%s", order.getId(), nl));
                            area.append(String.format("SALES ORDER DATE: %s%s%s", order.getDate(), nl, nl));
                            area.append(String.format("INVOICE ID: %s%s", invoice.getId(), nl));
                            area.append(String.format("INVOICE DATE: %s%s%s", invoice.getDate(), nl, nl));
                            area.append(String.format("ITEMS: %,d%s", order.getItems().size(), nl));
                            for(final SalesOrder.Item item : order.getItems()){
                                area.append(String.format(
                                        " - %s x %,d @ %s = %s%s",
                                        item.getDef().getName(),
                                        item.getQuantity(),
                                        DecimalFormat.getCurrencyInstance().format(item.getPrice()),
                                        DecimalFormat.getCurrencyInstance().format(item.getTotalPrice()),
                                        nl
                                ));
                            }
                            area.append(nl);
                            area.append(String.format("TOTAL: %s", DecimalFormat.getCurrencyInstance().format(order.getTotalPrice())));
                        }
                        area.repaint();
                    }
                }
        );
    }
}
