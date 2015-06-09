package res;

import java.io.File;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class Res {

    private Res(){}

    public static File xml(final String name){
        return new File(new File("res", "xml"), name + ".xml");
    }
}
