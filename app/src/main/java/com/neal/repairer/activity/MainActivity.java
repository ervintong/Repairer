package com.neal.repairer.activity;
/**
 * 登陆页
 * Created by lichao on 17/5/2.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.neal.repairer.R;
import com.neal.repairer.ease.ui.VideoCallActivity;
import com.neal.repairer.fragment.BaseFragment;
import com.neal.repairer.fragment.IndexFragment;
import com.neal.repairer.fragment.MyFragment;
import com.neal.repairer.fragment.OrderFragment;
import com.neal.repairer.fragment.TalkBackFragment;
import com.neal.repairer.service.ChatService;
import com.neal.repairer.util.Logs;
import com.neal.repairer.util.Toasts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    /**
     * fragment manager 碎片管理器
     */
    private FragmentManager fm;

    /**
     * talkback fragment 对讲碎片
     */
    private TalkBackFragment talkBackFragment;

    /**
     * index fragment 首页碎片
     */
    private IndexFragment indexFragment;

    /**
     * index fragment 工单碎片
     */
    private OrderFragment orderFragment;

    /**
     * my fragment 我的碎片
     */
    private MyFragment myFragment;

    /**
     * fragment list 碎片列表
     */
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();


    /**
     * 单选按钮组
     */
    private RadioGroup rg_main;

    /**
     * 登陆
     */
    Button bt_login;

    /**
     * 用户名
     */
    EditText et_user;

    /**
     * 密码
     */
    EditText et_password;

    /**
     * 动画色块
     */
    ImageView iv_anim_top, iv_anim_bottom;

    /**
     * 保险箱动画图片
     */
    ImageView iv_logo_top, iv_logo_bottom;

    /**
     * logo
     */
    ImageView iv_logo;

    /**
     * logo起始位置
     */
    float firstLogoY, firstTopLogoY, firstBottomLogoY;

    /**
     * 登陆页面,动画页面
     */
    PercentRelativeLayout prl_login, prl_anim;

    /**
     * 主页面
     */
    PercentRelativeLayout prl_main;

    /**
     * 主页四个按钮
     */
    Button bt_home, bt_order, bt_talkback, bt_my;

    /**
     * 四个按钮的父容器
     */
    LinearLayout ll_home, ll_order, ll_talkback, ll_my;

    /**
     * 四个按钮文字
     */
    TextView tv_home, tv_order, tv_talkback, tv_my;

    /**
     * 上下半logo当前高度
     */
    float topCurrentY, bottomCurrentY;


    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initResource() {

    }

    @Override
    protected void initViews() {
        bt_login = (Button) findViewById(R.id.bt_login);
        et_password = (EditText) findViewById(R.id.et_password);
        et_user = (EditText) findViewById(R.id.et_user);
        iv_anim_top = (ImageView) findViewById(R.id.iv_anim_top);
        iv_anim_bottom = (ImageView) findViewById(R.id.iv_anim_bottom);
        iv_logo_top = (ImageView) findViewById(R.id.iv_logo_top);
        iv_logo_bottom = (ImageView) findViewById(R.id.iv_logo_bottom);
        prl_login = (PercentRelativeLayout) findViewById(R.id.prl_login);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        prl_main = (PercentRelativeLayout) findViewById(R.id.prl_main);
        prl_anim = (PercentRelativeLayout) findViewById(R.id.prl_anim);
        bt_home = (Button) findViewById(R.id.bt_home);
        bt_order = (Button) findViewById(R.id.bt_order);
        bt_talkback = (Button) findViewById(R.id.bt_talkback);
        bt_my = (Button) findViewById(R.id.bt_my);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_order = (LinearLayout) findViewById(R.id.ll_order);
        ll_talkback = (LinearLayout) findViewById(R.id.ll_talkback);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_order = (TextView) findViewById(R.id.tv_order);
        tv_my = (TextView) findViewById(R.id.tv_my);
        fm = getSupportFragmentManager();


    }

    @Override
    protected void setViews() {
        ll_my.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        ll_talkback.setOnClickListener(this);
        //显示首页
        switchFragment(0);
        //登陆逻辑
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //记录logo起始位置
                firstLogoY = iv_logo.getY();
                firstTopLogoY = iv_logo_top.getY();
                firstBottomLogoY = iv_logo_bottom.getY();
                //防止误触
                bt_login.setClickable(false);
                if (!TextUtils.isEmpty(et_user.getText().toString()) && !TextUtils.isEmpty(et_password.getText().toString())) {
                    //开始播放过渡动画
                    //页面高度
                    final float height = prl_login.getHeight();
                    //上半logo当前高度
                    topCurrentY = iv_logo_top.getY();
                    //上半logo运行至的目标高度
                    float topTargetY = height / 2 - iv_logo_top.getHeight();
                    //下半logo当前高度
                    bottomCurrentY = iv_logo_bottom.getY();
                    //下半logo运行至的目标高度
                    float bottomTargetY = height / 2;
                    //设置上部logo旋转原点
                    iv_logo_top.setPivotX(iv_logo_top.getWidth() / 2);
                    iv_logo_top.setPivotY(iv_logo_top.getHeight());
                    //设置下部logo旋转原点
                    iv_logo_bottom.setPivotX(iv_logo_bottom.getWidth() / 2);
                    iv_logo_bottom.setPivotY(0);
                    //渐变时间
                    final long fadeTime = 1000;
                    //旋转时间
                    long rotaTime = 500;
                    //上部色块2秒渐入
                    ObjectAnimator bgFadeInTop = ObjectAnimator.ofFloat(iv_anim_top, "alpha", 0f, 1f);
                    bgFadeInTop.setInterpolator(new LinearInterpolator());
                    bgFadeInTop.setDuration(fadeTime);
                    //下部色块2秒渐入
                    ObjectAnimator bgFadeInBottom = ObjectAnimator.ofFloat(iv_anim_bottom, "alpha", 0f, 1f);
                    bgFadeInBottom.setInterpolator(new LinearInterpolator());
                    bgFadeInBottom.setDuration(fadeTime);
                    //上部隐藏logo2秒渐入
                    ObjectAnimator logoFadeInTop = ObjectAnimator.ofFloat(iv_logo_top, "alpha", 0f, 1f);
                    logoFadeInTop.setInterpolator(new LinearInterpolator());
                    logoFadeInTop.setDuration(fadeTime);
                    //下部隐藏logo
                    ObjectAnimator logoFadeInBottom = ObjectAnimator.ofFloat(iv_logo_bottom, "alpha", 0f, 1f);
                    logoFadeInBottom.setInterpolator(new LinearInterpolator());
                    logoFadeInBottom.setDuration(fadeTime);
                    //原logo渐出
                    ObjectAnimator logoFadeOut = ObjectAnimator.ofFloat(iv_logo, "alpha", 1f, 0f);
                    logoFadeOut.setInterpolator(new LinearInterpolator());
                    logoFadeOut.setDuration(fadeTime);
                    //原logo平移
                    ObjectAnimator logoTrans = ObjectAnimator.ofFloat(iv_logo, "y", topCurrentY, topTargetY);
                    logoTrans.setInterpolator(new LinearInterpolator());
                    logoTrans.setDuration(fadeTime);
                    //登陆页面2秒渐出
                    ObjectAnimator loginFadeOut = ObjectAnimator.ofFloat(prl_login, "alpha", 0f, 1f);
                    loginFadeOut.setInterpolator(new LinearInterpolator());
                    loginFadeOut.setDuration(fadeTime);
                    //上半logo平移
                    ObjectAnimator topLogoTrans = ObjectAnimator.ofFloat(iv_logo_top, "y", topCurrentY, topTargetY);
                    topLogoTrans.setInterpolator(new LinearInterpolator());
                    topLogoTrans.setDuration(fadeTime);
                    //下半logo平移
                    ObjectAnimator bottomLogoTrans = ObjectAnimator.ofFloat(iv_logo_bottom, "y", bottomCurrentY, bottomTargetY);
                    bottomLogoTrans.setInterpolator(new LinearInterpolator());
                    bottomLogoTrans.setDuration(fadeTime);
                    //上半logo旋转
                    final ObjectAnimator topLogoRota = ObjectAnimator.ofFloat(iv_logo_top, "rotation", 0f, 360f);
                    topLogoRota.setDuration(rotaTime);
                    topLogoRota.setInterpolator(new LinearInterpolator());

                    //下半logo旋转
                    final ObjectAnimator bottomLogoRota = ObjectAnimator.ofFloat(iv_logo_bottom, "rotation", 0f, 360f);
                    bottomLogoRota.setDuration(rotaTime);
                    bottomLogoRota.setInterpolator(new LinearInterpolator());


                    //设置动画队列
                    AnimatorSet animSet = new AnimatorSet();
                    //渐入
                    animSet.play(bgFadeInBottom).with(bgFadeInTop).with(loginFadeOut).with(logoFadeInTop).with(logoFadeInBottom)
                            .with(topLogoTrans).with(bottomLogoTrans).with(logoFadeOut).with(logoTrans);
                    //旋转
                    animSet.play(topLogoRota).with(bottomLogoRota).after(1100);
                    animSet.start();

                    EMClient.getInstance().login(et_user.getText().toString(), et_password.getText().toString(), new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Logs.d(TAG, "登录聊天服务器成功！");
                            hideBottomUIMenu();
                            Intent intent = new Intent(MainActivity.this, ChatService.class);
                            startService(intent);
                            //监听旋转完毕后的实际Y
                            topLogoRota.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    iv_logo_top = (ImageView) (((ObjectAnimator) animation).getTarget());
                                    topCurrentY = iv_logo_top.getY();
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            //监听旋转完毕后的实际Y
                            bottomLogoRota.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    iv_logo_bottom = (ImageView) (((ObjectAnimator) animation).getTarget());
                                    bottomCurrentY = iv_logo_bottom.getY();
                                    //镜像上半logo平移
                                    ObjectAnimator mTopLogoTrans = ObjectAnimator.ofFloat(iv_logo_top, "y", topCurrentY, 0 - iv_logo_bottom.getHeight());
                                    mTopLogoTrans.setInterpolator(new LinearInterpolator());
                                    mTopLogoTrans.setDuration(fadeTime);
                                    //镜像下半logo平移
                                    ObjectAnimator mBottomLogoTrans = ObjectAnimator.ofFloat(iv_logo_bottom, "y", bottomCurrentY, height);
                                    mBottomLogoTrans.setInterpolator(new LinearInterpolator());
                                    mBottomLogoTrans.setDuration(fadeTime);
                                    //上半色块平移
                                    ObjectAnimator bgTransTop = ObjectAnimator.ofFloat(iv_anim_top, "y", 0, 0 - height / 2);
                                    bgTransTop.setInterpolator(new LinearInterpolator());
                                    bgTransTop.setDuration(fadeTime);
                                    //下半色块平移
                                    ObjectAnimator bgTransBottom = ObjectAnimator.ofFloat(iv_anim_bottom, "y", height / 2, height);
                                    bgTransBottom.setInterpolator(new LinearInterpolator());
                                    bgTransBottom.setDuration(fadeTime);
                                    //设置动画队列
                                    final AnimatorSet animSet = new AnimatorSet();
                                    //渐出
                                    animSet.play(mTopLogoTrans).with(mBottomLogoTrans).with(bgTransTop).with(bgTransBottom).after(200);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            animSet.start();
                                            //设置UI状态，进入主页
                                            prl_login.setVisibility(View.GONE);
                                            animSet.addListener(new Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    prl_anim.setVisibility(View.GONE);
                                                }

                                                @Override
                                                public void onAnimationCancel(Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animator animation) {

                                                }
                                            });
                                            prl_main.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });


                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {

                            //监听旋转完毕后的实际Y
                            topLogoRota.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    iv_logo_top = (ImageView) (((ObjectAnimator) animation).getTarget());
                                    topCurrentY = iv_logo_top.getY();
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            //监听旋转完毕后的实际Y
                            bottomLogoRota.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    iv_logo_bottom = (ImageView) (((ObjectAnimator) animation).getTarget());
                                    bottomCurrentY = iv_logo_bottom.getY();
                                    //镜像上半logo平移
                                    ObjectAnimator mTopLogoTrans = ObjectAnimator.ofFloat(iv_logo_top, "y", topCurrentY, 0 - iv_logo_bottom.getHeight());
                                    mTopLogoTrans.setInterpolator(new LinearInterpolator());
                                    mTopLogoTrans.setDuration(fadeTime);
                                    //镜像下半logo平移
                                    ObjectAnimator mBottomLogoTrans = ObjectAnimator.ofFloat(iv_logo_bottom, "y", bottomCurrentY, height);
                                    mBottomLogoTrans.setInterpolator(new LinearInterpolator());
                                    mBottomLogoTrans.setDuration(fadeTime);
                                    //上半色块平移
                                    ObjectAnimator bgTransTop = ObjectAnimator.ofFloat(iv_anim_top, "y", 0, 0 - height / 2);
                                    bgTransTop.setInterpolator(new LinearInterpolator());
                                    bgTransTop.setDuration(fadeTime);
                                    //下半色块平移
                                    ObjectAnimator bgTransBottom = ObjectAnimator.ofFloat(iv_anim_bottom, "y", height / 2, height);
                                    bgTransBottom.setInterpolator(new LinearInterpolator());
                                    bgTransBottom.addListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            //重置UI状态
                                            bt_login.setClickable(true);

                                            iv_logo.setY(firstLogoY);

                                            iv_anim_top.setAlpha(0f);
                                            iv_anim_top.setY(0);

                                            iv_anim_bottom.setAlpha(0f);
                                            iv_anim_bottom.setY(height / 2);

                                            iv_logo_top.setAlpha(0f);
                                            iv_logo_top.setY(firstTopLogoY);

                                            iv_logo_bottom.setAlpha(0f);
                                            iv_logo_bottom.setY(firstBottomLogoY);


                                            ObjectAnimator logoFadeIn = ObjectAnimator.ofFloat(iv_logo, "alpha", 0f, 1f);
                                            logoFadeIn.start();

                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                                    bgTransBottom.setDuration(fadeTime);
                                    //设置动画队列
                                    AnimatorSet animSet = new AnimatorSet();
                                    //渐出
                                    animSet.play(mTopLogoTrans).with(mBottomLogoTrans).with(bgTransTop).with(bgTransBottom).after(200);
                                    animSet.start();


                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasts.makeText("登录失败！");
                                }
                            });
                        }
                    });
                } else {
                    Toasts.makeText("密码不能为空！");
                    bt_login.setClickable(true);

                }
            }
        });

    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    protected void setTitleBar() {

    }

    /**
     * 切换碎片
     *
     * @param index 碎片下标
     */
    private void switchFragment(int index) {
        hideAllFragment();
        switch (index) {
            //首页
            case 0:
                ll_home.setBackgroundResource(R.color.colorPrimary);
                ll_order.setBackgroundResource(R.color.colorWhite);
                ll_talkback.setBackgroundResource(R.color.colorWhite);
                ll_my.setBackgroundResource(R.color.colorWhite);
                bt_home.setBackgroundResource(R.drawable.rb_home_focus);
                bt_order.setBackgroundResource(R.drawable.rb_order_normal);
                bt_talkback.setBackgroundResource(R.drawable.rb_talkback_normal);
                bt_my.setBackgroundResource(R.drawable.rb_my_normal);
                tv_home.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_order.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_my.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                if (indexFragment == null) {
                    indexFragment = new IndexFragment();
                    fragmentList.add(indexFragment);
                    fm.beginTransaction().add(R.id.fm_container, indexFragment)
                            .commit();
                } else {
                    fm.beginTransaction().show(indexFragment).commit();
                }
                break;
            //工单
            case 1:
                ll_home.setBackgroundResource(R.color.colorWhite);
                ll_order.setBackgroundResource(R.color.colorPrimary);
                ll_talkback.setBackgroundResource(R.color.colorWhite);
                ll_my.setBackgroundResource(R.color.colorWhite);
                bt_home.setBackgroundResource(R.drawable.rb_home_normal);
                bt_order.setBackgroundResource(R.drawable.rb_order_focus);
                bt_talkback.setBackgroundResource(R.drawable.rb_talkback_normal);
                bt_my.setBackgroundResource(R.drawable.rb_my_normal);
                tv_home.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_order.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_my.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                if (orderFragment == null) {
                    orderFragment = new OrderFragment();
                    fragmentList.add(orderFragment);
                    fm.beginTransaction().add(R.id.fm_container, orderFragment)
                            .commit();
                } else {
                    fm.beginTransaction().show(orderFragment).commit();
                }
                break;
            //对讲
            case 2:
                startVideoCall();
                break;
            //我的
            case 3:
                ll_home.setBackgroundResource(R.color.colorWhite);
                ll_order.setBackgroundResource(R.color.colorWhite);
                ll_talkback.setBackgroundResource(R.color.colorWhite);
                ll_my.setBackgroundResource(R.color.colorPrimary);
                bt_home.setBackgroundResource(R.drawable.rb_home_normal);
                bt_order.setBackgroundResource(R.drawable.rb_order_normal);
                bt_talkback.setBackgroundResource(R.drawable.rb_talkback_normal);
                bt_my.setBackgroundResource(R.drawable.rb_my_focus);
                tv_home.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_order.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_my.setTextColor(getResources().getColor(R.color.colorWhite));
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    fragmentList.add(myFragment);
                    fm.beginTransaction().add(R.id.fm_container, myFragment)
                            .commit();
                } else {
                    fm.beginTransaction().show(myFragment).commit();

                }
                break;
        }
    }

    /**
     * hide all fragment 隐藏所有Fragment布局
     */
    private void hideAllFragment() {

        for (int i = 0; i < fragmentList.size(); i++) {
            fm.beginTransaction().hide(fragmentList.get(i)).commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.ll_home:
                switchFragment(0);
                break;
            //工单
            case R.id.ll_order:
                switchFragment(1);

                break;
            //对讲
            case R.id.ll_talkback:
                switchFragment(2);

                break;
            //我的
            case R.id.ll_my:
                switchFragment(3);

                break;
        }

    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toasts.makeText("无网络");
        else {
            startActivity(new Intent(this, VideoCallActivity.class).putExtra("username", "296648638")
                    .putExtra("isComingCall", false));
        }
    }
}
