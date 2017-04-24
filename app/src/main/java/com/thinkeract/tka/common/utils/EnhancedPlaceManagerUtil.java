package com.thinkeract.tka.common.utils;

import android.util.SparseArray;

import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.data.api.entity.AreaVO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minHeng on 2016/12/12 11:47.
 * mail:minhengyan@gmail.com
 */

public class EnhancedPlaceManagerUtil {

    private static final String ASSERT_PLACE_FILE_NAME = "placedata.xml"; // 初始assert文件名

    private RegionVO region;
    private Thread loadThread;
    private static  final int TIME_OUT_MILLIS=200;

    public RegionVO getRegion() {
        if (loadThread != null && loadThread.isAlive()) {
            synchronized (this) {
                try {
                    wait(TIME_OUT_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        if (region != null) {
            return region;
        }
        return region = loadLocalAreaData();

    }

    public void asyncInitRegion() {
        if (region == null && (loadThread == null || !loadThread.isAlive())) {
            loadThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    region = loadLocalAreaData();
                }
            };
            loadThread.start();
        }

    }

    private ArrayList<AreaVO> loadDetailRegion() {
        ArrayList<AreaVO> areaVOs = new ArrayList<>();
        try {
            InputStream inStream = ThinkerActApplication.getInstance().getAssets().open(ASSERT_PLACE_FILE_NAME);
            // 获得pull解析器对象
            // 方式一:使用工厂类XmlPullParserFactory
            XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullFactory.newPullParser();
            // 指定解析的文件和编码格式
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType(); // 获得事件类型
            AreaVO areaVO = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagNameString = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("p".equals(tagNameString)) {
                            areaVO = new AreaVO();
                            areaVO.setKey(Integer.parseInt(parser.getAttributeValue(0)));
                            areaVO.setVal(parser.getAttributeValue(1));
                            areaVO.setId(parser.getAttributeValue(2));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("p".equals(tagNameString)) {
                            areaVOs.add(areaVO);
                            areaVO = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();//重新赋值，不然会死循环
            }

            inStream.close();
            synchronized (this) {
                notifyAll();
            }


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return areaVOs;
    }

    private RegionVO loadLocalAreaData() {
        List<AreaVO> newAreaList = loadDetailRegion();
        ArrayList<AreaVO> tempCityList = new ArrayList<>();
        ArrayList<AreaVO> tempAreaList = new ArrayList<>();
        RegionVO regionVO = new RegionVO();
        for(AreaVO areaVO:newAreaList){
            if(areaVO.getLevel() == 1){
                regionVO.provinceList.add(areaVO);
                regionVO.cityMap.put(areaVO.getKey(),new ArrayList<AreaVO>());
            }else if(areaVO.getLevel() == 2){
                tempCityList.add(areaVO);
                regionVO.areaMap.put(areaVO.getKey(),new ArrayList<AreaVO>());
            }else{
                tempAreaList.add(areaVO);
            }
        }

        for (AreaVO item : tempCityList) {
            ArrayList<AreaVO> list = regionVO.cityMap.get(item.getParentId());
            if (list != null) {
                list.add(item);
            } else {
                ArrayList<AreaVO> newList = new ArrayList<>();
                newList.add(item);
                regionVO.cityMap.put(item.getParentId(), newList);
            }
        }

        for (AreaVO item : tempAreaList) {
            ArrayList<AreaVO> list = regionVO.areaMap.get(item.getParentId());
            if (list != null) {
                list.add(item);
            } else {
                ArrayList<AreaVO> newList = new ArrayList<>();
                newList.add(item);
                regionVO.areaMap.put(item.getParentId(), newList);
            }
        }
        return regionVO;
    }

    public class RegionVO {
        public final ArrayList<AreaVO> provinceList = new ArrayList<>();
        public final SparseArray<ArrayList<AreaVO>> cityMap = new SparseArray<>();
        public final SparseArray<ArrayList<AreaVO>> areaMap = new SparseArray<>();
    }
}
