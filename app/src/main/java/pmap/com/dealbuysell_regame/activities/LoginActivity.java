package pmap.com.dealbuysell_regame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import pmap.com.dealbuysell_regame.R;
import pmap.com.dealbuysell_regame.api.Api;
import pmap.com.dealbuysell_regame.api.WebServices;
import pmap.com.dealbuysell_regame.custom_views.EditTextRoboto_Light;
import pmap.com.dealbuysell_regame.custom_views.TextViewRoboto_Light;
import pmap.com.dealbuysell_regame.databases.RealmU;
import pmap.com.dealbuysell_regame.logics.P;
import pmap.com.dealbuysell_regame.models.PmapRgUserLoginInput;
import pmap.com.dealbuysell_regame.models.PmapRgUserLoginResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edt_mobile_number)
    EditTextRoboto_Light edt_mobile_number;
    @BindView(R.id.edt_password)
    EditTextRoboto_Light edt_password;
    @BindView(R.id.btn_submit)
    ActionProcessButton btn_submit;
    @BindView(R.id.txt_register)
    TextViewRoboto_Light txt_register;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = LoginActivity.this;
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
        if (new RealmU(context).isUserLoggedIn()) {
            moveToNextActivity();
        }

    }


    public void validation() {
        if (!P.isValidEditText(edt_mobile_number, "Mobile number")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        userLogin();
    }


    private void userLogin() {

        Retrofit retrofit = Api.getRetrofitBuilder(context);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        PmapRgUserLoginInput userLoginInput = new PmapRgUserLoginInput(
                edt_mobile_number.getText().toString().trim(),
                edt_password.getText().toString().trim()
        );

        btn_submit.setProgress(1);
        btn_submit.setEnabled(false);
        P.hideSoftKeyboard(LoginActivity.this);

        //CALL NOW
        webServices.PmapRgLogin(userLoginInput)
                .enqueue(new Callback<PmapRgUserLoginResult>() {
                    @Override
                    public void onResponse(Call<PmapRgUserLoginResult> call, Response<PmapRgUserLoginResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PmapRgUserLoginResult result = response.body();
                        if (result.is_success) {
                            btn_submit.setProgress(100);
                            btn_submit.setEnabled(true);
                            P.USER_ID = result.user.get(0).user_id;
                            if (new RealmU(context).insertUserDetails(result.user.get(0))) {
                                moveToNextActivity();
                            } else {
                                Toast.makeText(context, "Something went wrong with realm", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            btn_submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PmapRgUserLoginResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_submit.setProgress(0);
                        btn_submit.setEnabled(true);

                    }
                });
    }

    private void moveToNextActivity() {
        if (new RealmU(context).isUserPaid()) {
            Intent intent = new Intent(context, ReGameActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(context, BuyActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
