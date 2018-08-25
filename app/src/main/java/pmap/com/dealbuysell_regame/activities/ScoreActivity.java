package pmap.com.dealbuysell_regame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import pmap.com.dealbuysell_regame.R;
import pmap.com.dealbuysell_regame.api.Api;
import pmap.com.dealbuysell_regame.api.WebServices;
import pmap.com.dealbuysell_regame.databases.RealmU;
import pmap.com.dealbuysell_regame.logics.P;
import pmap.com.dealbuysell_regame.models.PmapRgNextPhaseInput;
import pmap.com.dealbuysell_regame.models.PmapRgNextPhaseResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScoreActivity extends AppCompatActivity {
    @BindView(R.id.txt_score)
    TextView txt_score;
    @BindView(R.id.btn_next_level)
    ActionProcessButton btn_next_level;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        context = ScoreActivity.this;
        final int count = getIntent().getExtras().getInt("score");
        txt_score.setText("Your score is :" + count);

        btn_next_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReGameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nextphase();

    }

    private void nextphase() {
        Retrofit retrofit = Api.getRetrofitBuilder(context);
        WebServices webServices = retrofit.create(WebServices.class);
        //PREPARE INPUT/REQUEST PARAMETERS
        PmapRgNextPhaseInput PmapRgNextPhase = new PmapRgNextPhaseInput(
                new RealmU(context).getUserdetails().getUser_id(),
                getIntent().getStringExtra("count")
        );

        btn_next_level.setProgress(1);
        btn_next_level.setEnabled(false);
        P.hideSoftKeyboard(ScoreActivity.this);

        //CALL NOW
        webServices.PmapRgNextPhase(PmapRgNextPhase)
                .enqueue(new Callback<PmapRgNextPhaseResult>() {
                    @Override
                    public void onResponse(Call<PmapRgNextPhaseResult> call, Response<PmapRgNextPhaseResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_next_level.setProgress(0);
                            btn_next_level.setEnabled(true);
                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        PmapRgNextPhaseResult result = response.body();
                        if (result.is_success) {
                            btn_next_level.setProgress(100);
                            btn_next_level.setEnabled(true);
                        } else {
                            btn_next_level.setProgress(0);
                            btn_next_level.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<PmapRgNextPhaseResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_next_level.setProgress(0);
                        btn_next_level.setEnabled(true);

                    }
                });
    }

}


