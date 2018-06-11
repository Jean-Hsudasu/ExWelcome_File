package com.example.user.exwelcome_file;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String sname,msg;
    private TextView txtName;
    private EditText edtname;
    private Button btnend,btnclear;
    private SharedPreferences preference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = findViewById(R.id.txtname);
        edtname = findViewById(R.id.edtname);
        btnend = findViewById(R.id.btnend);
        btnclear = findViewById(R.id.btnclear);


        edtname.setOnClickListener(listener);
        btnend.setOnClickListener(listener);
        btnclear.setOnClickListener(listener);

        preference = getSharedPreferences("preFile",MODE_PRIVATE);//建立儲存檔
        sname = preference.getString("name","");

        if(sname.equals("")) {
            txtName.setVisibility(TextView.VISIBLE);
            edtname.setVisibility(TextView.VISIBLE);
            btnclear.setVisibility(TextView.INVISIBLE);
            msg = "歡迎使用本應用程式!\n您尚未基本資料，請輸入姓名!";
        } else {
            msg = "親愛的 "+ sname +"，您好!\n歡迎再次使用本程式!";
        }
        //以下為彈跳出歡迎視窗
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("歡迎使用本軟體!")
                .setMessage(msg)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private Button.OnClickListener listener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnend:
                    finish();
                    break;
                case R.id.btnclear:
                    if(!sname.equals("")) {
                        preference.edit()
                                .clear()
                                .commit();
                        Toast.makeText(getApplicationContext(),"所有的資料都已清除!",Toast.LENGTH_LONG).show();
                    }
                    btnclear.setVisibility(Button.INVISIBLE);
                    break;
            }
        }
    };

    protected  void onStop() {
        super.onStop();
        if(sname.equals("")) {
            preference.edit()
                    .putString("name",edtname.getText()
                    .toString())
                    .commit();
        }
    }

}
