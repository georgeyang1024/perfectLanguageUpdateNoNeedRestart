package cn.georgeyang.languageupdate;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import cn.georgeyang.languageupdate.util.ViewUtil;

/**
 * Created by george.yang on 2016-4-27.
 */
public class LangeChangeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        EventBus.getDefault().register(this);

        findViewById(R.id.btn_english).setOnClickListener(this);
        findViewById(R.id.btn_chinese).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        Log.d("test","LangeChangeActivity got message:" +  event);
        ViewUtil.updateViewLanguage(findViewById(android.R.id.content));
    }

    public void switchLanguage(Locale locale) {
        Configuration config = getResources().getConfiguration();// 获得设置对象
        Resources resources = getResources();// 获得res资源对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_english:
                switchLanguage(Locale.ENGLISH);
                break;
            case R.id.btn_chinese:
                switchLanguage(Locale.CHINESE);
                break;
        }
        ClassEvent event = new ClassEvent();
        event.msg = "do it";
        EventBus.getDefault().post(event);

        Toast.makeText(this,"!!!!!!!!!",Toast.LENGTH_SHORT).show();
    }
}
