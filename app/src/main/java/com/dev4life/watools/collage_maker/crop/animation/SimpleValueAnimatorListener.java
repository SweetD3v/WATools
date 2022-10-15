package com.dev4life.watools.collage_maker.crop.animation;

public interface SimpleValueAnimatorListener {
  void onAnimationStarted();

  void onAnimationUpdated(float scale);

  void onAnimationFinished();
}
