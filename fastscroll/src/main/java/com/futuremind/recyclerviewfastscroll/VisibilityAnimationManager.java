package com.futuremind.recyclerviewfastscroll;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Michal on 05/08/16.
 */
public class VisibilityAnimationManager {

    protected final View view;

    protected AnimatorSet hideAnimator;
    protected AnimatorSet showAnimator;

    protected VisibilityAnimationManager(final View view, int showAnimator, int hideAnimator, float pivotXRelative, float pivotYRelative, int hideDelay){
        this.view = view;
        view.setPivotX(pivotXRelative*view.getMeasuredWidth());
        view.setPivotY(pivotYRelative*view.getMeasuredHeight());
        this.hideAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), hideAnimator);
        this.hideAnimator.setStartDelay(hideDelay);
        this.hideAnimator.setTarget(view);
        this.showAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), showAnimator);
        this.showAnimator.setTarget(view);
        this.hideAnimator.addListener(new AnimatorListenerAdapter() {

            //because onAnimationEnd() goes off even for canceled animations
            boolean wasCanceled;

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!wasCanceled) view.setVisibility(View.INVISIBLE);
                wasCanceled = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                wasCanceled = true;
            }
        });
    }

    public void show(){
        hideAnimator.cancel();
        if (view.getVisibility() == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
            showAnimator.start();
        }
    }

    public void hide(){
        hideAnimator.start();
    }

    public static abstract class AbsBuilder<T extends VisibilityAnimationManager> {
        protected final View view;
        protected int showAnimatorResource = R.animator.fastscroll__default_show;
        protected int hideAnimatorResource = R.animator.fastscroll__default_hide;
        protected int hideDelay = 1000;
        protected float pivotX = 0.5f;
        protected float pivotY = 0.5f;

        public AbsBuilder(View view) {
            this.view = view;
        }

        public AbsBuilder<T> withShowAnimator(int showAnimatorResource){
            this.showAnimatorResource = showAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideAnimator(int hideAnimatorResource){
            this.hideAnimatorResource = hideAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideDelay(int hideDelay){
            this.hideDelay = hideDelay;
            return this;
        }

        public AbsBuilder<T> withPivotX(float pivotX){
            this.pivotX = pivotX;
            return this;
        }

        public AbsBuilder<T> withPivotY(float pivotY){
            this.pivotY = pivotY;
            return this;
        }

        public abstract T build();
    }

    public static class Builder extends AbsBuilder<VisibilityAnimationManager> {

        public Builder(View view) {
            super(view);
        }

        public VisibilityAnimationManager build(){
            return new VisibilityAnimationManager(view, showAnimatorResource, hideAnimatorResource, pivotX, pivotY, hideDelay);
        }

    }

}
