// Generated by view binder compiler. Do not edit!
package com.example.walking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.walking.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class CustomListviewDogwalkcordBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout mypageDogAddItem;

  private CustomListviewDogwalkcordBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout mypageDogAddItem) {
    this.rootView = rootView;
    this.mypageDogAddItem = mypageDogAddItem;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomListviewDogwalkcordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomListviewDogwalkcordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_listview_dogwalkcord, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomListviewDogwalkcordBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    LinearLayout mypageDogAddItem = (LinearLayout) rootView;

    return new CustomListviewDogwalkcordBinding((LinearLayout) rootView, mypageDogAddItem);
  }
}