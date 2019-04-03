package com.gallery.android.gallery;

import android.support.v7.widget.RecyclerView;

public class MyRunnable implements  Runnable{

        public RecyclerView resycler_view;

        int adapter_position;

        public MyRunnable(RecyclerView resycler_view, int adapter_position) {
            this.resycler_view  = resycler_view;
            this.adapter_position = adapter_position;
        }

        @Override
        public void run() {

        }
    }


