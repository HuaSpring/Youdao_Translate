package com.fspt.practice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.fspt.practice.R;
import com.fspt.practice.base.BaseActivity;
import com.fspt.practice.bean.Fanyi_En_ZH_YouDao;
import com.fspt.practice.bean.Fanyi_ZH_EN_YouDao;
import com.fspt.practice.global.Constants;
import com.fspt.practice.net.TranslateApiService;
import com.fspt.practice.utils.CommonUtils;
import com.fspt.practice.utils.LoginCheckUtils;
import com.fspt.practice.utils.SPHelper;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PracticeMainActivity_YouDao extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivRight;
    private RelativeLayout rlTitle;
    private SPHelper spHelper;//数据 存储工具
    private boolean isLogin = false;

    private TranslateApiService apiService = null;
    private TranslateApiService apiServicerx = null;
    private TextView tv_result;// Translate result

    //    public static final String FANYI_URL = "http://fy.iciba.com/";
    public static final String FANYI_URL = "http://fanyi.youdao.com/";
    private EditText ed_word;
    private String defaultEN = "apple";
    private String defaultZH = "苹果";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
        addListener();
    }

    @Override
    public void loadData() {
    }

    @Override
    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        rlTitle = (RelativeLayout) findViewById(R.id.rlTitle);
        rlTitle.getBackground().setAlpha(180);//设置透明度
        ivRight.setVisibility(View.VISIBLE);//设置显示
        spHelper = SPHelper.getInstant(this);//获取实例
        ivBack.setImageResource(R.mipmap.icon);//消失不可见

        String ids = spHelper.getStringFromSP(this, Constants.IDS);
        tvTitle.setText("有道 Copyright By Author @ " + LoginCheckUtils.getNameByID(PracticeMainActivity_YouDao.this, ids));

        ed_word = (EditText) findViewById(R.id.ed_word);
        tv_result = (TextView) findViewById(R.id.tv_result);
