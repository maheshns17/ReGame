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
import pmap.com.dealbuysell_regame.logics.P;
import pmap.com.dealbuysell_regame.models.PmapRgUserRegisterInput;
import pmap.com.dealbuysell_regame.models.PmapRgUserRegisterResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edt_fullname)
    EditTextRoboto_Light edt_fullname;
    @BindView(R.id.edt_email)
    EditTextRoboto_Light edt_email;
    @BindView(R.id.edt_mobile_number)
    EditTextRoboto_Light edt_mobile_number;
    @BindView(R.id.edt_password)
    EditTextRoboto_Light edt_password;
    @BindView(R.id.edt_confirm_password)
    EditTextRoboto_Light edt_confirm_password;
    @BindView(R.id.btn_submit)
    ActionProcessButton btn_submit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = RegisterActivity.this;

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });
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
        PmapRgUserRegisterInput UserLoginInput = new PmapRgUserRegisterInput(

                edt_fullname.getText().toString().trim(),
                edt_mobile_number.getText().toString().trim(),
                edt_email.getText().toString().trim(),
                edt_password.getText().toString().trim()
        );

        btn_submit.setProgress(1);
        btn_submit.setEnabled(false);
        P.hideSoftKeyboard(RegisterActivity.this);
        //CALL NOW
        webServices.PmapRgRegister(UserLoginInput)
                .enqueue(new Callback<PmapRgUserRegisterResult>() {
                    @Override
                    public void onResponse(Call<PmapRgUserRegisterResult> call, Response<PmapRgUserRegisterResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PmapRgUserRegisterResult result = response.body();
                        if (result.isSuccess) {
                            btn_submit.setProgress(100);
                            P.ShowSuccessDialog(context, "Success", result.msg);
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            btn_submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<PmapRgUserRegisterResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_submit.setProgress(0);
                        btn_submit.setEnabled(true);

                    }
                });
    }
}