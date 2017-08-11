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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.et_email);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        Button btnClick = (Button) findViewById(R.id.btn_click);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            etEmail.setText(bundle.getString(EMAIL_KEY));
            etPassword.setText(bundle.getString(PASSWORD_KEY));
        }

        btnClick.setOnClickListener(view -> {
            User user = new User();
            user.setEmail(etEmail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            login(user);
        });
    }

    private void login(User user) {
        ApiManager.getInstance().login(user)
                .subscribe(model -> openUploadAvatarActivity(LoginActivity.this)
                        , throwable -> Log.d(TAG, "call() called with: throwable = [" + throwable.getMessage() + "]"));
    }

    public static void openUploadAvatarActivity(Context context) {
        Intent starter = new Intent(context, UploadAvatarActivity.class);
        context.startActivity(starter);
    }
}
