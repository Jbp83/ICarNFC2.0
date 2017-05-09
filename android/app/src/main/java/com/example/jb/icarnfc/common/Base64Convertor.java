package com.example.jb.icarnfc.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
/**
 * Created by Mars on 09/05/2017.
 */



/*
 * Convertis/Déconvertis en Base64
  * Utilisé pour l'avatar, permet de sauvegarder
  * un tableau de bytes dans sharedprefs sous forme de String
 */
public class Base64Convertor {

    /*
     * Bitmap (bytearray) vers Base64
     */
    /*public static String encodeTobase64(Base64Convertor image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }*/

    /*
     * Base64 vers Bitmap
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
