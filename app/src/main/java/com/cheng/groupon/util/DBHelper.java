package com.cheng.groupon.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cheng.groupon.domain.city.CitynameBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static DBHelper INSTANCE;

    public static DBHelper getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (DBHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBHelper(context);

                }
            }

        }
        return INSTANCE;

    }


    private DBHelper(Context context) {
        super(context, "city.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        //在第一次创建city.db数据库时，该方法会被调用
        //创建存储数据的数据表
        try {
            TableUtils.createTableIfNotExists(connectionSource, CitynameBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, CitynameBean.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
