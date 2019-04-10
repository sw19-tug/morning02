package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FileLoaderJTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setUpClass() {
        //create test.png
        String name = "test.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File dest = new File(path, name);

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        for(int x = 0; x < 100; x++){
            for(int y = 0; y < 100; y++){
                bitmap.setPixel(x, y, Color.rgb(2, 100, 56));
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        //delete test.png
        String name = "test.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(path, name);
        if(file.exists())
        {
            file.delete();
        }
    }

    @Test
    public void testPathsAreRetrieved(){
        FileLoader f=new FileLoader();
        assertNotNull(f);
        List<String> paths=f.getImagesPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
        ImageContainer iC = new ImageContainer(paths.get(0));
        assert(iC.getPath().compareTo(paths.get(0)) != 0);
        //assertNotNull(iC.getImage());
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        for (int childCount = rView.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterImages.ViewHolderImages holder = (AdapterImages.ViewHolderImages) rView.getChildViewHolder(rView.getChildAt(i));
            assertNotNull(holder.photo.getDrawable());

        }

    }

    @Test
    public void testDefaultPath(){
        String name = "test.png";
        String defaultPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        String path = defaultPath + "/" + name;
        FileLoader f=new FileLoader();
        List<String> paths=f.getImagesPaths();
        boolean error = true;
        for (String currentPath : paths){
            if (path.compareTo(currentPath) == 0){
                error = false;
            }
        }
        assertFalse(error);
    }

    @Test
    public void testSubfolder(){


        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/myPhotos");
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) {
        } else {
        }


        String name = "test.png";
        String subfolderpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+ "/myPhotos";


        File dest = new File(subfolderpath, name);

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        for(int x = 0; x < 100; x++){
            for(int y = 0; y < 100; y++){
                bitmap.setPixel(x, y, Color.rgb(2, 100, 56));
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = subfolderpath + "/" + name;
        FileLoader f=new FileLoader();

        List<String> paths=f.getImagesPaths();
        boolean error = true;
        for (String currentPath : paths){
            if (path.compareTo(currentPath) == 0){
                error = false;
            }
        }
        assertFalse(error);

        //delete file
        File file = new File(subfolderpath, name);
        if(file.exists())
        {
            file.delete();
        }
        //delete subfolder
        File dir = new File(subfolderpath);
        if(dir.exists())
        {
            dir.delete();
        }
    }
}