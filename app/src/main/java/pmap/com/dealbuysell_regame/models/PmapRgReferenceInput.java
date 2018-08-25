package pmap.com.dealbuysell_regame.models;

/**
 * Created by Win10 on 17-02-2018.
 */

public class PmapRgReferenceInput {
    public String user_id;
    public String user_payment_reference_no;

    public PmapRgReferenceInput(String reference_no,String user_id) {
        this.user_id=user_id;
        this.user_payment_reference_no = reference_no;

    }
}
