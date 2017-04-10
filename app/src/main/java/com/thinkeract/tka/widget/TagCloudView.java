package com.thinkeract.tka.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.thinkeract.tka.R;
import com.zitech.framework.utils.ViewUtils;
import java.util.List;

/**
 * Created by minHeng on 15-7-29 12:02.
 * mail:minhengyan@gmail.com
 */
public class TagCloudView extends ViewGroup {

    private static final int TYPE_TEXT_NORMAL = 1;
    private int mChooseTextColor;
    private boolean mIsSingleSelect;
    private boolean mBackgroundRandom;
    private int mChooseBackground;

    private List<String> tags;

    private OnTagClickListener onTagClickListener;

    private int sizeWidth;

    private float mTagSize;
    private int mTagColor;
    private int mBackground;
    private int mViewBorder;
    private int mTagBorderHor;
    private int mTagBorderVer;
    private boolean mCanTagClick;
    private int mTextPadding;
    private int mRowNum;
    private boolean isStayRight;

    private static final int DEFAULT_TEXT_SIZE = ViewUtils.getDimenPx(R.dimen.w28);
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.bg_black_stroke_rectangle_corner_r10;
    private static final int DEFAULT_CHOOSE_BACKGROUND = R.drawable.bg_red_stroke_rectangle_corner_r10;
    private static final int DEFAULT_UN_ENABLE_BACKGROUND = R.drawable.bg_gray_stroke_rectangle_corner_r10;
    private static final int DEFAULT_VIEW_BORDER = ViewUtils.getDimenPx(R.dimen.w24);
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 0;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = ViewUtils.getDimenPx(R.dimen.w20);
    private static final int DEFAULT_TEXT_PADDING = ViewUtils.getDimenPx(R.dimen.w6);
    private static final int DEFAULT_ROW_NUM = 10086;

