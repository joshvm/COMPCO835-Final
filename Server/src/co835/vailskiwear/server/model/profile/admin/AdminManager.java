package co835.vailskiwear.server.model.profile.admin;

import co835.vailskiwear.server.model.profile.ProfileManager;
import org.w3c.dom.Element;
import res.Res;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class AdminManager extends ProfileManager<Admin>{

    private static AdminManager instance;

    public AdminManager(){
        super(Res.xml("admins"), "admin");
    }

    public Admin parse(final Element e){
        return Admin.parse(e);
    }

    public static AdminManager getInstance(){
        if(instance == null)
            instance = new AdminManager();
        return instance;
    }
}
