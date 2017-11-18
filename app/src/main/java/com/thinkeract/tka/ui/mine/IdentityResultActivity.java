package com.thinkeract.tka.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkeract.tka.Constants;
import com.thinkeract.tka.R;
import com.thinkeract.tka.common.utils.ViewUtils;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.MainActivity;

/**
 * Created by minHeng on 2017/4/5 20:15.
 * mail:minhengyan@gmail.com
 */

public class IdentityResultActivity extends AppBarActivity {
    private TextView identityAgainTv;
    private TextView resultContentTv;
    private Button identityAgainBtn;
    private TextView connectCustomServiceTv;
    private boolean mShouldLaunchMain;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_identity_result;
    }

    @Override
    protected void initView() {
        mShouldLaunchMain = getIntent().getBooleanExtra(Constants.ActivityExtra.SHOULD_LAUNCH_MAIN,false);
        setTitle(R.string.identity_review);
        initializeView();
    }

    @Override
    protected void initData() {
        identityAgainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.identityAgainBtn){

        }
    }

    private void initializeView() {

        identityAgainTv = (TextView) findViewById(R.id.identityAgainTv);
        resultContentTv = (TextView) findViewById(R.id.resultContentTv);
        identityAgainBtn = (Button) findViewById(R.id.identityAgainBtn);
        connectCustomServiceTv = (TextView) findViewById(R.id.connectCustomServiceTv);
    }

    @Override
    public void onBackPressed() {
        if(mShouldLaunchMain){
            MainActivity.launch(this);
        }else{
            super.onBackPressed();
        }
    }

    public static void launch(Activity activity, boolean shouldLaunchMain){
        Intent intent = new Intent(activity,IdentityResultActivity.class);
        intent.putExtra(Constants.ActivityExtra.SHOULD_LAUNCH_MAIN,shouldLaunchMain);
        activity.startActivity(intent);
        ViewUtils.anima(ViewUtils.RIGHT_IN,activity);
    }
}
