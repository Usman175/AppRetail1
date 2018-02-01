package com.example.usman.appretail;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Fahad Tanwir on 1/15/2018.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button reg;
    private TextView tvLogin;
    private EditText etlogin,etpass;
    private DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrer_layout);

        db =new DBHelper(this);
        reg = (Button) findViewById(R.id.btnregister);
        tvLogin =(TextView)findViewById(R.id.tvlogin);
        etlogin =(EditText)findViewById(R.id.etuser);
        etpass =(EditText)findViewById(R.id.etpass);
        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnregister:
                // business logic
                register();
                break;
            case R.id.tvlogin:

                break;
                default:

        }

    }



    public void register(){
        String uid=etlogin.getText().toString();
        String pass=etpass.getText().toString();

        if (uid.isEmpty() && pass.isEmpty()){
            displayToast("Username/Password field Empty");
        }
        else{
            db.addUser(uid,pass);
            displayToast("User Registered");
            finish();
        }
    }

    private void displayToast(String message){
      Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
