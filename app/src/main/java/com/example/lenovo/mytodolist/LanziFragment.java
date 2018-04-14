package com.example.lenovo.mytodolist;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LanziFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LanziFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanziFragment extends Fragment {

    ListView IngredientsList;//材料布局listview，带有header和item
    IngredientsAdapter InAdapter;
    SwipeRefreshLayout pullrefresh;
    TextView emptyview;
    String get_ingredients;
    String get_dish;
    //DataBase newdata;
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
        pullrefresh=view.findViewById(R.id.refresh);
        IngredientsList = (ListView) view.findViewById(R.id.list);
        //数组适配器，将itemlist的数据显示到ingredientslist
        InAdapter = new IngredientsAdapter(this.getActivity(), StaticData.IngredientsData);
        IngredientsList.setAdapter(InAdapter);
        IngredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type=InAdapter.getItemViewType(position);
                final int p=position;
                IngredientsData in= InAdapter.getIngredients(position);
                if(type==1){// 点击组内材料，显示删除线和变灰
                    TextView itemtext = view.findViewById(R.id.ingredient);
                    if (itemtext.getPaint().getFlags() == Paint.STRIKE_THRU_TEXT_FLAG) {
                        itemtext.setTextColor(getResources().getColor(R.color.black));
                        itemtext.getPaint().setFlags(0);
                        in.setStatus(InAdapter.getItemPositioninList(position)-1,0);
                    } else {
                        itemtext.setTextColor(getResources().getColor(R.color.ltgray));
                        itemtext.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        in.setStatus(InAdapter.getItemPositioninList(position)-1,1);
                    }
                }
                else
                {
                    ImageButton delete;
                    delete = view.findViewById(R.id.deleteButton);
                    if(delete.getVisibility()==View.INVISIBLE) {
                        delete.setVisibility(View.VISIBLE);
                        in.header_status=1;
                    }
                    else{
                        delete.setVisibility(View.INVISIBLE);
                        in.header_status=0;}
                }
            }
        });
        emptyview=view.findViewById(R.id.emptybasket);
        IngredientsList.setEmptyView(emptyview);
        IngredientsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view!=null)
                if(firstVisibleItem==0)
                    if(view.getChildAt(0)!=null)
                    if(view.getChildAt(0).getTop()>=0) {
                        pullrefresh.setEnabled(true);
                    }else
                        pullrefresh.setEnabled(false);
            }
        });
        pullrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullrefresh.setRefreshing(true);
                final Thread tbase=new Thread(new Runnable() {
                    @Override
                    public void run() {
                       // newdata = new DataBase();//连接池
                       /* try {
                            newdata.tbase.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        User u = new User(StaticData.username, StaticData.datapool);
                        try {
                            u.t1.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        get_dish = u.get_basket();
                        if(get_dish!=null) {
                            if (StaticData.dishlist.size()<=0||get_dish != StaticData.dishlist.get
                                    (0)) {
                                Menu m = new Menu(get_dish, StaticData.datapool);

                                try {
                                    m.t2.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                get_ingredients = m.getIngredients();
                                PutIngredients(get_dish, get_ingredients);
                                m.finish();
                            }
                        }
                       // String basket = getDishinBasket();
                       // u.setBasket(basket);
                        u.finish();

                        //newdata.finish();
                    }
                });
                tbase.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        try {
                            tbase.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        InAdapter.notifyDataSetChanged();
                        pullrefresh.setRefreshing(false);
                    }
                }, 0);
            }
        });
        //材料数据，材料信息+菜名
        updateData();
        fristfresh();
        return  view;
    }

    public  void updateData() {

        if (InAdapter != null && StaticData.IngredientsData != null) {

            InAdapter.notifyDataSetChanged();
        }
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

    public String getDishinBasket()
    {
        int num = StaticData.IngredientsData.size();
        String dishinbasket = new String("");
        for (int i=0;i<num;i++)
        {
            dishinbasket=dishinbasket+"#"+StaticData.IngredientsData.get(i).getName();
        }
        return dishinbasket;
    }

    public void PutIngredients(String name,String ingredientslist)
    {
        if(ingredientslist!=null) {
            int num = StaticData.IngredientsData.size();
            String dishname;
            for (int i = 0; i < num; i++) {
                dishname = StaticData.IngredientsData.get(i).getName();
                Boolean flag=name.equals(dishname);
                if (flag)
                {
                     Toast.makeText(LanziFragment.this.getActivity(), dishname + "已存在于菜篮子", Toast
                             .LENGTH_SHORT);
                    return;
                }
            }
            String[] list = ingredientslist.split("#");
            String[] newlist=new String[list.length-1];
            System.arraycopy(list,1,newlist,0,list.length-1);
            List<String> inlist = Arrays.asList(newlist);
            IngredientsData newdish = new IngredientsData(name, inlist);
            StaticData.addIngredientsToBasket(newdish);
        }
    }

    public void fristfresh()
    {
        final Thread tbase=new Thread(new Runnable() {
            @Override
            public void run() {
               // newdata = new DataBase();//连接池
                StaticData.datapool= new DataBase();//连接池
                try {
                    StaticData.datapool.tbase.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User u = new User(StaticData.username,StaticData.datapool);
                try {
                    u.t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                get_dish = u.get_basket();
                if(get_dish!=null)
                    if (StaticData.dishlist.size()<=0||get_dish != StaticData.dishlist.get(0)){
                        Menu m = new Menu(get_dish, StaticData.datapool);
                        try {
                            m.t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        get_ingredients = m.getIngredients();
                        PutIngredients(get_dish, get_ingredients);
                        m.finish();
                }
                //String basket = getDishinBasket();
                //   Log.v("getDishinBasket", basket);
                //u.setBasket(basket);
                u.finish();
                //newdata.finish();
            }
        });
        tbase.start();
        try {
            tbase.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
