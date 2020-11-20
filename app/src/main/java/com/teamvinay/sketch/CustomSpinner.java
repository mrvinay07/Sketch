package com.teamvinay.sketch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Spinner;

public class CustomSpinner extends Spinner {
    private static final String TAG = "CustomSpinner";
    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;

    public interface OnSpinnerEventsListener {
        void onSpinnerClosed();

        void onSpinnerOpened();
    }

    public CustomSpinner(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public CustomSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CustomSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomSpinner(Context context, int i) {
        super(context, i);
    }

    public CustomSpinner(Context context) {
        super(context);
    }

    public boolean performClick() {
        this.mOpenInitiated = true;
        if (this.mListener != null) {
            this.mListener.onSpinnerOpened();
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener) {
        this.mListener = onSpinnerEventsListener;
    }

    public void performClosedEvent() {
        this.mOpenInitiated = false;
        if (this.mListener != null) {
            this.mListener.onSpinnerClosed();
        }
    }

    public boolean hasBeenOpened() {
        return this.mOpenInitiated;
    }

    public void onWindowFocusChanged(boolean z) {
        Log.d(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(z);
        if (hasBeenOpened() && z) {
            Log.i(TAG, "closing popup");
            performClosedEvent();
        }
    }
}
