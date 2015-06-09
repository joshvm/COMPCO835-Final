package co835.vailskiwear.adminclient.model.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class SalesOrderManager {

    private static final Map<Integer, SalesOrder> MAP = new HashMap<>();

    private SalesOrderManager(){}

    public static SalesOrder get(final int id){
        return MAP.get(id);
    }

    public static List<SalesOrder> getOrdersForVendor(final int vendorId){
        final List<SalesOrder> list = new ArrayList<>();
        for(final SalesOrder order : get())
            if(order.getVendorId() == vendorId)
                list.add(order);
        return list;
    }

    public static Collection<SalesOrder> get(){
        return MAP.values();
    }

    public static void add(final SalesOrder order){
        MAP.put(order.getId(), order);
    }

    public static void remove(final SalesOrder order){
        MAP.remove(order.getId());
    }

    public static void clear(){
        MAP.clear();
    }
}
