package com.example.listener;

import android.util.Log;
import android.view.View;

import com.example.eweather.R;

public class ClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_city:
                Log.d("ClickListener","点击了search_city");
                break;
            case R.id.search_city_btn:
                Log.d("ClickListener","点击了search_city_btn");
            default:
                break;
        }
    }
}
