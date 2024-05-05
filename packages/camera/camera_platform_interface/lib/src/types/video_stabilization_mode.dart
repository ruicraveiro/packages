// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

/// The possible video stabilization modes that can be capturing video.
enum VideoStabilizationMode {
  /// Video stabilization is disabled.
  off,

  /// Video stabilization is enabled.
  on,

  /// Video stabilization is enabled and applied to preview.
  previewStabilization,
}

/// Returns the video stabilization mode as a String.
String serializeVideoStabilizationMode(
    VideoStabilizationMode videoStabilizationMode) {
  switch (videoStabilizationMode) {
    case VideoStabilizationMode.off:
      return 'off';
    case VideoStabilizationMode.on:
      return 'on';
    case VideoStabilizationMode.previewStabilization:
      return 'previewStabilization';
  }
}

/// Returns the video stabilization mode for a given String.
VideoStabilizationMode deserializeVideoStabilizationMode(String str) {
  switch (str) {
    case 'off':
      return VideoStabilizationMode.off;
    case 'on':
      return VideoStabilizationMode.on;
    case 'previewStabilization':
      return VideoStabilizationMode.previewStabilization;
    default:
      throw ArgumentError('"$str" is not a valid VideoStabilizationMode value');
  }
}
