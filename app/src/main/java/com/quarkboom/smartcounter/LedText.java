package com.quarkboom.smartcounter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.File;

/**
 * @author: zhuozhang6
 * @date: 2020/12/25
 * @email: zhangzhuo1024@163.com
 */
public class LedText extends TextView {

    public LedText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        String file = "fonts" + File.separator + "digital-7.ttf";
        AssetManager assets = context.getAssets();
        Typeface font = Typeface.createFromAsset(assets, file);
        setTypeface(font);
    }
}
