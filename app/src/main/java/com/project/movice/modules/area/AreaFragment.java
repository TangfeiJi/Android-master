package com.project.movice.modules.area;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.project.movice.R;
import com.project.movice.application.MoviceApp;
import com.project.movice.base.fragment.BaseFragment;
import com.project.movice.modules.home.adappter.BaseRecyclerAdapter;
import com.project.movice.utils.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wy on 2018/2/2 10:47.
 */


public class AreaFragment extends BaseFragment<AreaPresenter> implements BaseRecyclerAdapter.OnItemClickListener, AreaContract.View {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private static final String TB_SQL = "sql";
    private static final String KTY_LEVEL = "level";
    private String type;
    private String pid;
    private int level;

    private List<BeanAreaInfo> newList = new ArrayList();
    private AreaAdapter adapter;


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(BeanAreaInfo areaInfo);
    }

    private OnFragmentInteractionListener mListener;

    public static AreaFragment newInstance(String type,String pid, int level) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("level", level);
        args.putString("pid", pid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView() {
        if (null != getArguments()) {
            try {
                type = getArguments().getString("type");
                pid = getArguments().getString("pid");
                level = getArguments().getInt("level");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adapter = new AreaAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_area;
    }

    @Override
    protected void initEventAndData() {
        Map<String,String> hm=new HashMap<>();
        hm.put("level",level+"");
        hm.put("type",type+"");
        hm.put("pid",pid+"");
        mPresenter.requestData(hm);
    }

    @Override
    public void getData(List<BeanAreaInfo> list) {
        newList=list;
        Collections.sort(newList, new PinyinComparator());
        adapter.resetItem(newList);
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




    @Override
    public void onItemClick(int position, long itemId) {
        BeanAreaInfo areaInfo = (BeanAreaInfo) newList.get(position);
        if (areaInfo == null) return;
        if (mListener != null) {
            mListener.onFragmentInteraction(areaInfo);
        }
    }


}
