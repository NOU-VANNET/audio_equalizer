import 'package:audio_equalizer/src/bass_booster.dart';
import 'package:audio_equalizer/src/equalizer.dart';
import 'package:flutter/services.dart';

class AudioEqualizer {
  static const _channel = MethodChannel("nouvannet.com/flutter-packages/audio_equalizer");

  static Future<void> initialize(int audioSessionId) async {
    try {
      await _channel.invokeMethod('initialize', audioSessionId);
    } catch(_) {
      return;
    }
  }

  static final bassBooster = BassBooster(_channel);

  static final equalizer = Equalizer(_channel);
  
}