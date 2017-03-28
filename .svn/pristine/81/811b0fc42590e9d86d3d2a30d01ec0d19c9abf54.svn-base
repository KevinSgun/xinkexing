package com.thinkeract.tka.common.utils;

import android.util.SparseArray;

import com.thinkeract.tka.ThinkerActApplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by minHeng on 2016/12/12 11:47.
 * mail:minhengyan@gmail.com
 */

public class PlaceManagerUtil {

    private static final String ASSERT_PLACE_FILE_NAME = "addresses.xml"; // 初始assert文件名

    private Region region;
    private Thread loadThread;
    private static  final int TIME_OUT_MILLIS=200;

    public Region getRegion() {
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
        return region = loadRegion();

    }

    public void asynInitRegion() {
//		if(areaList)
        if (region == null && (loadThread == null || !loadThread.isAlive())) {
            loadThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    region = loadRegion();
                }
            };
            loadThread.start();
        }

    }

    private Region loadRegion() {

        Region region = new Region();
        InputStream inStream = null;
        try {
//			R.raw.
            inStream = ThinkerActApplication.getInstance().getAssets().open(ASSERT_PLACE_FILE_NAME);
            // 获得pull解析器对象
            // 方式一:使用工厂类XmlPullParserFactory
            XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullFactory.newPullParser();
            // 指定解析的文件和编码格式
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType(); // 获得事件类型
//            Book book = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagNameString = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("province".equals(tagNameString)) {
                            int index = region.provinces.size();
                            region.provinces.add(parser.getAttributeValue(0));
                            region.cities.put(index, new ArrayList<String>());
                        } else if ("city".equals(tagNameString)) {
                            region.cities.get(region.cities.size() - 1).add(parser.getAttributeValue(0));
                        }
                        break;
                    case XmlPullParser.END_TAG:
//                        if ("Book".equals(tagNameString)) {
//                            list.add(book);
//                        }
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


        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return region;
    }


    public class Region {
        public final SparseArray<ArrayList<String>> cities = new SparseArray<>();
        public final ArrayList<String> provinces = new ArrayList<>();


    }
}
