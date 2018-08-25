package pmap.com.dealbuysell_regame.models;

/**
 * Created by admin on 17/02/2017.
 */
public class PmapRgUserLoginInput {
    public String mobile_number;
    public String password;

    public PmapRgUserLoginInput(String user_mobile_number, String user_password) {
        this.mobile_number = user_mobile_number;
        this.password = user_password;
    }
}