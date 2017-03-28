package com.thinkeract.tka.common.manager;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

public class UpdateManager {

	private static final String TAG = "Updater";

	private Context context;

	private DownloadManager downloadManager;

	private BroadcastReceiver receiver;

	private long lastDownloadId = -1L;

	protected static String mCurrentVersion;

	public static String mNewVersion;

	public static String mVersionUrl;

	private boolean isOnlineInstall;

	protected static String mVersionUpdateContent;

	public static String OLDVERSION = "oldversion_brocast";

	public static String NEWVERSION = "newversion_brocast";

	private AlertDialog SuggestUpdateDialog;

	// private AlertDialog MustUpdateDialog;

	private ProgressDialog mProgressDialog;

	private CallBack updateCallBack;

	public UpdateManager(Context context) {
		this.context = context;
	}

	public static interface CallBack {

		public void updateFinsh();

		public void updateError();
	}

	public synchronized void update( final boolean Install,final CallBack  back,String userId) {
		
		if (Install) 
			showUpdateDialog();
//		int isCheckUpdate=XMSApplication.getApplication().getConfigure().getInt(AppConfigure.AUTO_CHECK_UPDATE);
		int isCheckUpdate=1;
		updateCallBack=back;
		isOnlineInstall=Install;
//		executor = Utils.getDefaultExecutor();

		if (isCheckUpdate == -1 || isCheckUpdate == 1||Install) {
			
//			Request request = new Request(RequestHeader.create(Api.UPDATE_CHECK), null);
//			HttpTaskExecutor.getInstance().execute(request, new ResponseListener<ApiResponse<?>>() {
//
//				@Override
//				public void onResponse(ApiResponse<?> result) {
//
//					UpdateCheckResponse upgradeDetail = (UpdateCheckResponse) result.getData();
//					String url=upgradeDetail.getAddress();
//					String desContent=upgradeDetail.getVersion_describe();
//					if (upgradeDetail.getStatus() == UpdateCheckResponse.NOUPGRADE) {
//						if (Install) {
//							ToastMaster.shortToast("当前已经是最新版本");
//						}
//						back.updateFinsh();
//					}else if (upgradeDetail.getStatus() == UpdateCheckResponse.FORCEUPGRADE) {
//						if (url==null ||url.equals("")) {
//							 return ;
//						}
//						showMustUpdateDialog(url, desContent);
//					}else if (upgradeDetail.getStatus() == UpdateCheckResponse.SUGGESTUPGRADE) {
//						if (url==null ||url.equals("")) {
//							 return ;
//						}
//						showSuggestUpdateDialog(url, desContent);
//					}
//
//				}
//
//				@Override
//				public void onError(HttpError httpError) {
//					if (mProgressDialog!=null)
//						mProgressDialog.dismiss();
//				}
//			});

		}
	}

	private void showUpdateDialog() {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("正在检查新版本");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.show();
	}

//	private void showMustUpdateDialog(final String url, String content) {
//		ForceDialog dialog = new ForceDialog(context,context.getString(R.string.check_new_version),content);
//		dialog.setOnConfirmButtonClickListener(new ForceDialog.OnConfirmButtonClickListener() {
//			@Override
//			public void onClick(Dialog dialog) {
//				Intent intent = new Intent(context, DownLoadApkService.class);
//				intent.putExtra(DownLoadApkService.DOWNLOADURL, url);
//				context.startService(intent);
//				ToastMaster.shortToast("开始下载...");
//			}
//		});
//		dialog.show();
//
//	}
//
//	private void showSuggestUpdateDialog(final String url, String content) {
//		CommonDialog dialog = new CommonDialog(context,content);
//		dialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
//			@Override
//			public void onClick(Dialog dialog) {
//				Intent intent = new Intent(context, DownLoadApkService.class);
//				intent.putExtra(DownLoadApkService.DOWNLOADURL, url);
//				context.startService(intent);
//				ToastMaster.shortToast("开始下载...");
//			}
//		});
//		dialog.show();
//
//	}

	public boolean isNewVersion(String current, String remote) {
		String[] oldArray = current.split("\\.");
		String[] newArray = remote.split("\\.");
		int length = oldArray.length < newArray.length ? oldArray.length : newArray.length;
		for (int i = 0; i < length; i++) {
			if (Integer.parseInt(oldArray[i]) < Integer.parseInt(newArray[i]))
				return true;
			else if (Integer.parseInt(oldArray[i]) > Integer.parseInt(newArray[i]))
				return false;
		}
		return false;
	}

}
