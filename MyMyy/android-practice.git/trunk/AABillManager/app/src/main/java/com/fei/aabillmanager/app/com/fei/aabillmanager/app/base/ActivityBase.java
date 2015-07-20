package com.fei.aabillmanager.app.com.fei.aabillmanager.app.base;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Eric on 06/06/2014.
 */
public class ActivityBase extends Activity {

    protected void ShowMsg(String pMsg) {
        Toast.makeText(this,pMsg,Toast.LENGTH_SHORT).show();
    }

    protected void OpenActivity(Class pClass) {
        Intent _Intent = new Intent();
        _Intent.setClass(this,pClass);
        startActivity(_Intent);
    }
}
