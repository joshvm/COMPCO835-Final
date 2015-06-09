package co835.vailskiwear.server.model.order;

import co835.vailskiwear.server.model.EntityManager;
import org.w3c.dom.Element;
import res.Res;

import java.util.ArrayList;
import java.util.List;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class SalesOrderManager extends EntityManager<SalesOrder> {

    private static SalesOrderManager instance;

    public SalesOrderManager(){
        super(Res.xml("orders"), "order");
    }

    public List<SalesOrder> getByVendorId(final int vendorId){
        final List<SalesOrder> list = new ArrayList<>();
        for(final SalesOrder order : get())
            if(order.getVendorId() == vendorId)
                list.add(order);
        return list;
    }

    protected SalesOrder parse(final Element e){
        return SalesOrder.parse(e);
    }

    public static SalesOrderManager getInstance(){
        if(instance == null)
            instance = new SalesOrderManager();
        return instance;
    }
}
