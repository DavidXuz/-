package com.talkover.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.talkover.MyApplication;
import com.talkover.R;
import com.talkover.adapter.AppAdapter;
import com.talkover.model.AppEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppHomeActivity extends AppCompatActivity {

    private static final String KEY_CONDITION = "key_condition";

    public static void startActivity(Context ctx) {
        Intent intent = new Intent(ctx, AppHomeActivity.class);
        ctx.startActivity(intent);
    }

    public static void startActivity(Context ctx, String condition) {
        Intent intent = new Intent(ctx, AppHomeActivity.class);
        intent.putExtra(KEY_CONDITION, condition);
        ctx.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        findViewById(R.id.back).setOnClickListener(it -> finish());
        RecyclerView rv = findViewById(R.id.main_template_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        AppAdapter appAdapter = new AppAdapter();
        rv.setAdapter(appAdapter);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra(KEY_CONDITION);
        Callback<List<AppEntity>> callback = new Callback<List<AppEntity>>() {
            @Override
            public void onResponse(Call<List<AppEntity>> call, Response<List<AppEntity>> response) {
                if (response.isSuccessful()) {
                    List<AppEntity> data = response.body();
                    runOnUiThread(() -> appAdapter.setData(data));
                } else {
                    onFailure(call, new RuntimeException());
                }
            }

            @Override
            public void onFailure(Call<List<AppEntity>> call, Throwable t) {
                Toast.makeText(AppHomeActivity.this, "网络状况异常，请确认网络是否开启", Toast.LENGTH_SHORT).show();
            }
        };
        if (keyword == null) {
            MyApplication.sTemplateApi.getTemplateList().enqueue(callback);
        } else {
            MyApplication.sTemplateApi.query(keyword).enqueue(callback);
        }
    }
}
