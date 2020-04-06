package com.project.movice.modules.drawname.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.print.PrintAttributes;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class BitmapUtils {




//bitmap转换成pdf
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File saveBitmapForPdf(List<Bitmap> bitmaps, String appDir, String name) {
        PdfDocument doc = new PdfDocument();
        int pageWidth = PrintAttributes.MediaSize.ISO_A10.getWidthMils() * 72 / 1000;
        float scale = (float) pageWidth / (float) bitmaps.get(0).getWidth();
        int pageHeight = (int) (bitmaps.get(0).getHeight() * scale);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < bitmaps.size(); i++) {
            PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create();
            PdfDocument.Page page = doc.startPage(newPage);
            Canvas canvas = page.getCanvas();
            canvas.drawBitmap(bitmaps.get(i), matrix, paint);
            doc.finishPage(page);
        }
        File file = new File(appDir, name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            doc.writeTo(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            doc.close();
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
