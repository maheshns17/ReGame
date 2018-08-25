package pmap.com.dealbuysell_regame.models;

/**
 * Created by admin on 17/02/2017.
 */
public class PmapRgUserRegisterInput {
    public String user_name;
    public String user_mobile_number;
    public String user_email_id;
    public String user_password;

    public PmapRgUserRegisterInput(String user_name, String user_mobile_number, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_mobile_number = user_mobile_number;
        this.user_email_id = user_email;
        this.user_password = user_password;
    }
}