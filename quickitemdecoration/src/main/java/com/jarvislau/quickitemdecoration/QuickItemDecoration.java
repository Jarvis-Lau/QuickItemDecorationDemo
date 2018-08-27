package com.jarvislau.quickitemdecoration;

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
    private Paint paintRecyclerMarginTopColor;
    private Paint paintRecyclerMarginBottomColor;
    private Paint paintGridItemBackground;

    private ItemDivider itemDivider;

    private int recyclerMarginTopPxValue = 0;
    private int recyclerMarginBottomPxValue = 0;

    private boolean isMarginBottomWhenNotMatch = true;

    public QuickItemDecoration(ItemDivider itemDivider) {
        this(itemDivider, 0, 0);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue) {
        this(itemDivider, recyclerMarginTopPxValue, 0, -1);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue) {
        this(itemDivider, recyclerMarginTopPxValue, recyclerMarginBottomPxValue, -1);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , boolean isMarginBottomWhenNotMatch) {
        this(itemDivider, recyclerMarginTopPxValue, recyclerMarginBottomPxValue, -1, -1, isMarginBottomWhenNotMatch);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , int recyclerMarginColor) {
        this(itemDivider, recyclerMarginTopPxValue, recyclerMarginBottomPxValue, recyclerMarginColor, recyclerMarginColor, true);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , int recyclerMarginColor
            , boolean isMarginBottomWhenNotMatch) {
        this(itemDivider
                , recyclerMarginTopPxValue
                , recyclerMarginBottomPxValue
                , recyclerMarginColor
                , recyclerMarginColor
                , isMarginBottomWhenNotMatch);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , int recyclerMarginTopColor
            , int recyclerMarginBottomColor) {
        this(itemDivider, recyclerMarginTopPxValue, recyclerMarginBottomPxValue, recyclerMarginTopColor, recyclerMarginBottomColor, true);
    }

    public QuickItemDecoration(ItemDivider itemDivider
            , int recyclerMarginTopPxValue
            , int recyclerMarginBottomPxValue
            , int recyclerMarginTopColor
            , int recyclerMarginBottomColor
            , boolean isMarginBottomWhenNotMatch) {
        this.itemDivider = itemDivider;
        initPaint();
        this.recyclerMarginTopPxValue = recyclerMarginTopPxValue;
        this.recyclerMarginBottomPxValue = recyclerMarginBottomPxValue;
        paintRecyclerMarginTopColor.setColor(recyclerMarginTopColor);
        paintRecyclerMarginBottomColor.setColor(recyclerMarginBottomColor);
        this.isMarginBottomWhenNotMatch = isMarginBottomWhenNotMatch;
    }

    private void initPaint() {
        paintRecyclerMarginTopColor = new Paint();
        paintRecyclerMarginBottomColor = new Paint();
        paintItemBackground = new Paint();
        paintGridItemBackground = new Paint();
        paintGridItemBackground.setColor(itemDivider.getItemGridBackgroundColor());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (itemDivider != null) {
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
        if (itemDivider != null) {
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
        int realWidth = parent.getChildAt(0).getWidth();
        c.drawRect(itemDivider.getMarginLeft(), 0, realWidth - itemDivider.getMarginRight(), parent.getHeight(), itemDivider.getPaint());
        c.drawRect(0, 0, itemDivider.getMarginLeft(), parent.getHeight(), paintItemBackground);
        c.drawRect(realWidth - itemDivider.getMarginRight(), 0, realWidth, parent.getHeight(), paintItemBackground);
        int itemCount = parent.getAdapter().getItemCount();
        if (itemCount == 1) {
            View view = parent.getChildAt(0);
            if (!reverseLayout) {
                c.drawRect(0, 0, view.getWidth(), view.getTop(), paintRecyclerMarginTopColor);
                c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), paintRecyclerMarginBottomColor);
            } else {
                c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), paintRecyclerMarginBottomColor);
                c.drawRect(0, 0, view.getWidth(), view.getTop(), paintRecyclerMarginTopColor);
            }
        } else {
            for (int i = 0; i < itemCount; i++) {
                View view = parent.getChildAt(i);
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                //正序
                if (!reverseLayout) {
                    //如果是第一个item上面画margin
                    if (childAdapterPosition == 0) {
                        c.drawRect(0, 0, view.getWidth(), view.getTop(), paintRecyclerMarginTopColor);
                    } else if (childAdapterPosition == itemCount - 1) {
                        c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), paintRecyclerMarginTopColor);
                    }
                } else {
                    if (childAdapterPosition == 0) {
                        c.drawRect(0, view.getBottom(), view.getWidth(), parent.getHeight(), paintRecyclerMarginBottomColor);
                    } else if (childAdapterPosition == itemCount - 1) {
                        c.drawRect(0, 0, view.getWidth(), view.getTop(), paintRecyclerMarginTopColor);
                    }
                }
            }
        }
    }

    private void drawLinearLayoutHorizontal(Canvas c, boolean reverseLayout, RecyclerView parent) {
        int realHeight = parent.getChildAt(0).getHeight();
        c.drawRect(0, itemDivider.getMarginTop(), parent.getWidth(), realHeight - itemDivider.getMarginBottom(), itemDivider.getPaint());
        c.drawRect(0, 0, parent.getWidth(), itemDivider.getMarginTop(), paintItemBackground);
        c.drawRect(0, realHeight - itemDivider.getMarginBottom(), parent.getWidth(), realHeight, paintItemBackground);
        int itemCount = parent.getAdapter().getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            if (childAdapterPosition == 0) {
                if (!reverseLayout) {
                    c.drawRect(0, 0, view.getLeft(), view.getHeight(), paintRecyclerMarginTopColor);
                } else {
                    c.drawRect(view.getRight(), 0, parent.getWidth(), view.getHeight(), paintRecyclerMarginBottomColor);
                }
            } else if (childAdapterPosition == itemCount - 1) {
                if (!reverseLayout) {
                    c.drawRect(view.getRight(), 0, parent.getWidth(), view.getHeight(), paintRecyclerMarginBottomColor);
                } else {
                    c.drawRect(0, 0, view.getLeft(), view.getHeight(), paintRecyclerMarginTopColor);
                }
            }
        }
    }

    private void drawGridLayoutVertical(Canvas c, boolean reverseLayout, RecyclerView parent) {
        int itemCount = parent.getAdapter().getItemCount();
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View firstView = parent.getChildAt(0);
        View lastView = layoutManager.findViewByPosition(itemCount - 1);
        c.drawRect(itemDivider.getMarginLeft(), itemDivider.getMarginTop(), parent.getWidth() - itemDivider.getMarginRight(), parent.getHeight() - itemDivider.getMarginBottom(), itemDivider.getPaint());
        //第一个item绘制顶部颜色
        if (!reverseLayout) {
            //如果是第一个可见item，并且第一个item不是只显示了一半。
            // 这里findFirstCompletelyVisibleItemPosition获取到的结果与实际不符，不然只需要判断FirstCompletelyVisibleItem即可
            if (firstVisibleItemPosition == 0 && firstView.getTop() >= 0) {
                c.drawRect(0, 0, parent.getWidth(), firstView.getTop(), paintRecyclerMarginTopColor);
            }
        } else {
            if (firstVisibleItemPosition == 0 && (parent.getHeight() - firstView.getBottom()) > 0) {
                c.drawRect(0, firstView.getBottom(), parent.getWidth(), parent.getHeight(), paintRecyclerMarginTopColor);
            }
        }
        //最后一个item绘制底部颜色
        if (lastVisibleItemPosition == itemCount - 1) {
            //如果最后一个item后面是空的，绘制一块用户设置的颜色
            c.drawRect(lastView.getRight() + itemDivider.getWidth(), lastView.getTop(), parent.getWidth(), lastView.getBottom(), paintGridItemBackground);
            if (!reverseLayout) {
                if (isMarginBottomWhenNotMatch || firstVisibleItemPosition != 0) {
                    c.drawRect(0, lastView.getBottom(), parent.getWidth(), parent.getHeight(), paintRecyclerMarginBottomColor);
                }
            } else {
                if (isMarginBottomWhenNotMatch || lastVisibleItemPosition != itemCount) {
                    c.drawRect(0, 0, parent.getWidth(), lastView.getTop(), paintRecyclerMarginBottomColor);
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
                if (paintRecyclerMarginTopColor.getColor() == -1) {
                    paintRecyclerMarginTopColor.setColor(color);
                }
                if (paintRecyclerMarginBottomColor.getColor() == -1) {
                    paintRecyclerMarginBottomColor.setColor(color);
                }
            }
        }
    }

    private void getLinearLayoutItemOffsetsVertical(Rect outRect, boolean reverseLayout, RecyclerView parent, View view) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        //正序item
        if (!reverseLayout) {
            if (childAdapterPosition == 0) {
                outRect.set(0, recyclerMarginTopPxValue, 0, 0);
                if (parent.getAdapter().getItemCount() == 1) {
                    outRect.set(0, recyclerMarginBottomPxValue, 0, recyclerMarginTopPxValue);
                }
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                if (isMarginBottomWhenNotMatch || firstCompletelyVisibleItemPosition != 0) {
                    outRect.set(0, itemDivider.getWidth(), 0, recyclerMarginBottomPxValue);
                } else {
                    outRect.set(0, itemDivider.getWidth(), 0, 0);
                }
            } else {
                outRect.set(0, itemDivider.getWidth(), 0, 0);
            }
            //倒序item
        } else {
            if (childAdapterPosition == 0) {
                outRect.set(0, 0, 0, recyclerMarginTopPxValue);
                if (parent.getAdapter().getItemCount() == 1 && isMarginBottomWhenNotMatch) {
                    outRect.set(0, recyclerMarginBottomPxValue, 0, recyclerMarginTopPxValue);
                }
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                if (isMarginBottomWhenNotMatch || firstCompletelyVisibleItemPosition != 0) {
                    outRect.set(0, recyclerMarginBottomPxValue, 0, itemDivider.getWidth());
                } else {
                    outRect.set(0, 0, 0, itemDivider.getWidth());
                }
            } else {
                outRect.set(0, 0, 0, itemDivider.getWidth());
            }
        }
    }

    private void getLinearLayoutItemOffsetsHorizontal(Rect outRect, boolean reverseLayout, RecyclerView parent, View view) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        //正序item
        if (!reverseLayout) {
            if (childAdapterPosition == 0) {
                outRect.set(recyclerMarginTopPxValue, 0, 0, 0);
                if (parent.getAdapter().getItemCount() == 1) {
                    outRect.set(recyclerMarginBottomPxValue, 0, recyclerMarginTopPxValue, 0);
                }
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                if (isMarginBottomWhenNotMatch || firstCompletelyVisibleItemPosition != 0) {
                    outRect.set(itemDivider.getWidth(), 0, recyclerMarginBottomPxValue, 0);
                } else {
                    outRect.set(itemDivider.getWidth(), 0, 0, 0);
                }
            } else {
                outRect.set(itemDivider.getWidth(), 0, 0, 0);
            }
            //倒序item
        } else {
            if (childAdapterPosition == 0) {
                outRect.set(0, 0, recyclerMarginTopPxValue, 0);
                if (parent.getAdapter().getItemCount() == 1 && isMarginBottomWhenNotMatch) {
                    outRect.set(recyclerMarginBottomPxValue, 0, recyclerMarginTopPxValue, 0);
                }
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                //根据firstCompleteVisibleItem下标，判断item是否超出屏幕（是否能滑动）
                if (isMarginBottomWhenNotMatch || firstCompletelyVisibleItemPosition != 0) {
                    outRect.set(recyclerMarginBottomPxValue, 0, itemDivider.getWidth(), 0);
                } else {
                    outRect.set(0, 0, itemDivider.getWidth(), 0);
                }
            } else {
                outRect.set(0, 0, itemDivider.getWidth(), 0);
            }
        }
    }

    private void getGridLayoutItemOffsetsVertical(Rect outRect, boolean reverseLayout, RecyclerView parent, View view) {
        int itemCount = parent.getAdapter().getItemCount();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = gridLayoutManager.getSpanCount();
        int top = 0;
        int right = itemDivider.getWidth();
        int bottom = itemDivider.getWidth();
        if (childAdapterPosition < spanCount) {
            if (!reverseLayout) {
                top = recyclerMarginTopPxValue;
            } else {
                bottom = recyclerMarginTopPxValue;
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
                if (isMarginBottomWhenNotMatch || firstVisibleItemPosition > spanCount) {
                    bottom = recyclerMarginBottomPxValue;
                } else {
                    bottom = 0;
                }
            } else {
                if (isMarginBottomWhenNotMatch || lastCompletelyVisibleItemPosition > spanCount) {
                    top = recyclerMarginBottomPxValue;
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