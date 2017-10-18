package com.thinkeract.tka.widget.filterview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.ThinkerActApplication;
import com.thinkeract.tka.common.utils.ViewUtils;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * 一个条件筛选器控件，默认支持分类、排序、和筛选三种
 * Created by minHeng on 17/4/12 13:57.
 * mail:minhengyan@gmail.com
 */
public class FilterView extends LinearLayout implements View.OnClickListener {

    private TextView tvCategoryTitle;
    private ImageView ivCategoryArrow;
    private TextView tvSortTitle;
    private ImageView ivSortArrow;
    private TextView tvFilterTitle;
    private ImageView ivFilterArrow;
    private LinearLayout llCategory;
    private LinearLayout llSort;
    private LinearLayout llFilter;
    private ListView lvLeft;
    private ListView lvRight;
//    private LinearLayout llHeadLayout;
    private LinearLayout llContentListView;
    private View viewMaskBg;

    private Context mContext;
    private Activity mActivity;

    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    public static final int POSITION_CATEGORY = 0; // 分类的位置
    public static final int POSITION_SORT = 1; // 排序的位置
    public static final int POSITION_FILTER = 2; // 筛选的位置

    private boolean isShowing = false;
    private int panelHeight;
    private FilterData filterData;

    private FilterLeftAdapter leftAdapter;
    private FilterRightAdapter rightAdapter;
    private FilterLonelyAdapter sortAdapter;
    private FilterLonelyAdapter filterAdapter;

