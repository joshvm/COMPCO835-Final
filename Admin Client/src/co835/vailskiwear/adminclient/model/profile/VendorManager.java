package co835.vailskiwear.adminclient.model.profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class VendorManager {

    private static final Map<Integer, Vendor> MAP = new HashMap<>();

    private VendorManager(){}

    public static void add(final Vendor vendor){
        MAP.put(vendor.getId(), vendor);
    }

    public static void remove(final Vendor vendor){
        MAP.remove(vendor.getId());
    }

    public static void clear(){
        MAP.clear();
    }

    public static Vendor get(final int id){
        return MAP.get(id);
    }

    public static Collection<Vendor> get(){
        return MAP.values();
    }
}
