package com.nike.ntc_cn.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.nike.ntc_cn.NtcApplication;

/**
 * @ClassName: InitDataControl 
 * @Description: 初始化数据库内容 
 * @author LEIKANG 
 * @date 2013-3-7 下午6:27:17
 */
public final class InitDataControl extends DBControl{
	
	private static final String TAG = "InitDataControl";

	private static volatile InitDataControl instance;
	
	private Context mContext;
	
	protected InitDataControl(Context context, Executor executor,
			SQLiteOpenHelper openHelper) {
		super(context, executor, openHelper);
		mContext = context;
	}
	
	public static InitDataControl getInstance(Context context) {
        if (instance == null) {
            context = context.getApplicationContext();
            ThreadFactory logThreadFactory = Executors.defaultThreadFactory();
            Executor logExecutor = Executors.newSingleThreadExecutor(logThreadFactory);
            SQLiteOpenHelper openHelper = DbOpenHelper.getInstance(context, DBControl.DB_NAME, DBControl.DB_VERSION,
                    logExecutor);
            instance = new InitDataControl(context, logExecutor, openHelper);
            
        }
        return instance;
    }
	
	public void init() {
		//异步执行该操作 耗时大概在3-5s
		runTransactionAsync(new SQLiteTransaction() {
			@Override
			protected boolean performTransaction(SQLiteDatabase db) {
				long start = System.currentTimeMillis();
				if (!isInitMode(mContext)) {
					Log.i(TAG,"=======start  init the databases:" + start);
					try {
						//db.beginTransaction();
						InputStream in = mContext.getAssets().open("content.sql");
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(in));
						String sqlUpdate = null;
						while ((sqlUpdate = bufferedReader.readLine()) != null) {
							
							if (!TextUtils.isEmpty(sqlUpdate)) {
								db.execSQL(sqlUpdate);
							}
						}
						bufferedReader.close();
						in.close();
						//db.endTransaction();
						setInitMode(mContext, true);

					} catch (SQLException e) {
					} catch (IOException e) {
					}
					Log.i(TAG,"=======end  init the databases:" + (System.currentTimeMillis() - start));
				}
 
				return true;
			}
		});

	}
	
	//判断数据库是否已初始化
    public static boolean isInitMode(Context ctx) {
            final SharedPreferences settings = ctx.getSharedPreferences(
            		NtcApplication.PREFS_NAME, 0);
        return   settings.getBoolean(NtcApplication.TAG_IS_INIT_DATABASES, false);
    }
 
    public static void setInitMode(Context ctx, boolean privateMode) {
        final SharedPreferences settings = ctx.getSharedPreferences(NtcApplication.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(NtcApplication.TAG_IS_INIT_DATABASES, privateMode);
        editor.commit();
    }

}
