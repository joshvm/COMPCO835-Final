package co835.vailskiwear.server.model.order;

import co835.vailskiwear.server.model.Entity;
import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.itemdef.ItemDef;
import co835.vailskiwear.server.model.itemdef.ItemDefManager;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class SalesOrder extends Entity{

    public static class Item extends Entity{

        private final double price;
        private final int quantity;

        public Item(final int id, final double price, final int quantity){
            super(id);
            this.price = price;
            this.quantity = quantity;
        }

        public ItemDef getDef(){
            return ItemDefManager.getInstance().get(id);
        }

        public double getPrice(){
            return price;
        }

        public int getQuantity(){
            return quantity;
        }

        public Element toElement(final Document doc){
            final Element item = createElement(doc, "item");
            final Element price = doc.createElement("price");
            price.setTextContent(Double.toString(this.price));
            final Element quantity = doc.createElement("quantity");
            quantity.setTextContent(Integer.toString(this.quantity));
            item.appendChild(price);
            item.appendChild(quantity);
            return item;
        }

        public static Item parse(final Element e){
            final Entity entity = Entity.parse(e);
            final double price = Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent());
            final int quantity = Integer.parseInt(e.getElementsByTagName("quantity").item(0).getTextContent());
            return new Item(entity.getId(), price, quantity);
        }

    }

    public enum State{
        PENDING,
        APPROVED,
        DECLINED,
        SHIPPED,
    }

    private final Date date;
    private final int vendorId;
    private final List<Item> items;
    private State state;

    public SalesOrder(final int id, final Date date, final int vendorId, final State state){
        super(id);
        this.date = date;
        this.vendorId = vendorId;
        this.state = state;

        items = new ArrayList<>();
    }

    public SalesOrder(final int vendorId){
        this(SalesOrderManager.getInstance().getNextId(), new Date(), vendorId, State.PENDING);
    }

    public Date getDate(){
        return date;
    }

    public int getVendorId(){
        return vendorId;
    }

    public Vendor getVendor(){
        return VendorManager.getInstance().get(vendorId);
    }

    public State getState(){
        return state;
    }

    public void setState(final State state){
        this.state = state;
    }

    public List<Item> getItems(){
        return items;
    }

    public Invoice getInvoice(){
        return InvoiceManager.getInstance().getByOrderId(id);
    }

    public Element toElement(final Document doc){
        final Element order = createElement(doc, "order");
        order.setAttribute("state", state.name());
        final Element date = doc.createElement("date");
        date.setAttribute("ms", Long.toString(this.date.getTime()));
        date.setAttribute("stamp", this.date.toString());
        final Element vendor = doc.createElement("vendor");
        vendor.setAttribute("id", Integer.toString(vendorId));
        vendor.setTextContent(getVendor().getUser());
        final Element items = doc.createElement("items");
        for(final Item i : this.items)
            items.appendChild(i.toElement(doc));
        order.appendChild(vendor);
        order.appendChild(date);
        order.appendChild(items);
        return order;
    }

    public static SalesOrder parse(final Element e){
        final Entity entity = Entity.parse(e);
        final State state = State.valueOf(e.getAttribute("state"));
        final Element dateElement = (Element)e.getElementsByTagName("date").item(0);
        final Date date = new Date(Long.parseLong(dateElement.getAttribute("ms")));
        final Element vendorElement = (Element) e.getElementsByTagName("vendor").item(0);
        final int vendorId = Integer.parseInt(vendorElement.getAttribute("id"));
        final SalesOrder order = new SalesOrder(entity.getId(), date, vendorId, state);
        final NodeList itemsList = e.getElementsByTagName("item");
        for(int i = 0; i < itemsList.getLength(); i++){
            final Node n = itemsList.item(i);
            if(n.getNodeType() != Node.ELEMENT_NODE)
                continue;
            final Element ie = (Element) n;
            final Item item = Item.parse(ie);
            order.items.add(item);
        }
        return order;
    }
}
