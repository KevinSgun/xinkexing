package com.thinkeract.tka.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.thinkeract.tka.data.db.greendao.DaoMaster;
import com.thinkeract.tka.data.db.greendao.GDAddressDao;
import com.thinkeract.tka.data.db.greendao.GDGoodsItemDao;

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
            String[] goodsColumns = getColumnNames(db, GDGoodsItemDao.TABLENAME);
            if (goodsColumns != null) {
                upgradeTable(db, GDGoodsItemDao.TABLENAME, buildColumnsString(goodsColumns));
            } else {
                GDGoodsItemDao.createTable(db, false);
            }

            String[] addressColumns = getColumnNames(db, GDAddressDao.TABLENAME);
            if (addressColumns != null) {
                upgradeTable(db, GDAddressDao.TABLENAME, buildColumnsString(addressColumns));
            } else {
                GDAddressDao.createTable(db, false);
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
        if (GDGoodsItemDao.TABLENAME.equals(tableName)) {
            GDGoodsItemDao.createTable(db, ifNotExists);
        } else if (GDAddressDao.TABLENAME.equals(tableName)) {
            GDAddressDao.createTable(db, ifNotExists);
        }
    }

    public static void dropTables(SQLiteDatabase db, String tableName, boolean ifExists) {
        if (GDGoodsItemDao.TABLENAME.equals(tableName)) {
            GDGoodsItemDao.dropTable(db, ifExists);
        } else if (GDAddressDao.TABLENAME.equals(tableName)) {
            GDAddressDao.dropTable(db, ifExists);
        }
    }

}
