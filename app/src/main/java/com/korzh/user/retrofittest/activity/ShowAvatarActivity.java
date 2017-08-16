package com.korzh.user.retrofittest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.korzh.user.retrofittest.R;

import static com.korzh.user.retrofittest.util.Const.URI_KEY;

public class ShowAvatarActivity extends AppCompatActivity {

    private SimpleDraweeView mDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_avatar);

        mDraweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showImage(bundle.getString(URI_KEY));
        }
    }

    private void showImage(String uriStr) {
        mDraweeView.setImageURI(Uri.parse(uriStr));
    }
}
