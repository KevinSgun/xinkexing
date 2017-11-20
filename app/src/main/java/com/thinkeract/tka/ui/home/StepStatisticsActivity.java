package com.thinkeract.tka.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.DateUtil;
import com.thinkeract.tka.common.utils.Logger;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.data.api.entity.StepItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.contract.StepStuffContract;
import com.thinkeract.tka.ui.home.presenter.StepStuffPresenter;
import com.thinkeract.tka.widget.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.zitech.framework.SP;

import java.util.List;

/**
 * Created by ymh on 2017/11/19 20:01
 * e-mail:minhengyan@gmail.com
 */

public class StepStatisticsActivity extends AppBarActivity implements StepStuffContract.View {
    private TextRoundCornerProgressBar mProgressA;
    private TextView mDateATv;
    private TextRoundCornerProgressBar mProgressB;
    private TextView mDateBTv;
    private TextRoundCornerProgressBar mProgressC;
    private TextView mDateCTv;
    private TextRoundCornerProgressBar mProgressD;
    private TextView mDateDTv;
    private TextRoundCornerProgressBar mProgressE;
    private TextView mDateETv;
    private TextRoundCornerProgressBar mProgressF;
    private TextView mDateFTv;
    private TextRoundCornerProgressBar mProgressG;
    private TextView mDateGTv;

    private TextView mStepCountTv;
    private TextView mEquivalenceStepTv;
    private TextView mEnergyDepleteTv;
    private TextView mEquivalenceFatTv;
    private TextView mTargetCountTv;
    private StepStuffPresenter mPresenter;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private long stepCount;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_step_statistics;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity);
        mProgressA = (TextRoundCornerProgressBar) findViewById(R.id.progressA);
        mDateATv = (TextView) findViewById(R.id.dateATv);
        mProgressB = (TextRoundCornerProgressBar) findViewById(R.id.progressB);
        mDateBTv = (TextView) findViewById(R.id.dateBTv);
        mProgressC = (TextRoundCornerProgressBar) findViewById(R.id.progressC);
        mDateCTv = (TextView) findViewById(R.id.dateCTv);
        mProgressD = (TextRoundCornerProgressBar) findViewById(R.id.progressD);
        mDateDTv = (TextView) findViewById(R.id.dateDTv);
        mProgressE = (TextRoundCornerProgressBar) findViewById(R.id.progressE);
        mDateETv = (TextView) findViewById(R.id.dateETv);
        mProgressF = (TextRoundCornerProgressBar) findViewById(R.id.progressF);
        mDateFTv = (TextView) findViewById(R.id.dateFTv);
        mProgressG = (TextRoundCornerProgressBar) findViewById(R.id.progressG);
        mDateGTv = (TextView) findViewById(R.id.dateGTv);
        mStepCountTv = (TextView) findViewById(R.id.stepCountTv);
        mEquivalenceStepTv = (TextView) findViewById(R.id.equivalenceStepTv);
        mEnergyDepleteTv = (TextView) findViewById(R.id.energyDepleteTv);
        mEquivalenceFatTv = (TextView) findViewById(R.id.equivalenceFatTv);
        mTargetCountTv = (TextView) findViewById(R.id.targetCountTv);
    }

    @Override
    protected void initData() {

        mPresenter = new StepStuffPresenter(this);
        mPresenter.getStepCountData();

        mProgressA.setProgressText(String.valueOf((int) mProgressA.getProgress()));
        mProgressB.setProgressText(String.valueOf((int) mProgressB.getProgress()));
        mProgressC.setProgressText(String.valueOf((int) mProgressC.getProgress()));
        mProgressD.setProgressText(String.valueOf((int) mProgressD.getProgress()));
        mProgressE.setProgressText(String.valueOf((int) mProgressE.getProgress()));
        mProgressF.setProgressText(String.valueOf((int) mProgressF.getProgress()));
        mProgressG.setProgressText(String.valueOf((int) mProgressG.getProgress()));
        mDelayHandler.sendEmptyMessageDelayed(0, 500);
    }

    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            //每隔2000毫秒获取一次计步数据刷新UI
            long step = SP.getDefaultSP().getLong(Constants.STEP_COUNT, 0);
            if (stepCount != step) {
                stepCount = step;
                Logger.i("qwert", "取步数：" + (stepCount));
                mStepCountTv.setText(String.valueOf(stepCount));
                mEquivalenceStepTv.setText(String.format("相当于走了%s公里", getDistanceByStep(stepCount)));
                mEnergyDepleteTv.setText(getCalorieByStep(stepCount));
                mEquivalenceFatTv.setText(String.format("相当于燃烧了%s克脂肪", getFatByStep(stepCount)));
                mDelayHandler.sendEmptyMessageDelayed(0, 2000);
            }
            return false;
        }
    }

    // 千卡路里计算公式
    private String getCalorieByStep(long steps) {
        return String.format("%.1f", steps * 0.6f * 60 * 1.036f / 1000);
    }

    //脂肪消耗计算公式
    private String getFatByStep(long steps){
        return String.format("%.1f", steps * 0.6f * 60 * 1.036f/7700);
    }

    // 公里计算公式
    private String getDistanceByStep(long steps) {
        return String.format("%.2f", steps * 0.6f / 1000);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(List<StepItem> response) {
        if (response != null) {
            int size = response.size();
            if (size > 0) {
                setStepCountAndDate(response.get(0), mProgressA, mDateATv);
            }
            if (size > 1) {
                setStepCountAndDate(response.get(1), mProgressB, mDateBTv);
            }
            if (size > 2) {
                setStepCountAndDate(response.get(2), mProgressC, mDateCTv);
            }
            if (size > 3) {
                setStepCountAndDate(response.get(3), mProgressD, mDateDTv);
            }
            if (size > 4) {
                setStepCountAndDate(response.get(4), mProgressE, mDateETv);
            }
            if (size > 5) {
                setStepCountAndDate(response.get(5), mProgressF, mDateFTv);
            }
            if (size > 6) {
                setStepCountAndDate(response.get(6), mProgressG, mDateGTv);
            }

        }
    }

    private void setStepCountAndDate(StepItem stepItem, TextRoundCornerProgressBar progressBar, TextView dateTv) {
        progressBar.setProgress(stepItem.getSteps());
        progressBar.setProgressText(String.valueOf(stepItem.getSteps()));
        dateTv.setText(DateUtil.getMonthDay(stepItem.getDateStr()) + "\n" + DateUtil.getWeek(stepItem.getDateStr()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.upStepCount(String.valueOf(stepCount));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(null);
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,StepStatisticsActivity.class);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
