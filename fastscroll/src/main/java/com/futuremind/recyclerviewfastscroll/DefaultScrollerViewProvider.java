package com.futuremind.recyclerviewfastscroll;

import android.graphics.drawable.InsetDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Michal on 05/08/16.
 */
public class DefaultScrollerViewProvider extends ScrollerViewProvider {

    private FastScrollBubble bubble;

    public DefaultScrollerViewProvider(FastScroller scroller) {
        super(scroller);
    }

    @Override
    public View provideHandleView(ViewGroup container) {
        View handle = new View(getContext());

        int verticalInset = getScroller().isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.fastscroll__handle_inset);
        int horizontalInset = !getScroller().isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.fastscroll__handle_inset);
        InsetDrawable handleBg = new InsetDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fastscroll__handle), horizontalInset, verticalInset, horizontalInset, verticalInset);
        Utils.setBackground(handle, handleBg);

        int handleWidth = getContext().getResources().getDimensionPixelSize(getScroller().isVertical() ? R.dimen.fastscroll__handle_clickable_width : R.dimen.fastscroll__handle_height);
        int handleHeight = getContext().getResources().getDimensionPixelSize(getScroller().isVertical() ? R.dimen.fastscroll__handle_height : R.dimen.fastscroll__handle_clickable_width);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(handleWidth, handleHeight);
        handle.setLayoutParams(params);

        return handle;
    }

    @Override
    public FastScrollBubble provideBubbleView(ViewGroup container) {
        bubble = (FastScrollBubble) LayoutInflater.from(getContext()).inflate(R.layout.fastscroll__bubble, container, false);
        return bubble;
    }

    @Override
    public void setText(String text) {
        bubble.setText(text);
    }
}
