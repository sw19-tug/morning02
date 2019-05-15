package com.gallery.android.gallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;

public class MediaStoreDataLoader {

    private Context context;

    public MediaStoreDataLoader(Context context) {
        this.context = context;
    };

    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATE_MODIFIED,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.ORIENTATION,
            MediaStore.Images.ImageColumns.SIZE,
            MediaStore.Images.ImageColumns.HEIGHT,
            MediaStore.Images.ImageColumns.WIDTH,
    };

    public ArrayList<ImageContainer> parseAllImages() {
        rescanFolder(Environment.getExternalStorageDirectory().toString());
        try {

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_PROJECTION,
                    null,
                    null,
                    null
            );
            if (cursor == null)
                return null;

            int size = cursor.getCount();
            ArrayList<ImageContainer> image_list = new ArrayList<>();
            if (size > 0) {

                final int path_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                final int date_taken_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
                final int date_modified_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED);
                final int mime_type_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE);
                final int orientation_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION);
                final int size_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE);
                final int height_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT);
                final int width_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH);

                while (cursor.moveToNext()) {

                    String path = cursor.getString(path_col_num);
                    String file_name =  path.substring(path.lastIndexOf("/") + 1);
                    file_name = file_name.substring(0, file_name.lastIndexOf("."));
//                    Date date_modified = new Date(Long.parseLong(cursor.getString(date_modified_col_num)));
                    Date date_taken = new Date(Long.parseLong(cursor.getString(date_taken_col_num)));
                    long image_size = Long.parseLong(cursor.getString(size_col_num));
                    int height = Integer.parseInt(cursor.getString(height_col_num));
                    int width = Integer.parseInt(cursor.getString(width_col_num));
                    String orientation = cursor.getString(orientation_col_num);

                    Bitmap thumb_nail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path),
                            256, 256);



                    ImageContainer image_container = new ImageContainer(
                            thumb_nail,
                            path,
                            file_name,
                            date_taken,
                            height,
                            width,
                            image_size,
                            orientation);

                    image_list.add(image_container);
                }
            }
            cursor.close();
            return image_list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImageContainer parseImage(String path) {
        MediaScannerConnection.scanFile(context, new String[] { path }, null, null);
        try {
            Uri uri = Uri.parse("file://" + path);
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_PROJECTION,
                    MediaStore.Images.ImageColumns.DATA + " = \"" + path + "\"",
                    null,
                    null
            );
            if (cursor == null)
                return null;

            int size = cursor.getCount();
            ImageContainer image_container = null;
            if (size > 0) {

                final int path_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                final int date_taken_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
                final int date_modified_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED);
                final int mime_type_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.MIME_TYPE);
                final int orientation_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION);
                final int size_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE);
                final int height_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT);
                final int width_col_num = cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH);

                cursor.moveToNext();

                String file_path = cursor.getString(path_col_num);
                String file_name = path.substring(path.lastIndexOf("/") + 1);
                file_name = file_name.substring(0, file_name.lastIndexOf("."));
//                Date date_modified = new Date(Long.parseLong(cursor.getString(date_modified_col_num)));
                Date date_taken = new Date(Long.parseLong(cursor.getString(date_taken_col_num)));
                long image_size = Long.parseLong(cursor.getString(size_col_num));
                int height = Integer.parseInt(cursor.getString(height_col_num));
                int width = Integer.parseInt(cursor.getString(width_col_num));
                String orientation = cursor.getString(orientation_col_num);

                Bitmap thumb_nail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path),
                            256, 256);

                image_container = new ImageContainer(thumb_nail,
                        file_path, file_name, date_taken, height, width, image_size, orientation);

            }
            cursor.close();
            return image_container;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void rescanFolder(String dest) {
        // Scan files only (not folders);
        // https://stackoverflow.com/questions/13737261/nexus-4-not-showing-files-via-mtp/27321544#27321544

        File dir = new File(dest);
        File[] files = dir.listFiles();

        int file_counter = 0;
        for (int co=0; co<files.length; co++) {
            if (!files[co].isDirectory())
                file_counter++;
        }
        String[] paths = new String[file_counter];
        for (int co=0; co< files.length; co++) {
            if (!files[co].isDirectory()) {
                file_counter--;
                paths[file_counter] = files[co].getAbsolutePath();
            }
        }

        MediaScannerConnection.scanFile(context, paths, null, null);

        for (int co=0; co<files.length; co++) {
            if (files[co].isDirectory())
                rescanFolder(files[co].getAbsolutePath());
        }
    }
}
