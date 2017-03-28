package com.thinkeract.tka.common.utils;

import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.data.db.DaoFactory;
import com.thinkeract.tka.data.db.greendao.GDBankCardItem;
import com.thinkeract.tka.data.db.greendao.GDBankCardItemDao;
import com.thinkeract.tka.data.db.greendao.GDBankItem;
import com.thinkeract.tka.data.db.greendao.GDGagUserItem;
import com.thinkeract.tka.data.db.greendao.GDGagUserItemDao;
import com.thinkeract.tka.data.db.greendao.GDHistoryRecord;
import com.thinkeract.tka.data.db.greendao.GDHistoryRecordDao;
import com.thinkeract.tka.data.db.greendao.GDIndustry;
import com.thinkeract.tka.data.db.greendao.GDPhase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2016/12/5 14:51.
 * mail:minhengyan@gmail.com
 */

public class DBUtils {

    /**
     * 查询所有行业列表
     * @return
     */
    public static List<GDIndustry> queryAllIndustryList() {
        return DaoFactory.sharedSessions()//
                .getGDIndustryDao()//
                .queryBuilder()//
                .list();
    }

    /**
     * 插入一组行业列表
     * @param gdIndustries
     */
    public static void insertAllIndustryList(List<? extends GDIndustry> gdIndustries) {
        if (gdIndustries == null) return;
        for (int i = 0, size = gdIndustries.size(); i < size; i++) {
            GDIndustry item = gdIndustries.get(i);
            insertOrUpdateIndustry(item);
        }

    }

