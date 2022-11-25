// ILayerService.aidl
package com.dev4life.watools;

oneway interface ILayerService {

    void restart();

    void stop();

    void startSnapshot(long previewBytes);
}
