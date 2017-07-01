package com.sywwifi.syw.module.my.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sywwifi.syw.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    private View view;
    private TextView mTvSet;
    private View mRl;
    private ImageView mIvPicture;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        view = getView();
        mTvSet = (TextView) view.findViewById(R.id.tv_my_set);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_picture);
        mRl =  view.findViewById(R.id.rl_my);


    }
}
