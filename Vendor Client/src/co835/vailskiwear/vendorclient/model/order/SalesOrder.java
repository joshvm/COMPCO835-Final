package co835.vailskiwear.vendorclient.model.order;

import co835.vailskiwear.vendorclient.model.Entity;
import co835.vailskiwear.vendorclient.model.invoice.Invoice;
import co835.vailskiwear.vendorclient.model.itemdef.ItemDef;
import co835.vailskiwear.vendorclient.model.itemdef.ItemDefManager;
import res.Res;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class SalesOrder extends Entity {

    public static class Item extends Entity{

        private final double price;
        private int quantity;

        public Item(final int id, final double price, final int quantity){
            super(id);
            this.price = price;
            this.quantity = quantity;
        }

        public ItemDef getDef(){
            return ItemDefManager.get(id);
        }

        public double getPrice(){
            return price;
        }

        public int getQuantity(){
            return quantity;
        }

        public void setQuantity(final int quantity){
            this.quantity = quantity;
        }

        public double getTotalPrice(){
            return price * quantity;
        }

    }

    public enum State{
        PENDING(Res.ORDER_PENDING_16),
        APPROVED(Res.ORDER_APPROVED_16),
        DECLINED(Res.ORDER_DECLINED_16),
        SHIPPED(Res.ORDER_SHIPPED_16);

        private final ImageIcon icon;

        private State(final ImageIcon icon){
            this.icon = icon;
        }

        public ImageIcon getIcon(){
            return icon;
        }
    }

    private final Date date;
    private final List<Item> items;
    private State state;

    private Invoice invoice;

    public SalesOrder(final int id, final Date date, final State state){
        super(id);
        this.date = date;
        this.state = state;

        items = new ArrayList<>();
    }

    public Date getDate(){
        return date;
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

    public double getTotalPrice(){
        double total = 0;
        for(final Item item : items)
            total += item.getTotalPrice();
        return total;
    }

    public Invoice getInvoice(){
        return invoice;
    }

    public void setInvoice(final Invoice invoice){
        this.invoice = invoice;
    }
}
