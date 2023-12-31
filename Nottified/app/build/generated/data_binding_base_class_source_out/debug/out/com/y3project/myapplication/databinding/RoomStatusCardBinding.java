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

public final class RoomStatusCardBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ConstraintLayout aclay;

  @NonNull
  public final CardView cardView3;

  @NonNull
  public final ConstraintLayout idIVConstraintLayout;

  @NonNull
  public final TextView idTVCourseRating;

  @NonNull
  public final ImageView imageView38;

  @NonNull
  public final TextView lights;

  @NonNull
  public final ConstraintLayout lightslay;

  @NonNull
  public final TextView textView118;

  @NonNull
  public final TextView textView40;

  private RoomStatusCardBinding(@NonNull CardView rootView, @NonNull ConstraintLayout aclay,
      @NonNull CardView cardView3, @NonNull ConstraintLayout idIVConstraintLayout,
      @NonNull TextView idTVCourseRating, @NonNull ImageView imageView38, @NonNull TextView lights,
      @NonNull ConstraintLayout lightslay, @NonNull TextView textView118,
      @NonNull TextView textView40) {
    this.rootView = rootView;
    this.aclay = aclay;
    this.cardView3 = cardView3;
    this.idIVConstraintLayout = idIVConstraintLayout;
    this.idTVCourseRating = idTVCourseRating;
    this.imageView38 = imageView38;
    this.lights = lights;
    this.lightslay = lightslay;
    this.textView118 = textView118;
    this.textView40 = textView40;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static RoomStatusCardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RoomStatusCardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.room_status_card, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RoomStatusCardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.aclay;
      ConstraintLayout aclay = ViewBindings.findChildViewById(rootView, id);
      if (aclay == null) {
        break missingId;
      }

      id = R.id.cardView3;
      CardView cardView3 = ViewBindings.findChildViewById(rootView, id);
      if (cardView3 == null) {
        break missingId;
      }

      id = R.id.idIVConstraintLayout;
      ConstraintLayout idIVConstraintLayout = ViewBindings.findChildViewById(rootView, id);
      if (idIVConstraintLayout == null) {
        break missingId;
      }

      id = R.id.idTVCourseRating;
      TextView idTVCourseRating = ViewBindings.findChildViewById(rootView, id);
      if (idTVCourseRating == null) {
        break missingId;
      }

      id = R.id.imageView38;
      ImageView imageView38 = ViewBindings.findChildViewById(rootView, id);
      if (imageView38 == null) {
        break missingId;
      }

      id = R.id.lights;
      TextView lights = ViewBindings.findChildViewById(rootView, id);
      if (lights == null) {
        break missingId;
      }

      id = R.id.lightslay;
      ConstraintLayout lightslay = ViewBindings.findChildViewById(rootView, id);
      if (lightslay == null) {
        break missingId;
      }

      id = R.id.textView118;
      TextView textView118 = ViewBindings.findChildViewById(rootView, id);
      if (textView118 == null) {
        break missingId;
      }

      id = R.id.textView40;
      TextView textView40 = ViewBindings.findChildViewById(rootView, id);
      if (textView40 == null) {
        break missingId;
      }

      return new RoomStatusCardBinding((CardView) rootView, aclay, cardView3, idIVConstraintLayout,
          idTVCourseRating, imageView38, lights, lightslay, textView118, textView40);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
