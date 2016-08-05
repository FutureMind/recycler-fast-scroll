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

    private final int hideDelay;
    private final int animationDuration;
    private final View view;

    private AnimatorSet bubbleHideAnimator;

    public ViewVisibilityManager(View view){
        this(view, DEFAULT_HIDE_DELAY, DEFAULT_ANIMATION_DURATION);
    }

    public ViewVisibilityManager(View view, int hideDelay, int animationDuration){
        this.view = view;
        this.hideDelay = hideDelay;
        this.animationDuration = animationDuration;
    }

    public void show(){
        if (bubbleHideAnimator != null) {
            bubbleHideAnimator.cancel();
        }
        if (view.getVisibility() == View.INVISIBLE) {
            animateShow();
        }
    }

    public void hide(){
        animateHide();
    }

    private void animateShow() {
        AnimatorSet bubbleShowAnimator = new AnimatorSet();
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());
        view.setVisibility(View.VISIBLE);
        Animator growerX = ObjectAnimator.ofFloat(view, SCALE_X, 0f, 1f).setDuration(animationDuration);
        Animator growerY = ObjectAnimator.ofFloat(view, SCALE_Y, 0f, 1f).setDuration(animationDuration);
        Animator alpha = ObjectAnimator.ofFloat(view, ALPHA, 0f, 1f).setDuration(animationDuration);
        bubbleShowAnimator.setInterpolator(new DecelerateInterpolator());
        bubbleShowAnimator.playTogether(growerX, growerY, alpha);
        bubbleShowAnimator.start();
    }

    private void animateHide() {
        bubbleHideAnimator = new AnimatorSet();
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight());
        Animator shrinkerX = ObjectAnimator.ofFloat(view, SCALE_X, 1f, 0f).setDuration(animationDuration);
        Animator shrinkerY = ObjectAnimator.ofFloat(view, SCALE_Y, 1f, 0f).setDuration(animationDuration);
        Animator alpha = ObjectAnimator.ofFloat(view, ALPHA, 1f, 0f).setDuration(animationDuration);
        bubbleHideAnimator.setInterpolator(new AccelerateInterpolator());
        bubbleHideAnimator.playTogether(shrinkerX, shrinkerY, alpha);
        bubbleHideAnimator.setStartDelay(hideDelay);
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

}