    /**
     * 插入一个行业
     * @param item
     */
    private static void insertOrUpdateIndustry(GDIndustry item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDIndustryDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDIndustry.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 查询所有经营阶段列表
     * @return
     */
    public static List<GDPhase> queryAllGDPhaseList() {
        return DaoFactory.sharedSessions()//
                .getGDPhaseDao()//
                .queryBuilder()//
                .list();
    }

    /**
     * 查询一组经营阶段列表
     * @param gdPhases
     */
    public static void insertAllGDPhaseList(List<? extends GDPhase> gdPhases) {
        if (gdPhases == null) return;
        for (int i = 0, size = gdPhases.size(); i < size; i++) {
            GDPhase item = gdPhases.get(i);
            insertOrUpdateGDPhase(item);
        }

    }

    /**
     * 插入一个经营阶段
     * @param item
     */
    private static void insertOrUpdateGDPhase(GDPhase item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDPhaseDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDPhase.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 查询所有银行列表
     * @return
     */
    public static List<GDBankItem> queryAllGDBankList() {
        return DaoFactory.sharedSessions()//
                .getGDBankItemDao()//
                .queryBuilder()//
                .list();
    }

    /**
     * 插入一组银行列表
     * @param gdBankItems
     */
    public static void insertGDBankList(List<? extends GDBankItem> gdBankItems) {
        if (gdBankItems == null) return;
        for (int i = 0, size = gdBankItems.size(); i < size; i++) {
            GDBankItem item = gdBankItems.get(i);
            insertOrUpdateGDBank(item);
        }

    }

    /**
     * 插入一个银行
     * @param item
     */
    private static void insertOrUpdateGDBank(GDBankItem item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDBankItemDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDBankItem.class, DaoFactory.DATA_UPDATE, wrap(item));
    }


    /**
     * 查询所有银行卡列表
     * @return
     */
    public static List<GDBankCardItem> queryAllGDBankCardList() {
        return DaoFactory.sharedSessions()//
                .getGDBankCardItemDao()//
                .queryBuilder()//
                .where(GDBankCardItemDao.Properties.UserId.eq(ThinkerActApplication.getInstance().getUser().getId())).list();
    }

    /**
     * 插入一组银行卡列表
     * @param gdBankCardItems
     */
    public static void insertGDBankCardList(List<GDBankCardItem> gdBankCardItems) {
        if (gdBankCardItems == null) return;
        for (int i = 0, size = gdBankCardItems.size(); i < size; i++) {
            GDBankCardItem item = gdBankCardItems.get(i);
            insertOrUpdateGDBankCard(item);
        }

    }

    /**
     * 插入一张银行卡
     * @param item
     */
    private static void insertOrUpdateGDBankCard(GDBankCardItem item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDBankCardItemDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDBankCardItem.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 根据id删除一张银行卡
     * @param cardId
     */
    public static void deleteBankCard(int cardId) {
        GDBankCardItem item = queryGdBankCardItem(cardId);
        if (item != null)
            DaoFactory.sharedSessions()//
                    .getGDBankCardItemDao().delete(item);
    }

    /**
     * 根据id查询一张银行卡
     * @param cardId
     * @return
     */
    public static GDBankCardItem queryGdBankCardItem(int cardId) {
        return DaoFactory.sharedSessions()
                .getGDBankCardItemDao()
                .queryBuilder().where(GDBankCardItemDao.Properties.CardId.eq(cardId)).unique();
    }


    /**
     * 插入一个被禁言者
     * @param item
     */
    public static void insertOrUpdateGDGagItem(GDGagUserItem item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDGagUserItemDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDGagUserItem.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 解除一个被禁言者
     * @param userId
     */
    public static void deleteGagItem(String userId) {
        GDGagUserItem item = queryGdGagItem(userId);
        if (item != null)
            DaoFactory.sharedSessions()//
                    .getGDGagUserItemDao().delete(item);
    }

    /**
     * 根据用户id查询一个被禁言者
     * @param userId
     * @return
     */
    public static GDGagUserItem queryGdGagItem(String userId) {
        return DaoFactory.sharedSessions()
                .getGDGagUserItemDao()
                .queryBuilder().where(GDGagUserItemDao.Properties.UserId.eq(userId)).unique();
    }

    /**
     * 删除所有被禁言者列表
     */
    public static void deleteAllGagItem() {
        DaoFactory.sharedSessions()//
                .getGDGagUserItemDao().deleteAll();
    }

    /**
     * 插入一个搜索历史记录
     * @param item
     */
    public static void insertOrUpdateHistoryRecord(GDHistoryRecord item) {
        if (item == null) return;
        DaoFactory.sharedSessions()//
                .getGDHistoryRecordDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDHistoryRecord.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 插入一组搜索历史记录列表
     * @param items
     */
    public static void insertOrUpdateHistoryRecord(List<GDHistoryRecord> items) {
        if (items == null) return;
        for(GDHistoryRecord historyRecord:items)
            insertOrUpdateHistoryRecord(historyRecord);
    }

    /**
     * 查询所有搜索历史列表
     * @return
     */
    public static List<GDHistoryRecord> queryAllHistoryRecordList() {
        return DaoFactory.sharedSessions()
                .getGDHistoryRecordDao()
                .queryBuilder().list();
    }

    /**
     * 根据搜索的关键字查询
     * @param searchKey
     * @return
     */
    public static GDHistoryRecord queryHistoryRecord(String searchKey) {
        return DaoFactory.sharedSessions()
                .getGDHistoryRecordDao()
                .queryBuilder().where(GDHistoryRecordDao.Properties.SearchKey.eq(searchKey)).unique();
    }

    /**
     * 根据关键字删除搜索历史记录
     * @param searchKey
     */
    public static void deleteHistoryRecord(String searchKey) {
        GDHistoryRecord item = queryHistoryRecord(searchKey);
        if (item != null)
            DaoFactory.sharedSessions()//
                    .getGDHistoryRecordDao().delete(item);
    }

    /**
     * 删除所有历史记录
     */
    public static void deleteAllHistoryRecord() {
        DaoFactory.sharedSessions()//
                .getGDHistoryRecordDao().deleteAll();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<?> wrap(Object value) {
        ArrayList list = new ArrayList();
        list.add(value);
        return list;
    }

}
