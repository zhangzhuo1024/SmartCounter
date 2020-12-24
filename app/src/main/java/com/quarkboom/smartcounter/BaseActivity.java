package com.quarkboom.smartcounter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/**
 * @author: zhuozhang6
 * @date: 2020/12/24
 * @email: zhangzhuo1024@163.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(getLayoutId());
        initView();
    }


}
