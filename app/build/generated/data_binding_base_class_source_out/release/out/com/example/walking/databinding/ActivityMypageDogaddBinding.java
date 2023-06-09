// Generated by view binder compiler. Do not edit!
package com.example.walking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.walking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMypageDogaddBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final EditText mypageDogAddDogBirthDay;

  @NonNull
  public final EditText mypageDogAddDogBirthMonth;

  @NonNull
  public final EditText mypageDogAddDogBirthYear;

  @NonNull
  public final EditText mypageDogAddDogSex;

  @NonNull
  public final EditText mypageDogAddDogWeight;

  @NonNull
  public final EditText mypageDogAddName;

  @NonNull
  public final AppCompatButton mypageDogAddSub;

  @NonNull
  public final AutoCompleteTextView mypageDogSpecies;

  private ActivityMypageDogaddBinding(@NonNull FrameLayout rootView,
      @NonNull EditText mypageDogAddDogBirthDay, @NonNull EditText mypageDogAddDogBirthMonth,
      @NonNull EditText mypageDogAddDogBirthYear, @NonNull EditText mypageDogAddDogSex,
      @NonNull EditText mypageDogAddDogWeight, @NonNull EditText mypageDogAddName,
      @NonNull AppCompatButton mypageDogAddSub, @NonNull AutoCompleteTextView mypageDogSpecies) {
    this.rootView = rootView;
    this.mypageDogAddDogBirthDay = mypageDogAddDogBirthDay;
    this.mypageDogAddDogBirthMonth = mypageDogAddDogBirthMonth;
    this.mypageDogAddDogBirthYear = mypageDogAddDogBirthYear;
    this.mypageDogAddDogSex = mypageDogAddDogSex;
    this.mypageDogAddDogWeight = mypageDogAddDogWeight;
    this.mypageDogAddName = mypageDogAddName;
    this.mypageDogAddSub = mypageDogAddSub;
    this.mypageDogSpecies = mypageDogSpecies;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMypageDogaddBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMypageDogaddBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_mypage_dogadd, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMypageDogaddBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.mypage_DogAdd_DogBirthDay;
      EditText mypageDogAddDogBirthDay = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddDogBirthDay == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_DogBirthMonth;
      EditText mypageDogAddDogBirthMonth = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddDogBirthMonth == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_DogBirthYear;
      EditText mypageDogAddDogBirthYear = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddDogBirthYear == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_DogSex;
      EditText mypageDogAddDogSex = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddDogSex == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_DogWeight;
      EditText mypageDogAddDogWeight = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddDogWeight == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_Name;
      EditText mypageDogAddName = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddName == null) {
        break missingId;
      }

      id = R.id.mypage_DogAdd_sub;
      AppCompatButton mypageDogAddSub = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogAddSub == null) {
        break missingId;
      }

      id = R.id.mypage_DogSpecies;
      AutoCompleteTextView mypageDogSpecies = ViewBindings.findChildViewById(rootView, id);
      if (mypageDogSpecies == null) {
        break missingId;
      }

      return new ActivityMypageDogaddBinding((FrameLayout) rootView, mypageDogAddDogBirthDay,
          mypageDogAddDogBirthMonth, mypageDogAddDogBirthYear, mypageDogAddDogSex,
          mypageDogAddDogWeight, mypageDogAddName, mypageDogAddSub, mypageDogSpecies);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
