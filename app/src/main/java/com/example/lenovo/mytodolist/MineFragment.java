package com.example.lenovo.mytodolist;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    String username;
    TextView myusername,details;
    ImageButton mydetails,share;
    PopupwindowForshare mPopwindow;
    LinearLayout LL_record;
    NumberProgressBar energy,axunge,protein;
    String[] text=new String[21];
    int rate_e,rate_a,rate_p;
    float user_e,user_a,user_p;
    RefreshRecord RE;

    TextView mor_1,mor_2,mor_3,mor_4,mor_5,mor_6,mor_7;
    TextView noon_1,noon_2,noon_3,noon_4,noon_5,noon_6,noon_7;
    TextView eve_1,eve_2,eve_3,eve_4,eve_5,eve_6,eve_7;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_mine, container, false);
       myusername=view.findViewById(R.id.myusername);
       //接受现在所登陆的用户名数据
       //username=getArguments().getString("username");
        username=StaticData.username;
        LL_record=view.findViewById(R.id.ll_record);
       myusername.setText(username);
       energy=view.findViewById(R.id.energy);
       axunge=view.findViewById(R.id.axunge);
       protein=view.findViewById(R.id.protein);
       details=view.findViewById(R.id.tv_text_content);
       details.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setDetails();
           }
       });
       mydetails=view.findViewById(R.id.ib_right_icon);
       mydetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setDetails();
           }
       });
        share=view.findViewById(R.id.ib_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mPopwindow = new PopupwindowForshare(getActivity(), itemsOnClick);
                mPopwindow.setAnimationStyle(R.style.popwin_anim_style) ;
                mPopwindow.showAtLocation(v,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/
              showShare();

            }
        });
        initviews(view);
        settext();
        caculate();
        energy.setProgress(rate_e);
        axunge.setProgress(rate_a);
        protein.setProgress(rate_p);
        if(rate_a>=100){
            axunge.setReachedBarColor(R.color.red);
            axunge.setProgressTextColor(R.color.red);
        }
        if(rate_e>=100){
            energy.setReachedBarColor(R.color.red);
            energy.setProgressTextColor(R.color.red);
        }
        if(rate_p>=100) {
            protein.setReachedBarColor(R.color.red);
            protein.setProgressTextColor(R.color.red);
        }
       return view;
    }

    private  View.OnClickListener itemsOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopwindow.dismiss();
            mPopwindow.backgroundAlpha(getActivity(), 1f);
            switch (v.getId()) {
                case R.id.wechatfriends:
                    Toast.makeText(getActivity(), "微信好友", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pyq:
                    Toast.makeText(getActivity(), "朋友圈", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }


        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //点击我的资料，弹出资料修改页面
    public void setDetails(){
            /*
            启动detailsinfoactivity
             */
        Intent i=new Intent();
        i.setClass(getActivity(), DetailsInfoActivity.class);
        getActivity().startActivity(i);

    }

    void initviews(View v)
    {
        mor_1=v.findViewById(R.id.m1);
        mor_2=v.findViewById(R.id.m2);
        mor_3=v.findViewById(R.id.m3);
        mor_4=v.findViewById(R.id.m4);
        mor_5=v.findViewById(R.id.m5);
        mor_6=v.findViewById(R.id.m6);
        mor_7=v.findViewById(R.id.m7);
        noon_1=v.findViewById(R.id.n1);
        noon_2=v.findViewById(R.id.n2);
        noon_3=v.findViewById(R.id.n3);
        noon_4=v.findViewById(R.id.n4);
        noon_5=v.findViewById(R.id.n5);
        noon_6=v.findViewById(R.id.n6);
        noon_7=v.findViewById(R.id.n7);
        eve_1=v.findViewById(R.id.e1);
        eve_2=v.findViewById(R.id.e2);
        eve_3=v.findViewById(R.id.e3);
        eve_4=v.findViewById(R.id.e4);
        eve_5=v.findViewById(R.id.e5);
        eve_6=v.findViewById(R.id.e6);
        eve_7=v.findViewById(R.id.e7);
    }

    void settext()  {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               RE=new RefreshRecord();
                RE.init();
                RE.initconnection(StaticData.datapool);
                try {
                    RE.t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                text[0]=RE.get_cooked_1m();
                text[1]=RE.get_cooked_2m();
                text[2]=RE.get_cooked_3m();

                text[3]=RE.get_cooked_4m();

                text[4]=RE.get_cooked_5m();

                text[5]=RE.get_cooked_6m();

                text[6]=RE.get_cooked_7m();

                text[7]=RE.get_cooked_1n();

                text[8]=RE.get_cooked_2n();

                text[9]=RE.get_cooked_3n();

                text[10]=RE.get_cooked_4n();

                text[11]=RE.get_cooked_5n();

                text[12]=RE.get_cooked_6n();

                text[13]=RE.get_cooked_7n();

                text[14]=RE.get_cooked_1e();

                text[15]=RE.get_cooked_2e();

                text[16]=RE.get_cooked_3e();

                text[17]=RE.get_cooked_4e();

                text[18]=RE.get_cooked_5e();

                text[19]=RE.get_cooked_6e();

                text[20]=RE.get_cooked_7e();

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mor_1.setText(text[0]);
        mor_2.setText(text[1]);
        mor_3.setText(text[2]);
        mor_4.setText(text[3]);
        mor_5.setText(text[4]);
        mor_6.setText(text[5]);
        mor_7.setText(text[6]);
        noon_1.setText(text[7]);
        noon_2.setText(text[8]);
        noon_3.setText(text[9]);
        noon_4.setText(text[10]);
        noon_5.setText(text[11]);
        noon_6.setText(text[12]);
        noon_7.setText(text[13]);
        eve_1.setText(text[14]);
        eve_2.setText(text[15]);
        eve_3.setText(text[16]);
        eve_4.setText(text[17]);
        eve_5.setText(text[18]);
        eve_6.setText(text[19]);
        eve_7.setText(text[20]);
        if(!text[0].equals("")&&text!=null)
            mor_1.setBackgroundColor(getActivity().getResources().getColor(R.color.ltyellow));
        if(!text[1].equals("")&&text!=null)
            mor_2.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[2].equals("")&&text!=null)
            mor_3.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[3].equals("")&&text!=null)
            mor_4.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[4].equals("")&&text!=null)
            mor_5.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[5].equals("")&&text!=null)
            mor_6.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[6].equals("")&&text!=null)
            mor_7.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[7].equals("")&&text!=null)
            noon_1.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[8].equals("")&&text!=null)
            noon_2.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[9].equals("")&&text!=null)
            noon_3.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[10].equals("")&&text!=null)
            noon_4.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[11].equals("")&&text!=null)
            noon_5.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[12].equals("")&&text!=null)
            noon_6.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[13].equals("")&&text!=null)
            noon_7.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[14].equals("")&&text!=null)
            eve_1.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));

        if(!text[15].equals("")&&text!=null)
            eve_2.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[16].equals("")&&text!=null)
            eve_3.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[17].equals("")&&text!=null)
            eve_4.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[18].equals("")&&text!=null)
            eve_5.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[19].equals("")&&text!=null)
            eve_6.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));
        if(!text[20].equals("")&&text!=null)
            eve_7.setBackgroundColor(getActivity().getResources().getColor(R.color
                    .ltyellow));



    }

    //
    void caculate()
    {
       Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                User u=new User(StaticData.username,StaticData.datapool);
                try {
                    u.t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user_a=u.get_axunge()*7;
                user_e=u.get_energy()*7;
                user_p=u.get_protein()*7;

                u.finish();
                float energy=0,axunge=0,protein=0;
                for(int i=0;i<RE.cooked.size();i++)
                {
                    String dish=RE.cooked.get(i);
                    if(!dish.equals("")&&dish!=null)
                    {
                        Menu m=new Menu(dish,StaticData.datapool);
                        try {
                            m.t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        energy+=m.get_energy();
                        axunge+=m.get_axunge();
                        protein+=m.get_protein();
                        m.finish();
                    }
                }
                rate_a=(int)(axunge/user_a*100);
                rate_e=(int)(energy/user_e*100);
                rate_p=(int)(protein/user_p*100);
                if(rate_a>100) rate_a=100;
                if(rate_e>100) rate_e=100;
                if(rate_p>100) rate_p=100;

            }
        });
       t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.share));
        // titleUrl QQ和QQ空间跳转链接
       // oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("来自菜谱大师");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        getpicture();
        oks.setImagePath("/sdcard/test.png");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
       // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
       // oks.setComment("我是测试评论文本");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("oncomplete","oncomplete");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("onError","onError");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("onCancel","onCancel");
            }
        });
        // 启动分享GUI
        oks.show(this.getActivity());
    }

    private void getpicture()
    {
        /*View view = this.getActivity().findViewById(R.id.ll_record);
        //打开图像缓存
        view.setDrawingCacheEnabled(true);
        //测量大小
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View
                .MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //发送尺寸到view和子view
        view.layout(0, 0, view.getMeasuredWidth(),view.getMeasuredHeight());*/
        //获取可视组件的截图
        Bitmap cacheBitmap = getLinearLayoutBitmap(LL_record);
        try {
            FileOutputStream fout = new FileOutputStream("/sdcard/test.png");
            cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Bitmap getLinearLayoutBitmap(LinearLayout linearLayout) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            h += linearLayout.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(linearLayout.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        linearLayout.draw(canvas);
        return bitmap;
    }


}

