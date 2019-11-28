package com.chejdj.wanandroid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chejdj.wanandroid.ui.main.MainActivity;
import com.chejdj.wanandroid.util.PermissionUtils;
import com.chejdj.wanandroid.util.StringUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23
            && PermissionUtils.checkPermission(this, PermissionUtils.PERMISSIONS_STORAGE).length
            > 0) {
            PermissionUtils.checkAndRequestPermissions(this, PermissionUtils.PERMISSIONS_STORAGE);
        } else {
            intentToMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDenin = false;
        if (requestCode == PermissionUtils.REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDenin = true;
                    break;
                }
            }
            if (hasPermissionDenin) {
                Toast.makeText(this, StringUtils.getString(this, R.string.deny_permissions_warning),
                    Toast.LENGTH_LONG).show();
            }
            intentToMainActivity();
        }
    }

    private void intentToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
