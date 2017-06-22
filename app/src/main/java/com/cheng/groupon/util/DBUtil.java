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

    private Dao<CitynameBean, String> dao;
    private DBHelper helper;

    private static DBUtil instance = null;

    public static DBUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (DBUtil.class) {
                if (instance == null)
                    instance = new DBUtil(context);
            }
        }
        return instance;
    }


    private DBUtil(Context context) {
        helper = new DBHelper(context);
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
        List<CitynameBean> all = null;
        try {
            all = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }
}
