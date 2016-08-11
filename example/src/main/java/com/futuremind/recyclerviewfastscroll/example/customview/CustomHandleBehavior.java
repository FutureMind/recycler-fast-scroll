package com.futuremind.recyclerviewfastscroll.example.customview;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.futuremind.recyclerviewfastscroll.viewprovider.ViewBehavior;
import com.futuremind.recyclerviewfastscroll.viewprovider.VisibilityAnimationManager;

/**
 * Created by Michal on 11/08/16.
 */
public class CustomHandleBehavior implements ViewBehavior{

    private final VisibilityAnimationManager visibilityManager;
    private final HandleAnimationManager grabManager;

    private boolean isGrabbed;

    public CustomHandleBehavior(VisibilityAnimationManager visibilityManager, HandleAnimationManager grabManager) {
        this.visibilityManager = visibilityManager;
        this.grabManager = grabManager;
    }

    @Override
    public void onHandleGrabbed() {
        isGrabbed = true;
        visibilityManager.show();
        grabManager.onGrab();
    }

    @Override
    public void onHandleReleased() {
        isGrabbed = false;
        visibilityManager.hide();
        grabManager.onRelease();
    }

    @Override
    public void onScrollStarted() {
        visibilityManager.show();
    }

    @Override
    public void onScrollFinished() {
        if(!isGrabbed) visibilityManager.hide();
    }

    static class HandleAnimationManager {

        @Nullable
        private AnimatorSet grabAnimator;
        @Nullable
        private AnimatorSet releaseAnimator;

        protected HandleAnimationManager(View handle, @AnimatorRes int grabAnimator, @AnimatorRes int releaseAnimator) {
            if (grabAnimator != -1) {
                this.grabAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), grabAnimator);
                this.grabAnimator.setTarget(handle);
            }
            if (releaseAnimator != -1) {
                this.releaseAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), releaseAnimator);
                this.releaseAnimator.setTarget(handle);
            }
        }

        public void onGrab() {
            if (releaseAnimator != null) {
                releaseAnimator.cancel();
            }
            if (grabAnimator != null) {
                grabAnimator.start();
            }
        }

        public void onRelease() {
            if (grabAnimator != null) {
                grabAnimator.cancel();
            }
            if (releaseAnimator != null) {
                releaseAnimator.start();
            }
        }

        public static class Builder {
            private View handle;
            private int grabAnimator;
            private int releaseAnimator;

            public Builder(View handle) {
                this.handle = handle;
            }

            public Builder withGrabAnimator(@AnimatorRes int grabAnimator) {
                this.grabAnimator = grabAnimator;
                return this;
            }

            public Builder withReleaseAnimator(@AnimatorRes int releaseAnimator) {
                this.releaseAnimator = releaseAnimator;
                return this;
            }

            public HandleAnimationManager build() {
                return new HandleAnimationManager(handle, grabAnimator, releaseAnimator);
            }
        }
    }

}
