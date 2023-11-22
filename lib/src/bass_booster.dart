import 'package:flutter/services.dart';

class BassBooster {
  final MethodChannel _channel;
  BassBooster(this._channel);

  Future<bool> getEnabled() async {
    try {
      return await _channel.invokeMethod('getBassBoosterEnabled');
    } catch(_) {
      return false;
    }
  }

  Future<void> setEnabled(bool isEnabled) async {
    try {
      await _channel.invokeMethod('setBassBoosterEnabled', isEnabled);
    } catch(_) {
      return;
    }
  }

  Future<int> getStrength() async {
    try {
      return await _channel.invokeMethod('getBassBoosterStrength');
    } catch(_) {
      return 0;
    }
  }

  Future<void> setStrength(int strength) async {
    if (strength >= 0 && strength <= 1000) {
      try {
        await _channel.invokeMethod('setBassBoosterStrength', strength);
      } catch(_) {
        return;
      }
    }
  }

  void release() {
    try {
      _channel.invokeMethod("releaseBassBooster");
    } catch(_) {
      return;
    }
  }

}