package com.chejdj.wanandroid.ui.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.event.LoginSuccessEvent;
import com.chejdj.wanandroid.ui.base.WanAndroidMvpBaseActivty;
import com.chejdj.wanandroid.ui.login.contract.LoginContract;
import com.chejdj.wanandroid.ui.login.presenter.LoginPresenter;
import com.chejdj.wanandroid.ui.main.MainActivity;
import com.chejdj.wanandroid.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends WanAndroidMvpBaseActivty implements LoginContract.View {
    @BindView(R.id.username)
    EditText usernameET;
    @BindView(R.id.password)
    EditText passwordET;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    private boolean isRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        title.setText("登录");
        presenter =new LoginPresenter(this);
    }

    @OnClick(R.id.login)
    public void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            Toast.makeText(this,StringUtil.getString(this,R.string.login_warning) , Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isRegister) {
            ((LoginPresenter) presenter).login(username, password);
        } else {
            ((LoginPresenter) presenter).register(username, password);
        }
    }

    @OnClick(R.id.register)
    public void register() {
        if (isRegister) {
            title.setText(StringUtil.getString(this,R.string.login));
            login.setText(StringUtil.getString(this,R.string.login));
        } else {
            title.setText(StringUtil.getString(this,R.string.register));
            login.setText(StringUtil.getString(this,R.string.register_and_login));
        }
        isRegister = !isRegister;
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @Override
    public void loginSuccess() {
        EventBus.getDefault().post(new LoginSuccessEvent());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
