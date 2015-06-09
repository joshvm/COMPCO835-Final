package co835.vailskiwear.server.model.invoice;

import co835.vailskiwear.server.model.Entity;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Date;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Invoice extends Entity {

    private final Date date;
    private final int orderId;

    public Invoice(final int id, final Date date, final int orderId){
        super(id);
        this.date = date;
        this.orderId = orderId;
    }

    public Invoice(final int orderId){
        this(orderId, new Date(), orderId);
    }

    public Date getDate(){
        return date;
    }

    public int getOrderId(){
        return orderId;
    }

    public SalesOrder getOrder(){
        return SalesOrderManager.getInstance().get(orderId);
    }

    public Element toElement(final Document doc){
        final Element invoice = createElement(doc, "invoice");
        final Element date = doc.createElement("date");
        date.setAttribute("ms", Long.toString(this.date.getTime()));
        date.setAttribute("stamp", this.date.toString());
        final Element order = doc.createElement("order");
        order.setAttribute("id", Integer.toString(orderId));
        invoice.appendChild(date);
        invoice.appendChild(order);
        return invoice;
    }

    public static Invoice parse(final Element e){
        final Entity entity = Entity.parse(e);
        final Element dateElement = (Element) e.getElementsByTagName("date").item(0);
        final Date date = new Date(Long.parseLong(dateElement.getAttribute("ms")));
        final Element orderElement = (Element) e.getElementsByTagName("order").item(0);
        final int orderId = Integer.parseInt(orderElement.getAttribute("id"));
        return new Invoice(entity.getId(), date, orderId);
    }

}
