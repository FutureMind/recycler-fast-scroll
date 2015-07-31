package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by mklimczak on 28/07/15.
 */
public class FastScroller extends LinearLayout {

    private FastScrollBubble bubble;
    private View handle;

    private RecyclerView recyclerView;

    private final ScrollListener scrollListener = new ScrollListener();
    private int height;

    private boolean manuallyChangingPosition;

    private SectionTitleProvider titleProvider;

    public FastScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.fastscroller, this);
        bubble = (FastScrollBubble) findViewById(R.id.fastscroller_bubble);
        handle = findViewById(R.id.fastscroller_handle);

        handle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    manuallyChangingPosition = true;
                    float yInParent = event.getRawY() - Utils.getViewRawY(handle);
                    setPosition(Utils.getValueInRange(0, 1, yInParent / (height - handle.getHeight())));
                    bubble.show();
                    setRecyclerViewPosition(yInParent);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    manuallyChangingPosition = false;
                    bubble.hide();
                    return true;
                }
                return false;
            }
        });

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
            int targetPos = (int) Utils.getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            recyclerView.scrollToPosition(targetPos);
            if(titleProvider!=null) bubble.setText(titleProvider.getSectionTitle(targetPos));
        }
    }

    private void setPosition(float y) {
        int bubbleOffset = (int) (((float)handle.getHeight()/2f)-bubble.getHeight());
        bubble.setY(Utils.getValueInRange(
                        0,
                        height - bubble.getHeight(),
                        y * (height - handle.getHeight()) + bubbleOffset)
        );
        handle.setY(y * (height - handle.getHeight()));
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

}
