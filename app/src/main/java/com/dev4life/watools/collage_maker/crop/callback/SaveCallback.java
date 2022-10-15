package com.dev4life.watools.collage_maker.crop.callback;

import android.net.Uri;


public interface SaveCallback extends Callback {
  void onSuccess(Uri uri);
}
