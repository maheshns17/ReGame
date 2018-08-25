package pmap.com.dealbuysell_regame.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import pmap.com.dealbuysell_regame.R;
import pmap.com.dealbuysell_regame.api.Api;
import pmap.com.dealbuysell_regame.api.WebServices;
import pmap.com.dealbuysell_regame.custom_views.TextViewRoboto_Light;
import pmap.com.dealbuysell_regame.custom_views.TextViewRoboto_Regular;
import pmap.com.dealbuysell_regame.databases.RealmU;
import pmap.com.dealbuysell_regame.logics.P;
import pmap.com.dealbuysell_regame.models.PmapRgQuestionInput;
import pmap.com.dealbuysell_regame.models.PmapRgQuestionResult;
import pmap.com.dealbuysell_regame.setgets.QuestionsAndAnswers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReGameActivity extends AppCompatActivity implements View.OnClickListener {
    // int user_id = getIntent().getExtras().getInt("user_id");
    int count = 0;

    CountDownTimer timer = null;
    @BindView(R.id.txt_question_attended)
    TextViewRoboto_Light txt_question_attended;
    @BindView(R.id.txt_time_left)
    TextViewRoboto_Light txt_time_left;
    @BindView(R.id.txt_question)
    TextViewRoboto_Regular txt_question;
    @BindView(R.id.txt_option_1)
    TextViewRoboto_Light txt_option_1;
    @BindView(R.id.txt_option_2)
    TextViewRoboto_Light txt_option_2;
    @BindView(R.id.txt_option_3)
    TextViewRoboto_Light txt_option_3;
    @BindView(R.id.txt_option_4)
    TextViewRoboto_Light txt_option_4;

    @BindView(R.id.image_View_question)
    ImageView image_View_question;

    private Context context;

    private List<QuestionsAndAnswers> questionsAndAnswers = new ArrayList<>();
    private int numberOfQuestionsAttended = 0;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_re_game);
        ButterKnife.bind(this);
        context = ReGameActivity.this;

        //getSupportActionBar().setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher));


        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Processing...");
        pDialog.setCancelable(false);


        txt_option_1.setOnClickListener(this);
        txt_option_2.setOnClickListener(this);
        txt_option_3.setOnClickListener(this);
        txt_option_4.setOnClickListener(this);
        getQuestions();

    }

    private void getQuestions() {
        Retrofit retrofit = Api.getRetrofitBuilder(context);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        PmapRgQuestionInput pmapRgQuestionInput = new PmapRgQuestionInput(
                new RealmU(context).getUserdetails().getUser_id()
        );
        pDialog.show();
        //CALL NOW
        webServices.PmapRgQuestions(pmapRgQuestionInput)
                .enqueue(new Callback<PmapRgQuestionResult>() {
                    @Override
                    public void onResponse(Call<PmapRgQuestionResult> call, Response<PmapRgQuestionResult> response) {
                        if (pDialog.isShowing()) pDialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            P.ShowErrorDialogAndExit(context, getString(R.string.alert), getString(R.string.server_error));
                            return;
                        }
                        PmapRgQuestionResult result = response.body();
                        if (result.is_success) {
                            questionsAndAnswers = result.questions;
                            ShowSuccessDialog(context, "Are you Ready?", "Start Game");
                        } else {
                            P.ShowErrorDialogAndExit(context, getString(R.string.alert), result.msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<PmapRgQuestionResult> call, Throwable t) {
                        if (pDialog.isShowing()) pDialog.dismiss();
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        P.ShowErrorDialogAndExit(context, getString(R.string.alert), getString(R.string.internet_connection));
                    }
                });
    }

    public void ShowSuccessDialog(final Context context, final String tittle, final String msg) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText(tittle);
        pDialog.setContentText(msg);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (sweetAlertDialog.isShowing()) sweetAlertDialog.dismiss();
                showNextQuestion(numberOfQuestionsAttended);
            }
        });
    }

    private void countDownTimer(int time) {
        timer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                txt_time_left.setText("Time Left : " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                countDownTimer(60000);
            }
        };
        timer.start();

    }


    private void showNextQuestion(int pos) {

        if (pos != 0) {
            timer.cancel();
            txt_time_left.setText("");
        }
        countDownTimer(20000);

        txt_option_1.setTextColor(Color.BLACK);
        txt_option_2.setTextColor(Color.BLACK);
        txt_option_3.setTextColor(Color.BLACK);
        txt_option_4.setTextColor(Color.BLACK);
        txt_option_2.setBackgroundColor(Color.WHITE);
        txt_option_1.setBackgroundColor(Color.WHITE);
        txt_option_3.setBackgroundColor(Color.WHITE);
        txt_option_4.setBackgroundColor(Color.WHITE);
        txt_option_1.setBackgroundResource(R.drawable.rounded_corner);
        txt_option_2.setBackgroundResource(R.drawable.rounded_corner);
        txt_option_3.setBackgroundResource(R.drawable.rounded_corner);
        txt_option_4.setBackgroundResource(R.drawable.rounded_corner);
        txt_option_1.setPadding(15, 15, 15, 15);
        txt_option_2.setPadding(15, 15, 15, 15);
        txt_option_3.setPadding(15, 15, 15, 15);
        txt_option_4.setPadding(15, 15, 15, 15);

        if (pos >= questionsAndAnswers.size()) {
            return;
        }
        QuestionsAndAnswers qa = questionsAndAnswers.get(pos);
        txt_question.setText(qa.getQue_questions());
        txt_option_1.setText(qa.getQue_option1());
        txt_option_2.setText(qa.getQue_option2());
        txt_option_3.setText(qa.getQue_option3());
        txt_option_4.setText(qa.getQue_option4());

        if (qa.getQue_question_image() != null && qa.getQue_question_image().trim().length() > 0) {
            image_View_question.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(P.IMAGE_URL + qa.getQue_question_image()) //server path of the image
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.ic_launcher)) //this is optional the image to display while the url image is downloading
                    .error(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(image_View_question);
        } else {
            image_View_question.setVisibility(View.GONE);
        }
        //txt_question_attended.setText((pos + 1) + "/20");
    }

    @Override
    public void onClick(View view) {
        if (numberOfQuestionsAttended >= questionsAndAnswers.size()) {
            P.ShowErrorDialogAndExit(context, "Alert", "No More Questions Available");
            return;
        }
        QuestionsAndAnswers qa = questionsAndAnswers.get(numberOfQuestionsAttended);
        //makeAnswerGreen(qa.getQue_answer());
        switch (view.getId()) {
            case R.id.txt_option_1:
                txt_option_1.setTextColor(Color.BLACK);
                if (qa.getQue_answer() == 1) {
                    txt_option_1.setBackgroundColor(Color.GREEN);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.sweet);
                    mPlayer.start();
                    count++;

                    nextQuestion();
                } else {
                    txt_option_1.setBackgroundColor(Color.RED);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.warning);
                    mPlayer.start();

                    timer.cancel();
                    txt_time_left.setText("");
                    countDownTimer(300000);
                }
