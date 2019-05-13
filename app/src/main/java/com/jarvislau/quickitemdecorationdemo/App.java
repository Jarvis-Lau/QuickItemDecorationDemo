package com.jarvislau.quickitemdecorationdemo;

import android.app.Application;
import android.graphics.Color;

import com.jarvislau.quickitemdecoration.ItemDecorationConfig;
import com.jarvislau.quickitemdecoration.ItemDivider;
import com.jarvislau.quickitemdecoration.QuickItemDecoration;

/**
 * Created by JarvisLau on 2019-05-09.
 * Description :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        QuickItemDecoration.setGlobalConfig(
                ItemDecorationConfig.GlobalConfig.getInstance(
                        new ItemDecorationConfig()
                                .create()
                                .setRecyclerMarginTop(500)
                                .setRecyclerMarginBottom(500)
                                .setRecyclerMarginTopColor(Color.RED)
                                .setRecyclerMarginBottomColor(Color.YELLOW)
                                .setMarginBottomWhenNotMatch(false)
                                .setItemDivider(new ItemDivider()
                                        .setColor(Color.BLUE)
                                        .setMarginLeft(150)
                                        .setMarginRight(0)
                                        .setWidth(10))
                                .build()));
        QuickItemDecoration.setGlobalConfig(ItemDecorationConfig.GlobalConfig.getInstance(new ItemDecorationConfig().create().build()));
    }

}