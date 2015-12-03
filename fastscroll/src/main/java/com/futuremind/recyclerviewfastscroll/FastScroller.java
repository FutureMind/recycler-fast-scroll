package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by mklimczak on 28/07/15.
 */
public class FastScroller extends LinearLayout {

    private FastScrollBubble bubble;
    private ImageView handle;
    private int scrollerOrientation;

    private RecyclerView recyclerView;

    private final ScrollListener scrollListener = new ScrollListener();

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

    /**
     * Attach the FastScroller to RecyclerView. Should be used after the Adapter is set
     * to the RecyclerView. If the adapter implements SectionTitleProvider, the FastScroller
     * will show a bubble with title.
     * @param recyclerView A RecyclerView to attach the FastScroller to
     */
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

    private void init(Context context) {

        scrollerOrientation = getOrientation();

        //switching orientation, because orientation in linear layout
        //is something different than orientation of fast scroller
        setOrientation(getOrientation() == HORIZONTAL ? VERTICAL : HORIZONTAL);
        setClipChildren(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(isVertical() ? R.layout.fastscroller_vertical : R.layout.fastscroller_horizontal, this);
        bubble = (FastScrollBubble) findViewById(R.id.fastscroller_bubble);
        handle = (ImageView) findViewById(R.id.fastscroller_handle);

        handle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    manuallyChangingPosition = true;
                    float yInParent = event.getRawY() - Utils.getViewRawY(handle);
                    setPosition(Utils.getValueInRange(0, 1, yInParent / (getHeight() - handle.getHeight())));
                    if(titleProvider!=null) bubble.show();
                    setRecyclerViewPosition(yInParent);
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    manuallyChangingPosition = false;
                    if(titleProvider!=null) bubble.hide();
                    return true;
                }
                return false;
            }
        });

    }

    private void invalidateVisibility() {
        if(
                recyclerView.getAdapter()==null ||
                recyclerView.getAdapter().getItemCount()==0 ||
                recyclerView.getChildAt(0)==null ||
                recyclerView.getChildAt(0).getHeight() * recyclerView.getAdapter().getItemCount()<=getHeight()
                ){
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;
            proportion = y / (float) getHeight();
            int targetPos = (int) Utils.getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            recyclerView.scrollToPosition(targetPos);
            if(titleProvider!=null) bubble.setText(titleProvider.getSectionTitle(targetPos));
        }
    }

    private void setPosition(float y) {
        int bubbleOffset = (int) (((float)handle.getHeight()/2f)-bubble.getHeight());
        bubble.setY(Utils.getValueInRange(
                        0,
                        getHeight() - bubble.getHeight(),
                        y * (getHeight() - handle.getHeight()) + bubbleOffset)
        );
        handle.setY(y * (getHeight() - handle.getHeight()));
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy) {
            if(!manuallyChangingPosition) {
                View firstVisibleView = recyclerView.getChildAt(0);
                float rvHeight = firstVisibleView.getHeight() * rv.getAdapter().getItemCount();
                int recyclerViewScrollY = recyclerView.getChildLayoutPosition(firstVisibleView) * firstVisibleView.getHeight() - firstVisibleView.getTop();
                setPosition(recyclerViewScrollY / (rvHeight - getHeight()));
            }
        }
    }

    private boolean isVertical(){
        return scrollerOrientation == VERTICAL;
    }

}
