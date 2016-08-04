package com.futuremind.recyclerviewfastscroll;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Michal on 04/08/16.
 * Responsible for updating the handle position when user scrolls the {@link RecyclerView}.
 */
public class RecyclerScrollListener extends RecyclerView.OnScrollListener {

    private final FastScroller scroller;

    public RecyclerScrollListener(FastScroller scroller) {
        this.scroller = scroller;
    }

    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        if(scroller.shouldUpdateHandlePosition()) {
            float relativePos;
            if(scroller.isVertical()) {
                int offset = rv.computeVerticalScrollOffset();
                int extent = rv.computeVerticalScrollExtent();
                int range = rv.computeVerticalScrollRange();
                relativePos = offset / (float)(range - extent);
            } else {
                int offset = rv.computeHorizontalScrollOffset();
                int extent = rv.computeHorizontalScrollExtent();
                int range = rv.computeHorizontalScrollRange();
                relativePos = offset / (float)(range - extent);
            }
            scroller.setHandlePosition(relativePos);
        }
    }
}
