package com.thinkeract.tka.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.thinkeract.tka.data.db.greendao.DaoMaster;
import com.thinkeract.tka.data.db.greendao.GDBankCardItemDao;
import com.thinkeract.tka.data.db.greendao.GDBankItemDao;
import com.thinkeract.tka.data.db.greendao.GDGagUserItemDao;
import com.thinkeract.tka.data.db.greendao.GDHistoryRecordDao;
import com.thinkeract.tka.data.db.greendao.GDIndustryDao;
import com.thinkeract.tka.data.db.greendao.GDPhaseDao;

/**
 * <pre>
 * 继承自greenDao的DaoMaster
 * 可以在更新数据库版本时保留历史数据
 * @author ymh
 */
public class UpdateDaoMaster extends DaoMaster {

    public UpdateDaoMaster(SQLiteDatabase db) {
        super(db);
    }

    /**
     * <pre>
     * 继承自greenDao的DaoMaster.DevOpenHelper
     * 只重写onUpgrade方法，以便在更新数据库版本时保留历史数据
     */
    public static class UpdateDevOpenHelper extends DevOpenHelper {

        public UpdateDevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            upgradeTables(db);
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by update tables");
        }

    }

    private static void upgradeTables(SQLiteDatabase db) {
        try {
            String[] industryColumns = getColumnNames(db, GDIndustryDao.TABLENAME);
            if (industryColumns != null) {
                upgradeTable(db, GDIndustryDao.TABLENAME, buildColumnsString(industryColumns));
            } else {
                GDIndustryDao.createTable(db, false);
            }

            String[] phaseColumns = getColumnNames(db, GDPhaseDao.TABLENAME);
            if (phaseColumns != null) {
                upgradeTable(db, GDPhaseDao.TABLENAME, buildColumnsString(phaseColumns));
            } else {
                GDPhaseDao.createTable(db, false);
            }
            String[] bankColumns = getColumnNames(db, GDBankItemDao.TABLENAME);
            if (bankColumns != null) {
                upgradeTable(db, GDBankItemDao.TABLENAME, buildColumnsString(bankColumns));
            } else {
                GDBankItemDao.createTable(db, false);
            }
            String[] bankCardColums = getColumnNames(db, GDBankCardItemDao.TABLENAME);
            if (bankCardColums != null) {
                upgradeTable(db, GDBankCardItemDao.TABLENAME, buildColumnsString(bankCardColums));
            } else {
                GDBankCardItemDao.createTable(db, false);
            }
            String[] gagItemColums = getColumnNames(db, GDGagUserItemDao.TABLENAME);
            if (gagItemColums != null) {
                upgradeTable(db, GDGagUserItemDao.TABLENAME, buildColumnsString(gagItemColums));
            } else {
                GDGagUserItemDao.createTable(db, false);
            }
            String[] historyColums = getColumnNames(db, GDHistoryRecordDao.TABLENAME);
            if (historyColums != null) {
                upgradeTable(db, GDHistoryRecordDao.TABLENAME, buildColumnsString(historyColums));
            } else {
                GDHistoryRecordDao.createTable(db, false);
            }

        } catch (SQLException ex) {
            // Utils.E("couldn't create table in downloads database");
            throw ex;
        }

    }

    protected static String[] getColumnNames(SQLiteDatabase db, String tableName) {
        // createTable(db, tableName, true);
        String[] columnNames = null;
        Cursor c = null;

        try {
            c = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            if (null != c) {
                int columnIndex = c.getColumnIndex("name");
                if (-1 == columnIndex) {
                    return null;
                }

                int index = 0;
                columnNames = new String[c.getCount()];
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    columnNames[index] = c.getString(columnIndex);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // closeCursor(c);
            try {
                if (c != null && !c.isClosed())
                    c.close();
            } catch (Exception e2) {
            }
        }

        return columnNames;
    }

    private static String buildColumnsString(String[] colums) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < colums.length; i++) {
            buffer.append(colums[i]);
            if (i != colums.length - 1) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }

    protected static void upgradeTable(SQLiteDatabase db, String tableName, String columns) {
        try {
            db.beginTransaction();

            // 1, 建一个用来转移数据的临时表
            String tempTableName = tableName + "_temp";
            String sql = "ALTER TABLE " + tableName + " RENAME TO " + tempTableName;
            // execSQL(db, sql, null);
            db.execSQL(sql);

            // 2, 删除原表，并建新表
            dropTables(db, tableName, true);
            createTable(db, tableName, true);
            // 3, 从临时表转移数据到新表
            sql = "INSERT INTO " + tableName + " (" + columns + ") " + " SELECT " + columns + " FROM " + tempTableName;

            // execSQL(db, sql, null);
            db.execSQL(sql);

            // 4, 删除临时表
            db.execSQL("DROP TABLE IF EXISTS " + tempTableName);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private static void createTable(SQLiteDatabase db, String tableName, boolean ifNotExists) {
        if (GDIndustryDao.TABLENAME.equals(tableName)) {
            GDIndustryDao.createTable(db, ifNotExists);
        } else if (GDPhaseDao.TABLENAME.equals(tableName)) {
            GDPhaseDao.createTable(db, ifNotExists);
        } else if (GDBankItemDao.TABLENAME.equals(tableName)) {
            GDBankItemDao.createTable(db, ifNotExists);
        } else if (GDBankCardItemDao.TABLENAME.equals(tableName)) {
            GDBankCardItemDao.createTable(db, ifNotExists);
        } else if (GDGagUserItemDao.TABLENAME.equals(tableName)) {
            GDGagUserItemDao.createTable(db, ifNotExists);
        } else if (GDHistoryRecordDao.TABLENAME.equals(tableName)) {
            GDHistoryRecordDao.createTable(db, ifNotExists);
        }
    }

    public static void dropTables(SQLiteDatabase db, String tableName, boolean ifExists) {
        if (GDIndustryDao.TABLENAME.equals(tableName)) {
            GDIndustryDao.dropTable(db, ifExists);
        } else if (GDPhaseDao.TABLENAME.equals(tableName)) {
            GDPhaseDao.dropTable(db, ifExists);
        } else if (GDBankItemDao.TABLENAME.equals(tableName)) {
            GDBankItemDao.dropTable(db, ifExists);
        } else if (GDBankCardItemDao.TABLENAME.equals(tableName)) {
            GDBankCardItemDao.dropTable(db, ifExists);
        } else if (GDGagUserItemDao.TABLENAME.equals(tableName)) {
            GDGagUserItemDao.dropTable(db, ifExists);
        } else if (GDHistoryRecordDao.TABLENAME.equals(tableName)) {
            GDHistoryRecordDao.dropTable(db, ifExists);
        }
    }

}