//                timer.cancel();
                txt_time_left.setText("");
                break;
            case R.id.txt_option_2:
                txt_option_2.setTextColor(Color.BLACK);
                if (qa.getQue_answer() == 2) {
                    txt_option_2.setBackgroundColor(Color.GREEN);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.sweet);
                    mPlayer.start();
                    count++;

                    timer.cancel();
                    txt_time_left.setText("");
                    nextQuestion();
                } else {
                    txt_option_2.setBackgroundColor(Color.RED);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.warning);
                    mPlayer.start();

                    timer.cancel();
                    txt_time_left.setText("");
                    countDownTimer(300000);
                }
//                timer.cancel();
                txt_time_left.setText("");
                break;

            case R.id.txt_option_3:
                txt_option_2.setTextColor(Color.BLACK);
                if (qa.getQue_answer() == 3) {
                    txt_option_3.setBackgroundColor(Color.GREEN);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.sweet);
                    mPlayer.start();
                    count++;
                    timer.cancel();
                    txt_time_left.setText("");
                    //Intent intent = new Intent(context, ScoreActivity.class);

                    nextQuestion();
                } else {
                    txt_option_3.setBackgroundColor(Color.RED);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.warning);
                    mPlayer.start();

                    timer.cancel();
                    txt_time_left.setText("");
                    countDownTimer(300000);
                }

//                timer.cancel();
                txt_time_left.setText("");

                break;
            case R.id.txt_option_4:
                txt_option_4.setTextColor(Color.BLACK);
                if (qa.getQue_answer() == 4) {
                    txt_option_4.setBackgroundColor(Color.GREEN);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.sweet);
                    mPlayer.start();
                    count++;

                    timer.cancel();
                    txt_time_left.setText("");

                    nextQuestion();
                } else {
                    txt_option_4.setBackgroundColor(Color.RED);
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.warning);
                    mPlayer.start();

                    timer.cancel();
                    txt_time_left.setText("");
                    countDownTimer(300000);
                }
//                timer.cancel();
                txt_time_left.setText("");
                break;
        }
    }

    private void makeAnswerGreen(int ansPos) {
        switch (ansPos) {
            case 1:
                txt_option_1.setTextColor(Color.WHITE);
                txt_option_1.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                txt_option_2.setTextColor(Color.WHITE);
                txt_option_2.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                txt_option_3.setTextColor(Color.WHITE);
                txt_option_3.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                txt_option_4.setTextColor(Color.WHITE);
                txt_option_4.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    private void nextQuestion() {
        txt_option_1.setEnabled(false);
        txt_option_2.setEnabled(false);
        txt_option_3.setEnabled(false);
        txt_option_4.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt_option_1.setEnabled(true);
                txt_option_2.setEnabled(true);
                txt_option_3.setEnabled(true);
                txt_option_4.setEnabled(true);
                numberOfQuestionsAttended++;
                if (numberOfQuestionsAttended >= P.MAX_QUESTIONS_PER_PHASE) {

                    timer.cancel();
                    txt_time_left.setText("");
                    Toast.makeText(context, "You have completed", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(context, ScoreActivity.class);
                    //  intent.putExtra("user_id",user_id);
                    intent.putExtra("score", count);
                    startActivity(intent);
                    finish();

                    return;
                }
                showNextQuestion(numberOfQuestionsAttended);
            }
        }, 2000);
    }
}