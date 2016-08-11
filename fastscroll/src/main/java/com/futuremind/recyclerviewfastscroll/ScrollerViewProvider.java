package com.futuremind.recyclerviewfastscroll;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michal on 05/08/16.
 */
public abstract class ScrollerViewProvider {

    private FastScroller scroller;
    private VisibilityAnimationManager bubbleVisibilityManager;
    private VisibilityAnimationManager handleVisibilityManager;
    private HandleAnimationManager handleAnimationManager;

    private boolean isGrabbed;

    void setFastScroller(FastScroller scroller){
        this.scroller = scroller;
    }

    protected Context getContext(){
        return scroller.getContext();
    }

    protected FastScroller getScroller() {
        return scroller;
    }

    /**
     * @param container The container {@link FastScroller} for the view to inflate properly.
     * @return A view which will be by the {@link FastScroller} used as a handle.
     */
    protected abstract View provideHandleView(ViewGroup container);

    /**
     * @param container The container {@link FastScroller} for the view to inflate properly.
     * @return A view which will be by the {@link FastScroller} used as a bubble.
     */
    protected abstract View provideBubbleView(ViewGroup container);

    /**
     * Bubble view has to provide a {@link TextView} that will show the index title.
     * @return A {@link TextView} that will hold the index title.
     */
    protected abstract TextView provideBubbleTextView();

    /**
     * To offset the position of the bubble relative to the handle. E.g. in {@link DefaultScrollerViewProvider}
     * the sharp corner of the bubble is aligned with the center of the handle.
     * @return the position of the bubble in relation to the handle (according to the orientation).
     */
    protected abstract int getBubbleOffset();

    /**
     * @return {@link VisibilityAnimationManager} responsible for showing and hiding bubble.
     */
    protected abstract VisibilityAnimationManager provideBubbleVisibilityManager();

    /**
     * @return {@link VisibilityAnimationManager} responsible for showing and hiding handle.
     */
    protected abstract VisibilityAnimationManager provideHandleVisibilityManager();

    /**
     * @return {@link HandleAnimationManager} responsible for animating handle grab/release
     */
    protected HandleAnimationManager provideHandleAnimationManager() { return null; }

    private VisibilityAnimationManager getBubbleVisibilityManager(){
        if(bubbleVisibilityManager==null) bubbleVisibilityManager = provideBubbleVisibilityManager();
        return bubbleVisibilityManager;
    }

    private VisibilityAnimationManager getHandleVisibilityManager(){
        if(handleVisibilityManager==null) handleVisibilityManager = provideHandleVisibilityManager();
        return handleVisibilityManager;
    }

    private HandleAnimationManager getHandleAnimationManger(){
        if(handleAnimationManager==null) handleAnimationManager = provideHandleAnimationManager();
        return handleAnimationManager;
    }

    protected void handleGrabbed(){
        isGrabbed = true;
        if(getBubbleVisibilityManager()!=null) getBubbleVisibilityManager().show();
        if(getHandleVisibilityManager()!=null) getHandleVisibilityManager().show();
        if(getHandleVisibilityManager()!=null) getHandleAnimationManger().onGrab();
    }

    protected void handleReleased(){
        isGrabbed = false;
        if(getBubbleVisibilityManager()!=null) getBubbleVisibilityManager().hide();
        if(getHandleVisibilityManager()!=null) getHandleVisibilityManager().hide();
        if(getHandleVisibilityManager()!=null) getHandleAnimationManger().onRelease();
    }

    protected void onScrollStarted(){
        if(getHandleVisibilityManager()!=null) getHandleVisibilityManager().show();
    }

    protected void onScrollFinished(){
        if(getHandleVisibilityManager()!=null && !isGrabbed) getHandleVisibilityManager().hide();
    }

}