    private static final boolean DEFAULT_CAN_TAG_CLICK = false;
    private static final boolean DEFAULT_STAY_RIGHT = false;
    private static final boolean DEFAULT_SINGLE_SELECT = false;
    private static final boolean DEFAULT_BACKGROUND_STATUS = false;
    //	private int[] backRoundResList = {R.drawable.bg_green_round_corner_02e1c3_r80,R.drawable.bg_yellow_round_corner_ff9b05_r80
//	,R.drawable.bg_yellow_round_corner_fcda17_r80,R.drawable.bg_purple_round_corner_f06aff_r80};
    protected SparseArray<TextView> chooseList = new SparseArray<>();
    private TextView mChoosed;

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagCloudView,
                defStyleAttr,
                defStyleAttr
        );
        int DEFAULT_TEXT_COLOR = context.getResources().getColor(R.color.textColorPrimary);
        mChooseTextColor = context.getResources().getColor(R.color.text_red);
        mTagSize = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvTextSize, DEFAULT_TEXT_SIZE);
        mTagColor = a.getColor(R.styleable.TagCloudView_tcvTextColor, DEFAULT_TEXT_COLOR);
        mBackground = a.getResourceId(R.styleable.TagCloudView_tcvBackground, DEFAULT_TEXT_BACKGROUND);
        mChooseBackground = a.getResourceId(R.styleable.TagCloudView_tcvChooseBackground, DEFAULT_CHOOSE_BACKGROUND);
        mViewBorder = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvBorder, DEFAULT_VIEW_BORDER);
        mTagBorderHor = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderHorizontal, DEFAULT_TEXT_BORDER_HORIZONTAL);
        mTagBorderVer = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderVertical, DEFAULT_TEXT_BORDER_VERTICAL);
        mCanTagClick = a.getBoolean(R.styleable.TagCloudView_tcvCanTagClick, DEFAULT_CAN_TAG_CLICK);
        mIsSingleSelect = a.getBoolean(R.styleable.TagCloudView_tcvIsSingleSelect, DEFAULT_SINGLE_SELECT);

        mTextPadding = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvTagTextPadding, DEFAULT_TEXT_PADDING);
        mRowNum = a.getInteger(R.styleable.TagCloudView_tcvRowNum, DEFAULT_ROW_NUM);
        isStayRight = a.getBoolean(R.styleable.TagCloudView_tcvIsStayRight, DEFAULT_STAY_RIGHT);
        mBackgroundRandom = a.getBoolean(R.styleable.TagCloudView_tcvBackgroundRandom, DEFAULT_BACKGROUND_STATUS);

        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !mCanTagClick || super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * 计算 ChildView 宽高
     *
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
         * 计算 ViewGroup 上级容器为其推荐的宽高
         */
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算 childView 宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = 0;
        int totalHeight = mTagBorderVer;

        totalHeight = getMultiTotalHeight(totalWidth, totalHeight);

        /*
         * 高度根据设置改变
         * 如果为 MATCH_PARENT 则充满父窗体，否则根据内容自定义高度
         */
        setMeasuredDimension(
                sizeWidth,
                (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));

    }

    /**
     * 为 singleLine 模式布局，并计算视图高度
     *
     */
    private int getSingleTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;

        totalWidth += mViewBorder;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();


            if (i == 0) {
                totalWidth += childWidth;
                totalHeight = childHeight + mViewBorder;
            } else {
                totalWidth += childWidth + mTagBorderHor;
            }
            int tag = -1;
            Object obj = child.getTag();
            if (obj instanceof Integer) {
                tag = (Integer) obj;
            }
            if (child.getTag() != null && tag == TYPE_TEXT_NORMAL) {
                if (totalWidth + mTagBorderHor + mViewBorder + mViewBorder < sizeWidth) {
                    child.layout(
                            totalWidth - childWidth + mTagBorderVer,
                            totalHeight - childHeight,
                            totalWidth + mTagBorderVer,
                            totalHeight);
                }
            }
        }

        totalHeight += mViewBorder;

        return totalHeight;
    }

    /**
     * 为 multiLine 模式布局，并计算视图高度
     *
     */
    private int getMultiTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + mViewBorder;

            if (i == 0) {
                totalHeight = childHeight;
            }

            if ((totalHeight - childHeight) >= (childHeight + mTagBorderVer) * (mRowNum - 1)) {
                return totalHeight;
            }
            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + mTagBorderHor + mViewBorder > sizeWidth) {

                totalWidth = mViewBorder;
                totalHeight += childHeight + mTagBorderVer;
                if (isStayRight) {
                    child.layout(
                            sizeWidth - totalWidth - childWidth,
                            totalHeight - childHeight,
                            sizeWidth - totalWidth,
                            totalHeight);
                } else {
                    child.layout(
                            totalWidth + mTagBorderHor,
                            totalHeight - childHeight,
                            totalWidth + childWidth + mTagBorderHor,
                            totalHeight);
                }
                totalWidth += childWidth;

            } else {
                if (isStayRight) {
                    child.layout(
                            sizeWidth - totalWidth,
                            totalHeight - childHeight,
                            sizeWidth - totalWidth + childWidth,
                            totalHeight);
                } else {
                    child.layout(
                            totalWidth - childWidth + mTagBorderHor,
                            totalHeight - childHeight,
                            totalWidth + mTagBorderHor,
                            totalHeight);
                }

            }
        }
        return totalHeight;
    }

    private int getTextTotalWidth() {
        if (getChildCount() == 0) {
            return 0;
        }
        int totalChildWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Object obj = child.getTag();
            int tag = -1;
            if (obj instanceof Integer) {
                tag = (Integer) obj;
            }
            if (child.getTag() != null && tag == TYPE_TEXT_NORMAL) {
                totalChildWidth += child.getMeasuredWidth() + mViewBorder;
            }
        }
        return totalChildWidth + mTagBorderHor * 2;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public void setTags(List<String> tagList) {
        if (tags == tagList) return;
        if (tags != null && tags.size() > 0) {
            tags.clear();
            removeAllViews();
        }
        this.tags = tagList;
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = new TextView(getContext());

                tagView.setBackgroundResource(mBackground);
                tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTagSize);
                tagView.setTextColor(mTagColor);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setPadding(mTextPadding * 2, mTextPadding, mTextPadding * 2, mTextPadding);
                tagView.setLayoutParams(layoutParams);
                final String attrNameString = tags.get(i);
                tagView.setText(attrNameString);
                tagView.setTag(TYPE_TEXT_NORMAL);
                final int finalI = i;
                if (mCanTagClick) {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextColor(mTagColor);
                    tagView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mIsSingleSelect)
                                markSingleChooseView((TextView) v, finalI, attrNameString);
                            else
                                markChooseView((TextView) v, finalI, attrNameString);
                        }
                    });
                } else if (mBackgroundRandom) {
                    tagView.setBackgroundResource(RandomBackRoundRes());
                    tagView.setTextColor(Color.WHITE);
                } else {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextColor(mTagColor);
                }
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void markChooseView(TextView current, int position, String attrNameString) {
        TextView choosed = chooseList.get(position);
        if (current != choosed) {
            current.setBackgroundResource(RandomBackRoundRes());
            current.setTextColor(Color.WHITE);
            chooseList.put(position, current);
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(position, attrNameString, true);
        } else {
            current.setBackgroundResource(mBackground);
            current.setTextColor(mTagColor);
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(position, attrNameString, false);
        }


    }

    public void markSingleChooseView(TextView current, int position, String attrNameString) {
        TextView choosed = chooseList.get(position);
        if (mChoosed != null && mChoosed != choosed) {
            mChoosed.setBackgroundResource(mBackground);
            mChoosed.setTextColor(mTagColor);
        }
        if (mChoosed != null && mChoosed != choosed || mChoosed == null) {
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(position, attrNameString, true);
        }

        mChoosed = current;
        current.setBackgroundResource(RandomBackRoundRes());
        current.setTextColor(mChooseTextColor);
        chooseList.put(position, current);
    }

    public int RandomBackRoundRes() {
//		Random random = new Random();
//		return backRoundResList[random.nextInt(backRoundResList.length)];
        return mChooseBackground;
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        /**
         * @param position       选择标签的下标位置
         * @param attrNameString 标签所代表的字符串
         * @param isChoose       是选择该标签还是取消
         */
        void onTagClick(int position, String attrNameString, boolean isChoose);
    }

}
