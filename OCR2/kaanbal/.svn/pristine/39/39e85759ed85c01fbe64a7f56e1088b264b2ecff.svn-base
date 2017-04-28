package com.proyectos.efrain.ocr.Util;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by Efrain on 12/04/2017.
 */

public class MyTessOCR {
    private final TessBaseAPI mTess;

    public MyTessOCR(Context context, String language) {
        mTess = new TessBaseAPI();
        String datapath = context.getFilesDir() + "/tesseract/"; mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap); return mTess.getUTF8Text();
    }

    public void onDestroy() {
        if (mTess != null) mTess.end();
    }
}