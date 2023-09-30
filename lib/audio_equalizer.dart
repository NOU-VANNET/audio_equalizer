import 'package:audio_equalizer/src/bass_booster.dart';
import 'package:audio_equalizer/src/equalizer.dart';
import 'package:flutter/services.dart';

class AudioEqualizer {
  static const _channel = MethodChannel("audio_equalizer");

  static Future<AudioEqualizer> initialize(int audioSessionId) async {
    return await _channel.invokeMethod('initialize', audioSessionId);
  }

  static final bassBooster = BassBooster(_channel);

  static final equalizer = Equalizer(_channel);
  
}