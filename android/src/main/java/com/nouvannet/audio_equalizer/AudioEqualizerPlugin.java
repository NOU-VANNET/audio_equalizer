package com.nouvannet.audio_equalizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** AudioEqualizerPlugin */
public class AudioEqualizerPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  private MethodChannel channel;

  private boolean isInitialized = false;

  BassBooster bassBooster;
  AudioEqualizer audioEqualizer;
  private int audioSessionId;

  private Context applicationContext;
  private Activity activity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "audio_equalizer");
    channel.setMethodCallHandler(this);
    this.applicationContext = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (isMethod(call, "initialize")) {
      int sessionId = (int)call.arguments;
      initialize(sessionId);
    } else {
      if (!isInitialized) {
        result.error("Audio Equalizer",
                "You must call initialize method first!",
                ""
                );
      } else {
        if (isMethod(call,"setBassBoosterEnabled")) {
          boolean isEnabled = (boolean)call.arguments;
          bassBooster.setEnabled(isEnabled);
        } else if (isMethod(call,"getBassBoosterEnabled")) {
          result.success(bassBooster.getEnabled());
        } else if (isMethod(call,"getBassBoosterStrength")) {
          result.success(bassBooster.getStrength());
        } else if (isMethod(call,"setBassBoosterStrength")) {
          int val = (int)call.arguments;
          short strength = ((short) val);
          bassBooster.setStrength(strength);
        } else if (isMethod(call, "releaseBassBooster")) {
          bassBooster.release();
        } else if (isMethod(call,"setEQEnabled")) {
          boolean isEnabled = (boolean)call.arguments;
          audioEqualizer.setEnabled(isEnabled);
        } else if (isMethod(call, "getEQEnabled")) {
          result.success(audioEqualizer.getEnabled());
        } else if (isMethod(call, "getEQBandLevelRange")) {
          result.success(audioEqualizer.getBandsLevelRange());
        } else if (isMethod(call, "getEQBandLevel")) {
          int bandId = (int)call.arguments;
          result.success(audioEqualizer.getBandLevel(bandId));
        } else if (isMethod(call, "setEQBandLevel")) {
          int bandId = (int)call.argument("bandId");
          int level = (int)call.argument("level");
          audioEqualizer.setBandLevel(bandId, level);
        } else if (isMethod(call, "getEQCenterBandFreq")) {
          result.success(audioEqualizer.getCenterBandFreq());
        } else if (isMethod(call, "getEQPresetNames")) {
          result.success(audioEqualizer.getPresetNames());
        } else if (isMethod(call, "setEQPreset")) {
          String preset = (String)call.arguments;
          audioEqualizer.setPreset(preset);
        } else if (isMethod(call, "openDeviceEQ")) {
          int contentType = (int)call.arguments;
          openDeviceEqualizerSettings(contentType, result);
        } else if (isMethod(call, "getDeviceEQSupported")) {
          int contentType = (int)call.arguments;
          result.success(getDeviceEQSupported(contentType));
        } else if (isMethod(call,"releaseEQ")) {
          audioEqualizer.release();
        } else {
          result.notImplemented();
        }
      }
    }
  }

  boolean isMethod(MethodCall call, String method) {
    return call.method.equals(method);
  }

  private void initialize(int id) {
    this.audioSessionId = id;
    this.bassBooster = new BassBooster(id);
    this.audioEqualizer = new AudioEqualizer(id);
    this.isInitialized = true;
  }

  boolean getDeviceEQSupported(int contentType) {
    Intent intent = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
    intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, applicationContext.getPackageName());
    intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, audioSessionId);
    intent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, contentType);
    if ((intent.resolveActivity(applicationContext.getPackageManager()) != null)) {
      return true;
    } else {
      return false;
    }
  }

  void openDeviceEqualizerSettings(int contentType, Result result) {
    Intent intent = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
    intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, applicationContext.getPackageName());
    intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, audioSessionId);
    intent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, contentType);
    if ((intent.resolveActivity(applicationContext.getPackageManager()) != null)) {
      activity.startActivityForResult(intent, 0);
      result.success(true);
    } else {
      result.success(false);
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }
}
