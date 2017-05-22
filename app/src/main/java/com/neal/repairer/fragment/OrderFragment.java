package com.neal.repairer.fragment;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neal.repairer.R;
import com.neal.repairer.activity.OrderActivity;
import com.neal.repairer.util.TabUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单
 * Create by lichao 2017/5/21
 */
public class OrderFragment extends BaseFragment implements View.OnClickListener{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    /**
     * 页卡标题集合
     */
    private List<String> mTitleList = new ArrayList<>();
    /**
     * 页卡视图
     */
    private View normal, urgency;
    /**
     * 页卡视图集合
     */
    private List<View> mViewList = new ArrayList<>();
    /**
     * 添加工单按钮
     */
    private Button bt_add_normal, bt_add_urgency;

    @Override
    protected int setLayoutID() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        mInflater = LayoutInflater.from(getActivity());
        normal = mInflater.inflate(R.layout.view_normal_order, null);
        urgency = mInflater.inflate(R.layout.view_urgency_order, null);
        bt_add_normal = (Button) normal.findViewById(R.id.bt_add_normal);
        bt_add_urgency = (Button) urgency.findViewById(R.id.bt_add_urgency);
        //添加页卡视图
        mViewList.add(urgency);
        mViewList.add(normal);

        //添加页卡标题
        mTitleList.add("急修工单");
        mTitleList.add("维保工单");
    }

    @Override
    protected void setViews() {
        //设置按钮监听
        bt_add_normal.setOnClickListener(this);
        bt_add_urgency.setOnClickListener(this);
        //设置tab模式，当前为系统默认模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));


        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);

        //让TabLayout下划线短一点
        TabUtil.setIndicator(mTabLayout, 20, 20);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        switch (v.getId()) {
            //普通工单
            case R.id.bt_add_normal:
                startActivity(intent);
                break;
            //急修工单
            case R.id.bt_add_urgency:
                startActivity(intent);
                break;
        }
    }


    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            //页卡数
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方推荐写法
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加页卡
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除页卡
            container.removeView(mViewList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //页卡标题
            return mTitleList.get(position);
        }

    }
}
