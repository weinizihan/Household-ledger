package com.weinizihan.householdledger;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 记录分录的一行（涉及的一个科目）
 */


/**
 * 交易涉及账户和记账所需其他信息
 */



/**
 * 所有的会计处理
 * 按道理，只在这里对数据库进行操作
 */
public class Account {
    static class Acc{
        Acc(String accountName, int Amount){
            this.accountName=accountName;
            this.Amount=Amount;
        }
        String accountName;  // 账户名称
        int Amount;  // 金额，将float转换为int，正借负贷
    }

    class Entry{
        Entry(Acc[] accounts, String date, String description){
            if (accounts == null){
                this.accounts = new ArrayList<>();
            } else {
                this.accounts=new ArrayList<>(Arrays.asList(accounts));
            }
            this.date=date;
            this.description=description;
        }

        int num; //交易编号
        ArrayList<Acc> accounts;  // 交易涉及的所有账户
        String date;  // 日期（yyyy-mm-dd）
        String description;  // 交易描述

        void Dr(String accountName, float amount){
            if(amount < 0){
                this.Cr(accountName, -amount);
                return;
            }
            this.accounts.add(new Acc(accountName, (int) (amount + 0.5)));  // +0.5进行四舍五入
        }

        void Cr(String accountName, float amount){
            if(amount < 0){
                this.Dr(accountName, -amount);
                return;
            }
            this.accounts.add(new Acc(accountName, -(int) (amount + 0.5)));  // +0.5进行四舍五入
        }

        private boolean checkEntry(){
            int sum = 0;
            for(Acc acc : accounts){
                sum += acc.Amount;
            }
            return sum==0;
        }

        void write(){
            if(this.checkEntry()){
                db.write(this);
            }
            else {
                // TODO 抛出错误
            }
        }
    }

    private List<Acc> accList = new ArrayList<>(); //有多个账户构成交易的分录
    private SQLiteHelper db;

    public Account(Context context){
        db = new SQLiteHelper(context);
    }

    public boolean check(){
        return db.check();
    }

    private void Dr(String accountName, float amount){
        if(amount < 0){
            Cr(accountName, -amount);
            return;
        }
        accList.add(new Acc(accountName, (int) (amount + 0.5)));  // +0.5进行四舍五入
    }

    private void Cr(String accountName, float amount){
        if(amount < 0){
            Dr(accountName, -amount);
            return;
        }
        accList.add(new Acc(accountName, -(int) (amount + 0.5)));  // +0.5进行四舍五入
    }

    private boolean checkEntry(){
        int sum = 0;
        for(Acc acc : accList){
            sum += acc.Amount;
        }
        return sum==0;
    }

    void writeEntry(Entry entry){
        if(entry.checkEntry()){
            db.write(entry);
        }
        else {
            // TODO 抛出错误
        }
    }
}
