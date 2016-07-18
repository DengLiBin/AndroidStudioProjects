package com.denglibin.designsupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FloatingActionButton btn_fab= (FloatingActionButton) findViewById(R.id.btn_fab);
         btn_fab.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 final Snackbar snackbar = Snackbar.make(btn_fab, "你点击了按钮", Snackbar.LENGTH_SHORT);
                 snackbar.show();//显示snackbar(增强版的Toast)
                 snackbar.setAction("知道了", new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         snackbar.dismiss();//隐藏掉
                     }
                 });
             }
         });

        final TextInputLayout textInput= (TextInputLayout) findViewById(R.id.text_input);
        textInput.setHint("请输入用户名");
        EditText editText= (EditText) textInput.findViewById(R.id.et_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>10){
                    textInput.setError("用户名不能超过10个字符");
                    textInput.setErrorEnabled(true);//显示错误提示
                }else{
                    textInput.setErrorEnabled(false);//隐藏提示
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button button= (Button) findViewById(R.id.btn_btn);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TabActivity.class));
            }
        });
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
}
