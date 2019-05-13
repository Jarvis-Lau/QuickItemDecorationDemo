package com.jarvislau.quickitemdecoration;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by JarvisLau on 2019-05-10.
 * Description : 设置分割线配置的类
 */
public class ItemDecorationConfig {

    /**
     * Context
     */
    private QuickItemDecoration itemDecoration;

    /**
     * recyclerView margin配置
     */
    private int recyclerMarginTopPxValue = 0;
    private int recyclerMarginBottomPxValue = 0;

    private Paint paintRecyclerMarginTopColor = new Paint();
    private Paint paintRecyclerMarginBottomColor = new Paint();

    /**
     * top和bottom color的优先级高于marginColor
     */
    private int recyclerMarginTopColor = 0;
    private int recyclerMarginBottomColor = 0;
    private int recyclerMarginColor = 0;

    /**
     * item没有充满屏幕时是否显示marginBottom
     */
    private boolean isMarginBottomWhenNotMatch = false;

    /**
     * 空视图、头部、底部等不需要分割线、margin等参数的view id集合
     */
    private LinkedList<Integer> ignoreViewIds = new LinkedList<>();

    /**
     * item分割线配置
     */
    private ItemDivider itemDivider = new ItemDivider();

    public ItemDecorationConfig() {
    }

    private ItemDecorationConfig(int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , Paint paintRecyclerMarginTopColor
            , Paint paintRecyclerMarginBottomColor
            , int recyclerMarginTopColor
            , int recyclerMarginBottomColor
            , int recyclerMarginColor
            , boolean isMarginBottomWhenNotMatch
            , ItemDivider itemDivider
            , LinkedList<Integer> ignoreViewIds) {
        this.recyclerMarginTopPxValue = recyclerMarginTopPxValue;
        this.recyclerMarginBottomPxValue = recyclerMarginBottomPxValue;
        this.paintRecyclerMarginTopColor = paintRecyclerMarginTopColor;
        this.paintRecyclerMarginBottomColor = paintRecyclerMarginBottomColor;
        this.recyclerMarginTopColor = recyclerMarginTopColor;
        this.recyclerMarginBottomColor = recyclerMarginBottomColor;
        this.recyclerMarginColor = recyclerMarginColor;
        this.isMarginBottomWhenNotMatch = isMarginBottomWhenNotMatch;
        this.itemDivider = itemDivider;
        this.ignoreViewIds = ignoreViewIds;
    }

    public Config create() {
        return new Config();
    }

    /**
     * 设置全局配置
     */
    public static class GlobalConfig {

        private static volatile GlobalConfig globalConfig;
        private final ItemDecorationConfig itemDecorationConfig;

        private GlobalConfig(ItemDecorationConfig config) {
            this.itemDecorationConfig = config;
        }

        public static GlobalConfig getInstance(ItemDecorationConfig config) {
            if (globalConfig == null) {
                synchronized (GlobalConfig.class) {
                    if (globalConfig == null) {
                        globalConfig = new GlobalConfig(config);
                    }
                }
            }
            return globalConfig;
        }

        ItemDecorationConfig getItemDecorationConfig() {
            return itemDecorationConfig;
        }

    }

    public class Config {

        public Config setRecyclerMarginTop(int pxValue) {
            recyclerMarginTopPxValue = pxValue;
            return this;
        }

        public Config setRecyclerMarginBottom(int pxValue) {
            recyclerMarginBottomPxValue = pxValue;
            return this;
        }

        public Config setRecyclerMarginTopColor(int color) {
            recyclerMarginTopColor = color;
            return this;
        }

        public Config setRecyclerMarginBottomColor(int color) {
            recyclerMarginBottomColor = color;
            return this;
        }

        public Config setRecyclerMarginColor(int color) {
            recyclerMarginColor = color;
            return this;
        }

        public Config setMarginBottomWhenNotMatch(boolean marginBottomWhenNotMatch) {
            isMarginBottomWhenNotMatch = marginBottomWhenNotMatch;
            return this;
        }

        public Config setItemDivider(ItemDivider itemDivider) {
            ItemDecorationConfig.this.itemDivider = itemDivider;
            return this;
        }

        public Config addIgnoreViewId(Integer viewId) {
            ignoreViewIds.add(viewId);
            return this;
        }

        public Config setIgnoreViewId(List<Integer> viewsId) {
            ignoreViewIds.addAll(viewsId);
            return this;
        }

        public ItemDecorationConfig build() {
            setConfig();
            return new ItemDecorationConfig(recyclerMarginTopPxValue
                    , recyclerMarginBottomPxValue
                    , paintRecyclerMarginTopColor
                    , paintRecyclerMarginBottomColor
                    , recyclerMarginTopColor
                    , recyclerMarginBottomColor
                    , recyclerMarginColor
                    , isMarginBottomWhenNotMatch
                    , itemDivider
                    , ignoreViewIds);
        }

        public void update() {
            setConfig();
            itemDecoration.setConfig(new ItemDecorationConfig(recyclerMarginTopPxValue
                    , recyclerMarginBottomPxValue
                    , paintRecyclerMarginTopColor
                    , paintRecyclerMarginBottomColor
                    , recyclerMarginTopColor
                    , recyclerMarginBottomColor
                    , recyclerMarginColor
                    , isMarginBottomWhenNotMatch
                    , itemDivider
                    , ignoreViewIds));
            itemDecoration = null;
        }

        private void setConfig() {
            paintRecyclerMarginTopColor.setColor(recyclerMarginColor);
            paintRecyclerMarginBottomColor.setColor(recyclerMarginColor);
            if (recyclerMarginTopColor != 0) {
                paintRecyclerMarginTopColor.setColor(recyclerMarginTopColor);
            }
            if (recyclerMarginBottomColor != 0) {
                paintRecyclerMarginBottomColor.setColor(recyclerMarginBottomColor);
            }
        }

    }

    public int getRecyclerMarginTopPxValue() {
        return recyclerMarginTopPxValue;
    }

    public int getRecyclerMarginBottomPxValue() {
        return recyclerMarginBottomPxValue;
    }

    public Paint getPaintRecyclerMarginTopColor() {
        return paintRecyclerMarginTopColor;
    }

    public Paint getPaintRecyclerMarginBottomColor() {
        return paintRecyclerMarginBottomColor;
    }

    public int getRecyclerMarginTopColor() {
        return recyclerMarginTopColor;
    }

    public int getRecyclerMarginBottomColor() {
        return recyclerMarginBottomColor;
    }

    public int getRecyclerMarginColor() {
        return recyclerMarginColor;
    }

    public boolean isMarginBottomWhenNotMatch() {
        return isMarginBottomWhenNotMatch;
    }

    public ItemDivider getItemDivider() {
        return itemDivider;
    }

    public LinkedList<Integer> getIgnoreViewIds() {
        return ignoreViewIds;
    }

    void setQuickItemDecoration(QuickItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

}
