package com.futuremind.recyclerviewfastscroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Michal on 05/08/16.
 */
public class ViewVisibilityManager {

    private static final int DEFAULT_HIDE_DELAY = 1000;
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private static final String ALPHA = "alpha";

    private final BubbleHider bubbleHider = new BubbleHider();
    private final View view;
    private AnimatorSet bubbleHideAnimator = null;

    public ViewVisibilityManager(View view){
        this.view = view;
    }

    //TODO
    public ViewVisibilityManager(View view, int hideDelay, int animationDuration){
        this(view);
    }

    public void show(){
        if (bubbleHideAnimator != null) {
            bubbleHideAnimator.cancel();
        }
        view.getHandler().removeCallbacks(bubbleHider);
        if (view.getVisibility() == View.INVISIBLE) {
            animateShow();
        }
    }

    public void hide(){
        view.getHandler().postDelayed(bubbleHider, DEFAULT_HIDE_DELAY);
    }

    private void animateShow() {

        AnimatorSet animatorSet = new AnimatorSet();
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());
        view.setVisibility(View.VISIBLE);
        Animator growerX = ObjectAnimator.ofFloat(this, SCALE_X, 0f, 1f).setDuration(DEFAULT_ANIMATION_DURATION);
        Animator growerY = ObjectAnimator.ofFloat(this, SCALE_Y, 0f, 1f).setDuration(DEFAULT_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(this, ALPHA, 0f, 1f).setDuration(DEFAULT_ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(growerX, growerY, alpha);
        animatorSet.start();
    }

    private void animateHide() {
        bubbleHideAnimator = new AnimatorSet();
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());
        Animator shrinkerX = ObjectAnimator.ofFloat(this, SCALE_X, 1f, 0f).setDuration(DEFAULT_ANIMATION_DURATION);
        Animator shrinkerY = ObjectAnimator.ofFloat(this, SCALE_Y, 1f, 0f).setDuration(DEFAULT_ANIMATION_DURATION);
        Animator alpha = ObjectAnimator.ofFloat(this, ALPHA, 1f, 0f).setDuration(DEFAULT_ANIMATION_DURATION);
        bubbleHideAnimator.setInterpolator(new AccelerateInterpolator());
        bubbleHideAnimator.playTogether(shrinkerX, shrinkerY, alpha);
        bubbleHideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                bubbleHideAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setVisibility(View.INVISIBLE);
                bubbleHideAnimator = null;
            }
        });
        bubbleHideAnimator.start();
    }

    private class BubbleHider implements Runnable {
        @Override
        public void run() {
            animateHide();
        }
    }

}
