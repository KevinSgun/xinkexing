package com.thinkeract.tka.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkeract.tka.Events;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.vo.Dir;
import com.thinkeract.tka.common.vo.Photo;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.preview.adapter.ListAdapter;
import com.thinkeract.tka.widget.ContentViewHolder;
import com.zitech.framework.widget.RemoteImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看所有含有图片的目录
 * 
 */
public class PhotoDirActivity extends AppBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private ListView mListView;
	private ArrayList<Photo> selectedList;
	private String recentPreview;
	private int recentSize;
	private ContentViewHolder contentHolder;

	@Override
	protected int getContentViewId() {
		return R.layout.preview_dir_list;
	}

	@Override
	protected void initView() {
		contentHolder = (ContentViewHolder) findViewById(R.id.content_holder);
		contentHolder.setErrorPrompt(R.string.album_error_prompt);
		contentHolder.showLoading();
		setTitle("选择相册");
		mListView = (ListView) findViewById(R.id.listview);
		handleIntent();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dir dir = (Dir) parent.getItemAtPosition(position);
				if (dir != null) {
					Intent data = new Intent();
					data.putExtra(PhotoGridActivity.PHOTO_DIR_ID, dir.id);
					data.putExtra(PhotoGridActivity.PHOTO_DIR_NAME, dir.name);
					data.putExtra(PhotoGridActivity.INTENT_CHOOSE_RECENTLY, dir.isRecent);
					data.putExtra(PhotoGridActivity.SELECTED_PHOTO_LIST, selectedList);
					// startActivityForResult(intent, 1);
					// startActivity(data);
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});

//		mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));

	}

	@Override
	protected void initData() {
		getSupportLoaderManager().initLoader(0, null, this);
	}


	@Override
	public void onNotifyClose(Events.CloseEvent closeEvent) {
		super.onNotifyClose(closeEvent);
		if (Events.CloseEvent.FINISH_SELECT_PHOTO.equals(closeEvent.eventType)) {
			finish();
		}
	}

	private void handleIntent() {
		selectedList = getIntent().getParcelableArrayListExtra(PhotoGridActivity.SELECTED_PHOTO_LIST);
		// updateCheckedNum(selectedList.size());
		recentPreview = getIntent().getStringExtra(PhotoGridActivity.RECENT_LIST_PREVIEW);
		recentSize = getIntent().getIntExtra(PhotoGridActivity.RECENT_LIST_SIZE, 0);
	}

//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		handleIntent();
//	}

	protected void onActionBarItemClick( int position) {
		super.onActionBarItemClick( position);
		// if (position == ActionBar.LEFT_ITEM) {
		// selectedList.clear();
		// notifyChange(ACTION_FINISH, ApplicationExtra.FINISH_SELECT_PHOTO);
		// }
	};

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { "count(1) length",
				MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA },
				"1=1) GROUP BY " + MediaStore.Images.Media.BUCKET_ID + " -- (", null, MediaStore.Images.Media.BUCKET_DISPLAY_NAME
						+ " ASC," + MediaStore.Images.Media.DATE_MODIFIED + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		if (isFinishing())
			return;
		if (cursor != null && cursor.getCount() > 0) {
			ArrayList<Dir> list = new ArrayList<Dir>();
			Dir recent = new Dir();
			recent.isRecent = true;
			recent.name = "最近照片";
			recent.imgPath = recentPreview;
			recent.length = recentSize;

			list.add(recent);
			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				String dirPath;
				int index = path.lastIndexOf('/');
				if (index > 0) {
					dirPath = path.substring(0, index);
				} else {
					dirPath = path;
				}

				Dir dir = new Dir();
				dir.id = String.valueOf(id);
				dir.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
				dir.text = dirPath;
				dir.imgPath = path;
				dir.length = cursor.getInt(cursor.getColumnIndex("length"));

				int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
				String imageId = null;
				if (idIndex >= 0) {
					imageId = String.valueOf(cursor.getInt(idIndex));
				} else {
					imageId = String.valueOf(System.currentTimeMillis());
				}
				String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA };
				Cursor thumbCusor = getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, projection,
						Thumbnails.IMAGE_ID + "=" + imageId, null, null);
				if (thumbCusor.moveToFirst()) {
					String imagePath = null;
					int dataColumn = thumbCusor.getColumnIndex(Thumbnails.DATA);
					imagePath = thumbCusor.getString(dataColumn);
					dir.thumbPath = imagePath;
				}
				thumbCusor.close();

				list.add(dir);
			}
			cursor.close();
			if (list.size() > 0) {
				contentHolder.showContent();
			} else {
				contentHolder.showNoData();
			}
			ImageDirAdapter adapter = new ImageDirAdapter(this, list);
			mListView.setAdapter(adapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	public class ImageDirAdapter extends ListAdapter<Dir> {
		public ImageDirAdapter(Context context, List<Dir> list) {
			super(context);
			setList(list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = inflatView(R.layout.preview_dir_list_item);
				holder = new Holder();
				holder.mImageView = (RemoteImageView) convertView.findViewById(R.id.iv);
				// holder.mTextView = (TextView)
				// convertView.findViewById(R.id.path);
				holder.mNameTextView = (TextView) convertView.findViewById(R.id.name);

				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Dir dir = mList.get(position);
			// holder.mTextView.setText(dir.text);
			holder.mNameTextView.setText(Html.fromHtml(dir.name + " <font color=\"#999999\">(" + dir.length + ")</font>"));
			holder.mImageView.setDefaultImageResource(R.drawable.ic_pic_empty);
			holder.mImageView.setImageUri(dir.getThumbUrl());
			return convertView;
		}

		private View inflatView(int layoutId) {
			return LayoutInflater.from(PhotoDirActivity.this).inflate(layoutId, null);
		}

		private class Holder {
			public TextView mNameTextView;
			public RemoteImageView mImageView;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getSupportLoaderManager().destroyLoader(0);
	}
	// private void updateCheckedNum(int size) {
	// mCurrentPicNum.setText(size + "/" + PhotoGridActivity.MAX_SIZE);
	// }
}
