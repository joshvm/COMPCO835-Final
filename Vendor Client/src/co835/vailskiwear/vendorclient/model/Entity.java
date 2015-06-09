package co835.vailskiwear.vendorclient.model;

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
}
