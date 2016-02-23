package com.siweiwang.webview1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SqlView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_view);

        TextView tv = (TextView)findViewById(R.id.getInfo);
        HotOrNot info = new HotOrNot(this);
        info.open();
        String data = info.getData();
        info.close();

        tv.setText(data);



    }



}
