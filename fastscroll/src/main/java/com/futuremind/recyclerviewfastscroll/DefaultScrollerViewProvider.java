package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.graphics.drawable.InsetDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Michal on 05/08/16.
 */
public class DefaultScrollerViewProvider extends ScrollerViewProvider {

    public DefaultScrollerViewProvider(Context cxt) {
        super(cxt);
    }

    @Override
    public View getHandleView() {
        View handleView = new View(getContext());

        int verticalInset = isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.fastscroll__handle_inset);
        int horizontalInset = !isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.fastscroll__handle_inset);
        InsetDrawable handleBg = new InsetDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fastscroll__handle), horizontalInset, verticalInset, horizontalInset, verticalInset);
        Utils.setBackground(handleView, handleBg);

        int handleWidth = getContext().getResources().getDimensionPixelSize(isVertical() ? R.dimen.fastscroll__handle_clickable_width : R.dimen.fastscroll__handle_height);
        int handleHeight = getContext().getResources().getDimensionPixelSize(isVertical() ? R.dimen.fastscroll__handle_height : R.dimen.fastscroll__handle_clickable_width);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(handleWidth, handleHeight);
        handleView.setLayoutParams(params);

        return handleView;
    }
}
