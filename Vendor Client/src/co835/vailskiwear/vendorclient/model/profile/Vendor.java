package co835.vailskiwear.vendorclient.model.profile;

import co835.vailskiwear.vendorclient.model.order.SalesOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Vendor extends Profile{

    private String billingAddress;
    private String shippingAddress;

    private final Map<Integer, SalesOrder> orders;

    public Vendor(final int id, final String user, final String email, final String phoneNumber, final String billingAddress, final String shippingAddress){
        super(id, user, email, phoneNumber);
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;

        orders = new HashMap<>();
    }

    public String getBillingAddress(){
        return billingAddress;
    }

    public void setBillingAddress(final String billingAddress){
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress(){
        return shippingAddress;
    }

    public void setShippingAddress(final String shippingAddress){
        this.shippingAddress = shippingAddress;
    }

    public void addOrder(final SalesOrder order){
        orders.put(order.getId(), order);
    }

    public void removeOrder(final SalesOrder order){
        orders.remove(order.getId());
    }

    public SalesOrder getOrder(final int id){
        return orders.get(id);
    }

    public Collection<SalesOrder> getOrders(){
        return orders.values();
    }

}
