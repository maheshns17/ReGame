package pmap.com.dealbuysell_regame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import pmap.com.dealbuysell_regame.R;
import pmap.com.dealbuysell_regame.api.Api;
import pmap.com.dealbuysell_regame.api.WebServices;
import pmap.com.dealbuysell_regame.databases.RealmU;
import pmap.com.dealbuysell_regame.logics.P;
import pmap.com.dealbuysell_regame.models.PmapRgReferenceInput;
import pmap.com.dealbuysell_regame.models.PmapRgReferenceResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BuyActivity extends AppCompatActivity {
    @BindView(R.id.btn_submit_reference_no)
    ActionProcessButton btn_submit_reference_no;

    @BindView(R.id.edt_reference_no)
    EditText edt_reference_no;


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        ButterKnife.bind(this);
        context = BuyActivity.this;

//        getSupportActionBar().setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));

        btn_submit_reference_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processNext();

            }
        });
    }

    private void processNext() {

        Retrofit retrofit = Api.getRetrofitBuilder(context);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        PmapRgReferenceInput pmapRgReference = new PmapRgReferenceInput(edt_reference_no.getText().toString().trim(),
                new RealmU(context).getUserdetails().getUser_id()
        );

        btn_submit_reference_no.setProgress(1);
        btn_submit_reference_no.setEnabled(false);
        P.hideSoftKeyboard(BuyActivity.this);

        //CALL NOW
        webServices.PmapRgReference(pmapRgReference)
                .enqueue(new Callback<PmapRgReferenceResult>() {
                    @Override
                    public void onResponse(Call<PmapRgReferenceResult> call, Response<PmapRgReferenceResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_submit_reference_no.setProgress(0);
                            btn_submit_reference_no.setEnabled(true);
                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PmapRgReferenceResult result = response.body();
                        if (result.is_success) {
                            btn_submit_reference_no.setProgress(100);
                            btn_submit_reference_no.setEnabled(true);
                            Intent intent = new Intent(context, ReGameActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            btn_submit_reference_no.setProgress(0);
                            btn_submit_reference_no.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.alert), result.msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<PmapRgReferenceResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_submit_reference_no.setProgress(0);
                        btn_submit_reference_no.setEnabled(true);
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.alert), getString(R.string.internet_connection));

                    }
                });
    }


}