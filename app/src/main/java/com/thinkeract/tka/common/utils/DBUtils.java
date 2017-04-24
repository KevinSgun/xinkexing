package com.thinkeract.tka.common.utils;

import com.thinkeract.tka.User;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.db.DaoFactory;
import com.thinkeract.tka.data.db.greendao.GDAddress;
import com.thinkeract.tka.data.db.greendao.GDAddressDao;
import com.thinkeract.tka.data.db.greendao.GDGoodsItem;
import com.thinkeract.tka.data.db.greendao.GDGoodsItemDao;

import java.util.ArrayList;
import java.util.List;

import static com.thinkeract.tka.data.db.DaoFactory.sharedSessions;

/**
 * Created by minHeng on 2016/12/5 14:51.
 * mail:minhengyan@gmail.com
 */

public class DBUtils {

    /**
     * 插入一个商品
     * @param item
     */
    public static void insertOrReplaceGoods(GDGoodsItem item) {
        if (item == null||item.getGoodsId() == 0) return;
        sharedSessions()//
                .getGDGoodsItemDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDGoodsItemDao.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 更新一个商品记录
     * @param item
     */
    public static void updateGoods(GDGoodsItem item) {
        if (item == null||item.getGoodsId() == 0) return;
        sharedSessions()//
                .getGDGoodsItemDao().update(item);
        DaoFactory.notifyDBDataChanged(GDGoodsItemDao.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    public static void insertOrReplaceAll(List<GDGoodsItem> goodsItemList){
        if(goodsItemList == null) return;
        for(GDGoodsItem goodsItem:goodsItemList){
            if(goodsItem.getGoodsId() != 0)
            sharedSessions()//
                    .getGDGoodsItemDao().insertOrReplace(goodsItem);
        }
        DaoFactory.notifyDBDataChanged(GDGoodsItemDao.class, DaoFactory.DATA_UPDATE, goodsItemList);
    }

    /**
     * 删除所有商品记录
     */
    public static void deleteAllWatchHistory() {
        sharedSessions()//
                .getGDGoodsItemDao().deleteAll();
        DaoFactory.notifyDBDataChanged(GDGoodsItemDao.class, DaoFactory.DATA_DELETE, queryAllGoodsList());
    }

    /**
     * 根据商品id删除一条商品记录
     * @param userAndGoodsId
     */
    public static void deleteGoods(String userAndGoodsId) {
        GDGoodsItem item = queryGoods(userAndGoodsId);
        if (item != null)
            sharedSessions()//
                    .getGDGoodsItemDao().delete(item);
        DaoFactory.notifyDBDataChanged(GDGoodsItemDao.class, DaoFactory.DATA_DELETE, wrap(item));
    }

    /**
     * 根据商品id查询一条商品记录
     * @param userAndGoodsId
     * @return
     */
    public static GDGoodsItem queryGoods(String userAndGoodsId) {
        return sharedSessions()
                .getGDGoodsItemDao()
                .queryBuilder().where(GDGoodsItemDao.Properties.UserGoodsId.eq(userAndGoodsId)).unique();
    }

    /**
     * 查询一个用户购物车内的所有商品
     * @return
     */
    public static List<GDGoodsItem> queryAllGoodsList() {
        return sharedSessions()
                .getGDGoodsItemDao()
                .queryBuilder().where(GDGoodsItemDao.Properties.UserId.eq(User.get().getId())).list();
    }

    public static int queryAllGoodsCount(){
        return queryAllGoodsList().size();
    }

    /**
     * 插入一条地址
     * @param item
     */
    public static void insertOrReplaceAddress(GDAddress item) {
        if (item == null) return;
        sharedSessions()//
                .getGDAddressDao().insertOrReplace(item);
        DaoFactory.notifyDBDataChanged(GDAddressDao.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 插入多条地址
     * @param itemList
     */
    public static void insertAddressList(List<GDAddress> itemList) {
        if (itemList == null) return;
        for(GDAddress gdAddress:itemList)
            insertOrReplaceAddress(gdAddress);
    }

    /**
     * 更新一条地址记录
     * @param item
     */
    public static void updateGoods(GDAddress item) {
        if (item == null) return;
        sharedSessions()//
                .getGDAddressDao().update(item);
        DaoFactory.notifyDBDataChanged(GDAddressDao.class, DaoFactory.DATA_UPDATE, wrap(item));
    }

    /**
     * 删除所有收获地址记录
     */
    public static void deleteAllAddress() {
        sharedSessions()//
                .getGDAddressDao().deleteAll();
    }

    /**
     * 根据地址id删除一条地址记录
     * @param userAndAddressId
     */
    public static void deleteAddress(String userAndAddressId) {
        GDAddress item = queryAddress(userAndAddressId);
        if (item != null)
            sharedSessions()//
                    .getGDAddressDao().delete(item);
    }

    /**
     * 根据地址id查询一条地址记录
     * @param userAndAddressId
     * @return
     */
    public static GDAddress queryAddress(String userAndAddressId) {
        return sharedSessions()
                .getGDAddressDao()
                .queryBuilder().where(GDAddressDao.Properties.UserAddressId.eq(userAndAddressId)).unique();
    }

    /**
     * 查询一个用户的所有收货地址
     * @return
     */
    public static List<GDAddress> queryAllAddressList() {
        return sharedSessions()
                .getGDAddressDao()
                .queryBuilder().where(GDAddressDao.Properties.UserId.eq(User.get().getId())).list();
    }

    /**
     * 查询一个用户的默认收货地址
     * @return
     */
    public static GDAddress queryDefAddress() {
        List<GDAddress> gdAddressList = DaoFactory.sharedSessions()
                .getGDAddressDao()
                .queryBuilder().where(GDAddressDao.Properties.UserId.eq(User.get().getId())).list();
        if(gdAddressList != null&&gdAddressList.size()>0) {
            for (GDAddress gdAddress : gdAddressList) {
                if (gdAddress.getStatus() == 1)
                    return gdAddress;
            }
            return gdAddressList.get(0);
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<?> wrap(Object value) {
        ArrayList list = new ArrayList();
        list.add(value);
        return list;
    }

    public static List<AddressItem> convertToAddressItemList(List<GDAddress> addressList) {
        List<AddressItem> itemList = new ArrayList<>();
        if(addressList != null&&addressList.size()>0){
            for(GDAddress gdAddress:addressList){
                itemList.add(convertToAddress(gdAddress));
            }
        }
        return itemList;
    }

    private static AddressItem convertToAddress(GDAddress gdAddress) {
        AddressItem addressItem = new AddressItem();
        addressItem.setAddress(gdAddress.getAddress());
        addressItem.setId(gdAddress.getAddressId());
        addressItem.setCityname(gdAddress.getCity());
        addressItem.setContact(gdAddress.getContact());
        addressItem.setPhone(gdAddress.getPhone());
        addressItem.setStatus(gdAddress.getStatus());
        addressItem.setUid(gdAddress.getUserId());
        return addressItem;
    }

    public static List<GDAddress> convertToGDAddressItemList(List<AddressItem> addressList) {
        List<GDAddress> gdAddressList = new ArrayList<>();
        if(addressList != null&&addressList.size()>0){
            for(AddressItem addressItem:addressList){
                gdAddressList.add(convertToGDAddress(addressItem));
            }
        }
        return gdAddressList;
    }

    private static GDAddress convertToGDAddress(AddressItem addressItem) {
        GDAddress gdAddress = new GDAddress();
        gdAddress.setAddress(addressItem.getAddress());
        gdAddress.setAddressId(addressItem.getId());
        gdAddress.setCity(addressItem.getCityname());
        gdAddress.setContact(addressItem.getContact());
        gdAddress.setPhone(addressItem.getPhone());
        gdAddress.setStatus(addressItem.getStatus());
        gdAddress.setUserId(addressItem.getUid());
        return gdAddress;
    }
}
