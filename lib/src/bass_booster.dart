import 'package:flutter/services.dart';

class BassBooster {
  final MethodChannel _channel;
  BassBooster(this._channel);

  Future<bool> getEnabled() async => await _channel.invokeMethod('getBassBoosterEnabled');

  Future<void> setEnabled(bool isEnabled) async {
    await _channel.invokeMethod('setBassBoosterEnabled', isEnabled);
  }

  Future<int> getStrength() async => await _channel.invokeMethod('getBassBoosterStrength');

  Future<void> setStrength(int strength) async {
    if (strength >= 0 && strength <= 1000) {
      await _channel.invokeMethod('setBassBoosterStrength', strength);
    }
  }

  void release() => _channel.invokeMethod("releaseBassBooster");

}