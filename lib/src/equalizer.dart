import 'package:flutter/services.dart';

enum ContentType { music, movie, game, voice }

class Equalizer {
  final MethodChannel _channel;
  Equalizer(this._channel);

  Future<void> setEnabled(bool isEnabled) async {
    try {
      await _channel.invokeMethod('setEQEnabled', isEnabled);
    } catch(_) {
      return;
    }
  }

  Future<bool> getEnabled() async {
    try {
      return await _channel.invokeMethod("getEQEnabled");
    } catch(_) {
      return false;
    }
  }

  Future<List<int>> getBandLevelRange() async {
    try {
      return (await _channel.invokeMethod('getEQBandLevelRange')).cast<int>();
    } catch(_) {
      return [];
    }
  }

  Future<int> getBandLevel(int bandId) async {
    try {
      return await _channel.invokeMethod('getEQBandLevel', bandId);
    } catch(_) {
      return 0;
    }
  }

  Future<void> setBandLevel(int bandId, int level) async {
    try {
      await _channel.invokeMethod(
        'setEQBandLevel',
        {'bandId': bandId, 'level': level * 100},
      );
    } catch(_) {
      return;
    }
  }

  Future<List<int>> getCenterBandFreq() async {
    try {
      return (await _channel.invokeMethod('getEQCenterBandFreq')).cast<int>();
    } catch(_) {
      return [];
    }
  }

  Future<List<String>> getPresetNames() async {
    try {
      return (await _channel.invokeMethod('getEQPresetNames')).cast<String>();
    } catch(_) {
      return [];
    }
  }

  Future<void> setPreset(String preset) async {
    try {
      await _channel.invokeMethod('setEQPreset', preset);
    } catch(_) {
      return;
    }
  }

  ///Return true if the device is supported.
  Future<bool> openDeviceEqualizerSettings([ContentType type = ContentType.music]) async {
    try  {
      return await _channel.invokeMethod("openDeviceEQ", type.index);
    } catch(_) {
      return false;
    }
  }

  Future<bool> getDeviceEqualizerSettingsSupported([ContentType type = ContentType.music]) async {
    try {
      return await _channel.invokeMethod("getDeviceEQSupported", type.index);
    } catch(_) {
      return false;
    }
  }

  void release() {
    try {
      _channel.invokeMethod("releaseEQ");
    } catch(_) {
      return;
    }
  }

}