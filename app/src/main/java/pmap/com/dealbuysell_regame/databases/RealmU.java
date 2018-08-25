package pmap.com.dealbuysell_regame.databases;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import pmap.com.dealbuysell_regame.models.User;

/**
 * Created by Win10 on 17-02-2018.
 */

public class RealmU {
    private Context context;
    private Realm realm;

    public RealmU(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    public boolean insertUserDetails(final User u) {
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RUser rUser = realm.createObject(RUser.class);
                    rUser.setUser_id(u.user_id);
                    rUser.setIs_user_paid_amount(u.is_user_paid_amount);
                    rUser.setUser_email_id(u.user_email_id);
                    rUser.setUser_mobile_number(u.user_mobile_number);
                    rUser.setUser_name(u.user_name);
                    rUser.setUser_payment_reference_no(u.user_payment_reference_no);
                    rUser.setUser_que_from(u.user_que_from);
                    rUser.setUser_que_to(u.user_que_to);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isUserPaid() {
        try {
            RealmResults<RUser> user = realm.where(RUser.class).equalTo("is_user_paid_amount", "1")
                    .findAll();
            if (user == null || user.size() == 0) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public boolean truncateUserTable() {
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(RUser.class);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public RUser getUserdetails() {
        try {
            RUser user = realm.where(RUser.class).findFirst();
            return user;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public boolean isUserLoggedIn() {
        try {
            RealmResults<RUser> user = realm.where(RUser.class).findAll();
            if (user.size() > 0) return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserPaymentStatus(final String referenceNo, final String status){

        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    RUser user = realm.where(RUser.class).findFirst();
                    user.setUser_payment_reference_no(referenceNo);
                    user.setIs_user_paid_amount(status);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
