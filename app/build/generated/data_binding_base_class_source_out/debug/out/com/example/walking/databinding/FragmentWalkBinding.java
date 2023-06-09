// Generated by view binder compiler. Do not edit!
package com.example.walking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.walking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentWalkBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout NewWalkingStart;

  @NonNull
  public final ConstraintLayout WalkingStart;

  @NonNull
  public final Button WkstartBtn;

  @NonNull
  public final Chronometer chronometer;

  @NonNull
  public final FrameLayout frameLayout;

  @NonNull
  public final Button newpeebutton;

  @NonNull
  public final Button newpoobutton;

  @NonNull
  public final Button newstopbutton;

  @NonNull
  public final Button pausebutton;

  @NonNull
  public final Button playbutton;

  @NonNull
  public final Button stopbutton;

  @NonNull
  public final TextView text56;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView walklenge;

  private FragmentWalkBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout NewWalkingStart, @NonNull ConstraintLayout WalkingStart,
      @NonNull Button WkstartBtn, @NonNull Chronometer chronometer,
      @NonNull FrameLayout frameLayout, @NonNull Button newpeebutton, @NonNull Button newpoobutton,
      @NonNull Button newstopbutton, @NonNull Button pausebutton, @NonNull Button playbutton,
      @NonNull Button stopbutton, @NonNull TextView text56, @NonNull TextView textView4,
      @NonNull TextView walklenge) {
    this.rootView = rootView;
    this.NewWalkingStart = NewWalkingStart;
    this.WalkingStart = WalkingStart;
    this.WkstartBtn = WkstartBtn;
    this.chronometer = chronometer;
    this.frameLayout = frameLayout;
    this.newpeebutton = newpeebutton;
    this.newpoobutton = newpoobutton;
    this.newstopbutton = newstopbutton;
    this.pausebutton = pausebutton;
    this.playbutton = playbutton;
    this.stopbutton = stopbutton;
    this.text56 = text56;
    this.textView4 = textView4;
    this.walklenge = walklenge;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentWalkBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentWalkBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_walk, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentWalkBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.NewWalkingStart;
      ConstraintLayout NewWalkingStart = ViewBindings.findChildViewById(rootView, id);
      if (NewWalkingStart == null) {
        break missingId;
      }

      id = R.id.WalkingStart;
      ConstraintLayout WalkingStart = ViewBindings.findChildViewById(rootView, id);
      if (WalkingStart == null) {
        break missingId;
      }

      id = R.id.WkstartBtn;
      Button WkstartBtn = ViewBindings.findChildViewById(rootView, id);
      if (WkstartBtn == null) {
        break missingId;
      }

      id = R.id.chronometer;
      Chronometer chronometer = ViewBindings.findChildViewById(rootView, id);
      if (chronometer == null) {
        break missingId;
      }

      id = R.id.frameLayout;
      FrameLayout frameLayout = ViewBindings.findChildViewById(rootView, id);
      if (frameLayout == null) {
        break missingId;
      }

      id = R.id.newpeebutton;
      Button newpeebutton = ViewBindings.findChildViewById(rootView, id);
      if (newpeebutton == null) {
        break missingId;
      }

      id = R.id.newpoobutton;
      Button newpoobutton = ViewBindings.findChildViewById(rootView, id);
      if (newpoobutton == null) {
        break missingId;
      }

      id = R.id.newstopbutton;
      Button newstopbutton = ViewBindings.findChildViewById(rootView, id);
      if (newstopbutton == null) {
        break missingId;
      }

      id = R.id.pausebutton;
      Button pausebutton = ViewBindings.findChildViewById(rootView, id);
      if (pausebutton == null) {
        break missingId;
      }

      id = R.id.playbutton;
      Button playbutton = ViewBindings.findChildViewById(rootView, id);
      if (playbutton == null) {
        break missingId;
      }

      id = R.id.stopbutton;
      Button stopbutton = ViewBindings.findChildViewById(rootView, id);
      if (stopbutton == null) {
        break missingId;
      }

      id = R.id.text56;
      TextView text56 = ViewBindings.findChildViewById(rootView, id);
      if (text56 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.walklenge;
      TextView walklenge = ViewBindings.findChildViewById(rootView, id);
      if (walklenge == null) {
        break missingId;
      }

      return new FragmentWalkBinding((ConstraintLayout) rootView, NewWalkingStart, WalkingStart,
          WkstartBtn, chronometer, frameLayout, newpeebutton, newpoobutton, newstopbutton,
          pausebutton, playbutton, stopbutton, text56, textView4, walklenge);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
