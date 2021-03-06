package com.example.dogbreed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareDogData {



    public static void shareDogInfo(Activity activity) throws IOException {

        View view = activity.findViewById(R.id.rootView);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight() * 2,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);


        // Save Bitmap as JPG
        String imageFileName = "JPEG_SHARE_IMAGE_DOG.jpg";
        File imageDogShare = new File(activity.getExternalCacheDir(),imageFileName);
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,bos);
        byte[] bitmapdata = bos.toByteArray();
        //Write the bytes in file
        FileOutputStream fos = new FileOutputStream(imageDogShare);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        Uri shareImageURI = FileProvider.getUriForFile(activity, "com.rtv313.dogdeck.fileprovider", imageDogShare);
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, shareImageURI);
        intent.setType("image/jpg");
        activity.startActivity(Intent.createChooser(intent, "Share image via"));
    }
}