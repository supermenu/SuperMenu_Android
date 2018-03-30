package com.example.lenovo.mytodolist;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TuijianFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TuijianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuijianFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //xml的组件
    ListView list_view;
    List<Map<String,Object> > listdata;//存储每一个推荐菜的图片
    RelativeLayout wrapper;
    FrameLayout toolbar;
    FragmentManager fm;


    //
    static int sScreenWidth;
    static int sImageHeight;
    private OnFragmentInteractionListener mListener;

    public TuijianFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TuijianFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TuijianFragment newInstance(String param1, String param2) {
        TuijianFragment fragment = new TuijianFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tuijian, container, false);
        sScreenWidth = getResources().getDisplayMetrics().widthPixels;
        sImageHeight = getResources().getDimensionPixelSize(R.dimen.height_image);
        initViews(view);
        setListData();
        list_view.setAdapter(new tuijianListAdapter(this.getActivity(), R.layout.dishlist_item, listdata));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void initViews(View v)
    {
        toolbar=v.findViewById(R.id.toolbar_list);
        wrapper=v.findViewById(R.id.wrapper);
        list_view=v.findViewById(R.id.list_view);
       list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               showDetails((Map<String, Object>) parent.getItemAtPosition(position), view,position);
           }
       });
    }

    public void setListData()//添加每一个推荐菜的图片
    {
        Map<String,Object> Map;
        listdata= new ArrayList<>();
        //图片id
        int[] avatars = {
                R.drawable.fzpaigu,
                R.drawable.guobaorou,
                R.drawable.hspaigu,
                R.drawable.jjrousi,
                R.drawable.jxniurou,
                R.drawable.yxqiezi,
                R.drawable.laziji,
                };
      //  String[] name =getResources().getStringArray(R.array.dish);
        for (int i = 0; i < avatars.length; i++) {
            Map = new HashMap<>();
            Map.put("image", avatars[i]);
           // Map.put("name",name);
            listdata.add(Map);
        }
    }


    public void showDetails(Map<String, Object> item, final View view,int position)
    {//跳转详细菜谱activity
        Intent i=new Intent();
        Bundle b = new Bundle();
        //传递位置信息，便于显示对应菜谱
        b.putInt("position",position);
        i.setClass(getActivity(), DishDetailsActivity.class);
        i.putExtras(b);
        getActivity().startActivity(i);
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

}


