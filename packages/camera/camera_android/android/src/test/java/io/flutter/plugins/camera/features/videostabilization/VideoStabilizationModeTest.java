// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.camera.features.videostabilization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import io.flutter.plugins.camera.features.autofocus.FocusMode;

public class VideoStabilizationModeTest {

  @Test
  public void getValueForString_returnsCorrectValues() {
    assertEquals(
        "Returns VideoStabilizationMode.off for 'off'", VideoStabilizationMode.getValueForString("off"), VideoStabilizationMode.off);
    assertEquals(
            "Returns VideoStabilizationMode.on for 'on'", VideoStabilizationMode.getValueForString("on"), VideoStabilizationMode.on);
    assertEquals(
            "Returns VideoStabilizationMode.previewStabilization for 'previewStabilization'", VideoStabilizationMode.getValueForString("previewStabilization"), VideoStabilizationMode.previewStabilization);
  }

  @Test
  public void getValueForString_returnsNullForNonexistantValue() {
      assertNull("Returns null for 'nonexistant'", VideoStabilizationMode.getValueForString("nonexistant"));
  }

  @Test
  public void toString_returnsCorrectValue() {
    assertEquals("Returns 'off' for VideoStabilizationMode.off", VideoStabilizationMode.off.toString(), "off");
    assertEquals("Returns 'on' for VideoStabilizationMode.on", VideoStabilizationMode.on.toString(), "on");
    assertEquals("Returns 'previewStabilization' for VideoStabilizationMode.previewStabilization", VideoStabilizationMode.previewStabilization.toString(), "previewStabilization");
  }
}
