package com.neal.repairer.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * BaseFragment 基Fragment
 *
 * @author lichao
 *
 */
public abstract class BaseFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(setLayoutID(), null);
        initView(view);
        setViews();
        return view;
    }



    /**
     * set layout ID 设置布局文件
     *
     * @return
     */
    protected abstract int setLayoutID();

    /**
     * inti Views 初始化控件
     *
     * @param view parent view 父view
     */
    protected abstract void initView(View view);

    /**
     * 设置控件
     */
    protected abstract void setViews();

}