package com.example.listener;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.eweather.R;

public class OptionItemSelectListener extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*点击侧滑导航item*/
            case android.R.id.home:
                Log.d("OptionItemListener","点击了home");
                break;
            /*点击搜索城市item*/
            case R.id.search_city:
                Log.d("OptionItemListener","点击了search_city");
                break;
            /*点击隐藏item*/
            case R.id.manage_city:
                Log.d("OptionItemListener","点击了manage_city");
                break;
            default:
                break;
        }
        return true;
    }
}
