// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.camera.features.videostabilization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;

import org.junit.Test;

import io.flutter.plugins.camera.CameraProperties;

public class VideoStabilizationFeatureTest {
    private static final int[] VIDEO_STABILIZATION_MODES =
            new int[]{
                    CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF,
                    CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_ON,
                    CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_PREVIEW_STABILIZATION,
            };

    @Test
    public void getDebugName_shouldReturnTheNameOfTheFeature() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        assertEquals("VideoStabilizationFeature", videoStabilizationFeature.getDebugName());
    }

    @Test
    public void getValue_shouldReturnAutoIfNotSet() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        assertEquals(VideoStabilizationMode.off, videoStabilizationFeature.getValue());
    }

    @Test
    public void getValue_shouldEchoSetOn() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);
        VideoStabilizationMode expectedValue = VideoStabilizationMode.on;

        videoStabilizationFeature.setValue(expectedValue);
        VideoStabilizationMode actualValue = videoStabilizationFeature.getValue();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void getValue_shouldEchoSetPreviewStabilization() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);
        VideoStabilizationMode expectedValue = VideoStabilizationMode.previewStabilization;

        videoStabilizationFeature.setValue(expectedValue);
        VideoStabilizationMode actualValue = videoStabilizationFeature.getValue();

        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void checkIsSupported_shouldReturnFalseWhenNoAvailableMode() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(new int[]{});

        assertFalse(videoStabilizationFeature.checkIsSupported());
    }

    @Test
    public void checkIsSupported_shouldReturnFalseWhenOffIsOnlyAvailableMode() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(new int[]{
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF
        });

        assertFalse(videoStabilizationFeature.checkIsSupported());
    }

    @Test
    public void checkIsSupported_shouldReturnTrueWhenOnAvailableMode() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(new int[]{
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF,
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_ON,
        });

        assertTrue(videoStabilizationFeature.checkIsSupported());
    }

    @Test
    public void checkIsSupported_shouldReturnTrueWhenPreviewAvailableModes() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(new int[]{
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF,
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_ON,
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_PREVIEW_STABILIZATION,
        });

        assertTrue(videoStabilizationFeature.checkIsSupported());
    }


    @Test
    public void updateBuilderShouldReturnWhenCheckIsSupportedIsFalse() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        CaptureRequest.Builder mockBuilder = mock(CaptureRequest.Builder.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(new int[]{
                CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF
        });

        videoStabilizationFeature.updateBuilder(mockBuilder);

        verify(mockBuilder, never()).set(any(), any());
    }

    @Test
    public void updateBuilder_shouldSetControlModeToOn() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        CaptureRequest.Builder mockBuilder = mock(CaptureRequest.Builder.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(VIDEO_STABILIZATION_MODES);

        videoStabilizationFeature.setValue(VideoStabilizationMode.on);
        videoStabilizationFeature.updateBuilder(mockBuilder);

        verify(mockBuilder, times(1))
                .set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_ON);
    }


    @Test
    public void updateBuilder_shouldSetControlModeToPreviewStabilization() {
        CameraProperties mockCameraProperties = mock(CameraProperties.class);
        CaptureRequest.Builder mockBuilder = mock(CaptureRequest.Builder.class);
        VideoStabilizationFeature videoStabilizationFeature = new VideoStabilizationFeature(mockCameraProperties);

        when(mockCameraProperties.getControlVideoStabilizationAvailableModes()).thenReturn(VIDEO_STABILIZATION_MODES);

        videoStabilizationFeature.setValue(VideoStabilizationMode.previewStabilization);
        videoStabilizationFeature.updateBuilder(mockBuilder);

        // we are checking that in version 33 and above this changes the video stabilization mode to preview, but in earlier versions it's a no-op.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            verify(mockBuilder, times(1))
                    .set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_PREVIEW_STABILIZATION);
        } else {
            verify(mockBuilder, never()).set(any(), any());
        }
    }

}
