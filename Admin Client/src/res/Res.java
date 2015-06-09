package res;

import javax.swing.ImageIcon;
import java.io.File;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public final class Res {

    public static final ImageIcon LOGO = icon("logo");
    public static final ImageIcon ORDER_16 = icon("order16");
    public static final ImageIcon ORDER_REMOVE_16 = icon("order_remove16");
    public static final ImageIcon ADD_16 = icon("add16");
    public static final ImageIcon REMOVE_16 = icon("remove16");
    public static final ImageIcon CLEAR_16 = icon("clear16");
    public static final ImageIcon PROFILE_16 = icon("profile16");
    public static final ImageIcon PROFILE_EDIT_16 = icon("profile_edit16");
    public static final ImageIcon PROFILE_ADD_32 = icon("profile_add32");
    public static final ImageIcon PROFILE_ADD_16 = icon("profile_add16");
    public static final ImageIcon PROFILE_REMOVE_32 = icon("profile_remove32");
    public static final ImageIcon PROFILE_REMOVE_16 = icon("profile_remove16");
    public static final ImageIcon LOGOUT_32 = icon("logout32");
    public static final ImageIcon ORDER_PENDING_16 = icon("order_pending16");
    public static final ImageIcon ORDER_DECLINED_16 = icon("order_declined16");
    public static final ImageIcon ORDER_APPROVED_16 = icon("order_approved16");
    public static final ImageIcon ORDER_SHIPPED_16 = icon("order_shipped16");
    public static final ImageIcon PRODUCT_16 = icon("product16");
    public static final ImageIcon PRODUCT_32 = icon("product32");
    public static final ImageIcon PRODUCT_ADD_16 = icon("product_add16");
    public static final ImageIcon PRODUCT_REMOVE_16 = icon("product_remove16");
    public static final ImageIcon PRODUCT_EDIT_16 = icon("product_edit16");

    private Res(){}

    public static ImageIcon icon(final String name){
        return new ImageIcon(Res.class.getResource(String.format("img/%s.png", name)));
    }

    public static File xml(final String name){
        return new File(new File("res", "xml"), name + ".xml");
    }
}
