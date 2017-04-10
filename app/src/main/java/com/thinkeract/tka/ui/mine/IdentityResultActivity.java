package com.thinkeract.tka.ui.mine;

import android.widget.Button;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ui.AppBarActivity;

/**
 * Created by minHeng on 2017/4/5 20:15.
 * mail:minhengyan@gmail.com
 */

public class IdentityResultActivity extends AppBarActivity {
    private TextView identityAgainTv;
    private TextView resultContentTv;
    private Button identityAgainBtn;
    private TextView connectCustomServiceTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_identity_result;
    }

    @Override
    protected void initView() {
        setTitle(R.string.identity_review);
        initializeView();
    }

    @Override
    protected void initData() {

    }

    private void initializeView() {

        identityAgainTv = (TextView) findViewById(R.id.identityAgainTv);
        resultContentTv = (TextView) findViewById(R.id.resultContentTv);
        identityAgainBtn = (Button) findViewById(R.id.identityAgainBtn);
        connectCustomServiceTv = (TextView) findViewById(R.id.connectCustomServiceTv);
    }
}
