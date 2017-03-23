package com.thinkeract.tka;


/**
 * Created by minHeng on 2017/3/10 0001 14:13.
 * mail:minhengyan@gmail.com
 */
public class Events {

    /**
     * 关闭
     */
    public static class CloseEvent {
        public static final String FINISH_ALL = "finishAll";
        public static final String FINISH_REGISTER = "finishRegister";
        public static final String FINISH_FIND_PASSWORD = "finishFindPassWord";
        public static final String FINISH_SELECT_PHOTO ="finishSelectPhoto" ;
        public static final String FINISH_PERFECT_DATA = "finishPerfectData";
        public static final String CLOSE_GUIDE = "close_guide";
        public String eventType;

        public CloseEvent(String eventType) {
            this.eventType = eventType;
        }
    }

    public static class UserDataEvent{
        public final String[] keys;
        public UserDataEvent(String... keys){
            this.keys=keys;
        }
    }

}
