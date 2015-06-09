package co835.vailskiwear.adminclient.model.profile;

import co835.vailskiwear.adminclient.model.Entity;

/**
 * I Josh Maione, 000320309 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 * I have not made my work available to anyone else.
 */
public class Profile extends Entity {

    private final String user;
    private String email;
    private String phoneNumber;

    public Profile(final int id, final String user, final String email, final String phoneNumber){
        super(id);
        this.user = user;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUser(){
        return user;
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
}
