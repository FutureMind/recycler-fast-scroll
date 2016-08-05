package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Michal on 05/08/16.
 */
public abstract class ScrollerViewProvider {

    private final FastScroller scroller;

    public ScrollerViewProvider(FastScroller scroller) {
        this.scroller = scroller;
    }

    protected Context getContext(){
        return scroller.getContext();
    }

    protected FastScroller getScroller() {
        return scroller;
    }

    public abstract View provideHandleView(ViewGroup container);

    public abstract FastScrollBubble provideBubbleView(ViewGroup container);

    /**
     * To offset the position of the bubble relative to the handle. E.g. in {@link DefaultScrollerViewProvider}
     * the sharp corner of the bubble is aligned with the center of the handle.
     * @return the position of the bubble in relation to the handle (according to the orientation)
     */
    public abstract int getBubbleOffset();

    public abstract void setText(String text);

}
