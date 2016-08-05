package com.futuremind.recyclerviewfastscroll.example;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.ScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.ViewVisibilityManager;

/**
 * Created by Michal on 05/08/16.
 */
public class CustomScrollerViewProvider extends ScrollerViewProvider {

    private TextView bubble;
    private View handle;

    @Override
    public View provideHandleView(ViewGroup container) {
        handle = new View(getContext());
        handle.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
        handle.setBackgroundColor(0xffff0000);
        return handle;
    }

    @Override
    public View provideBubbleView(ViewGroup container) {
        bubble = new TextView(getContext());
        bubble.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        bubble.setBackgroundColor(0xff00ff00);
        bubble.setVisibility(View.INVISIBLE);
        return bubble;
    }

    @Override
    public TextView provideBubbleTextView() {
        return bubble;
    }

    @Override
    public int getBubbleOffset() {
        return (int) (getScroller().isVertical() ? ((float)handle.getHeight()/2f)-bubble.getHeight() : ((float)handle.getWidth()/2f)-bubble.getWidth());
    }

    @Override
    public ViewVisibilityManager provideBubbleVisibilityManager() {
        return new ViewVisibilityManager(bubble);
    }
}
