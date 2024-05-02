// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.camera.features.videostabilization;

import android.annotation.SuppressLint;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import io.flutter.plugins.camera.CameraProperties;
import io.flutter.plugins.camera.features.CameraFeature;

/** Controls the video stabilization configuration on the {@see anddroid.hardware.camera2} API. */
public class VideoStabilizationFeature extends CameraFeature<VideoStabilizationMode> {
  @NonNull private VideoStabilizationMode currentSetting = VideoStabilizationMode.off;

  /**
   * Creates a new instance of the {@see VideoStabilizationFeature}.
   *
   * @param cameraProperties Collection of the characteristics for the current camera device.
   */
  public VideoStabilizationFeature(@NonNull CameraProperties cameraProperties) {
    super(cameraProperties);
  }

  @NonNull
  @Override
  public String getDebugName() {
    return "VideoStabilizationFeature";
  }

  @NonNull
  @SuppressLint("KotlinPropertyAccess")
  @Override
  public VideoStabilizationMode getValue() {
    return currentSetting;
  }

  @Override
  public void setValue(@NonNull VideoStabilizationMode value) {
    this.currentSetting = value;
  }

  @Override
  public boolean checkIsSupported() {
    int[] modes = cameraProperties.getControlVideoStabilizationAvailableModes();

    return modes.length != 0
            && (modes.length != 1 || modes[0] != CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF);
  }

  @Override
  public void updateBuilder(@NonNull CaptureRequest.Builder requestBuilder) {
    if (!checkIsSupported()) {
      return;
    }

    switch (currentSetting) {
      case on:
        requestBuilder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_ON);
        break;
      case previewStabilization:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          requestBuilder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_PREVIEW_STABILIZATION);
        }
        break;
      default:
        break;
    }
  }
}
