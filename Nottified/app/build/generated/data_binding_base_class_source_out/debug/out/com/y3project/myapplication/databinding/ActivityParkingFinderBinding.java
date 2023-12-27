// Generated by view binder compiler. Do not edit!
package com.y3project.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.y3project.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityParkingFinderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ProgressBar emptyParking;

  @NonNull
  public final ImageView zone1circle;

  @NonNull
  public final TextView zone1empty;

  @NonNull
  public final ImageButton zone1parking;

  @NonNull
  public final ImageView zone2circle;

  @NonNull
  public final TextView zone2empty;

  @NonNull
  public final ImageButton zone2parking;

  @NonNull
  public final ImageView zone3circle;

  @NonNull
  public final TextView zone3empty;

  @NonNull
  public final ImageButton zone3parking;

  private ActivityParkingFinderBinding(@NonNull LinearLayout rootView,
      @NonNull ProgressBar emptyParking, @NonNull ImageView zone1circle,
      @NonNull TextView zone1empty, @NonNull ImageButton zone1parking,
      @NonNull ImageView zone2circle, @NonNull TextView zone2empty,
      @NonNull ImageButton zone2parking, @NonNull ImageView zone3circle,
      @NonNull TextView zone3empty, @NonNull ImageButton zone3parking) {
    this.rootView = rootView;
    this.emptyParking = emptyParking;
    this.zone1circle = zone1circle;
    this.zone1empty = zone1empty;
    this.zone1parking = zone1parking;
    this.zone2circle = zone2circle;
    this.zone2empty = zone2empty;
    this.zone2parking = zone2parking;
    this.zone3circle = zone3circle;
    this.zone3empty = zone3empty;
    this.zone3parking = zone3parking;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityParkingFinderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityParkingFinderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_parking_finder, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityParkingFinderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.empty_parking;
      ProgressBar emptyParking = ViewBindings.findChildViewById(rootView, id);
      if (emptyParking == null) {
        break missingId;
      }

      id = R.id.zone1circle;
      ImageView zone1circle = ViewBindings.findChildViewById(rootView, id);
      if (zone1circle == null) {
        break missingId;
      }

      id = R.id.zone1empty;
      TextView zone1empty = ViewBindings.findChildViewById(rootView, id);
      if (zone1empty == null) {
        break missingId;
      }

      id = R.id.zone1parking;
      ImageButton zone1parking = ViewBindings.findChildViewById(rootView, id);
      if (zone1parking == null) {
        break missingId;
      }

      id = R.id.zone2circle;
      ImageView zone2circle = ViewBindings.findChildViewById(rootView, id);
      if (zone2circle == null) {
        break missingId;
      }

      id = R.id.zone2empty;
      TextView zone2empty = ViewBindings.findChildViewById(rootView, id);
      if (zone2empty == null) {
        break missingId;
      }

      id = R.id.zone2parking;
      ImageButton zone2parking = ViewBindings.findChildViewById(rootView, id);
      if (zone2parking == null) {
        break missingId;
      }

      id = R.id.zone3circle;
      ImageView zone3circle = ViewBindings.findChildViewById(rootView, id);
      if (zone3circle == null) {
        break missingId;
      }

      id = R.id.zone3empty;
      TextView zone3empty = ViewBindings.findChildViewById(rootView, id);
      if (zone3empty == null) {
        break missingId;
      }

      id = R.id.zone3parking;
      ImageButton zone3parking = ViewBindings.findChildViewById(rootView, id);
      if (zone3parking == null) {
        break missingId;
      }

      return new ActivityParkingFinderBinding((LinearLayout) rootView, emptyParking, zone1circle,
          zone1empty, zone1parking, zone2circle, zone2empty, zone2parking, zone3circle, zone3empty,
          zone3parking);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}