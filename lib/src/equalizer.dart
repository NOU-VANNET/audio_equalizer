import 'package:flutter/services.dart';

enum ContentType { music, movie, game, voice }

class Equalizer {
  final MethodChannel _channel;
  Equalizer(this._channel);

  Future<void> setEnabled(bool isEnabled) async {
    await _channel.invokeMethod('setEQEnabled', isEnabled);
  }

  Future<bool> getEnabled() async => await _channel.invokeMethod("getEQEnabled");

  Future<List<int>> getBandLevelRange() async => (await _channel.invokeMethod('getEQBandLevelRange')).cast<int>();

  Future<int> getBandLevel(int bandId) async => await _channel.invokeMethod('getEQBandLevel', bandId);

  Future<void> setBandLevel(int bandId, int level) async {
    await _channel.invokeMethod(
      'setEQBandLevel',
      {'bandId': bandId, 'level': level * 100},
    );
  }

  Future<List<int>> getCenterBandFreq() async => (await _channel.invokeMethod('getEQCenterBandFreq')).cast<int>();

  Future<List<String>> getPresetNames() async => (await _channel.invokeMethod('getEQPresetNames')).cast<String>();

  Future<void> setPreset(String preset) async => await _channel.invokeMethod('setEQPreset', preset);

  ///Return true if the device is supported.
  Future<bool> openDeviceEqualizerSettings([ContentType type = ContentType.music]) async => await _channel.invokeMethod("openDeviceEQ", type.index);

  Future<bool> getDeviceEqualizerSettingsSupported([ContentType type = ContentType.music]) async => await _channel.invokeMethod("getDeviceEQSupported", type.index);

  void release() => _channel.invokeMethod("releaseEQ");

}