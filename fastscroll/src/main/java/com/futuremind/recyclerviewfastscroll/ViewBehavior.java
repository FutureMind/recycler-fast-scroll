package com.futuremind.recyclerviewfastscroll;

/**
 * Created by Michal on 11/08/16.
 */
public interface ViewBehavior {
    void onHandleGrabbed();
    void onHandleReleased();
    void onScrollStarted();
    void onScrollFinished();
}
