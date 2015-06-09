package co835.vailskiwear.vendorclient.model.itemdef;

import co835.vailskiwear.vendorclient.model.Entity;
import org.w3c.dom.Element;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class ItemDef extends Entity {

    private final String name;
    private final double price;

    public ItemDef(final int id, final String name, final double price){
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public static ItemDef parse(final Element e){
        final int id = Integer.parseInt(e.getAttribute("id"));
        final String name = e.getElementsByTagName("name").item(0).getTextContent();
        final double price = Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent());
        return new ItemDef(id, name, price);
    }
}
