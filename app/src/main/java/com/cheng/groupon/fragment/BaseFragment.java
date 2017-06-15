package com.cheng.groupon.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cheng.groupon.ui.MainActivity;
import com.cheng.groupon.util.CommonUtils;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class BaseFragment extends Fragment {

    protected void skip(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(getActivity(), MainActivity.class);
                getActivity().finish();
            }
        });


    }
}
