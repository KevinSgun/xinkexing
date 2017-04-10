package com.thinkeract.tka.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.zitech.framework.widget.ValidDialog;

public class CommonDialog extends ValidDialog {
	private TextView contentView;
	private Button confirm;
	private Button cancel;
	private OnPositiveButtonClickListener onPositiveButtonClickListener;
	private OnNegativeButtonClickListener onNegativeButtonClickListener;

	public CommonDialog(Context context, int resId) {
		this(context,context.getString(resId));
	}


	// private TextView titleView;

	public interface OnPositiveButtonClickListener {
		public void onClick(Dialog dialog);
	}

	public interface OnNegativeButtonClickListener {
		public void onClick(Dialog dialog);
	}

	public CommonDialog(Context context, String content) {
		super(context, R.style.CommonDialog);
		setContentView(R.layout.dialog_common_style);
		contentView = (TextView) findViewById(R.id.content);
		// titleView = (TextView) findViewById(R.id.title);

		// titleView.setText(title);
		contentView.setText(content);
		confirm = (Button) findViewById(R.id.confirm);
		cancel = (Button) findViewById(R.id.cancel);
		confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				if (onPositiveButtonClickListener != null)
					onPositiveButtonClickListener.onClick(CommonDialog.this);
			}


		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if (onNegativeButtonClickListener != null)
					onNegativeButtonClickListener.onClick(CommonDialog.this);
			}
		});
	}

	public void setContentText(String txtStr){
		contentView.setText(txtStr);
	}

	public void setPositiveButtonText(String text) {
		confirm.setText(text);
	}

	public void setCancelButtonText(String text){
		cancel.setText(text);
	}

	public void setOnPositiveButtonClickListener(OnPositiveButtonClickListener onPositiveButtonClickListener) {
		this.onPositiveButtonClickListener = onPositiveButtonClickListener;
	}

	public void setOnNegativeButtonClickListener(OnNegativeButtonClickListener onNegativeButtonClickListener) {
		this.onNegativeButtonClickListener = onNegativeButtonClickListener;
	}
}
