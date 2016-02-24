package com.siweiwang.webview1;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.SQLData;
import java.util.Scanner;

public class SqlData extends AppCompatActivity implements View.OnClickListener {

    EditText name, hot, getRowId;
    Button update, view, getInfo, editEntry, deleteEntry;
    int rowID;
    String tempString;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_data);

        name = (EditText) findViewById(R.id.etName);
        hot = (EditText) findViewById(R.id.etHotness);
        update = (Button) findViewById(R.id.btUpdate);
        view = (Button) findViewById(R.id.btView);
        getRowId = (EditText) findViewById(R.id.etRowID);
        getInfo = (Button) findViewById(R.id.btInfo);
        editEntry = (Button) findViewById(R.id.btGetEntry);
        deleteEntry = (Button) findViewById(R.id.btDeleteEntry);

        update.setOnClickListener(this);
        view.setOnClickListener(this);
        Log.i("blah", "blah");


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btUpdate:
                Log.i("Update", "Update");
                boolean didItWork = true;
                try {
                    String sqlname = name.getText().toString();
                    String hotness = hot.getText().toString();

                    HotOrNot entry = new HotOrNot(this);

                    entry.open();
                    entry.createEntry(sqlname, hotness);

                    entry.close();

                } catch (Exception e) {
                    didItWork = false;
                } finally {
                    if (didItWork) {
                        Dialog d = new Dialog(this);
                        d.setTitle("Heck, Ya!");
                        TextView tv = new TextView(this);
                        tv.setText("Success");
                        d.setContentView(tv);
                        d.show();
                    }
                }


                break;

            case R.id.btView:
                Log.i("View", "View");
                Intent i = new Intent(this, SqlView.class);
                startActivity(i);

                break;

            case R.id.btInfo:
                Log.i("tag", "tag");
                tempString = getRowId.getText().toString();
                //  if (tempString != null)
                //  rowID = Integer.parseInt(tempString);


                int l = Integer.parseInt(tempString);
                Log.i("tag", "tag");

                HotOrNot hon = new HotOrNot(this);
                hon.open();
                String returnedName = hon.getName(l);
                String returnHotess = hon.getHotness(l);
                hon.close();

                name.setText(returnedName);
                hot.setText(returnHotess);

                break;

            case R.id.btGetEntry:
                tempString = getRowId.getText().toString();
                int iRow = Integer.parseInt(tempString);
                String sqlname = name.getText().toString();
                String hotness = hot.getText().toString();

                HotOrNot hon2 = new HotOrNot(this);
                hon2.open();
                hon2.updateEntry(iRow, sqlname, hotness);
                hon2.close();

                break;
            case R.id.btDeleteEntry:

                tempString = getRowId.getText().toString();
                int lRow = Integer.parseInt(tempString);

                HotOrNot hon1 = new HotOrNot(this);
                hon1.open();
                hon1.deleteEntry(lRow);
                hon1.close();


                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SqlData Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.siweiwang.webview1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SqlData Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.siweiwang.webview1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
