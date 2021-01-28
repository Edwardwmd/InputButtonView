package com.edw.inputbuttonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.edw.inputbuttonview.ScreenHelpUtlis.dpTopx;
import static com.edw.inputbuttonview.ScreenHelpUtlis.getFullScreenHeight;
import static com.edw.inputbuttonview.ScreenHelpUtlis.getFullScreenWidth;
import static com.edw.inputbuttonview.ScreenHelpUtlis.spTopx;


public class InputButtonView extends ConstraintLayout implements View.OnClickListener {
    private static String TAG = InputButtonView.class.getSimpleName();
    private TextView tvMinus;
    private EditText edCount;
    private TextView tvPlus;


    private int currentCount = 0;
    private onCountChangeListener listener = null;
    private int max;
    private int min;
    private float btnTextSize;
    private int btnTextColor;
    private float btnWidth;
    private float btnHeight;
    private int counterTextColor;

    private View container;
    private LayoutParams layoutParams;
    private Context mC;
    private float density;

    public InputButtonView(Context context) {
        this(context, null);
    }

    public InputButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("Recycle")
    public InputButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //LayoutInflater.from(context).inflate(R.layout.input_button_view,this);
        //LayoutInflater.from(context).inflate(R.layout.input_button_view,this,true);
        //以上两种代码是等效关系
        //LayoutInflater.from(context).inflate(R.layout.input_button_view,this);和
        // View inflate = LayoutInflater.from(context).inflate(R.layout.input_button_view, this,false);
        // addView(inflate);的代码是一样的效果,都是把View添加到容器当中

        this.mC = context;
        //初始化属性
        initAttributes(attrs);
        //初始化布局控件
        initView();
        //设置事件
        initEvent();
    }

    private void initAttributes(@Nullable AttributeSet attrs) {
        TypedArray ta = mC.obtainStyledAttributes(attrs, R.styleable.InputButtonView);
        btnWidth = ta.getDimension(R.styleable.InputButtonView_btnLayoutWidth, .0f);
        btnHeight = ta.getDimension(R.styleable.InputButtonView_btnLayoutHeight, .0f);
        max = ta.getInt(R.styleable.InputButtonView_max, 10);
        min = ta.getInt(R.styleable.InputButtonView_min, 0);
        counterTextColor = ta.getResourceId(R.styleable.InputButtonView_counterTextColor, -1);
        btnTextSize = ta.getDimension(R.styleable.InputButtonView_btnTextSize, 12);
        btnTextColor = ta.getResourceId(R.styleable.InputButtonView_btnTextColor, -1);

        //回收TypedArray
        ta.recycle();
    }

    private void initEvent() {
        tvMinus.setOnClickListener(this);
        tvPlus.setOnClickListener(this);

    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void initView() {
        LayoutInflater.from(mC).inflate(R.layout.input_button_view, this);
        density = mC.getResources().getDisplayMetrics().density;
        container = this.findViewById(R.id.container);
        tvMinus = this.findViewById(R.id.tv_minus);
        edCount = this.findViewById(R.id.ed_count);
        tvPlus = this.findViewById(R.id.tv_plus);
        layoutParams = (LayoutParams) container.getLayoutParams();
        //设置控件宽度
        updateWidth();
        //设置控件高度
        updateHeight();
        //设置字体大小
        updateTextSize();
        //设置字体selector
        tvPlus.setTextColor(getResources().getColorStateList(btnTextColor));
        tvMinus.setTextColor(getResources().getColorStateList(btnTextColor));
        //设置数字颜色
        edCount.setTextColor(getResources().getColor(counterTextColor));

        Log.d(TAG, "ScreenWidth------->" + getFullScreenWidth(mC));
        Log.d(TAG, "ScreenHeight------->" + getFullScreenHeight(mC));
        updateCount();
    }

    private void updateTextSize() {
        tvMinus.setTextSize(spTopx(mC, btnTextSize / 2));
        tvPlus.setTextSize(spTopx(mC, btnTextSize / 2));
        edCount.setTextSize(spTopx(mC, btnTextSize / 2));
    }

    private void updateHeight() {
        //根据设置空间高度设置边界值,限制由于高度过大而超出边界
        if (btnHeight >= getFullScreenHeight(mC) / density * 8) {
            layoutParams.height = dpTopx(mC, getFullScreenHeight(mC) / density * 8 - 20);
        } else {
            layoutParams.height = dpTopx(mC, btnHeight);
        }
    }

    private void updateWidth() {
        //根据设置空间宽度设置边界值,限制由于宽度过大而超出边界
        if (btnWidth >= getFullScreenWidth(mC) / density) {
            layoutParams.width = dpTopx(mC, getFullScreenWidth(mC) / density - 20);
        } else {
            layoutParams.width = dpTopx(mC, btnWidth);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_minus:
                tvPlus.setEnabled(true);
                if (currentCount <= min) {
                    v.setEnabled(false);
                    return;
                }
                currentCount--;
                updateCount();

                break;
            case R.id.tv_plus:
                tvMinus.setEnabled(true);
                if (currentCount >= max) {
                    v.setEnabled(false);
                    return;
                }
                currentCount++;
                updateCount();
                break;
        }

    }

    /**
     * 更新数据
     */
    private void updateCount() {
        edCount.setText(String.valueOf(currentCount));
        if (listener != null)
            listener.onCountChange(this.currentCount);
    }


    public void setOnCountChangeListener(onCountChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 向外暴露改变数据的接口
     */
    public interface onCountChangeListener {
        void onCountChange(int count);
    }


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int nin) {
        this.min = nin;
    }

    public float getDimension() {
        return btnTextSize;
    }
    /**
     * 获取数值
     *
     * @return count
     */
    public int getCount() {
        return currentCount;
    }

    public void setDimension(float btnTextSize) {

        this.btnTextSize = btnTextSize;
        tvMinus.setTextSize(spTopx(mC, btnTextSize / 2));
        tvPlus.setTextSize(spTopx(mC, btnTextSize / 2));
        edCount.setTextSize(spTopx(mC, btnTextSize / 2));
    }

    public int getBtnTextColor() {
        return btnTextColor;
    }

    public void setBtnTextColor(int btnTextColor) {
        this.btnTextColor = btnTextColor;
        tvPlus.setTextColor(btnTextColor);
        tvMinus.setTextColor(btnTextColor);
        edCount.setTextColor(btnTextColor);
    }

    public int getCounterTextColor() {
        return counterTextColor;
    }

    public void setCounterTextColor(int counterTextColor) {
        this.counterTextColor = counterTextColor;
        edCount.setTextColor(getResources().getColor(counterTextColor));
    }

    public float getBtnWidth() {
        return btnWidth;
    }

    public void setBtnWidth(float btnWidth) {
        this.btnWidth = btnWidth;
        updateWidth();
    }

    public float getBtnHeight() {
        return btnHeight;
    }

    public void setBtnHeight(float btnHeight) {
        this.btnHeight = btnHeight;
        updateHeight();
    }

    /**
     * 销毁InputButtonView相关资源(在生命周期onDestory中调用)
     */
    public void destroyView() {
        this.mC = null;
        removeAllViews();
    }

}
