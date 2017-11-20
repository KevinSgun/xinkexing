package com.thinkeract.tka.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.response.CheckResultData;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.contract.CheckResultStuffContract;
import com.thinkeract.tka.ui.home.presenter.CheckResultStuffPresenter;
import com.thinkeract.tka.widget.MultiRadioGroup;
import com.zitech.framework.utils.ToastMaster;

import java.util.List;

/**
 * Created by ymh on 2017/10/15 15:45
 * e-mail:minhengyan@gmail.com
 */

public class CheckResultActivity extends AppBarActivity implements CheckResultStuffContract.View {
    private int mId;
    private TextView mScoreTv;
    private LinearLayout mResultListLayout;
    private TextView mDoctorSAdviceTv;
    private ImageView mActionIv;
    private LinearLayout mDoctorSAdviceLayout;
    private CheckResultStuffPresenter mPresenter;
    private String mTitle;
    private int mCurrentScore;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check_result;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra(Constants.ActivityExtra.BUSI_ID,0);
        mTitle = getIntent().getStringExtra(Constants.ActivityExtra.TITLE);
        setTitle(mTitle);
        mScoreTv = (TextView) findViewById(R.id.scoreTv);
        mResultListLayout = (LinearLayout) findViewById(R.id.resultListLayout);
        mDoctorSAdviceLayout = (LinearLayout) findViewById(R.id.doctorSAdviceLayout);
        mDoctorSAdviceTv = (TextView) findViewById(R.id.doctorSAdviceTv);
        mActionIv = (ImageView) findViewById(R.id.actionIv);
        mActionIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new CheckResultStuffPresenter(this);
        mPresenter.getCheckResultData(String.valueOf(mId));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.actionIv){
            showActivity(ActionActivity.class);
        }
    }

    @Override
    public void showError(String message) {
        ThinkerActApplication.getInstance().postDelay(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },800);
    }

    @Override
    public void showSuccess(CheckResultData response) {
        refreshUI(response);
    }

    private void refreshUI(CheckResultData response) {
        mCurrentScore = response.getGrade();
        mScoreTv.setText(String.valueOf(mCurrentScore));
        List<CheckResultData.ItemsBean>  itemsBeanList = response.getItems();
        View topView = LayoutInflater.from(this).inflate(R.layout.item_result_list_top,null,false);
        LinearLayout.LayoutParams params = new MultiRadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewUtils.getDimenPx(R.dimen.w60));
        mResultListLayout.addView(topView,params);
        int size = itemsBeanList != null?itemsBeanList.size():0;
        if(mCurrentScore <=0&&size==0){
            ToastMaster.longToast("暂无检测数据");
        }
        if(size>0){
            for(int i=0;i<size;i++){
                CheckResultData.ItemsBean item = itemsBeanList.get(i);
                View itemView = LayoutInflater.from(this).inflate(R.layout.item_check_result_list,null,false);
                LinearLayout rootLayout = (LinearLayout) itemView.findViewById(R.id.checkResultItemLayout);
                TextView checkItemTv = (TextView) itemView.findViewById(R.id.checkItemTv);
                TextView checkResultTv = (TextView) itemView.findViewById(R.id.checkResultTv);
                TextView referenceRangeTv = (TextView) itemView.findViewById(R.id.referenceRangeTv);
                boolean isCheck = (item.getIsCheck() == 1);
                checkItemTv.setTextColor(isCheck
                        ?ContextCompat.getColor(getContext(),R.color.textColorPrimary)
                        :ContextCompat.getColor(getContext(),R.color.textColorPrimaryDark));
                checkItemTv.setText(item.getName());
                int score = item.getScore();
                String range = item.getNormalRange();
                Drawable itemDrawable;
                if(i == size-1){
                    itemDrawable = ContextCompat.getDrawable(getContext(),R.drawable.bg_bottom_corner_gray_f7f7f7);
                }else {
                    itemDrawable = ContextCompat.getDrawable(getContext(),R.color.gray_f7f7f7);
                }
                int scoreColor = ContextCompat.getColor(getContext(),R.color.textColorPrimary);
                if(isCheck&&!TextUtils.isEmpty(range)){
                    String[] rangeArray = range.split("-");
                    if(rangeArray.length == 2){
                        int left = 0;
                        int right = 0;
                        try {
                            left = Integer.parseInt(rangeArray[0]);
                            right = Integer.parseInt(rangeArray[1]);
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }

                        if(score>0&&score>=left&&score<=right){
                            scoreColor = ContextCompat.getColor(getContext(),R.color.text_green_light);
                            if(i == size-1){
                                itemDrawable = ContextCompat.getDrawable(getContext(),R.drawable.bg_bottom_corner_gray_f4faec);
                            }else {
                                itemDrawable = ContextCompat.getDrawable(getContext(),R.color.gray_f4faec);
                            }
                        }else{
                            scoreColor = ContextCompat.getColor(getContext(),R.color.text_red);
                            if(i == size-1){
                                itemDrawable = ContextCompat.getDrawable(getContext(),R.drawable.bg_bottom_corner_pink_light);
                            }else {
                                itemDrawable = ContextCompat.getDrawable(getContext(),R.color.pink_light);
                            }
                        }
                    }
                }
                rootLayout.setBackground(itemDrawable);
                checkResultTv.setTextColor(scoreColor);
                checkResultTv.setText(isCheck?String.valueOf(item.getScore()):"未检测");
                referenceRangeTv.setTextColor(isCheck
                        ?ContextCompat.getColor(getContext(),R.color.textColorPrimary)
                        :ContextCompat.getColor(getContext(),R.color.textColorPrimaryDark));
                referenceRangeTv.setText(isCheck?item.getNormalRange():"正常参考范围");
                mResultListLayout.addView(itemView,params);
            }
        }
    }


    public static void launch(Activity activity, int id,String title){
        Intent intent = new Intent(activity,CheckResultActivity.class);
        intent.putExtra(Constants.ActivityExtra.BUSI_ID,id);
        intent.putExtra(Constants.ActivityExtra.TITLE,title);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
