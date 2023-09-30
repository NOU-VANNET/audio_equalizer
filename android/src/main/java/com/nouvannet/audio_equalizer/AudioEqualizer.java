package com.nouvannet.audio_equalizer;

import android.media.audiofx.Equalizer;

import java.util.ArrayList;

public class AudioEqualizer {
    private final Equalizer equalizer;

    public AudioEqualizer(int id) {
        this.equalizer = new Equalizer(0, id);
    }

    void setEnabled(boolean isEnabled) {
        equalizer.setEnabled(isEnabled);
    }

    boolean getEnabled() {
        return equalizer.getEnabled();
    }

    void release() {
        equalizer.release();
    }

    ArrayList<Integer> getBandsLevelRange() {
        short[] bandLevelRange = equalizer.getBandLevelRange();
        ArrayList<Integer> bandLevels = new ArrayList<>();
        bandLevels.add(bandLevelRange[0] / 100);
        bandLevels.add(bandLevelRange[1] / 100);
        return bandLevels;
    }

    int getBandLevel(int bandId) {
        return equalizer.getBandLevel((short)bandId) / 100;
    }

    void setBandLevel(int bandId, int level) {
        equalizer.setBandLevel((short)bandId, (short)level);
    }

    ArrayList<Integer> getCenterBandFreq() {
        int n = equalizer.getNumberOfBands();
        ArrayList<Integer> bands = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            bands.add(equalizer.getCenterFreq((short) i));
        }
        return bands;
    }

    ArrayList<String> getPresetNames() {
        short numberOfPresets = equalizer.getNumberOfPresets();
        ArrayList<String> presets = new ArrayList<>();
        for (int i = 0; i < numberOfPresets; i++) {
            presets.add(equalizer.getPresetName((short)i));
        }
        return presets;
    }

    void setPreset(String presetName) {
        equalizer.usePreset((short)getPresetNames().indexOf(presetName));
    }

}
