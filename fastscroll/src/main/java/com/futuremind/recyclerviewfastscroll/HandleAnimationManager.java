package com.futuremind.recyclerviewfastscroll;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.view.View;

public class HandleAnimationManager {

    @Nullable
    private AnimatorSet grabAnimator;
    @Nullable
    private AnimatorSet releaseAnimator;

    protected HandleAnimationManager(View handle, int grabAnimator, int releaseAnimator) {
        if(grabAnimator != -1) {
            this.grabAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), grabAnimator);
            this.grabAnimator.setTarget(handle);
        }
        if(releaseAnimator != -1) {
            this.releaseAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(handle.getContext(), releaseAnimator);
            this.releaseAnimator.setTarget(handle);
        }
    }

    public void onGrab() {
        if(releaseAnimator != null) {
            releaseAnimator.cancel();
        }
        if(grabAnimator != null) {
            grabAnimator.start();
        }
    }

    public void onRelease() {
        if(grabAnimator != null) {
            grabAnimator.cancel();
        }
        if(releaseAnimator != null) {
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
