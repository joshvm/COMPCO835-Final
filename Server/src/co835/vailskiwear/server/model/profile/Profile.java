package co835.vailskiwear.server.model.profile;

import co835.vailskiwear.server.model.Entity;
import co835.vailskiwear.server.net.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public abstract class Profile extends Entity {

    public static final AttributeKey<Profile> KEY = AttributeKey.valueOf("profile");

    private final String user;
    private String pass;
    private String email;
    private String phoneNumber;

    public ChannelHandlerContext ctx;

    public Profile(final int id, final String user, final String pass, final String email, final String phoneNumber){
        super(id);
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isOnline(){
        return ctx != null;
    }

    public void disconnect(){
        if(ctx != null)
            ctx.close();
    }

    public boolean send(final Packet pkt){
        if(!isOnline())
            return false;
        System.out.println("sending: " + pkt.getPayload());
        ctx.writeAndFlush(pkt);
        return true;
    }

    public String getUser(){
        return user;
    }

    public String getPass(){
        return pass;
    }

    public void setPass(final String pass){
        this.pass = pass;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(final String email){
        this.email = email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    protected Element createElement(final Document doc, final String tag){
        final Element profile = super.createElement(doc, tag);
        final Element user = doc.createElement("user");
        user.setTextContent(this.user);
        final Element pass = doc.createElement("pass");
        pass.setTextContent(this.pass);
        final Element email = doc.createElement("email");
        email.setTextContent(this.email);
        final Element phoneNumber = doc.createElement("phoneNumber");
        phoneNumber.setTextContent(this.phoneNumber);
        profile.appendChild(user);
        profile.appendChild(pass);
        profile.appendChild(email);
        profile.appendChild(phoneNumber);
        return profile;
    }

    public static Profile parse(final Element e){
        final int id = Integer.parseInt(e.getAttribute("id"));
        final String user = e.getElementsByTagName("user").item(0).getTextContent();
        final String pass = e.getElementsByTagName("pass").item(0).getTextContent();
        final String email = e.getElementsByTagName("email").item(0).getTextContent();
        final String phoneNumber = e.getElementsByTagName("phoneNumber").item(0).getTextContent();
        return new Profile(id, user, pass, email, phoneNumber) {
            public Element toElement(Document doc){
                return null;
            }
        };
    }
}
