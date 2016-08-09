package com.futuremind.recyclerviewfastscroll;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.View;

/**
 * Created by Michal on 05/08/16.
 */
public class VisibilityAnimationManager {

    private final View view;

    private AnimatorSet hideAnimator;
    private AnimatorSet showAnimator;

    private VisibilityAnimationManager(final View view, int showAnimator, int hideAnimator, float pivotXRelative, float pivotYRelative, int hideDelay){
        this.view = view;
        view.setPivotX(pivotXRelative*view.getWidth());
        view.setPivotY(pivotYRelative*view.getWidth());
        this.hideAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), hideAnimator);
        this.hideAnimator.setStartDelay(hideDelay);
        this.hideAnimator.setTarget(view);
        this.showAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), showAnimator);
        this.showAnimator.setTarget(view);
        this.hideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void show(){
        if (hideAnimator.isRunning()) {
            hideAnimator.cancel();
        }
        if (view.getVisibility() == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
            showAnimator.start();
        }
    }

    public void hide(){
        hideAnimator.start();
    }

    public static class Builder {

        private final View view;
        private int showAnimatorResource = R.animator.fastscroll__default_show;
        private int hideAnimatorResource = R.animator.fastscroll__default_hide;
        private int hideDelay = 1000;
        private float pivotX = 0.5f;
        private float pivotY = 0.5f;

        public Builder(View view) {
            this.view = view;
        }

        public Builder withShowAnimator(int showAnimatorResource){
            this.showAnimatorResource = showAnimatorResource;
            return this;
        }

        public Builder withHideAnimator(int hideAnimatorResource){
            this.hideAnimatorResource = hideAnimatorResource;
            return this;
        }

        public Builder withHideDelay(int hideDelay){
            this.hideDelay = hideDelay;
            return this;
        }

        public Builder withPivotX(float pivotX){
            this.pivotX = pivotX;
            return this;
        }

        public Builder withPivotY(float pivotY){
            this.pivotY = pivotY;
            return this;
        }

        public VisibilityAnimationManager build(){
            return new VisibilityAnimationManager(view, showAnimatorResource, hideAnimatorResource, pivotX, pivotY, hideDelay);
        }

    }

}
