package com.sywwifi.syw.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sywwifi.syw.R;

/**
 * Created by linjun on 2017/7/1.
 */

public abstract class BaseFragment extends Fragment {
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_ERR = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public int mCurState;
    protected static FragmentActivity mContext;
    public FrameLayout frameLayout;
    private View errPageView;
    private View loadingPageView;
    private View emptyPageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        showLoadingState();
        return frameLayout;
    }
    private  void init(){
        mContext=getActivity();
        frameLayout=new FrameLayout(getActivity());
        errPageView = View.inflate(getActivity(), R.layout.page_err_state, null);
        loadingPageView = View.inflate(getActivity(),R.layout.page_loading_state, null);
        emptyPageView=View.inflate(getActivity(),R.layout.page_empty_state,null);
        frameLayout.addView(errPageView);
        frameLayout.addView(loadingPageView);
        frameLayout.addView(emptyPageView);
        mCurState = STATE_LOADING;
        requestData();

        Button bt = ((Button) errPageView.findViewById(R.id.bt_reload));
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCurState = STATE_LOADING;
                showLoadingState();
                requestData();
            }
        });
    }
    public void showLoadingState() {
        emptyPageView.setVisibility(mCurState == STATE_EMPTY ? View.VISIBLE
                : View.INVISIBLE);
        errPageView.setVisibility(mCurState == STATE_ERR ? View.VISIBLE
                : View.INVISIBLE);
        loadingPageView.setVisibility(mCurState == STATE_UNKNOWN
                || mCurState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        if (mCurState == STATE_SUCCESS) {
            showPage();
        }

    }
    /**
     * 如果要显示加载完成页面必须将mCurState设置为STATE_SUCCESS
     */
    public abstract void requestData();

    /**
     * 如果STATE_SUCCESS加载成功将会调用此方法用于显示加载成功后的页面
     */
    public abstract void showPage();
}
