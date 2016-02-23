package com.siweiwang.webview1;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLData;

public class SqlData extends AppCompatActivity implements View.OnClickListener {

    EditText name, hot;
    Button update, view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_data);

        name = (EditText)findViewById(R.id.etName);
        hot = (EditText) findViewById(R.id.etHotness);
        update = (Button) findViewById(R.id.btUpdate);
        view = (Button)findViewById(R.id.btView);

        update.setOnClickListener(this);
        view.setOnClickListener(this );


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btUpdate :
                boolean didItWork = true;
                try {
                    String sqlname = name.getText().toString();
                    String hotness = hot.getText().toString();

                    HotOrNot entry = new HotOrNot(this);

                    entry.open();
                    entry.createEntry(sqlname, hotness);

                    entry.close();

                }catch(Exception e){
                    didItWork = false;
                }finally{
                    if (didItWork){
                        Dialog d = new Dialog(this);
                        d.setTitle("Heck, Ya!");
                        TextView tv = new TextView(this);
                        tv.setText("Success");
                        d.setContentView(tv);
                        d.show();
                    }
                }


                    break;
            case R.id.btView :
                Intent i = new Intent(this, SqlView.class);
                startActivity(i);

                break;
        }
    }
}
