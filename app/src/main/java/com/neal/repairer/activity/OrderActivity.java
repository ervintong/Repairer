package com.neal.repairer.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.neal.repairer.R;

/**
 * 工单页面
 * Create by lichao 2017/5/22
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener{
    /**
     * 返回按钮
     */
    private LinearLayout ll_back;

    @Override
    protected int setContent() {
        return R.layout.activity_order;
    }


    @Override
    protected void initResource() {

    }

    @Override
    protected void initViews() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back_order);
    }

    @Override
    protected void setViews() {
        ll_back.setOnClickListener(this);
    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    protected void setTitleBar() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.ll_back_order:
                finish();
                break;
        }
    }
}
