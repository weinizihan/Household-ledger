package com.weinizihan.householdledger;
/**
 * SQLite数据库操作
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class DbSchema{
    static final String dbName  = "book.db";
    static final int dbVersion = 1; // 用于升级
}

public class SQLiteHelper extends SQLiteOpenHelper {

    SQLiteHelper(@Nullable Context context) {
        super(context, DbSchema.dbName, null, DbSchema.dbVersion);
    }

    /**
     * 首次运行时用于创建数据库。数据库升级时，在onUpgrade中删除表后调用（注意保留数据）
     * @param sqLiteDatabase 自动调用不用管
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE book(" +
                "id   INT PRIMARY KEY     NOT NULL," + // 数据库唯一编号
                "num              INT     NOT NULL," + // 记账凭证的变化（分录编号）（交易编号）
                "account          TEXT    NOT NULL," + // 科目名称（账户）（分类）
                "amount           INT     NOT NULL," + // 账户改变的金额，正借负贷
                "description      TEXT    NOT NULL," + // 交易的描述（摘要）
                "balance          INT             )"); // 账户的余额，正借负贷
    }

    /**
     * 数据库升级时调用，DbSchema.dbVersion。处理时注意保留数据
     * @param sqLiteDatabase 数据库
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    void write(Account.Entry entry){
        SQLiteDatabase db = this.getWritableDatabase(); // 获取数据库
        for (Account.Acc acc : entry.accounts) {
            ContentValues values = new ContentValues();
            values.put("num", entry.num);
            values.put("account", acc.accountName);
            values.put("amount", acc.Amount);
            values.put("description", entry.description);
            // values.put("balance"); // TODO 随时计算余额
            db.insert("book", null, values);
        }
    }

    boolean check(){
        // TODO 检查数据库
        return true;
    }
}
