package com.korzh.user.retrofittest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.korzh.user.retrofittest.R;
import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;
import com.korzh.user.retrofittest.retrofit.ApiManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.korzh.user.retrofittest.Const.ID;
import static com.korzh.user.retrofittest.Const.TOKEN;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.tv_text);
        Button btnClick = (Button)findViewById(R.id.btn_click);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(textView);
            }
        });
    }

    private void registration(final TextView textView) {

        User user = new User();
        user.setName("Vasdfa1111 Petrosdfv");
        user.setEmail("vasyasdf11111@gmail.com");
        user.setPassword("pasw111111");

        ApiManager.getInstance().getService().registration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RegisteredUser>() {
                    @Override
                    public void call(RegisteredUser model) {
                        Log.d(TAG, "call: o - " + model.toString());
                        textView.setText(model.toString());
                        ID = model.getId();
                        TOKEN = model.getToken();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateUser(textView);
                            }
                        }, 5000);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "call() called with: throwable = [" + throwable + "]");
                        textView.setText("Throwable - "+throwable.getMessage());
                    }
                });;
    }


    private void updateUser(final TextView textView) {

        User user = new User();
        user.setName("Vasdfa2222 Petrosdfv222");
        user.setEmail("vasya222f@gmail.com");
        user.setPassword("pasw222");

        ApiManager.getInstance().getService().updateUser(TOKEN, ID, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RegisteredUser>() {
                    @Override
                    public void call(RegisteredUser model) {
                        Log.d(TAG, "call: o - " + model.toString());
                        textView.setText(model.toString());
                        ID = model.getId();
                        TOKEN = model.getToken();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "call() called with: throwable = [" + throwable + "]");
                        textView.setText("Throwable - "+throwable.getMessage());
                    }
                });
    }

}
