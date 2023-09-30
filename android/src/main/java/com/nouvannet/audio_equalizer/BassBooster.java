package com.nouvannet.audio_equalizer;

import android.media.audiofx.BassBoost;

public class BassBooster {
    final private BassBoost bassBoost;

    public BassBooster(int audioSessionId) {
        this.bassBoost = new BassBoost(0, audioSessionId);
    }

    void setEnabled(boolean isEnabled) {
        bassBoost.setEnabled(isEnabled);
    }

    boolean getEnabled() {
        return bassBoost.getEnabled();
    }

    void setStrength(short strength) {
        bassBoost.setStrength(strength);
    }

    short getStrength() {
        return bassBoost.getRoundedStrength();
    }

    void release() {
        bassBoost.release();
    }

}
