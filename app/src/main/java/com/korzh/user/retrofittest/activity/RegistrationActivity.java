package com.korzh.user.retrofittest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.korzh.user.retrofittest.R;
import com.korzh.user.retrofittest.model.User;
import com.korzh.user.retrofittest.retrofit.ApiManager;

import static com.korzh.user.retrofittest.util.Const.EMAIL_KEY;
import static com.korzh.user.retrofittest.util.Const.PASSWORD_KEY;


public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etEmail = (EditText) findViewById(R.id.et_email);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        Button btnClick = (Button) findViewById(R.id.btn_click);

        btnClick.setOnClickListener(v -> {
            final User user = new User();
            user.setName(etName.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            registration(user);
        });
    }

    private void registration(User user) {
        ApiManager.getInstance().registration(user)
                .subscribe(model -> openLoginActivity(RegistrationActivity.this, user)
                , throwable -> Log.d(TAG, "call() called with: throwable = [" + throwable.getMessage() + "]"));
    }
    
    public static void openLoginActivity(Context context, User user) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EMAIL_KEY, user.getEmail());
        intent.putExtra(PASSWORD_KEY, user.getPassword());
        context.startActivity(intent);
    }
}
