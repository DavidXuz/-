package com.talkover.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.talkover.MyApplication;
import com.talkover.R;
import com.talkover.adapter.TemplateAdapter;
import com.talkover.model.AppEntity;
import com.talkover.model.TemplateEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemplateHomeActivity extends AppCompatActivity {

    private static final String KEY_APP_NAME = "key_app_name";
    private static final String KEY_ENTITY = "key_entity";

    public static void startActivity(Context ctx, Object object) {
        Intent intent = new Intent(ctx, TemplateHomeActivity.class);
        if (object instanceof String) {
            intent.putExtra(KEY_APP_NAME, (String) object);
        } else if (object instanceof AppEntity) {
            Gson gson = new Gson();
            intent.putExtra(KEY_ENTITY, gson.toJson(object));
        }
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_home);

        findViewById(R.id.back).setOnClickListener(it -> finish());

        TextView appNameTv = findViewById(R.id.template_home_app_name);
        RecyclerView rv = findViewById(R.id.main_template_list);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String appName = intent.getStringExtra(KEY_APP_NAME);
        AppEntity appEntity;
        if (appName != null) {
            appEntity = MyApplication.sDatabase.get(appName);
        } else {
            appEntity = new Gson().fromJson(intent.getStringExtra(KEY_ENTITY), AppEntity.class);
        }
        assert appEntity != null;

        appNameTv.setText(appEntity.getApp());
        List<TemplateEntity> templateEntities = appEntity.getTemplates();
        rv.setAdapter(new TemplateAdapter(templateEntities));

        Button templateBtn = findViewById(R.id.template_btn_download);
        if (MyApplication.sDatabase.containsKey(appEntity.getApp())) {
            templateBtn.setEnabled(false);
            templateBtn.setText("已下载该模版");
        }


        templateBtn.setOnClickListener(it -> MyApplication.sTemplateApi.downloadTemplate(appEntity.getAppId()).enqueue(new Callback<AppEntity>() {
            @Override
            public void onResponse(Call<AppEntity> call, Response<AppEntity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MyApplication.insert(response.body(), it.getContext());
                    templateBtn.setEnabled(false);
                    templateBtn.setText("已下载该模版");
                    Toast.makeText(it.getContext(), "模版下载成功", Toast.LENGTH_SHORT).show();
                } else {
                    onFailure(call, new RuntimeException());
                }
            }

            @Override
            public void onFailure(Call<AppEntity> call, Throwable t) {
                Toast.makeText(it.getContext(), "下载模版失败，请检查网络情况", Toast.LENGTH_SHORT).show();
            }
        }));

    }


}
