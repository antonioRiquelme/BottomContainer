package com.example.aorl.buttononbottom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for handler the different heights of the screen in all devices.
 * When you need to have a layout in the bottom of the window, but in the small window the bottom
 * layout is to overlap of the rest of the views of your layout, you must use this class.
 * You must use this structure:
 * Use {@link ConstraintLayout} how a parent view
 * Put the bottom layout in the bottom of the view
 * Put this class between the bottom layout and the last view of your content view.
 *
 * If there are enough space, the view will not do anything. But if the height of the device doesn't
 * have enough space, the view will be converted on a scrollView
 *
 * Example of layout:
 * <ConstraintLayout xmlns>
 *
 *     <LinearLayout
 *         android:id="@+id/content_view">
 *
 *         ...
 *
 *     </LinearLayout>
 *
 *     <DynamicSpace
 *         android:layout_height="0dp"
 *         app:layout_constraintBottom_toTopOf="@id/bottom_layout"
 *         app:layout_constraintTop_toBottomOf="@id/content_view" />
 *
 *     <LinearLayout
 *         android:id="@+id/bottom_layout"
 *         app:layout_constraintBottom_toBottomOf="parent">
 *
 *         ...
 *
 *     </LinearLayout>
 * </ConstraintLayout>
 */
public class DynamicSpace extends View {

  public static final String TAG = "DynamicSpace";

  public DynamicSpace(Context context) {
    super(context);
  }

  public DynamicSpace(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initialize(context);
  }

  private void initialize(final Context context) {
    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);

        if (getHeight() == 0) {
          Log.v(TAG, "you need a scrollView!");
          switchToScrollView(context);
        }
      }
    });
  }

  private void switchToScrollView(Context context) {
    ConstraintLayout parent;
    if (getParent() instanceof ConstraintLayout) {
      parent = (ConstraintLayout) getParent();
      LinearLayout linearLayout = new LinearLayout(context);
      linearLayout.setOrientation(LinearLayout.VERTICAL);

      NestedScrollView scrollView = new NestedScrollView(context);
      scrollView.addView(linearLayout);

      moveViewsToScrollView(parent, linearLayout, getLayoutViews(parent));

      parent.addView(scrollView);
    } else {
      Log.e(TAG, "You must use a ConstraintLayout how a parent view of DynamicSpace");
    }
  }

  @NonNull
  private List<View> getLayoutViews(ConstraintLayout parent) {
    List<View> viewList = new ArrayList<>(parent.getChildCount());
    for (int i = 0; i < parent.getChildCount(); i++) {
      View childAt = parent.getChildAt(i);
      viewList.add(childAt);
    }
    return viewList;
  }

  private void moveViewsToScrollView(ConstraintLayout parent, LinearLayout linearLayout, List<View> viewList) {
    parent.removeAllViews();

    for (int i = 0; i < viewList.size(); i++) {
      linearLayout.addView(viewList.get(i));
    }
  }
}
