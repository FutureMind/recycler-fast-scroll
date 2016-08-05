package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.view.View;

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

    public abstract View provideHandleView();
}
