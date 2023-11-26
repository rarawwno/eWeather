package com.example.util;

import com.example.listener.ClickListener;

public class ControlManager {
    ClickListener clickListener=new ClickListener();


    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
