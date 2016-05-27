package com.example.gauvreaup7006.mycontactapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    DatatbaseHelper myDb;

    //vars for the layout
    EditText editName, editPhone, editEmail;
    Button btnAddData, viewContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatatbaseHelper(this);

        //add the layout vars
        editName = (EditText) findViewById(R.id.editText);
        editPhone = (EditText) findViewById(R.id.editText2);
        editEmail = (EditText) findViewById(R.id.editText3);
        btnAddData = (Button) findViewById(R.id.button);

        viewContact = (Button) findViewById(R.id.button2);

        addData();
        viewAll();
    }

    private void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString());

                        if (isInserted == true) {
                            Context context = getApplicationContext();
                            CharSequence text = "Contact Added";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewAll(){
        viewContact.setOnClickListener(
            new View.OnClickListener(){
                @Override
            public void onClick(View v){
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0){
                        showMessage("Error","no data found");
                        return;

                    }
                    // assemble db entries
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append(res.getString(0));
                        buffer.append(res.getString(1));
                        buffer.append(res.getString(2));
                        buffer.append(res.getString(3));
                    }
                    //  append data to string buffer

                    //show the data in dialog box to the user
                    showMessage("Data",buffer.toString());
                }
            }
        );
    }

    private void showMessage(String title, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(s);
        builder.show();
    }

    public boolean ShowMessage(){
    return true;
    }
}
