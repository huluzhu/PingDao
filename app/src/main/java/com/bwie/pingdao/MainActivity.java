package com.bwie.pingdao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String json;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        json = sharedPreferences.getString("user_setting", null);
        if (json == null) {
            List<ChannelBean> list = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                ChannelBean bean = null;
                if (i < 5) {
                    bean = new ChannelBean("模块-" + i, true);
                } else {
                    bean = new ChannelBean("模块-" + i, false);
                }
                list.add(bean);
            }
            ChannelActivity.startChannelActivity(this, list);
        } else {
            ChannelActivity.startChannelActivity(this, json);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            json = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            sharedPreferences.edit().putString("user_setting", json).commit();
        }
    }
}
