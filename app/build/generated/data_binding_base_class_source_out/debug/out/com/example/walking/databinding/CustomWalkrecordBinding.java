// Generated by view binder compiler. Do not edit!
package com.example.walking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.walking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomWalkrecordBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout customWalkrecord;

  @NonNull
  public final ListView customWalkrecordListview;

  private CustomWalkrecordBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout customWalkrecord, @NonNull ListView customWalkrecordListview) {
    this.rootView = rootView;
    this.customWalkrecord = customWalkrecord;
    this.customWalkrecordListview = customWalkrecordListview;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomWalkrecordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomWalkrecordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_walkrecord, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomWalkrecordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout customWalkrecord = (LinearLayout) rootView;

      id = R.id.custom_walkrecord_listview;
      ListView customWalkrecordListview = ViewBindings.findChildViewById(rootView, id);
      if (customWalkrecordListview == null) {
        break missingId;
      }

      return new CustomWalkrecordBinding((LinearLayout) rootView, customWalkrecord,
          customWalkrecordListview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
