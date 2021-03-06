package com.cheng.groupon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.cheng.groupon.ui.MainActivity;
import com.cheng.groupon.util.CommonUtils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment1 extends BaseFragment {


    public GuideFragment1() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_fragment_skip)
    TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guide_fragment1, container, false);
        ButterKnife.bind(this, view);
        skip(textView);
        return view;
    }

}
