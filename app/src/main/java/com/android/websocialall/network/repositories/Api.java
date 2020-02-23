package com.android.websocialall.network.repositories;



import com.android.websocialall.ui.login.models.LoginRespons;
import com.android.websocialall.ui.signup.models.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST(Constant.SIGNUP)
    Call<SignupResponse> getSignupRes(@Field("email") String email,
                                      @Field("pass") String pass);

    // Doctor login
    @POST(Constant.SIGNIN)
    Call<LoginRespons> userlogin(
            @Query("email") String email,
            @Query("password") String password);


//    @FormUrlEncoded
//    @POST(Common.ACCOUNT)
//    Call<SignupResponse> signupUser(
//            @Field("type") String type,
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("phone") String phone,
//            @Field("pass") String pass,
//            @Field("profession") String profession
//    );
//
//
//    @FormUrlEncoded
//    @POST(Common.ACCOUNT)
//    Call<LoginResponse> loginUser(
//            @Field("type") String type,
//            @Field("email") String email,
//            @Field("password") String pass
//    );
//
//    @FormUrlEncoded
//    @POST(Common.ACCOUNT)
//    Call<ForgotPassResponse> forgorPass(
//            @Field("type") String type,
//            @Field("email") String email
//    );
//
//    @GET(Common.HOME)
//    Call<HomeResponse> configureHome(@Query("user_id") String userID);
//
//    @GET(Common.IC_MOKE_TEST)
//    Call<IcMokeTestResponse> getIcMokeTest(@Query("cat_id") String catID,
//                                           @Query("user_id") String userID);
//
//    @GET(Common.IC_CHAPTER_TEST)
//    Call<IcMokeTestResponse> getIcChapterTest(@Query("cat_id") String catID,
//                                              @Query("user_id") String userID);
//
//    @GET(Common.QUESTION_LIST)
//    Call<QuestionListResponse> getExamQuestion(@Query("exam_id") String examID);
//
//    @FormUrlEncoded
//    @POST(Common.SUBMIT_RESULT)
//    Call<SubmitResResponse> getSubmitResult(
//            @Field("user_id") String userID,
//            @Field("exam_id") String examID,
//            @Field("ans") String answerArray);
//
//
//    @GET(Common.ACCOUNT_DATA)
//    Call<AccountResponse> profileView(@Query("user_id") String userID);
//
//    @GET(Common.PACKAGE_DATA)
//    Call<List<PackageResponse>> packageItem(@Query("action") String action);
//
//    @GET(Common.PACKAGE_DATA)
//    Call<List<PackageListItemResponse>> packageListItem(@Query("cat_id") String cat_id);
//
//    @GET(Common.NOTES_DATA)
//    Call<NoteItemResponse> getNoteItem(@Query("lang_id") String lang_id);
}
