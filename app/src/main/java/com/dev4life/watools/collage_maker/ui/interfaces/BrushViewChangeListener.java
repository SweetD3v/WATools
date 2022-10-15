package com.dev4life.watools.collage_maker.ui.interfaces;

import com.dev4life.watools.tools.photoeditor.BrushDrawingView;

public interface BrushViewChangeListener {
    void onStartDrawing();

    void onStopDrawing();

    void onViewAdd(BrushDrawingView brushDrawingView);

    void onViewRemoved(BrushDrawingView brushDrawingView);
}
