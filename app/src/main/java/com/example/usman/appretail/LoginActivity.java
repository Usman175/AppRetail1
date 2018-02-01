package com.example.usman.appretail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button login, register;
    private EditText etUser, etPass;
    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.btnlogin);
        register = (Button) findViewById(R.id.btnregister);
        etUser = (EditText) findViewById(R.id.etuser);
        etPass = (EditText) findViewById(R.id.etpass);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        db = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnlogin:
                //business logic
                auth();
                break;

            case R.id.btnregister:
                //business logic
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    public void auth() {

        String userId = etUser.getText().toString();
        String pass = etPass.getText().toString();

        if (userId.isEmpty() && pass.isEmpty()) {
            displayToast("Username/Password field is Empty");
        } else {

            boolean response = db.getUser(userId, pass);

            if (response==true && userId.equalsIgnoreCase("admin")){
                Intent intent = new Intent(this,AdminActivity.class);
                displayToast("Successfully Login as Administrator");
                startActivity(intent);
            }

            else if (response == true ) {
                Intent intent = new Intent(this, MainActivity.class);
                displayToast("Login Successful!");
                startActivity(intent);
            }



            else if (response == false)
                displayToast("Wrong username/password");
        }


    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}


