package com.futuremind.recyclerviewfastscroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mklimczak on 28/07/15.
 */
public class FastScroller extends LinearLayout {


    private static final int BUBBLE_HIDE_DELAY = 1000;
    private static final int BUBBLE_ANIMATION_DURATION = 200;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";

    private TextView bubble;
    private View handle;

    private RecyclerView recyclerView;

    private final BubbleHider bubbleHider = new BubbleHider();
    private final ScrollListener scrollListener = new ScrollListener();
    private int height;

    private boolean manuallyChangingPosition;

    private AnimatorSet bubbleHideAnimator = null;

    private SectionTitleProvider titleProvider;

    public FastScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise(context);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context);
    }

    private void initialise(Context context) {
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.fastscroller, this);
        bubble = (TextView) findViewById(R.id.fastscroller_bubble);
        handle = findViewById(R.id.fastscroller_handle);

        handle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    manuallyChangingPosition = true;
                    float yInParent = event.getRawY() - getViewRawY(handle);
                    setPosition(getValueInRange(0, 1, yInParent / (height - handle.getHeight())));
                    if (bubbleHideAnimator != null) {
                        bubbleHideAnimator.cancel();
                    }
                    getHandler().removeCallbacks(bubbleHider);
                    if (bubble.getVisibility() == INVISIBLE) {
                        showBubble();
                    }
                    setRecyclerViewPosition(yInParent);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    manuallyChangingPosition = false;
                    getHandler().postDelayed(bubbleHider, BUBBLE_HIDE_DELAY);
                    return true;
                }
                return false;
            }
        });

    }

    private float getViewRawY(View view) {
        int[] location = new int[2];
        location[0] = 0;
        location[1] = (int) view.getY();
        getLocationInWindow(location);
        return location[1];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }



    private void invalidateVisibility() {
        if(
                recyclerView.getAdapter()==null ||
                recyclerView.getAdapter().getItemCount()==0 ||
                recyclerView.getChildAt(0)==null ||
                recyclerView.getChildAt(0).getHeight() * recyclerView.getAdapter().getItemCount()<=height
                ){
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if(recyclerView.getAdapter() instanceof SectionTitleProvider) titleProvider = (SectionTitleProvider) recyclerView.getAdapter();
        recyclerView.addOnScrollListener(scrollListener);
        invalidateVisibility();
        recyclerView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                invalidateVisibility();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                invalidateVisibility();
            }
        });
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;
            proportion = y / (float) height;
            int targetPos = (int) getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            recyclerView.scrollToPosition(targetPos);
            if(titleProvider!=null) bubble.setText(titleProvider.getSectionTitle(targetPos));
        }
    }

    private float getValueInRange(float min, float max, float value) {
        float minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    private void setPosition(float y) {
        int bubbleOffset = (int) (((float)handle.getHeight()/2f)-bubble.getHeight());
        bubble.setY(getValueInRange(
                0,
                height - bubble.getHeight(),
                y*(height-handle.getHeight())+bubbleOffset)
            );
        handle.setY(y*(height - handle.getHeight()));
    }

    private void showBubble() {
        AnimatorSet animatorSet = new AnimatorSet();
        bubble.setPivotX(bubble.getWidth());
        bubble.setPivotY(bubble.getHeight());
        bubble.setVisibility(VISIBLE);
        Animator growerX = ObjectAnimator.ofFloat(bubble, SCALE_X, 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        Animator growerY = ObjectAnimator.ofFloat(bubble, SCALE_Y, 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(bubble, ALPHA, 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(growerX, growerY, alpha);
        animatorSet.start();
    }

    private void hideBubble() {
        bubbleHideAnimator = new AnimatorSet();
        bubble.setPivotX(bubble.getWidth());
        bubble.setPivotY(bubble.getHeight());
        Animator shrinkerX = ObjectAnimator.ofFloat(bubble, SCALE_X, 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        Animator shrinkerY = ObjectAnimator.ofFloat(bubble, SCALE_Y, 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(bubble, ALPHA, 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        bubbleHideAnimator.setInterpolator(new AccelerateInterpolator());
        bubbleHideAnimator.playTogether(shrinkerX, shrinkerY, alpha);
        bubbleHideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bubble.setVisibility(INVISIBLE);
                bubbleHideAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                bubble.setVisibility(INVISIBLE);
                bubbleHideAnimator = null;
            }
        });
        bubbleHideAnimator.start();
    }

    private class BubbleHider implements Runnable {
        @Override
        public void run() {
            hideBubble();
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {

            if(!manuallyChangingPosition) {
                View firstVisibleView = recyclerView.getChildAt(0);
                float rvHeight = firstVisibleView.getHeight() * rv.getAdapter().getItemCount();
                int recyclerViewScrollY = recyclerView.getChildLayoutPosition(firstVisibleView) * firstVisibleView.getHeight() - firstVisibleView.getTop();
                setPosition(recyclerViewScrollY / (rvHeight - height));
            }
        }
    }

    public interface SectionTitleProvider{
        String getSectionTitle(int position);
    }

}
