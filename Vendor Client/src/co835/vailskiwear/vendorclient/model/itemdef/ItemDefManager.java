package co835.vailskiwear.vendorclient.model.itemdef;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import res.Res;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class ItemDefManager {

    private static final File FILE = Res.xml("items");
    private static final Map<Integer, ItemDef> ITEMS = new HashMap<>();

    private ItemDefManager(){}

    public static ItemDef get(final int id){
        return ITEMS.get(id);
    }

    public static Collection<ItemDef> get(){
        return ITEMS.values();
    }

    public static ItemDef[] getArray(){
        final Collection<ItemDef> collection = get();
        return collection.toArray(new ItemDef[collection.size()]);
    }

    public static boolean load(){
        try{
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder bldr = factory.newDocumentBuilder();
            final Document doc = bldr.parse(FILE);
            final NodeList items = doc.getElementsByTagName("item");
            for(int i = 0; i < items.getLength(); i++){
                final Node n = items.item(i);
                if(n.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                final Element e = (Element) n;
                final ItemDef item = ItemDef.parse(e);
                ITEMS.put(item.getId(), item);
            }
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
