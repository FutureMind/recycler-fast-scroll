package com.futuremind.recyclerviewfastscroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
import android.view.View;


public class HandleAnimationManager extends VisibilityAnimationManager {

    private final int hideDelay;
    private final Handler hideHandler = new Handler();
    private final Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private HandleAnimationManager(View view, int showAnimator, int hideAnimator, float pivotXRelative, float pivotYRelative, int hideDelay) {
        super(view, showAnimator, hideAnimator, pivotXRelative, pivotYRelative, 0);

        this.hideDelay = hideDelay;

        super.showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startOrResetTimer();
            }
        });
    }

    public void onScroll() {
        if(view.getVisibility() == View.INVISIBLE || hideAnimator.isRunning()) {
            show();
        } else if(!showAnimator.isRunning()) {
            startOrResetTimer();
        }
    }

    private void startOrResetTimer() {
        hideHandler.removeCallbacks(hideRunnable);
        hideHandler.postDelayed(hideRunnable, hideDelay);
    }

    public static class Builder extends AbsBuilder<HandleAnimationManager> {
        public Builder(View view) {
            super(view);
            // By default, hide handle after bubble
            hideDelay *= 1.5;
        }

        public HandleAnimationManager build(){
            return new HandleAnimationManager(view, showAnimatorResource, hideAnimatorResource, pivotX, pivotY, hideDelay);
        }

    }
}
