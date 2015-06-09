package co835.vailskiwear.adminclient.model.profile;

import co835.vailskiwear.adminclient.model.order.SalesOrder;
import co835.vailskiwear.adminclient.model.order.SalesOrderManager;

import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Vendor extends Profile {

    private String pass;
    private String billingAddress;
    private String shippingAddress;

    public Vendor(final int id, final String user, final String pass, final String email, final String phoneNumber, final String billingAddress, final String shippingAddress){
        super(id, user, email, phoneNumber);
        this.pass = pass;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public String getPass(){
        return pass;
    }

    public void setPass(final String pass){
        this.pass = pass;
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

    public List<SalesOrder> getOrders(){
        return SalesOrderManager.getOrdersForVendor(id);
    }

}
