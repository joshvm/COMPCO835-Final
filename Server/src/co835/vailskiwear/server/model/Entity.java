package co835.vailskiwear.server.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public abstract class Entity {

    protected final int id;

    public Entity(final int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    protected Element createElement(final Document doc, final String tag){
        final Element e = doc.createElement(tag);
        e.setAttribute("id", Integer.toString(id));
        return e;
    }
    
    public abstract Element toElement(final Document doc);

    public static Entity parse(final Element e){
        return new Entity(Integer.parseInt(e.getAttribute("id"))) {
            @Override
            public Element toElement(Document doc){
                return null;
            }
        };
    }
}
