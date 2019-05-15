package com.jarvislau.quickitemdecoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JarvisLau on 2018/7/9.
 * Description :
 */

public class QuickItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable itemBackground;
    private Drawable parentBackground;

    private Paint paintItemBackground;
    private Paint paintGridItemBackground;

    /**
     * 设置全局配置
     */
    private static ItemDecorationConfig.GlobalConfig globalConfig;
    private ItemDecorationConfig itemDecorationConfig = null;

    public static void setGlobalConfig(ItemDecorationConfig.GlobalConfig globalConfig) {
        QuickItemDecoration.globalConfig = globalConfig;
    }

    /**
     * 无视全局配置，重新设置所有配置参数
     */
    public void setConfig(ItemDecorationConfig config) {
        this.itemDecorationConfig = config;
    }

    public static ItemDecorationConfig.GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public ItemDecorationConfig.Config getUpdateConfig() {
        this.itemDecorationConfig = globalConfig.getItemDecorationConfig();
        itemDecorationConfig.setQuickItemDecoration(this);
        return itemDecorationConfig.create();
    }

    private ItemDecorationConfig getItemDecorationConfig() {
        if (itemDecorationConfig != null) {
            return itemDecorationConfig;
        } else if (globalConfig != null) {
            return globalConfig.getItemDecorationConfig();
        } else {
            return ItemDecorationConfig.GlobalConfig.getInstance(new ItemDecorationConfig().create().build()).getItemDecorationConfig();
        }
    }

    public QuickItemDecoration() {
        initPaint();
    }

    private void initPaint() {
        paintItemBackground = new Paint();
        paintGridItemBackground = new Paint();
        paintGridItemBackground.setColor(getItemDecorationConfig().getItemDivider().getItemGridBackgroundColor());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter().getItemCount() > 0) {
            setEnvironmentColor(parent);
            //linearLayoutManager
            if (parent.getLayoutManager() instanceof LinearLayoutManager && !(parent.getLayoutManager() instanceof GridLayoutManager) && !(parent.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
                int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
                boolean reverseLayout = ((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout();
                switch (orientation) {
                    case LinearLayoutManager.VERTICAL:
                        drawLinearLayoutVertical(c, reverseLayout, parent);
                        break;
                    case LinearLayoutManager.HORIZONTAL:
                        drawLinearLayoutHorizontal(c, reverseLayout, parent);
                        break;
                }
                //GridLayoutManager
            } else if (parent.getLayoutManager() instanceof GridLayoutManager && !(parent.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
                boolean reverseLayout = ((GridLayoutManager) parent.getLayoutManager()).getReverseLayout();
                drawGridLayoutVertical(c, reverseLayout, parent);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter().getItemCount() > 0) {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            boolean reverseLayout = ((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout();
            if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager)) {
                int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
                switch (orientation) {
                    case LinearLayoutManager.VERTICAL:
                        getLinearLayoutItemOffsetsVertical(outRect, reverseLayout, parent, view);
                        break;
                    case LinearLayoutManager.HORIZONTAL:
                        getLinearLayoutItemOffsetsHorizontal(outRect, reverseLayout, parent, view);
                        break;
                }
            } else if (layoutManager instanceof GridLayoutManager) {
                getGridLayoutItemOffsetsVertical(outRect, reverseLayout, parent, view);
            }
        }
    }

    private void drawLinearLayoutVertical(Canvas c, boolean reverseLayout, RecyclerView parent) {
        int itemCount = parent.getAdapter().getItemCount();
        int realWidth = parent.getChildAt(0).getWidth();
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.findViewByPosition(0) == null || !getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(0).getId())) {
            c.drawRect(getItemDecorationConfig().getItemDivider().getMarginLeft(), 0, realWidth - getItemDecorationConfig().getItemDivider().getMarginRight(), parent.getHeight(), getItemDecorationConfig().getItemDivider().getPaint());
            c.drawRect(0, 0, getItemDecorationConfig().getItemDivider().getMarginLeft(), parent.getHeight(), paintItemBackground);
            c.drawRect(realWidth - getItemDecorationConfig().getItemDivider().getMarginRight(), 0, realWidth, parent.getHeight(), paintItemBackground);
        }
        if (itemCount == 1) {
            if (!getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(0).getId())) {
                View view = parent.getChildAt(0);
                if (!reverseLayout) {
                    c.drawRect(0, 0, view.getWidth(), view.getTop(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                    c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                } else {
                    c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                    c.drawRect(0, 0, view.getWidth(), view.getTop(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                }
            }
        } else {
            for (int i = 0; i < itemCount; i++) {
                if (layoutManager.findViewByPosition(i) == null || !getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(i).getId())) {
                    View view = parent.getChildAt(i);
                    int childAdapterPosition = parent.getChildAdapterPosition(view);
                    //正序
                    if (!reverseLayout) {
                        //如果是第一个item上面画margin
                        if (childAdapterPosition == 0) {
                            c.drawRect(0, 0, view.getWidth(), view.getTop(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                        } else if (childAdapterPosition == itemCount - 1) {
                            c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                        }
                    } else {
                        if (childAdapterPosition == 0) {
                            c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                        } else if (childAdapterPosition == itemCount - 1) {
                            c.drawRect(0, 0, view.getWidth(), view.getTop(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                        }
                    }
                }
            }
        }
    }

    private void drawLinearLayoutHorizontal(Canvas c, boolean reverseLayout, RecyclerView parent) {
        if (parent.getAdapter().getItemCount() > 0) {
            int realHeight = parent.getChildAt(0).getHeight();
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (layoutManager.findViewByPosition(0) == null || !getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(0).getId())) {
                c.drawRect(0, getItemDecorationConfig().getItemDivider().getMarginTop(), parent.getWidth(), realHeight - getItemDecorationConfig().getItemDivider().getMarginBottom(), getItemDecorationConfig().getItemDivider().getPaint());
                c.drawRect(0, 0, parent.getWidth(), getItemDecorationConfig().getItemDivider().getMarginTop(), paintItemBackground);
                c.drawRect(0, realHeight - getItemDecorationConfig().getItemDivider().getMarginBottom(), parent.getWidth(), realHeight, paintItemBackground);
            }
            int itemCount = parent.getAdapter().getItemCount();
            for (int i = 0; i < itemCount; i++) {
                if (!getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(i).getId())) {
                    View view = parent.getChildAt(i);
                    int childAdapterPosition = parent.getChildAdapterPosition(view);
                    if (childAdapterPosition == 0) {
                        if (!reverseLayout) {
                            c.drawRect(0, 0, view.getLeft(), view.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                        } else {
                            c.drawRect(view.getRight(), 0, parent.getWidth(), view.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                        }
                    } else if (childAdapterPosition == itemCount - 1) {
                        if (!reverseLayout) {
                            c.drawRect(view.getRight(), 0, parent.getWidth(), view.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                        } else {
                            c.drawRect(0, 0, view.getLeft(), view.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
                        }
                    }
                }
            }
        }
    }

    private void drawGridLayoutVertical(Canvas c, boolean reverseLayout, RecyclerView parent) {
        int itemCount = parent.getAdapter().getItemCount();
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View firstView = parent.getChildAt(0);
        View lastView = layoutManager.findViewByPosition(itemCount - 1);
        for (int i = 0; i < itemCount; i++) {
            if (!getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(i).getId())) {
                return;
            }
        }
        c.drawRect(getItemDecorationConfig().getItemDivider().getMarginLeft()
                , getItemDecorationConfig().getItemDivider().getMarginTop()
                , parent.getWidth() - getItemDecorationConfig().getItemDivider().getMarginRight()
                , parent.getHeight() - getItemDecorationConfig().getItemDivider().getMarginBottom()
                , getItemDecorationConfig().getItemDivider().getPaint());
        //第一个item绘制顶部颜色
        if (!reverseLayout) {
            //如果是第一个可见item，并且第一个item不是只显示了一半。
            // 这里findFirstCompletelyVisibleItemPosition获取到的结果与实际不符，不然只需要判断FirstCompletelyVisibleItem即可
            if (firstVisibleItemPosition == 0 && firstView.getTop() >= 0) {
                c.drawRect(0, 0, parent.getWidth(), firstView.getTop(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
            }
        } else {
            if (firstVisibleItemPosition == 0 && (parent.getHeight() - firstView.getBottom()) > 0) {
                c.drawRect(0, firstView.getBottom(), parent.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginTopColor());
            }
        }
        //最后一个item绘制底部颜色
        if (lastVisibleItemPosition == itemCount - 1) {
            //如果最后一个item后面是空的，绘制一块用户设置的颜色
            c.drawRect(lastView.getRight() + getItemDecorationConfig().getItemDivider().getWidth(), lastView.getTop(), parent.getWidth(), lastView.getBottom(), paintGridItemBackground);
            if (!reverseLayout) {
                if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstVisibleItemPosition != 0) {
                    c.drawRect(0, lastView.getBottom(), parent.getWidth(), parent.getHeight(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                }
            } else {
                if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || lastVisibleItemPosition != itemCount) {
                    c.drawRect(0, 0, parent.getWidth(), lastView.getTop(), getItemDecorationConfig().getPaintRecyclerMarginBottomColor());
                }
            }
        }
    }

    //获得recyclerView父布局背景颜色和和item的背景颜色
    private void setEnvironmentColor(RecyclerView parent) {
        if (itemBackground == null) {
            itemBackground = parent.getChildAt(0).getBackground();
            if (itemBackground instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) itemBackground;
                int color = colorDrawable.getColor();
                paintItemBackground.setColor(color);
            }
        }
        if (parentBackground == null) {
            parentBackground = ((ViewGroup) parent.getParent()).getBackground();
            if (parentBackground instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) parentBackground;
                int color = colorDrawable.getColor();
                if (getItemDecorationConfig().getPaintRecyclerMarginTopColor().getColor() == 0) {
                    getItemDecorationConfig().getPaintRecyclerMarginTopColor().setColor(color);
                }
                if (getItemDecorationConfig().getPaintRecyclerMarginBottomColor().getColor() == 0) {
                    getItemDecorationConfig().getPaintRecyclerMarginBottomColor().setColor(color);
                }
            }
        }
    }

    private void getLinearLayoutItemOffsetsVertical(Rect outRect,
                                                    boolean reverseLayout, RecyclerView parent, View view) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int position = layoutManager.getPosition(view);
        if (!getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(position).getId())) {
            int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
            //正序item
            if (!reverseLayout) {
                if (childAdapterPosition == 0) {
                    outRect.set(0, getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0, 0);
                    if (parent.getAdapter().getItemCount() == 1 && getItemDecorationConfig().isMarginBottomWhenNotMatch()) {
                        outRect.set(0, getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0, getItemDecorationConfig().getRecyclerMarginBottomPxValue());
                    }
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstCompletelyVisibleItemPosition != 0) {
                        outRect.set(0, getItemDecorationConfig().getItemDivider().getWidth(), 0, getItemDecorationConfig().getRecyclerMarginBottomPxValue());
                    } else {
                        outRect.set(0, getItemDecorationConfig().getItemDivider().getWidth(), 0, 0);
                    }
                } else {
                    outRect.set(0, getItemDecorationConfig().getItemDivider().getWidth(), 0, 0);
                }
                //倒序item
            } else {
                if (childAdapterPosition == 0) {
                    outRect.set(0, 0, 0, getItemDecorationConfig().getRecyclerMarginTopPxValue());
                    if (parent.getAdapter().getItemCount() == 1 && getItemDecorationConfig().isMarginBottomWhenNotMatch()) {
                        outRect.set(0, getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0, getItemDecorationConfig().getRecyclerMarginTopPxValue());
                    }
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstCompletelyVisibleItemPosition != 0) {
                        outRect.set(0, getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0, getItemDecorationConfig().getItemDivider().getWidth());
                    } else {
                        outRect.set(0, 0, 0, getItemDecorationConfig().getItemDivider().getWidth());
                    }
                } else {
                    outRect.set(0, 0, 0, getItemDecorationConfig().getItemDivider().getWidth());
                }
            }
        }
    }

    private void getLinearLayoutItemOffsetsHorizontal(Rect outRect,
                                                      boolean reverseLayout, RecyclerView parent, View view) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (!getItemDecorationConfig().getIgnoreViewIds().contains(layoutManager.findViewByPosition(childAdapterPosition).getId())) {
            int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
            //正序item
            if (!reverseLayout) {
                if (childAdapterPosition == 0) {
                    outRect.set(getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0, 0, 0);
                    if (parent.getAdapter().getItemCount() == 1) {
                        outRect.set(getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0, getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0);
                    }
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstCompletelyVisibleItemPosition != 0) {
                        outRect.set(getItemDecorationConfig().getItemDivider().getWidth(), 0, getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0);
                    } else {
                        outRect.set(getItemDecorationConfig().getItemDivider().getWidth(), 0, 0, 0);
                    }
                } else {
                    outRect.set(getItemDecorationConfig().getItemDivider().getWidth(), 0, 0, 0);
                }
                //倒序item
            } else {
                if (childAdapterPosition == 0) {
                    outRect.set(0, 0, getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0);
                    if (parent.getAdapter().getItemCount() == 1 && getItemDecorationConfig().isMarginBottomWhenNotMatch()) {
                        outRect.set(getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0, getItemDecorationConfig().getRecyclerMarginTopPxValue(), 0);
                    }
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstCompletelyVisibleItemPosition != 0) {
                        outRect.set(getItemDecorationConfig().getRecyclerMarginBottomPxValue(), 0, getItemDecorationConfig().getItemDivider().getWidth(), 0);
                    } else {
                        outRect.set(0, 0, getItemDecorationConfig().getItemDivider().getWidth(), 0);
                    }
                } else {
                    outRect.set(0, 0, getItemDecorationConfig().getItemDivider().getWidth(), 0);
                }
            }
        }
    }

    private void getGridLayoutItemOffsetsVertical(Rect outRect,
                                                  boolean reverseLayout, RecyclerView parent, View view) {
        int itemCount = parent.getAdapter().getItemCount();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (!getItemDecorationConfig().getIgnoreViewIds().contains(gridLayoutManager.findViewByPosition(childAdapterPosition).getId())) {
            int spanCount = gridLayoutManager.getSpanCount();
            int top = 0;
            int right = getItemDecorationConfig().getItemDivider().getWidth();
            int bottom = getItemDecorationConfig().getItemDivider().getWidth();
            if (childAdapterPosition < spanCount) {
                if (!reverseLayout) {
                    top = getItemDecorationConfig().getRecyclerMarginTopPxValue();
                } else {
                    bottom = getItemDecorationConfig().getRecyclerMarginTopPxValue();
                }
            }
            if (childAdapterPosition % spanCount == 4) {
                right = 0;
            }
            int i = itemCount % spanCount;
            if (childAdapterPosition >= (itemCount - i) || (i == 0 && childAdapterPosition >= itemCount - spanCount)) {
                int firstVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (!reverseLayout) {
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || firstVisibleItemPosition > spanCount - 1) {
                        bottom = getItemDecorationConfig().getRecyclerMarginBottomPxValue();
                    } else {
                        bottom = 0;
                    }
                } else {
                    if (getItemDecorationConfig().isMarginBottomWhenNotMatch() || lastCompletelyVisibleItemPosition > spanCount - 1) {
                        top = getItemDecorationConfig().getRecyclerMarginBottomPxValue();
                    } else {
                        top = 0;
                    }
                }
            }
            if (!reverseLayout) {
                outRect.set(0, top, right, bottom);
            } else {
                outRect.set(0, top, right, bottom);
            }
        }
    }

}