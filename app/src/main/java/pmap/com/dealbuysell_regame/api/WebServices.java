package pmap.com.dealbuysell_regame.api;
import pmap.com.dealbuysell_regame.models.PmapRgNextPhaseInput;
import pmap.com.dealbuysell_regame.models.PmapRgNextPhaseResult;
import pmap.com.dealbuysell_regame.models.PmapRgQuestionInput;
import pmap.com.dealbuysell_regame.models.PmapRgQuestionResult;
import pmap.com.dealbuysell_regame.models.PmapRgReferenceInput;
import pmap.com.dealbuysell_regame.models.PmapRgReferenceResult;
import pmap.com.dealbuysell_regame.models.PmapRgUserLoginInput;
import pmap.com.dealbuysell_regame.models.PmapRgUserLoginResult;
import pmap.com.dealbuysell_regame.models.PmapRgUserRegisterInput;
import pmap.com.dealbuysell_regame.models.PmapRgUserRegisterResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 17/02/2017.
 */
public interface WebServices {
    @POST("PmapRgLogin_c/userLogin")
    Call<PmapRgUserLoginResult> PmapRgLogin(@Body PmapRgUserLoginInput input);

    @POST("PmapRgUserRegister_c/userRegister")
    Call<PmapRgUserRegisterResult> PmapRgRegister(@Body PmapRgUserRegisterInput Input);

    @POST("PmapGetQuestions_c/userQuestions")
    Call<PmapRgQuestionResult> PmapRgQuestions(@Body PmapRgQuestionInput Input);

    @POST("PmapValidatePayment_c/uservalidation")
    Call<PmapRgReferenceResult> PmapRgReference(@Body PmapRgReferenceInput Input);

    @POST("PmapPhaseCompletion_c/onCompletion")
    Call<PmapRgNextPhaseResult> PmapRgNextPhase(@Body PmapRgNextPhaseInput Input);
}