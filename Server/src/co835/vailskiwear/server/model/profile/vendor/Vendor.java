package co835.vailskiwear.server.model.profile.vendor;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.net.packet.Packets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Vendor extends Profile{

    private String billingAddress;
    private String shippingAddress;

    public Vendor(final int id, final String user, final String pass, final String email, final String phoneNumber, final String billingAddress, final String shippingAddress){
        super(id, user, pass, email, phoneNumber);
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public Vendor(final String user, final String pass, final String email, final String phoneNumber, final String billingAddress, final String shippingAddress){
        this(VendorManager.getInstance().getNextId(), user, pass, email, phoneNumber, billingAddress, shippingAddress);
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
        return SalesOrderManager.getInstance().getByVendorId(id);
    }

    public boolean sendInit(){
        return send(Packets.vendorInit(this));
    }

    public boolean sendOrders(){
        if(!isOnline())
            return false;
        for(final SalesOrder order : getOrders())
            send(Packets.addOrder(order, false));
        return true;
    }

    public boolean sendInvoices(){
        if(!isOnline())
            return false;
        for(final SalesOrder order : getOrders()){
            final Invoice invoice = order.getInvoice();
            if(invoice != null)
                send(Packets.addInvoice(invoice));
        }
        return true;
    }

    public Element toElement(final Document doc){
        final Element vendor = createElement(doc, "vendor");
        final Element addresses = doc.createElement("addresses");
        final Element billingAddress = doc.createElement("billing");
        billingAddress.setTextContent(this.billingAddress);
        final Element shippingAddress = doc.createElement("shipping");
        shippingAddress.setTextContent(this.shippingAddress);
        addresses.appendChild(billingAddress);
        addresses.appendChild(shippingAddress);
        vendor.appendChild(addresses);
        return vendor;
    }

    public static Vendor parse(final Element e){
        final Profile profile = Profile.parse(e);
        final Element addressesElement = (Element) e.getElementsByTagName("addresses").item(0);
        final String billingAddress = addressesElement.getElementsByTagName("billing").item(0).getTextContent();
        final String shippingAddress = addressesElement.getElementsByTagName("shipping").item(0).getTextContent();
        return new Vendor(
                profile.getId(),
                profile.getUser(),
                profile.getPass(),
                profile.getEmail(),
                profile.getPhoneNumber(),
                billingAddress,
                shippingAddress);
    }

}
