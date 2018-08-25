package pmap.com.dealbuysell_regame.databases;

import io.realm.RealmObject;

/**
 * Created by Win10 on 17-02-2018.
 */


public class RUser extends RealmObject{
    private String user_id;
    private String user_name;
    private String user_mobile_number;
    private String user_email_id;
    private String user_password;
    private String user_que_from;
    private String user_que_to;
    private String user_payment_reference_no;
    private String is_user_paid_amount;

    public RUser() {

    }

    public RUser(String user_id, String user_name, String user_mobile_number, String user_email_id, String user_password, String user_que_from, String user_que_to, String user_payment_reference_no, String is_user_paid_amount) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_mobile_number = user_mobile_number;
        this.user_email_id = user_email_id;
        this.user_password = user_password;
        this.user_que_from = user_que_from;
        this.user_que_to = user_que_to;
        this.user_payment_reference_no = user_payment_reference_no;
        this.is_user_paid_amount = is_user_paid_amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile_number() {
        return user_mobile_number;
    }

    public void setUser_mobile_number(String user_mobile_number) {
        this.user_mobile_number = user_mobile_number;
    }

    public String getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(String user_email_id) {
        this.user_email_id = user_email_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_que_from() {
        return user_que_from;
    }

    public void setUser_que_from(String user_que_from) {
        this.user_que_from = user_que_from;
    }

    public String getUser_que_to() {
        return user_que_to;
    }

    public void setUser_que_to(String user_que_to) {
        this.user_que_to = user_que_to;
    }

    public String getUser_payment_reference_no() {
        return user_payment_reference_no;
    }

    public void setUser_payment_reference_no(String user_payment_reference_no) {
        this.user_payment_reference_no = user_payment_reference_no;
    }

    public String getIs_user_paid_amount() {
        return is_user_paid_amount;
    }

    public void setIs_user_paid_amount(String is_user_paid_amount) {
        this.is_user_paid_amount = is_user_paid_amount;
    }
}
