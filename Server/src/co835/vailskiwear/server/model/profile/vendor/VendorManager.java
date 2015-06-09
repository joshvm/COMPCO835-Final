package co835.vailskiwear.server.model.profile.vendor;

import co835.vailskiwear.server.model.profile.ProfileManager;
import org.w3c.dom.Element;
import res.Res;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class VendorManager extends ProfileManager<Vendor>{

    private static VendorManager instance;

    public VendorManager(){
        super(Res.xml("vendors"), "vendor");
    }

    public Vendor parse(final Element e){
        return Vendor.parse(e);
    }

    public static VendorManager getInstance(){
        if(instance == null)
            instance = new VendorManager();
        return instance;
    }
}
