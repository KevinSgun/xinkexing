package com.thinkeract.tka.ui.home;

import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.widget.roundcornerprogressbar.TextRoundCornerProgressBar;

/**
 * Created by ymh on 2017/11/19 20:01
 * e-mail:minhengyan@gmail.com
 */

public class StepStatisticsActivity extends AppBarActivity {
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
    }

    @Override
    protected void initData() {
        mProgressA.setProgressText(String.valueOf((int) mProgressA.getProgress()));
        mProgressB.setProgressText(String.valueOf((int) mProgressB.getProgress()));
        mProgressC.setProgressText(String.valueOf((int) mProgressC.getProgress()));
        mProgressD.setProgressText(String.valueOf((int) mProgressD.getProgress()));
        mProgressE.setProgressText(String.valueOf((int) mProgressE.getProgress()));
        mProgressF.setProgressText(String.valueOf((int) mProgressF.getProgress()));
        mProgressG.setProgressText(String.valueOf((int) mProgressG.getProgress()));
    }
}
