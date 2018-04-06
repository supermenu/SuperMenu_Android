package com.example.lenovo.mytodolist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
       username=getArguments().getString("username");
       myusername.setText(username);
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
                mPopwindow = new PopupwindowForshare(getActivity(), itemsOnClick);
                mPopwindow.setAnimationStyle(R.style.popwin_anim_style);
                mPopwindow.showAtLocation(v,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });
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
}
