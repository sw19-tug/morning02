package com.gallery.android.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ShareContentActivity  extends Activity{



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("*/*");
        Intent share=Intent.createChooser(intent,"Share Using");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(share);



    }

}
