package co835.vailskiwear.server.model.invoice;

import co835.vailskiwear.server.model.EntityManager;
import org.w3c.dom.Element;
import res.Res;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class InvoiceManager extends EntityManager<Invoice> {

    private static InvoiceManager instance;

    public InvoiceManager(){
        super(Res.xml("invoices"), "invoice");
    }

    public Invoice getByOrderId(final int orderId){
        for(final Invoice invoice : get())
            if(invoice.getOrderId() == orderId)
                return invoice;
        return null;
    }

    protected Invoice parse(final Element e){
        return Invoice.parse(e);
    }

    public static InvoiceManager getInstance(){
        if(instance == null)
            instance = new InvoiceManager();
        return instance;
    }
}
