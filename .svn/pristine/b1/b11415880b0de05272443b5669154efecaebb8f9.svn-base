package com.thinkeract.tka.data.db;

import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.data.db.greendao.DaoSession;
import com.zitech.framework.BaseApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DaoFactory {

	public static final int DATA_INSERT = 1;
	public static final int DATA_UPDATE = 2;
	public static final int DATA_DELETE = 3;
	public static final int DATA_CLEAR = 4;

	public static HashMap<Class<?>, List<DBListener>> dataChangeListeners = new HashMap<Class<?>, List<DBListener>>();

	private static UpdateDaoMaster.UpdateDevOpenHelper devOpenHelper;
	private static DaoSession sharedSession;

	public static DaoSession sharedSessions() {
		if (devOpenHelper == null) {
			devOpenHelper = new UpdateDaoMaster.UpdateDevOpenHelper(BaseApplication.getInstance().getApplicationContext(), "travel-db", null);
		}
		if (sharedSession == null) {
			UpdateDaoMaster daoMaster = new UpdateDaoMaster(devOpenHelper.getWritableDatabase());
			sharedSession = daoMaster.newSession();
		}
		return sharedSession;
	}
	public static void addListener(Class<?> cls, DBListener listener) {
		if (listener == null)
			return;
		if (dataChangeListeners.get(cls) == null) {
			List<DBListener> list = new ArrayList<DBListener>();
			list.add(listener);
			dataChangeListeners.put(cls, list);
		} else {
			dataChangeListeners.get(cls).add(listener);
		}
	}

	public static void removeListener(Class<?> cls, DBListener listener) {
		if (dataChangeListeners.get(cls) != null && listener != null) {
			dataChangeListeners.remove(listener);
		}
	}

	public static void notifyDBDataChanged(Class<?> cls, final int changeType, final List<?> data) {
		final List<DBListener> list = dataChangeListeners.get(cls);
		if (list != null && list.size() > 0) {
			ThinkerActApplication.getInstance().post(new Runnable() {
				@Override
				public void run() {
					for (DBListener listener : list) {
						listener.onDataChanged(changeType, data);
					}
				}
			});
		}
	}

	public  interface DBListener {
		void onDataChanged(int type, List<?> data);
	}

}
