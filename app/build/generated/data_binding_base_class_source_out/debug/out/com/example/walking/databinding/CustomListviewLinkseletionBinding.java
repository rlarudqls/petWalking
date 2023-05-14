// Generated by view binder compiler. Do not edit!
package com.example.walking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.walking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomListviewLinkseletionBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout customLinkseletion;

  @NonNull
  public final TextView linkseletionItemNewlink;

  @NonNull
  public final TextView linkseletionItemReadylink;

  @NonNull
  public final CheckBox linkseletionNewlinkcheckbox;

  @NonNull
  public final CheckBox linkseletionReadylinkcheckbox;

  private CustomListviewLinkseletionBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout customLinkseletion, @NonNull TextView linkseletionItemNewlink,
      @NonNull TextView linkseletionItemReadylink, @NonNull CheckBox linkseletionNewlinkcheckbox,
      @NonNull CheckBox linkseletionReadylinkcheckbox) {
    this.rootView = rootView;
    this.customLinkseletion = customLinkseletion;
    this.linkseletionItemNewlink = linkseletionItemNewlink;
    this.linkseletionItemReadylink = linkseletionItemReadylink;
    this.linkseletionNewlinkcheckbox = linkseletionNewlinkcheckbox;
    this.linkseletionReadylinkcheckbox = linkseletionReadylinkcheckbox;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomListviewLinkseletionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomListviewLinkseletionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_listview_linkseletion, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomListviewLinkseletionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout customLinkseletion = (LinearLayout) rootView;

      id = R.id.linkseletion_item_newlink;
      TextView linkseletionItemNewlink = ViewBindings.findChildViewById(rootView, id);
      if (linkseletionItemNewlink == null) {
        break missingId;
      }

      id = R.id.linkseletion_item_readylink;
      TextView linkseletionItemReadylink = ViewBindings.findChildViewById(rootView, id);
      if (linkseletionItemReadylink == null) {
        break missingId;
      }

      id = R.id.linkseletion_newlinkcheckbox;
      CheckBox linkseletionNewlinkcheckbox = ViewBindings.findChildViewById(rootView, id);
      if (linkseletionNewlinkcheckbox == null) {
        break missingId;
      }

      id = R.id.linkseletion_readylinkcheckbox;
      CheckBox linkseletionReadylinkcheckbox = ViewBindings.findChildViewById(rootView, id);
      if (linkseletionReadylinkcheckbox == null) {
        break missingId;
      }

      return new CustomListviewLinkseletionBinding((LinearLayout) rootView, customLinkseletion,
          linkseletionItemNewlink, linkseletionItemReadylink, linkseletionNewlinkcheckbox,
          linkseletionReadylinkcheckbox);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}