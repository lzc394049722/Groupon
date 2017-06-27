package com.cheng.groupon.util;

import android.content.Context;
import android.util.Log;

import com.cheng.groupon.domain.city.CitynameBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DBUtil {

    Dao<CitynameBean, String> dao;
    DBHelper helper;


    public DBUtil(Context context) {
        helper = DBHelper.getInstance(context);
        try {
            dao = helper.getDao(CitynameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(CitynameBean bean) {
        try {
            dao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insetAll(final List<CitynameBean> list) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                long start = System.currentTimeMillis();
                try {
                    dao.callBatchTasks(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            for (CitynameBean c : list) {
                                insert(c);
                            }
                            return null;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "写入数据库完毕，耗时：" + (System.currentTimeMillis() - start));

            }
        }.start();


    }

    public List<CitynameBean> getAllCityName() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询数据库时出现异常");
        }

    }
}
