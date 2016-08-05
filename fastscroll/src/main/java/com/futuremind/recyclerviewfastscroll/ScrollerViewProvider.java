package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.view.View;

/**
 * Created by Michal on 05/08/16.
 */
public abstract class ScrollerViewProvider {

    private final Context cxt;
    private boolean isVertical;

    public ScrollerViewProvider(Context cxt) {
        this.cxt = cxt;
    }

    protected Context getContext() {
        return cxt;
    }

    protected boolean isVertical() {
        return isVertical;
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public abstract View getHandleView();
}
