package com.example.util;

import com.example.listener.ClickListener;
import com.example.listener.OptionItemSelectListener;

public class ControlManager {
    ClickListener clickListener=new ClickListener();
    OptionItemSelectListener optionItemSelectListener=new OptionItemSelectListener();

    public OptionItemSelectListener getOptionItemSelectListener() {
        return optionItemSelectListener;
    }

    public void setOptionItemSelectListener(OptionItemSelectListener optionItemSelectListener) {
        this.optionItemSelectListener = optionItemSelectListener;
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
