/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.systemui.statusbar.notification.stack;

import static com.android.systemui.statusbar.notification.ActivityLaunchAnimator
        .ExpandAnimationParameters;

import android.view.View;
import android.view.ViewGroup;

import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.NotificationData;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/**
 * Interface representing the entity that contains notifications. It can have
 * notification views added and removed from it, and will manage displaying them to the user.
 */
public interface NotificationListContainer extends ExpandableView.OnHeightChangedListener,
        VisibilityLocationProvider {

    /**
     * Called when a child is being transferred.
     *
     * @param childTransferInProgress whether child transfer is in progress
     */
    void setChildTransferInProgress(boolean childTransferInProgress);

    /**
     * Change the position of child to a new location
     *
     * @param child the view to change the position for
     * @param newIndex the new index
     */
    void changeViewPosition(View child, int newIndex);

    /**
     * Called when a child was added to a group.
     *
     * @param row row of the group child that was added
     */
    void notifyGroupChildAdded(View row);

    /**
     * Called when a child was removed from a group.
     *
     * @param row row of the child that was removed
     * @param childrenContainer ViewGroup of the group that the child was removed from
     */
    void notifyGroupChildRemoved(View row, ViewGroup childrenContainer);

    /**
     * Generate an animation for an added child view.
     *
     * @param child The view to be added.
     * @param fromMoreCard Whether this add is coming from the "more" card on lockscreen.
     */
    void generateAddAnimation(View child, boolean fromMoreCard);

    /**
     * Generate a child order changed event.
     */
    void generateChildOrderChangedEvent();

    /**
     * Returns the number of children in the NotificationListContainer.
     *
     * @return the number of children in the NotificationListContainer
     */
    int getContainerChildCount();

    /**
     * Gets the ith child in the NotificationListContainer.
     *
     * @param i ith child to get
     * @return the ith child in the list container
     */
    View getContainerChildAt(int i);

    /**
     * Remove a view from the container
     *
     * @param v view to remove
     */
    void removeContainerView(View v);

    /**
     * Add a view to the container
     *
     * @param v view to add
     */
    void addContainerView(View v);

    /**
     * Sets the maximum number of notifications to display.
     *
     * @param maxNotifications max number of notifications to display
     */
    void setMaxDisplayedNotifications(int maxNotifications);

    /**
     * Handle snapping a non-dismissable row back if the user tried to dismiss it.
     *
     * @param entry the entry whose row needs to snap back
     */
    void snapViewIfNeeded(NotificationData.Entry entry);

    /**
     * Get the view parent for a notification entry. For example, NotificationStackScrollLayout.
     *
     * @param entry entry to get the view parent for
     * @return the view parent for entry
     */
    ViewGroup getViewParentForNotification(NotificationData.Entry entry);

    /**
     * Resets the currently exposed menu view.
     *
     * @param animate whether to animate the closing/change of menu view
     * @param force reset the menu view even if it looks like it is already reset
     */
    void resetExposedMenuView(boolean animate, boolean force);

    /**
     * Returns the NotificationSwipeActionHelper for the NotificationListContainer.
     *
     * @return swipe action helper for the list container
     */
    NotificationSwipeActionHelper getSwipeActionHelper();

    /**
     * Called when a notification is removed from the shade. This cleans up the state for a
     * given view.
     *
     * @param entry the entry whose view's view state needs to be cleaned up (say that 5 times fast)
     */
    void cleanUpViewStateForEntry(NotificationData.Entry entry);


    /**
     * Sets a listener to listen for changes in notification locations.
     *
     * @param listener listener to set
     */
    void setChildLocationsChangedListener(
            NotificationLogger.OnChildLocationsChangedListener listener);

    /**
     * Called when an update to the notification view hierarchy is completed.
     */
    default void onNotificationViewUpdateFinished() {}

    /**
     * Returns true if there are pulsing notifications.
     *
     * @return true if has pulsing notifications
     */
    boolean hasPulsingNotifications();

    /**
     * Apply parameters of the expand animation to the layout
     */
    default void applyExpandAnimationParams(ExpandAnimationParameters params) {}

    default void setExpandingNotification(ExpandableNotificationRow row) {}

    /**
     * Bind a newly created row.
     *
     * @param row The notification to bind.
     */
    default void bindRow(ExpandableNotificationRow row) {}
}
