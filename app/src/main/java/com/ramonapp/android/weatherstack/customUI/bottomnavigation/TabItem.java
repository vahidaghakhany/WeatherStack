package com.ramonapp.android.weatherstack.customUI.bottomnavigation;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.ramonapp.android.weatherstack.R;
import com.ramonapp.android.weatherstack.customUI.bottomnavigation.events.OnTabItemClickListener;
import com.ramonapp.android.weatherstack.customUI.bottomnavigation.utils.AnimationHelper;
import com.ramonapp.android.weatherstack.customUI.bottomnavigation.utils.LayoutParamsHelper;
import com.ramonapp.android.weatherstack.customUI.bottomnavigation.utils.Util;

public class TabItem extends FrameLayout implements View.OnClickListener {
    private OnTabItemClickListener onTabItemClickListener;
    private int position;

    //Attributes
    private String text;
    private int fontFamily;
    private Drawable selectedTabIcon;
    private int selectedTabTextColor;

    private Drawable unselectedTabIcon;
    private int unselectedTabTextColor;

    //Views
    private TextView textView;
    private ImageView iconImageView;

    private boolean isActive = false;
    private int type = BottomNavigation.TYPE_FIXED;
    private AnimationHelper animationHelper;
    private BottomNavigation bottomNavigation;

    private int itemPosition;

    public TabItem(Context context) {
        super(context);
        parseCustomAttributes(null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseCustomAttributes(attrs);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseCustomAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseCustomAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkParent();
    }

    private void checkParent() {
        post(new Runnable() {
            @Override
            public void run() {
                if (getParent() instanceof BottomNavigation) {
                    bottomNavigation = (BottomNavigation) getParent();
                    type = bottomNavigation.getType();
                    setupView();
                } else {
                    throw new RuntimeException("TabItem parent must be BottomNavigation!");
                }
            }
        });
    }

    private void setupView() {
        setOnClickListener(this);
        if (bottomNavigation.getMode() == BottomNavigation.MODE_PHONE) {
            setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        } else {
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dpToPx(56)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setForeground(getResources().getDrawable(R.drawable.tab_foreground, null));
        }

        animationHelper = new AnimationHelper(type);

        textView = new TextView(getContext());
        textView.setTextColor(selectedTabTextColor);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(LayoutParamsHelper.getTabItemTextLayoutParams(type));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.textSmall));
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        if (fontFamily != -1) textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamily));

        iconImageView = new ImageView(getContext());
        iconImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iconImageView.setImageDrawable(selectedTabIcon);
        iconImageView.setLayoutParams(LayoutParamsHelper.getTabItemIconLayoutParams(type));

        if (position == bottomNavigation.getDefaultItem()) {
            isActive = true;
            if (unselectedTabIcon == null || unselectedTabTextColor == 0) {
                animationHelper.animateActivate(textView, iconImageView);
            } else {
                animationHelper.animateActivate(textView, iconImageView, selectedTabTextColor, selectedTabTextColor, selectedTabIcon, unselectedTabIcon);
            }
        } else {
            if (unselectedTabIcon == null || unselectedTabTextColor == 0) {
                animationHelper.animateDeactivate(textView, iconImageView);
            } else {
                animationHelper.animateDeactivate(textView, iconImageView, unselectedTabTextColor, selectedTabTextColor, selectedTabIcon, unselectedTabIcon);
            }
        }

        switch (type) {
            case BottomNavigation.TYPE_FIXED:
                addView(iconImageView);
                addView(textView);
                break;
            case BottomNavigation.TYPE_DYNAMIC:
                if (isActive) {
                    addView(iconImageView);
                    addView(textView);
                } else {
                    addView(iconImageView);
                    addView(textView);
                }
                break;
        }
    }

    private void parseCustomAttributes(AttributeSet attributeSet) {

        if (attributeSet != null) {
            //get xml attributes
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.TabItem, 0, 0);
            try {
                text = typedArray.getString(R.styleable.TabItem_tab_text);
                fontFamily = typedArray.getResourceId(R.styleable.TabItem_font_family, -1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selectedTabTextColor = typedArray.getColor(R.styleable.TabItem_tab_text_color, getResources().getColor(R.color.purple_500, null));
                    unselectedTabTextColor = typedArray.getColor(R.styleable.TabItem_unselected_tab_text_color, 0);
                } else {
                    selectedTabTextColor = typedArray.getColor(R.styleable.TabItem_tab_text_color, getResources().getColor(R.color.purple_500));
                    unselectedTabTextColor = typedArray.getColor(R.styleable.TabItem_unselected_tab_text_color, 0);
                }
                selectedTabIcon = getResources().getDrawable(typedArray.getResourceId(R.styleable.TabItem_tab_icon,0));
                unselectedTabIcon = getResources().getDrawable(typedArray.getResourceId(R.styleable.TabItem_unselected_tab_icon,0));
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void setSelected(boolean isActive) {
        if (this.isActive != isActive) {
            notifyChange();
            this.isActive = isActive;
        }
    }

    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void onClick(View view) {
        if (onTabItemClickListener != null) {
            onTabItemClickListener.onTabItemClick(position);
        }
    }

    private void notifyChange() {
        if (unselectedTabIcon == null || unselectedTabTextColor == 0) {
            if (isActive) {
                animationHelper.animateDeactivate(textView, iconImageView);
            } else {
                animationHelper.animateActivate(textView, iconImageView);
            }
        } else {
            if (isActive) {
                animationHelper.animateDeactivate(textView, iconImageView, unselectedTabTextColor, selectedTabTextColor, selectedTabIcon, unselectedTabIcon);
            } else {
                animationHelper.animateActivate(textView, iconImageView, unselectedTabTextColor, selectedTabTextColor, selectedTabIcon, unselectedTabIcon);
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }
}
