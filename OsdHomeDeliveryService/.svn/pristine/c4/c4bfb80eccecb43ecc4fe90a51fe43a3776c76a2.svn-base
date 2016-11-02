package com.lenovo.csd.eservice.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.homedao.dao.DaoMaster;
import com.homedao.dao.DaoSession;

/**
 * Created by 彤 on 2016/8/26.
 */
public class DaoUtils {
    private static DaoSession daoSession;
    private static SQLiteDatabase db;

    public static DaoSession getDaoSession(Context context){
        if(daoSession == null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"home-db",null);
            db = helper.getWritableDatabase();
            DaoMaster master = new DaoMaster(db);
            daoSession = master.newSession();
        }
        return daoSession;
    }
}
