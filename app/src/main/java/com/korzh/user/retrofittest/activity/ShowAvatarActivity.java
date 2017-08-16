package com.korzh.user.retrofittest.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.korzh.user.retrofittest.R;
import com.korzh.user.retrofittest.manager.ApiManager;
import com.korzh.user.retrofittest.manager.SharedPrefManager;

import static com.korzh.user.retrofittest.util.Const.SUCCESS;
import static com.korzh.user.retrofittest.util.Const.URI_KEY;

public class ShowAvatarActivity extends AppCompatActivity {

    private SimpleDraweeView mDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_avatar);

        mDraweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        findViewById(R.id.btn_click).setOnClickListener(v -> deleteUser());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            showImage(bundle.getString(URI_KEY));
        }
    }

    private void showImage(String uriStr) {
        mDraweeView.setImageURI(Uri.parse(uriStr));
    }

    private void deleteUser() {
        ApiManager.getInstance().deleteUser(SharedPrefManager.getId())
                .subscribe(result -> {
                            if (result.getResult().equalsIgnoreCase(SUCCESS)) {
                                Toast.makeText(this, SUCCESS, Toast.LENGTH_SHORT).show();
                                SharedPrefManager.clear();
                                mDraweeView.setImageResource(0);
                            }
                        }
                        , throwable -> {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                        });
    }
}
