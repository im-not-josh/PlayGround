package nz.co.fendustries.playground.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BitmapUtils {
    static int ROTATE_90 = 90;
    static int ROTATE_180 = 180;
    static int ROTATE_270 = 270;

    /**
     * Takes a bitmap, checks the orientation and rotates it accordingly
     *
     * @param bitmap   bitmap to be rotated based on the current orientation
     * @param filePath filepath where the image is currently stored
     * @return Bitmap with orientation 0
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static Bitmap rotateImage(Bitmap bitmap, String filePath) throws IOException, IllegalArgumentException {
        Bitmap resultBitmap;

        ExifInterface exifInterface = new ExifInterface(filePath);
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(ROTATE_90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(ROTATE_180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(ROTATE_270);
                break;
            default:
                return bitmap;
        }

        // Rotate the bitmap
        resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resultBitmap;
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormatSymbols fts = new DecimalFormatSymbols(Locale.ENGLISH);
        DecimalFormat twoDForm = new DecimalFormat("#.##", fts);
        return Double.valueOf(twoDForm.format(d));
    }

    //compress image according to size_limit and available memory for decoding

    // downSample the bitmap from file due to memory limitation
    public static Bitmap downSampleBitmapFromFilePath(String filePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(
                filePath, opt);
        int originalWidth = opt.outWidth;
        int originalHeight = opt.outHeight;
        opt.inSampleSize = (int) Math.max(
                Math.pow(2, Math.ceil(Math.log(getScaleFactor(BitmapStrings.MEMORY, originalWidth * originalHeight)) / Math.log(2))),
                getScaleFactor(BitmapStrings.SIZE, originalWidth * originalHeight)
        );
        opt.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, opt);
        return bitmap;
    }

    //get suitable bitmap scale factor due to memory capacity
    private static double getScaleFactor(String scaleFactor, double pixelNum) {
        //use 4 byte per pixel and make sure it will not exceed available memory if it's not down sampled
        // calculate the scale factor given the available memory
        // use 75% of max available memory
        double factor = 0;
        if (scaleFactor.equalsIgnoreCase(BitmapStrings.MEMORY)) {
            double availableMemory = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory();
            factor = Math.sqrt(pixelNum / (availableMemory * 0.75 / 4));
        } else if (scaleFactor.equalsIgnoreCase(BitmapStrings.SIZE)) {
            factor = Math.sqrt(pixelNum / 6000000);
        }

        if (factor < 1) {
            factor = 1.0;
        }
        return factor;
    }

    // Scale and maintain aspect ratio given a desired width
    public static Bitmap scaleToFitWidth(Bitmap b, int width) {
        float factor = width / (float) b.getWidth();
        return Bitmap.createScaledBitmap(b, width,
                (int) (b.getHeight() * factor), true);
    }

    // Scale and maintain aspect ratio given a desired height
    private static Bitmap scaleToFitHeight(Bitmap b, int height) {
        float factor = height / (float) b.getHeight();
        return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor),
                height, true);
    }

    public static Bitmap decodeBitmapAndScale(String photoPath, Context mContext) {
        Bitmap photoBitmap = null;
        try {
            // creating a bitmap from the image file
            photoBitmap = downSampleBitmapFromFilePath(photoPath);
            if (photoBitmap == null) {
                return null;
            }
            // Resize a Bitmap maintaining aspect ratio
            int screenHeight = 1000;
            int screenWidth = 250;


            photoBitmap = rotateImage(photoBitmap, photoPath);
            if (photoBitmap.getHeight() > photoBitmap.getWidth()) {
                photoBitmap = scaleToFitHeight(photoBitmap, screenHeight);
            } else {
                photoBitmap = scaleToFitWidth(photoBitmap, screenWidth);
            }
            return photoBitmap;
        } catch (Exception e) {
        } catch (OutOfMemoryError e) {
        }
        return null;
    }

    private interface BitmapStrings {
        String MEMORY = "Memory";
        String SIZE = "Size";
    }
}
