package com.futuremind.recyclerviewfastscroll.example;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.ScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.Utils;
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
        int dimen = getContext().getResources().getDimensionPixelSize(R.dimen.custom_handle_size);
        handle.setLayoutParams(new ViewGroup.LayoutParams(dimen, dimen));
        Utils.setBackground(handle, drawCircle(getContext(), dimen, dimen, 0xff00ff00));
        return handle;
    }

    @Override
    public View provideBubbleView(ViewGroup container) {
        bubble = new TextView(getContext());
        int dimen = getContext().getResources().getDimensionPixelSize(R.dimen.custom_bubble_size);
        bubble.setLayoutParams(new ViewGroup.LayoutParams(dimen, dimen));
        Utils.setBackground(bubble, drawCircle(getContext(), dimen, dimen, 0xff00ff00));
        bubble.setVisibility(View.INVISIBLE);
        bubble.setGravity(Gravity.CENTER);
        return bubble;
    }

    @Override
    public TextView provideBubbleTextView() {
        return bubble;
    }

    @Override
    public int getBubbleOffset() {
        return (int) (getScroller().isVertical() ? (float)handle.getHeight()/2f-(float)bubble.getHeight()/2f : (float)handle.getWidth()/2f-(float)bubble.getWidth()/2);
    }

    @Override
    public ViewVisibilityManager provideBubbleVisibilityManager() {
        return new ViewVisibilityManager(bubble);
    }

    private static ShapeDrawable drawCircle (Context context, int width, int height, int color) {
        ShapeDrawable oval = new ShapeDrawable (new OvalShape());
        oval.setIntrinsicHeight(height);
        oval.setIntrinsicWidth(width);
        oval.getPaint().setColor(color);
        return oval;
    }

}
