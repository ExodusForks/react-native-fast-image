package com.dylanvann.fastimage.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;

public class FastImageProgressEvent extends Event<FastImageProgressEvent> {

    private final long mBytesRead;
    private final long mExpectedLength;

    public FastImageProgressEvent(int surfaceId, int viewTag, long bytesRead, long expectedLength) {
        super(surfaceId, viewTag);
        this.mBytesRead = bytesRead;
        this.mExpectedLength = expectedLength;
    }

    @NonNull
    @Override
    public String getEventName() {
        return "onFastImageProgress";
    }

    @Override
    protected WritableMap getEventData() {
        WritableMap eventData = Arguments.createMap();
        // Use doubles to avoid overflow: RN bridges numbers as doubles in JS.
        eventData.putDouble("loaded", (double) mBytesRead);
        if (mExpectedLength >= 0) {
            eventData.putDouble("total", (double) mExpectedLength);
        } else {
            // Unknown content-length
            eventData.putNull("total");
        }
        return eventData;
    }

}
