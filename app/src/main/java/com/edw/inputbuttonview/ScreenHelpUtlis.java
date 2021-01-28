package com.edw.inputbuttonview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenHelpUtlis {

    /**
     * dp转换px
     *
     * @param dpValue
     * @return px
     */
    public static int dpTopx(Context mC, float dpValue) {
        float density = mC.getResources().getDisplayMetrics().density;//获得当前屏幕密度
        return (int) (dpValue * density + 0.5f * (dpValue >= 0 ? 1 : -1));
    }

    /**
     * sp转换px
     *
     * @param spValue
     * @return px
     */
    public static int spTopx(Context mC, float spValue) {
        float density = mC.getResources().getDisplayMetrics().density;
        return (int) (spValue * density + 0.5f);
    }

    public static float getFullScreenWidth(Context mC) {
        // 从系统服务中获取窗口管理器
        WindowManager wm = (WindowManager) mC.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 从默认显示器中获取显示参数保存到DisplayMetrics对象中
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; // 返回屏幕的宽度数值

    }

    public static float getFullScreenHeight(Context mC) {
        // 从系统服务中获取窗口管理器
        WindowManager wm = (WindowManager) mC.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 从默认显示器中获取显示参数保存到DisplayMetrics对象中
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels; // 返回屏幕的高度数值

    }

}
