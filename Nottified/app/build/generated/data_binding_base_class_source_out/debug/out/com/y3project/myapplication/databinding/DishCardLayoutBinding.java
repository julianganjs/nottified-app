// Generated by view binder compiler. Do not edit!
package com.y3project.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.y3project.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DishCardLayoutBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ConstraintLayout idIVConstraintLayout;

  @NonNull
  public final ImageView idIVCourseImage;

  @NonNull
  public final TextView idTVCourseName;

  @NonNull
  public final TextView idTVCourseRating;

  private DishCardLayoutBinding(@NonNull CardView rootView,
      @NonNull ConstraintLayout idIVConstraintLayout, @NonNull ImageView idIVCourseImage,
      @NonNull TextView idTVCourseName, @NonNull TextView idTVCourseRating) {
    this.rootView = rootView;
    this.idIVConstraintLayout = idIVConstraintLayout;
    this.idIVCourseImage = idIVCourseImage;
    this.idTVCourseName = idTVCourseName;
    this.idTVCourseRating = idTVCourseRating;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DishCardLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DishCardLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dish_card_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DishCardLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.idIVConstraintLayout;
      ConstraintLayout idIVConstraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (idIVConstraintLayout == null) {
        break missingId;
      }

      id = R.id.idIVCourseImage;
      ImageView idIVCourseImage = ViewBindings.findChildViewById(rootView, id);
      if (idIVCourseImage == null) {
        break missingId;
      }

      id = R.id.idTVCourseName;
      TextView idTVCourseName = ViewBindings.findChildViewById(rootView, id);
      if (idTVCourseName == null) {
        break missingId;
      }

      id = R.id.idTVCourseRating;
      TextView idTVCourseRating = ViewBindings.findChildViewById(rootView, id);
      if (idTVCourseRating == null) {
        break missingId;
      }

      return new DishCardLayoutBinding((CardView) rootView, idIVConstraintLayout, idIVCourseImage,
          idTVCourseName, idTVCourseRating);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
