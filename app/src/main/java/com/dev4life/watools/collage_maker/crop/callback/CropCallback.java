package com.dev4life.watools.collage_maker.crop.callback;

import android.graphics.Bitmap;


public interface CropCallback extends Callback {
  void onSuccess(Bitmap cropped);
}
