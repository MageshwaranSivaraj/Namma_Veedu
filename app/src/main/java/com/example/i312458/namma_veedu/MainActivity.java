package com.example.i312458.namma_veedu;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ExpensesManagement ExpMng;

    public EditText ID;
    public EditText name;
    public EditText amount;
    public EditText desc;

    public Button add;
    public Button update;
    public Button view;

    public TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpMng=new ExpensesManagement(MainActivity.this);
        init();

    }

    private void init() {
        ID=(EditText) findViewById(R.id.nvId);
        name=(EditText) findViewById(R.id.nvName);
        amount=(EditText) findViewById(R.id.nvAmount);
        desc=(EditText) findViewById(R.id.nvDescritpion);

        add=(Button) findViewById(R.id.nvButtonAdd);
        update=(Button) findViewById(R.id.nvButtonUpdate);
        view=(Button) findViewById(R.id.nvButtonView);
        
        add.setOnClickListener(onClickButtonListener);
        update.setOnClickListener(onClickButtonListener);
        view.setOnClickListener(onClickButtonListener);

        display=(TextView) findViewById(R.id.nvDisplay);

    }
    private View.OnClickListener onClickButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.nvButtonAdd:
                    long Result=ExpMng.Insert(Integer.parseInt(ID.getText().toString().trim()),name.getText().toString().trim(),Double.valueOf(amount.getText().toString().trim()),desc.getText().toString().trim());

                    if(Result == -1)
                    {
                        Toast.makeText(MainActivity.this, "Error Occurred while Inserting", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.nvButtonUpdate:
                    long Result_update=ExpMng.Update(Integer.parseInt(ID.getText().toString().trim()),name.getText().toString().trim(),Double.valueOf(amount.getText().toString().trim()),desc.getText().toString().trim());

                    if(Result_update == 0)
                    {
                        Toast.makeText(MainActivity.this, "Error Occurred while Updating", Toast.LENGTH_SHORT).show();
                    }
                    else if (Result_update == 1)
                    {
                        Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Update Failed, Since attempt to update multiple record", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.nvButtonView:

                    StringBuilder finalData=new StringBuilder();
                    Cursor cursor=ExpMng.getAllRecords();


                    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
                    {
                        finalData.append(cursor.getInt(cursor.getColumnIndex(ExpMng.id)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(ExpMng.name)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(ExpMng.amount)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(ExpMng.description)));
                        finalData.append("\n");
                    }

                    display.setText(finalData);
                    break;

            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        ExpMng.OpenDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ExpMng.closeDB();
    }
}
