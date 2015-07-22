package com.fei.musicclient.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Fei on 03/06/2015.
 */
public class BitmapUtils {

    public static Bitmap loadBitmap (byte[] bytes,int width,int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        //only load bound information? bound information is regarding property of the picture
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        //to get the picture's original width and height
        int w = options.outWidth/width;
        int h = options.outHeight/height;
        int scale = w>h?w:h;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

    }

    public static Bitmap loadBitmap (String path) {
        File file = new File(path);
        if (file.exists()) {
            return BitmapFactory.decodeFile(path);
        }
        return null;

    }

    public static void savePic(File targetFile, Bitmap bitmap) throws IOException {

        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        } else if (!targetFile.exists()) {
            targetFile.createNewFile();
        } else {
            FileOutputStream fos = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }
    }

}
