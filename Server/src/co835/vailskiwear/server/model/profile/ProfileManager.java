package co835.vailskiwear.server.model.profile;

import co835.vailskiwear.server.model.EntityManager;

import java.io.File;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public abstract class ProfileManager<T extends Profile> extends EntityManager<T> {

    protected ProfileManager(final File file, final String tagName){
        super(file, tagName);
    }

    public T get(final String user){
        for(final T profile : map.values())
            if(profile.getUser().equalsIgnoreCase(user))
                return profile;
        return null;
    }
}
