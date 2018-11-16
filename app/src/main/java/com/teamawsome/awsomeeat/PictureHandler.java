package com.teamawsome.awsomeeat;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PictureHandler {

public static void setPictureFromUrl(String url, ImageView imageView){

    Picasso.get().load(url).into(imageView);
}

}
