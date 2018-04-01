package com.example.lenovo.mytodolist;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LanziFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LanziFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanziFragment extends Fragment {

    StickyListHeadersListView IngredientsList;//材料布局listview，带有header和item
    TextView header;
    IngredientsAdapter InAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LanziFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanziFragment.
     */

    /*  用于传参 activity和fragement之间*/
    // TODO: Rename and change types and number of parameters
    public static LanziFragment newInstance(String param1, String param2) {
        LanziFragment fragment = new LanziFragment();
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

    /*
    设置布局文件
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lanzi, container, false);
        IngredientsList = (StickyListHeadersListView) view.findViewById(R.id.list);
        //材料数据，材料信息+菜名
        initDatas();
        //数组适配器，将itemlist的数据显示到ingredientslist
        InAdapter = new IngredientsAdapter(this.getActivity(), StaticData.IngredientsData);
        IngredientsList.setAdapter(InAdapter);

        //点击组内材料，显示删除线和变灰
        IngredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView itemtext = view.findViewById(R.id.ingredient);
                if (itemtext.getPaint().getFlags() == Paint.STRIKE_THRU_TEXT_FLAG) {
                    itemtext.setTextColor(getResources().getColor(R.color.black));
                    itemtext.getPaint().setFlags(0);
                } else {
                    itemtext.setTextColor(getResources().getColor(R.color.ltgray));
                    itemtext.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        //点击头部出现删除按钮
        IngredientsList.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {

            ImageButton delete;
            TextView name;
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition,final long headerId, boolean currentlySticky) {
                delete = (ImageButton)header.findViewById(R.id.deleteButton);
                name=(TextView)header.findViewById(R.id.tvHeader);
                if(delete.getVisibility()==View.INVISIBLE)
                    delete.setVisibility(View.VISIBLE);
                else
                    delete.setVisibility(View.INVISIBLE);
            }
        });
        return  view;
    }

    private void updateData() {

        if (InAdapter != null && StaticData.IngredientsData != null) {

            InAdapter.notifyDataSetChanged();
        }
    }

    private void initDatas() {
/*
        ItemList.add(new IngredientsData(0,0,"红烧肉","五花肉"));
        ItemList.add(new IngredientsData(1,0,"红烧肉","八角"));
        ItemList.add(new IngredientsData(2,0,"红烧肉","酱油"));
        ItemList.add(new IngredientsData(3,0,"红烧肉","盐"));

        ItemList.add(new IngredientsData(0,1,"回锅肉","带皮五花肉"));
        ItemList.add(new IngredientsData(1,1,"回锅肉","蒜苗"));
        ItemList.add(new IngredientsData(2,1,"回锅肉","酱油"));
        ItemList.add(new IngredientsData(3,1,"回锅肉","盐"));
        ItemList.add(new IngredientsData(4,1,"回锅肉","豆豉"));
        */
        StaticData.IngredientsData.add(new IngredientsData(0,"红烧肉","五花肉"));
        StaticData.IngredientsData.add(new IngredientsData(0,"红烧肉","八角"));
        StaticData.IngredientsData.add(new IngredientsData(0,"红烧肉","酱油"));
        StaticData.IngredientsData.add(new IngredientsData(0,"红烧肉","盐"));

        StaticData.IngredientsData.add(new IngredientsData(1,"回锅肉","带皮五花肉"));
        StaticData.IngredientsData.add(new IngredientsData(1,"回锅肉","蒜苗"));
        StaticData.IngredientsData.add(new IngredientsData(1,"回锅肉","酱油"));
        StaticData.IngredientsData.add(new IngredientsData(1,"回锅肉","盐"));
        StaticData.IngredientsData.add(new IngredientsData(1,"回锅肉","豆豉"));
        StaticData.totaldish_inbasket=2;

    }


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




}
