package com.teamawsome.awsomeeat;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PictureHandler {

private static final PictureHandler pictureHandler = new PictureHandler();

private PictureHandler(){

}

public static PictureHandler getInstance(){
    return pictureHandler;
}

public void setPictureFromUrl(String url, ImageView imageView){
    Picasso.get().load(url).into(imageView);
}

}
