package com.example.util;

import com.example.db.SignalBean;

public class SignalManagerUtil {
    SignalBean signalBean=new SignalBean();

    public SignalBean getSignalBean() {
        return signalBean;
    }

    public void setSignalBean(SignalBean signalBean) {
        this.signalBean = signalBean;
    }
}
