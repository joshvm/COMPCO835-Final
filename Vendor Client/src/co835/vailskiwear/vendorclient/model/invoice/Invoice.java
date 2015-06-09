package co835.vailskiwear.vendorclient.model.invoice;

import co835.vailskiwear.vendorclient.model.Entity;

import java.util.Date;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Invoice extends Entity {

    private final Date date;

    public Invoice(final int id, final Date date){
        super(id);
        this.date = date;
    }

    public Date getDate(){
        return date;
    }

}
