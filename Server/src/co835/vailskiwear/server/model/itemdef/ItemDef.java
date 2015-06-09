package co835.vailskiwear.server.model.itemdef;

import co835.vailskiwear.server.model.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemDef extends Entity{

    private final String name;
    private final double price;

    public ItemDef(final int id, final String name, final double price){
        super(id);
        this.name = name;
        this.price = price;
    }

    public ItemDef(final String name, final double price){
        this(ItemDefManager.getInstance().size(), name, price);
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public Element toElement(final Document doc){
        final Element item = createElement(doc, "item");
        final Element name = doc.createElement("name");
        name.setTextContent(this.name);
        final Element price = doc.createElement("price");
        price.setTextContent(Double.toString(this.price));
        item.appendChild(name);
        item.appendChild(price);
        return item;
    }

    public static ItemDef parse(final Element e){
        final Entity entity = Entity.parse(e);
        final String name = e.getElementsByTagName("name").item(0).getTextContent();
        final double price = Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent());
        return new ItemDef(entity.getId(), name, price);
    }
}
