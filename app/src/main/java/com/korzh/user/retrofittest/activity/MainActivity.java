package com.korzh.user.retrofittest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.korzh.user.retrofittest.R;
import com.korzh.user.retrofittest.model.Model;
import com.korzh.user.retrofittest.model.RegisteredUser;
import com.korzh.user.retrofittest.model.User;
import com.korzh.user.retrofittest.retrofit.ApiManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


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
//                requestRout(textView);
                registration(textView);
            }
        });
    }

    private void registration(final TextView textView) {

        User user = new User();
        user.setName("Vasya Petrov");
        user.setEmail("vasya@gmail.com");
        user.setPassword("pasw0239840238042");

        ApiManager.getInstance().getService().registration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RegisteredUser>() {
                    @Override
                    public void call(RegisteredUser model) {
                        Log.d(TAG, "call: o - " + model.toString());
                        textView.setText(model.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "call() called with: throwable = [" + throwable + "]");
                        textView.setText("Throwable - "+throwable.getMessage());
                    }
                });;
    }

//    private void requestRout(final TextView textView) {
//
//        String location="-33.8670522,151.1957362";
//        String radius="500";
//        String type = "restaurant";
//        String keyword = "cruise";
//        String key = "AIzaSyBqOttlaWHwkPzkOTr9yJ2Uxi5Qy5SnS3k";
//
//        ApiManager.getInstance().getService().getData(
//                location,
//                radius,
//                type,
//                keyword,
//                key)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Model>() {
//                    @Override
//                    public void call(Model model) {
//                        Log.d(TAG, "call: o - " + model.toString());
//                        textView.setText("Status - "+model.getStatus());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.d(TAG, "call() called with: throwable = [" + throwable + "]");
//                        textView.setText("Throwable - "+throwable.getMessage());
//                    }
//                });
//    }
}
