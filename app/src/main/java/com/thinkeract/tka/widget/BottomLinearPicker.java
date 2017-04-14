package com.thinkeract.tka.widget;

import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.ActionSheetDialog;
import com.zitech.framework.widget.ActionSheetHelper;

import java.util.ArrayList;

/**
 * Created by ymh on 16/12/28.
 */
public class BottomLinearPicker {
	private int mItemHeight;
	ActionSheetDialog sheetDialog;
	LinearLayout contentView;
	Activity mContext;
	private ItemPickerListener listener;

	private ArrayList<String> mTextList = new ArrayList<>();
	private float textSize = ViewUtils.getDimenPx(R.dimen.w32);
	private float smallTextSize = ViewUtils.getDimenPx(R.dimen.w28);

	public interface ItemPickerListener {
		void onPicked(int itemIndex, String itemStr);
	}

	public BottomLinearPicker(Activity activity) {
		mContext = activity;
		mItemHeight = ViewUtils.getDimenPx(R.dimen.h100);
		initViews();
	}

	public BottomLinearPicker(Activity activity, String title) {
		mContext = activity;
		mItemHeight = ViewUtils.getDimenPx(R.dimen.h100);
		initViews();
		addTextHead(title);
	}

	private void initViews() {
		contentView = new LinearLayout(mContext);
		contentView.setBackgroundColor(mContext.getResources().getColor(R.color.windowBackground));
		contentView.setOrientation(LinearLayout.VERTICAL);
	}

	public void addText(final String content, int colorRes){
		TextView itemTv = new TextView(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mItemHeight);
		itemTv.setBackgroundResource(R.drawable.sl_white_item);
		itemTv.setGravity(Gravity.CENTER);
		itemTv.setTextColor(mContext.getResources().getColor(colorRes));
		itemTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		itemTv.setLayoutParams(params);
		itemTv.setText(content);
		itemTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onPicked(mTextList.indexOf(content),content);
				}
				sheetDialog.dismiss();
			}
		});
		mTextList.add(content);
		contentView.addView(itemTv);
		View dividerView = new View(mContext);
		LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		dividerParams.height = 1;
		dividerView.setLayoutParams(dividerParams);
		dividerView.setBackgroundColor(mContext.getResources().getColor(R.color.divider_color));
		contentView.addView(dividerView);
	}

	private void addTextCancel(){
		final String cancelStr = "取消";
		TextView cancelTv = new TextView(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mItemHeight);
		params.topMargin = mItemHeight/8;
		cancelTv.setBackgroundResource(R.drawable.sl_white_item);
		cancelTv.setGravity(Gravity.CENTER);
		cancelTv.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryGray));
		cancelTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallTextSize);
		cancelTv.setLayoutParams(params);
		cancelTv.setText(cancelStr);
		cancelTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(listener!=null){
//					listener.onPicked(mTextList.size()-1,cancelStr);
//				}
				sheetDialog.dismiss();
			}
		});
		mTextList.add(cancelStr);
		contentView.addView(cancelTv);
	}

	private void addTextHead(String headTitle){
		TextView cancelTv = new TextView(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mItemHeight);
		cancelTv.setBackgroundResource(R.drawable.sl_white_item);
		cancelTv.setGravity(Gravity.CENTER);
		cancelTv.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryGray));
		cancelTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallTextSize);
		cancelTv.setLayoutParams(params);
		cancelTv.setText(headTitle);
		mTextList.add(headTitle);
		contentView.addView(cancelTv);
		View dividerView = new View(mContext);
		LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		dividerParams.height = 1;
		dividerView.setLayoutParams(dividerParams);
		dividerView.setBackgroundColor(mContext.getResources().getColor(R.color.divider_color));
		contentView.addView(dividerView);
	}

	public void show() {
		showDialog();
	}
	private void showDialog() {
		if (sheetDialog == null) {
			addTextCancel();
			sheetDialog = ActionSheetHelper.createActionDialog(mContext, contentView);
		}
		sheetDialog.show();
	}

	public void setPickerListener(ItemPickerListener listener) {
		this.listener = listener;
	}
}
