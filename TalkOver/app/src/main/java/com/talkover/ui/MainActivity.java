package com.talkover.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.talkover.MyApplication;
import com.talkover.R;
import com.talkover.adapter.AppAdapter;
import com.talkover.service.VoiceToTextService;

public class MainActivity extends AppCompatActivity {

    private AppAdapter mAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent voiceToText = new Intent(this, VoiceToTextService.class);
        startService(voiceToText);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // show all template to download
        findViewById(R.id.main_show_all).setOnClickListener(it -> {
            AppHomeActivity.startActivity(this);
        });

        // search view
        SearchView searchView = findViewById(R.id.main_search);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.searchHintColor));
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp16));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                AppHomeActivity.startActivity(MainActivity.this, s);
                searchEditText.setText("");
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        RecyclerView templateRV = findViewById(R.id.main_template_list);
        templateRV.setLayoutManager(new LinearLayoutManager(this));
        // load from local
        mAppAdapter = new AppAdapter();
        mAppAdapter.setData(MyApplication.sApp);
        templateRV.setAdapter(mAppAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppAdapter.setData(MyApplication.sApp);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
