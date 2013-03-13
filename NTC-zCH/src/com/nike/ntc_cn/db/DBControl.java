package com.nike.ntc_cn.db;

import java.io.Closeable;
import java.util.concurrent.Executor;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DBControl implements Closeable {

    /**数据库名。*/
    public static final String DB_NAME = "NTC-zCH.db";
    /**数据库版本号，当数据库有变更时增加。*/
    public static final int DB_VERSION = 2;
    
    protected static  Context mContext;
    protected final Executor mExecutor;
    
    /**SQLiteOpenHelper.*/
    protected final SQLiteOpenHelper mOpenHelper;
    
    protected DBControl(Context context, Executor executor, SQLiteOpenHelper openHelper) {
        mContext = context;
        mExecutor = executor;
        mOpenHelper = openHelper;
    }
    
    /**
     * 异步执行数据库操作。
     * @param transaction Refer to {@link SQLiteTransaction}
     */
    protected void runTransactionAsync(final SQLiteTransaction transaction) {
        mExecutor.execute(new Runnable() {
            public void run() {
                transaction.run(mOpenHelper.getWritableDatabase());
            }
        });
    }
    
    @Override
    public void close() {
        mOpenHelper.close();
    }
    

}
