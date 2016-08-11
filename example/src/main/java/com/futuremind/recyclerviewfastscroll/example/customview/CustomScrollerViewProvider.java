package com.futuremind.recyclerviewfastscroll.example.customview;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.viewprovider.DefaultBubbleBehavior;
import com.futuremind.recyclerviewfastscroll.RecyclerViewScrollListener;
import com.futuremind.recyclerviewfastscroll.viewprovider.ScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.Utils;
import com.futuremind.recyclerviewfastscroll.viewprovider.ViewBehavior;
import com.futuremind.recyclerviewfastscroll.viewprovider.VisibilityAnimationManager;
import com.futuremind.recyclerviewfastscroll.example.R;

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
        Utils.setBackground(handle, drawCircle(dimen, dimen, ContextCompat.getColor(getContext(), R.color.custom_handle_color)));
        handle.setVisibility(View.INVISIBLE);
        return handle;
    }

    @Override
    public View provideBubbleView(ViewGroup container) {
        bubble = new TextView(getContext());
        int dimen = getContext().getResources().getDimensionPixelSize(R.dimen.custom_bubble_size);
        bubble.setLayoutParams(new ViewGroup.LayoutParams(dimen, dimen));
        Utils.setBackground(bubble, drawCircle(dimen, dimen, ContextCompat.getColor(getContext(), R.color.custom_bubble_color)));
        bubble.setVisibility(View.INVISIBLE);
        bubble.setGravity(Gravity.CENTER);
        bubble.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        getScroller().addScrollerListener(new RecyclerViewScrollListener.ScrollerListener() {
            @Override
            public void onScroll(float relativePos) {
                //Yeah, yeah, but we were so preoccupied with whether or not we could,
                //that we didn't stop to think if we should.
                bubble.setRotation(relativePos*360f);
            }
        });
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
    protected ViewBehavior provideHandleBehavior() {
        return new CustomHandleBehavior(
                new VisibilityAnimationManager.Builder(handle)
                        .withHideDelay(2000)
                        .build(),
                new CustomHandleBehavior.HandleAnimationManager.Builder(handle)
                        .withGrabAnimator(R.animator.custom_grab)
                        .withReleaseAnimator(R.animator.custom_release)
                        .build()
        );
    }

    @Override
    protected ViewBehavior provideBubbleBehavior() {
        return new DefaultBubbleBehavior(new VisibilityAnimationManager.Builder(bubble).withHideDelay(0).build());
    }

    private static ShapeDrawable drawCircle (int width, int height, int color) {
        ShapeDrawable oval = new ShapeDrawable (new OvalShape());
        oval.setIntrinsicHeight(height);
        oval.setIntrinsicWidth(width);
        oval.getPaint().setColor(color);
        return oval;
    }

}
