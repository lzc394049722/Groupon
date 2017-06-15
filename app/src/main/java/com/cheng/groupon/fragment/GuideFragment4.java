package com.cheng.groupon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cheng.groupon.R;
import com.cheng.groupon.ui.MainActivity;
import com.cheng.groupon.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment4 extends BaseFragment {


    public GuideFragment4() {
        // Required empty public constructor
    }

    @BindView(R.id.btn_fragment_skip)
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_fragment4, container, false);
        ButterKnife.bind(this, view);
        skip(button);
        return view;
    }

}
