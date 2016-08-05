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

    public abstract void setText(String text);

}
