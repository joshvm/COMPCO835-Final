package co835.vailskiwear.server.model.profile.admin;

import co835.vailskiwear.server.model.invoice.Invoice;
import co835.vailskiwear.server.model.invoice.InvoiceManager;
import co835.vailskiwear.server.model.order.SalesOrder;
import co835.vailskiwear.server.model.order.SalesOrderManager;
import co835.vailskiwear.server.model.profile.Profile;
import co835.vailskiwear.server.model.profile.vendor.Vendor;
import co835.vailskiwear.server.model.profile.vendor.VendorManager;
import co835.vailskiwear.server.net.packet.Packets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Admin extends Profile{

    public Admin(final int id, final String user, final String pass, final String email, final String phoneNumber){
        super(id, user, pass, email, phoneNumber);
    }

    public Admin(final String user, final String pass, final String email, final String phoneNumber){
        this(AdminManager.getInstance().getNextId(), user, pass, email, phoneNumber);
    }

    public Element toElement(final Document doc){
        return createElement(doc, "admin");
    }

    public boolean sendInit(){
        return send(Packets.adminInit(this));
    }

    public void sendVendors(){
        for(final Vendor vendor : VendorManager.getInstance().get())
            send(Packets.addVendor(vendor));
    }

    public void sendOrders(){
        for(final SalesOrder order : SalesOrderManager.getInstance().get())
            send(Packets.addOrder(order, true));
    }

    public void sendInvoices(){
        for(final Invoice invoice : InvoiceManager.getInstance().get())
            send(Packets.addInvoice(invoice));
    }

    public static Admin parse(final Element e){
        final Profile profile = Profile.parse(e);
        return new Admin(
                profile.getId(),
                profile.getUser(),
                profile.getPass(),
                profile.getEmail(),
                profile.getPhoneNumber()
        );
    }
}