    private FilterTwoEntity leftSelectedCategoryEntity; // 被选择的分类项左侧数据
    private FilterEntity rightSelectedCategoryEntity; // 被选择的分类项右侧数据
    private FilterEntity selectedSortEntity; // 被选择的排序项
    private FilterEntity selectedFilterEntity; // 被选择的筛选项
    private static final long ANIMATION_DURATION = 250;

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.filterview_layout, this);
        initView();
        initListener();
    }

    private void initView() {

        tvCategoryTitle = (TextView) findViewById(R.id.tv_category_title);
        ivCategoryArrow = (ImageView) findViewById(R.id.iv_category_arrow);
        llCategory = (LinearLayout) findViewById(R.id.categoryLayout);
        tvSortTitle = (TextView) findViewById(R.id.tv_sort_title);
        ivSortArrow = (ImageView) findViewById(R.id.iv_sort_arrow);
        llSort = (LinearLayout) findViewById(R.id.sortLayout);
        tvFilterTitle = (TextView) findViewById(R.id.filterTitleTv);
        ivFilterArrow = (ImageView) findViewById(R.id.filterArrowIv);
        llFilter = (LinearLayout) findViewById(R.id.filterLayout);
//        llHeadLayout = (LinearLayout) findViewById(R.id.ll_head_layout);
        viewMaskBg = findViewById(R.id.maskBgView);
        lvLeft = (ListView) findViewById(R.id.leftLv);
        lvRight = (ListView) findViewById(R.id.rightLv);
        llContentListView = (LinearLayout) findViewById(R.id.contentLvLayout);
        
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llCategory.setOnClickListener(this);
        llSort.setOnClickListener(this);
        llFilter.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(ViewUtils.isFastDoubleClick()) return;
        switch (v.getId()) {
            case R.id.categoryLayout:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }

                show(filterPosition);
                break;
            case R.id.sortLayout:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                show(filterPosition);
                break;
            case R.id.filterLayout:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                show(filterPosition);
                break;
            case R.id.maskBgView:
                hide();
                break;
        }
    }

    // 复位筛选的显示状态
    public void resetViewStatus() {
        tvCategoryTitle.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
        ivCategoryArrow.setImageResource(R.mipmap.ic_filter_down_arrow);

        tvSortTitle.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
        ivSortArrow.setImageResource(R.mipmap.ic_filter_down_arrow);

        tvFilterTitle.setTextColor(mContext.getResources().getColor(R.color.textColorPrimaryDark));
        ivFilterArrow.setImageResource(R.mipmap.ic_filter_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetViewStatus();
        hide();
    }

    // 设置分类数据
    private void setCategoryAdapter() {
        lvLeft.setVisibility(VISIBLE);
        lvRight.setVisibility(VISIBLE);

        // 左边列表视图
        leftAdapter = new FilterLeftAdapter(mContext, filterData.getCategory());
        lvLeft.setAdapter(leftAdapter);
        if (leftSelectedCategoryEntity == null) {
            leftSelectedCategoryEntity = filterData.getCategory().get(0);
        }
        leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);

        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftSelectedCategoryEntity = filterData.getCategory().get(position);
                leftAdapter.setSelectedEntity(leftSelectedCategoryEntity);

                // 右边列表视图
                rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
                lvRight.setAdapter(rightAdapter);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
            }
        });

        // 右边列表视图
        rightAdapter = new FilterRightAdapter(mContext, leftSelectedCategoryEntity.getList());
        lvRight.setAdapter(rightAdapter);
        rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightSelectedCategoryEntity = leftSelectedCategoryEntity.getList().get(position);
                rightAdapter.setSelectedEntity(rightSelectedCategoryEntity);
                if (onItemCategoryClickListener != null) {
                    onItemCategoryClickListener.onItemCategoryClick(leftSelectedCategoryEntity, rightSelectedCategoryEntity);
                }
                tvCategoryTitle.setText(rightSelectedCategoryEntity.getKey());
                hide();
            }
        });
        final int scrollPosition = leftSelectedCategoryEntity.getList().indexOf(rightSelectedCategoryEntity);
        ThinkerActApplication.getInstance().postDelay(new Runnable() {
            @Override
            public void run() {
                lvRight.smoothScrollToPosition(scrollPosition);
            }
        },300);
    }

    // 设置排序数据
    private void setSortAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        sortAdapter = new FilterLonelyAdapter(mContext, filterData.getSorts());
        lvRight.setAdapter(sortAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSortEntity = filterData.getSorts().get(position);
                sortAdapter.setSelectedEntity(selectedSortEntity);
                if (onItemSortClickListener != null) {
                    onItemSortClickListener.onItemSortClick(selectedSortEntity);
                }
                tvSortTitle.setText(selectedSortEntity.getKey());
                hide();
            }
        });
    }

    // 设置筛选数据
    private void setFilterAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        filterAdapter = new FilterLonelyAdapter(mContext, filterData.getFilters());
        lvRight.setAdapter(filterAdapter);

        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFilterEntity = filterData.getFilters().get(position);
                filterAdapter.setSelectedEntity(selectedFilterEntity);
                if (onItemFilterClickListener != null) {
                    onItemFilterClickListener.onItemFilterClick(selectedFilterEntity);
                }
                hide();
            }
        });
    }

    /**
     * 动画显示
     * @param position 点击的位置，分类或排序或筛选
     */
    public void show(int position) {
        if (isShowing && lastFilterPosition == position) {
            hide();
            return;
        } else if (!isShowing) {
            viewMaskBg.setVisibility(VISIBLE);
            llContentListView.setVisibility(VISIBLE);
            
            //半透明背景渐显动画
            ObjectAnimator.ofFloat(viewMaskBg, "alpha", 0f, 1f).setDuration(ANIMATION_DURATION).start();
            
            //内容列表布局下拉动画
            llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    llContentListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    panelHeight = llContentListView.getHeight();
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0);
                    objectAnimator.setDuration(ANIMATION_DURATION);
                    objectAnimator.setInterpolator(new FastOutLinearInInterpolator());
                    objectAnimator.start();
                }
            });
        }
        isShowing = true;
        resetViewStatus();
        rotateArrowUp(position);
        rotateArrowDown(lastFilterPosition);
        lastFilterPosition = position;

        switch (position) {
            case POSITION_CATEGORY:
                tvCategoryTitle.setTextColor(mActivity.getResources().getColor(R.color.text_theme_color));
                ivCategoryArrow.setImageResource(R.mipmap.ic_filter_down_arrow_red);
                setCategoryAdapter();
                break;
            case POSITION_SORT:
                tvSortTitle.setTextColor(mActivity.getResources().getColor(R.color.text_theme_color));
                ivSortArrow.setImageResource(R.mipmap.ic_filter_down_arrow_red);
                setSortAdapter();
                break;
            case POSITION_FILTER:
                tvFilterTitle.setTextColor(mActivity.getResources().getColor(R.color.text_theme_color));
                ivFilterArrow.setImageResource(R.mipmap.ic_filter_down_arrow_red);
                setFilterAdapter();
                break;
        }
    }

    /**
     * 动画隐藏
     */
    public void hide() {
        isShowing = false;
        resetViewStatus();
        rotateArrowDown(filterPosition);
        rotateArrowDown(lastFilterPosition);
        filterPosition = -1;
        lastFilterPosition = -1;

        //半透明背景渐隐动画
        ObjectAnimator maskBgAnimator =  ObjectAnimator.ofFloat(viewMaskBg, "alpha", 1f, 0f).setDuration(ANIMATION_DURATION);
        maskBgAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewMaskBg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        maskBgAnimator.start();

        //内容列表布局上拉收回动画
        ObjectAnimator objectAnimator = ofFloat(llContentListView, "translationY", 0, -panelHeight);
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llContentListView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(ANIMATION_DURATION).start();
    }

    /**
     * 设置筛选数据
     */
    public void setFilterData(Activity activity, FilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    /**
     * 判断数据是否为空
     */
    public boolean isDataEmpty(){
        return filterData == null||filterData.getCategory() == null;
    }

    /**
     * 是否正在显示
     */
    public boolean isShowing() {
        return isShowing;
    }

    public int getFilterPosition() {
        return filterPosition;
    }

    // 旋转箭头向上
    private void rotateArrowUp(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowUpAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivSortArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivFilterArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivSortArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivFilterArrow);
                break;
        }
    }

    // 旋转箭头向上的动画
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ANIMATION_DURATION);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    // 旋转箭头向下的动画
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(ANIMATION_DURATION);
        animation.setFillAfter(true);
        iv.startAnimation(animation);
    }

    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;
    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public void setClickEnabled(boolean enabled) {
        llCategory.setEnabled(enabled);
        llSort.setEnabled(enabled);
        llFilter.setEnabled(enabled);
        viewMaskBg.setEnabled(enabled);
        llContentListView.setEnabled(enabled);
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    // 分类Item点击
    private OnItemCategoryClickListener onItemCategoryClickListener;
    public void setOnItemCategoryClickListener(OnItemCategoryClickListener onItemCategoryClickListener) {
        this.onItemCategoryClickListener = onItemCategoryClickListener;
    }
    public interface OnItemCategoryClickListener {
        void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity);
    }

    // 排序Item点击
    private OnItemSortClickListener onItemSortClickListener;
    public void setOnItemSortClickListener(OnItemSortClickListener onItemSortClickListener) {
        this.onItemSortClickListener = onItemSortClickListener;
    }
    public interface OnItemSortClickListener {
        void onItemSortClick(FilterEntity entity);
    }

    // 筛选Item点击
    private OnItemFilterClickListener onItemFilterClickListener;
    public void setOnItemFilterClickListener(OnItemFilterClickListener onItemFilterClickListener) {
        this.onItemFilterClickListener = onItemFilterClickListener;
    }
    public interface OnItemFilterClickListener {
        void onItemFilterClick(FilterEntity entity);
    }

}
