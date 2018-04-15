package com.example.lenovo.mytodolist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ConFragment.OnFragmentInteractionListener,LanziFragment.OnFragmentInteractionListener,TuijianFragment.OnFragmentInteractionListener,MineFragment.OnFragmentInteractionListener{

    private TextView  tv_lanzi, tv_tuijian, tv_mine;
    private ImageView iv_lanzi,iv_tuijian,iv_mine,active;
    private ViewPager vp;
    private Bitmap lanzi1,lanzi2,mine1,mine2,tuijian1,tuijian2,con1,con2;
  //  ConFragment conversation_fargment;
    LanziFragment lanzi_fargment ;
    TuijianFragment recommand_fargment;
    MineFragment mine_fargment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    String username="小羊苏珊";
    LinearLayout splash;
    private final int STOP_SPLASH = 0;
    private final int SPLASH_TIME = 3000;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        getSupportActionBar().hide();
        //设置xml
        setContentView(R.layout.activity_main);
        //获得用户名字符串
        Intent intent =getIntent();
        username=intent.getStringExtra("username");
        StaticData.username=new String(this.username);
        initViews();
        getpermission();
        Message msg = new Message();
        msg.what = STOP_SPLASH;
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(3);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        //设置下方导航栏，文字文灰色
        tv_lanzi.setTextColor(Color.parseColor("#FF9900"));
        //加载下方图标
        loadPictures();
        //ViewPager的监听事件
        // 注：这里必须用延迟发送消息的方法，否则ImageView不会显示出来
        splashHandler.sendMessageDelayed(msg, SPLASH_TIME);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用,设置选择页面的图标和文字颜色*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        tv_lanzi = (TextView) findViewById(R.id.tv_lanzi);
        tv_tuijian= (TextView) findViewById(R.id.tv_tuijian);
        tv_mine = (TextView) findViewById(R.id.tv_mine);
      //  tv_con = (TextView) findViewById(R.id.tv_con);
        iv_lanzi = (ImageView) findViewById(R.id.iv_lanzi);
        iv_tuijian = (ImageView) findViewById(R.id.iv_tuijian);
        iv_mine = (ImageView) findViewById(R.id.iv_mine);
     //   iv_con = (ImageView) findViewById(R.id.iv_con);
        splash=findViewById(R.id.ll_splash);
        active=findViewById(R.id.activepage);

        //设置下方图标和文字点击事件

        tv_lanzi.setOnClickListener(this);
        tv_tuijian.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
       // tv_con.setOnClickListener(this);
        iv_lanzi.setOnClickListener(this);
        iv_tuijian.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
      //  iv_con.setOnClickListener(this);

        vp = (ViewPager) findViewById(R.id.mainViewPager);
       // conversation_fargment = new ConFragment();
        lanzi_fargment= new LanziFragment();
        recommand_fargment = new TuijianFragment();
        mine_fargment = new MineFragment();
        //无需向我的页面传递用户名字符串，直接从staticdata获取
       // Bundle userb=new Bundle();
        //userb.putString("username",username);
       // mine_fargment.setArguments(userb);
        //给FragmentList添加数据，即添加在viewpager中
       // mFragmentList.add(conversation_fargment);
        mFragmentList.add(lanzi_fargment);
        mFragmentList.add(recommand_fargment);
        mFragmentList.add(mine_fargment);
    }

    /**
     * 加载底部导航图片
     */
    void loadPictures()
    {
        lanzi1= BitmapFactory.decodeResource(this.getResources(),R.drawable.lanzi1);
        lanzi2= BitmapFactory.decodeResource(this.getResources(),R.drawable.lanzi2);
        mine1= BitmapFactory.decodeResource(this.getResources(),R.drawable.mine1);
        mine2= BitmapFactory.decodeResource(this.getResources(),R.drawable.mine2);
        tuijian1= BitmapFactory.decodeResource(this.getResources(),R.drawable.tuijian1);
        tuijian2= BitmapFactory.decodeResource(this.getResources(),R.drawable.tuijian2);
       // con1= BitmapFactory.decodeResource(this.getResources(),R.drawable.con1);
       // con2= BitmapFactory.decodeResource(this.getResources(),R.drawable.con2);
        //iv_con.setImageBitmap(con1);
        iv_lanzi.setImageBitmap(lanzi1);
        iv_tuijian.setImageBitmap(tuijian2);
        iv_mine.setImageBitmap(mine2);
    }
    /**
     * 点击底部Text 动态修改ViewPager的页面内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          // case R.id.tv_con:
            //    vp.setCurrentItem(0, true);
           //     break;
            case R.id.tv_lanzi:
                vp.setCurrentItem(0, true);
                lanzi_fargment.updateData();
                break;
            case R.id.tv_tuijian:
                vp.setCurrentItem(1, true);
                break;
            case R.id.tv_mine:
                vp.setCurrentItem(2, true);
                break;
            //case R.id.iv_con:
               // vp.setCurrentItem(0, true);
               // break;
            case R.id.iv_lanzi:
                vp.setCurrentItem(0, true);
                break;
            case R.id.iv_tuijian:
                vp.setCurrentItem(1, true);
                break;
            case R.id.iv_mine:
                vp.setCurrentItem(2, true);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色和图片
     */
    private void changeTextColor(int position) {
        /*if (position == 0) {
            tv_con.setTextColor(Color.parseColor("#FF9900"));
            tv_lanzi.setTextColor(Color.parseColor("#bfbfbf"));
            tv_tuijian.setTextColor(Color.parseColor("#bfbfbf"));
            tv_mine.setTextColor(Color.parseColor("#bfbfbf"));
            iv_con.setImageBitmap(con1);
            iv_lanzi.setImageBitmap(lanzi2);
            iv_tuijian.setImageBitmap(tuijian2);
            iv_mine.setImageBitmap(mine2);
        } else*/ if (position == 0) {
          //  tv_con.setTextColor(Color.parseColor("#bfbfbf"));
            tv_lanzi.setTextColor(Color.parseColor("#FF9900"));
            tv_tuijian.setTextColor(Color.parseColor("#bfbfbf"));
            tv_mine.setTextColor(Color.parseColor("#bfbfbf"));
          //  iv_con.setImageBitmap(con2);
            iv_lanzi.setImageBitmap(lanzi1);
            iv_tuijian.setImageBitmap(tuijian2);
            iv_mine.setImageBitmap(mine2);
            lanzi_fargment.updateData();
        } else if (position == 1) {
           // tv_con.setTextColor(Color.parseColor("#bfbfbf"));
            tv_lanzi.setTextColor(Color.parseColor("#bfbfbf"));
            tv_tuijian.setTextColor(Color.parseColor("#FF9900"));
            tv_mine.setTextColor(Color.parseColor("#bfbfbf"));
          //  iv_con.setImageBitmap(con2);
            iv_lanzi.setImageBitmap(lanzi2);
            iv_tuijian.setImageBitmap(tuijian1);
            iv_mine.setImageBitmap(mine2);
        }else if(position==2){
           // tv_con.setTextColor(Color.parseColor("#bfbfbf"));
            tv_lanzi.setTextColor(Color.parseColor("#bfbfbf"));
            tv_tuijian.setTextColor(Color.parseColor("#bfbfbf"));
            tv_mine.setTextColor(Color.parseColor("#FF9900"));
            //iv_con.setImageBitmap(con2);
            iv_lanzi.setImageBitmap(lanzi2);
            iv_tuijian.setImageBitmap(tuijian2);
            iv_mine.setImageBitmap(mine1);
        }
    }

    //重写Destroy防止内存溢出，滑动页面后回收资源
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        StaticData.datapool.finish();
        if (!lanzi1.isRecycled())
            lanzi1.recycle();
        if (!lanzi2.isRecycled())
            lanzi2.recycle();
       /* if (!con1.isRecycled())
            con1.recycle();
        if (!con2.isRecycled())
            con2.recycle();*/
        if (!tuijian1.isRecycled())
            tuijian1.recycle();
        if (!tuijian2.isRecycled())
            tuijian2.recycle();
        if (!mine1.isRecycled())
            mine1.recycle();
        if (!mine2.isRecycled())
            mine2.recycle();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    //连续点击两次退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Handler splashHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    splash.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };

    private void getpermission()
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);//android.manifest.xml
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("yx","get permission");
                ActivityCompat.requestPermissions(MainActivity.this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);


            }
            Log.d("yx","get permission2");
            ActivityCompat.requestPermissions(MainActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);

        }

        Log.d("yx","wait for PERMISSION_GRANTED");
        while (( ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE))!= PackageManager.PERMISSION_GRANTED) {

        }
        Log.d("yx","wait for PERMISSION_GRANTED finish");
    }

}

