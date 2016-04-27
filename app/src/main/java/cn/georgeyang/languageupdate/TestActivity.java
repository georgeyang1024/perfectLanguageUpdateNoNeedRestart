package cn.georgeyang.languageupdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.georgeyang.languageupdate.util.ViewUtil;

/**
 * Created by george.yang on 2016-4-27.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        EventBus.getDefault().register(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, LangeChangeActivity.class
                ));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        Log.d("test","TestActivity got message:" +  event);
        ViewUtil.updateViewLanguage(findViewById(android.R.id.content));
    }
}
