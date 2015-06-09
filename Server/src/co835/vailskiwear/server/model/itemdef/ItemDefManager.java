package co835.vailskiwear.server.model.itemdef;

import co835.vailskiwear.server.model.EntityManager;
import org.w3c.dom.Element;
import res.Res;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemDefManager extends EntityManager<ItemDef> {

    private static ItemDefManager instance;

    public ItemDefManager(){
        super(Res.xml("items"), "item");
    }

    protected ItemDef parse(final Element e){
        return ItemDef.parse(e);
    }

    public static ItemDefManager getInstance(){
        if(instance == null)
            instance = new ItemDefManager();
        return instance;
    }
}