//        mAppAction = new AppActionImpl(this);//实例化云平台操作类
        //接收登录页面传来的是否要登陆的信息
        // TODO  3.6.1：2、接收登录页面传来的是否要登陆的信息，决定是否在功能选择页面登录
        isLogin = getIntent().getBooleanExtra("isGoToLogin", false);
        if (isLogin) {
            String UserName = spHelper.getStringFromSP(this, Constants.USERNAME);
            login(UserName, ids);
            Log.d("HHHH", UserName + ":" + ids);
        }

        CommonUtils.autoSetClickListener(getWindow().getDecorView(), AppCompatButton.class, this, 500);
        CommonUtils.autoSetClickListener(getWindow().getDecorView(), AppCompatImageView.class, this, 500);
    }

    @Override
    public void addListener() {
    }


    /**
     * 设置登录信息
     */
    private void setLoginMSG() {
        Intent intent = new Intent(PracticeMainActivity_YouDao.this, SplashLoginActivity.class);
        intent.putExtra("isSetAccount", true);
        startActivity(intent);
    }


    private void login(String userName, String ids) {
        boolean b = LoginCheckUtils.checkLogin(PracticeMainActivity_YouDao.this, ids);
        if (b) {
            Toast.makeText(PracticeMainActivity_YouDao.this, "登陆成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PracticeMainActivity_YouDao.this, "该学号不存在，请检查", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PracticeMainActivity_YouDao.this, SplashLoginActivity.class);
            intent.putExtra("isSetAccount", true);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                //点击右上角设置按钮跳到设置登陆信息页面
                setLoginMSG();
                finish();
                break;
            case R.id.btn_zh_en:
                getFanyi_ZH_EN();
                break;
            case R.id.btn_en_zh:
                getFanyi_EN_ZH_Normal();
                break;
            case R.id.btn_rxjava:
                rxFanyi_EN_ZH_RX();
                break;
            default:
                break;
        }
    }

    /**
     * Eng -> China  英 -> 汉
     */
    private void getFanyi_EN_ZH_Normal() {
        getFanyi_ZH_EN();
    }

    /**
     * ZH -> China  汉 -> 英
     */
    private void getFanyi_ZH_EN(){
        getFanyi_ZH_EN("");
    }

    private void getFanyi_ZH_EN(String debugWord) {
        if (apiService == null) {
            // 获取Retrofit实例
            Retrofit retrofit = createRetrofit();
            // 通过代理方式创建ApiService 得到一个接口的实例
            apiService = retrofit.create(TranslateApiService.class);
        }
        // 调用方法返回Call请求对象

        String word;
        if(TextUtils.isEmpty(debugWord)) {
            word = ed_word.getText().toString().trim();
            word = TextUtils.isEmpty(word) ? defaultEN : word;
        } else {
            word = debugWord;
        }

        Call<Fanyi_ZH_EN_YouDao> call = apiService.getFanyiResult_ZH_EN(word);
        call.enqueue(new Callback<Fanyi_ZH_EN_YouDao>() {
            @Override
            public void onResponse(Call<Fanyi_ZH_EN_YouDao> call, retrofit2.Response<Fanyi_ZH_EN_YouDao> response) {
                if( response.code() == 200){
                    assert response.body() != null;
                    StringBuilder sb = new StringBuilder();
                    List<List<Fanyi_ZH_EN_YouDao.TranslateResultDTO>> translateResult = response.body().getTranslateResult();
                    int translateResultSize =  translateResult.size();
                    if( translateResultSize >= 1){
                        for( int i = 0;i<translateResultSize;i++){
                            List<Fanyi_ZH_EN_YouDao.TranslateResultDTO> translateResultEntities = translateResult.get(i);
                            for( Fanyi_ZH_EN_YouDao.TranslateResultDTO ret : translateResultEntities){
                                sb.append(ret.getTgt());
                            }
                        }
                    }
                    tv_result.setText(sb);
                }
            }

            @Override
            public void onFailure(Call<Fanyi_ZH_EN_YouDao> call, Throwable t) {
                Log.e("HHHH", " Retrofit  Call onFailure: "  );
            }
        });
    }


    // 4. 创建 Reprofit 实例
    private Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FANYI_URL)
                // 设置OKHttpClient,如果不设置会提供一个默认的
                .client(getOkHttpClient())
                // 添加Gson配置转化库
                .addConverterFactory(GsonConverterFactory.create())
                // 添加对Rxjava2的适配(只用Retrofit访问网络不需要添加对RxJava的适配)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    private OkHttpClient getOkHttpClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .removeHeader("User-Agent")//移除旧的
                                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(getBaseContext()))//添加真正的头部
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }


    /*该方法用于调用接口ApiService中定义的以Observable模式返回的登陆方法，实现云平台的登录。
        在“Retrofit+Rxjava”按钮单击事件中调用该方法。
     */

    public void rxFanyi_EN_ZH_RX(String debugWord){
        if (apiServicerx == null) {
            // 获取Retrofit实例
            Retrofit retrofit = createRetrofit();
            // 通过代理方式创建ApiService 得到一个接口的实例
            apiServicerx = retrofit.create(TranslateApiService.class);
        }

        String word;
        if(TextUtils.isEmpty(debugWord)) {
            word = ed_word.getText().toString().trim();
            word = TextUtils.isEmpty(word) ? defaultEN : word;
        } else {
            word = debugWord;
        }
        StringBuilder sb = new StringBuilder("");
        apiServicerx.getYouDao(word).subscribeOn(Schedulers.newThread()).subscribe(new Observer<Fanyi_En_ZH_YouDao>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(Fanyi_En_ZH_YouDao value) {
                Log.d("HHHH", "++++++++  onNext +++++ " );
                if( value!=null){
                    List<List<Fanyi_En_ZH_YouDao.TranslateResultEntity>> translateResult = value.getTranslateResult();
                    int translateResultSize =  translateResult.size();
                    if( translateResultSize >= 1){
                        for( int i = 0;i<translateResultSize;i++){
                            List<Fanyi_En_ZH_YouDao.TranslateResultEntity> translateResultEntities = translateResult.get(i);
                            for( Fanyi_En_ZH_YouDao.TranslateResultEntity ret : translateResultEntities){
                                sb.append(ret.getTgt());
                            }
                        }
                    }
                    Log.d("HHHH", "onNext: " +sb.toString());
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d("HHHH", "RX onFailure: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                // 显示结果
                tv_result.setText(sb.toString());
            }
        });
    }

    public void rxFanyi_EN_ZH_RX() {
        rxFanyi_EN_ZH_RX("");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Debug
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter fout, @Nullable String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("MainTestActivity ######################").append("args").append(Arrays.toString(args));
        fout.println(sb);
        if (args != null) {
            for (String test : args) {
                switch (test) {
                    case "zh":
                        getFanyi_ZH_EN("好人");
                        break;
                    case "en":
                        rxFanyi_EN_ZH_RX("good");
                        break;
                }
            }
        }
    }


}