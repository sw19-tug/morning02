package com.gallery.android.gallery;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FileLoaderJTest {
    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test1.png");
                    TestHelper.createFile("test2.png");
                }
            });

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test1.png");
        TestHelper.deleteFile("test2.png");
    }

    @Test
    public void testPathsAreRetrieved(){
        FileLoader f=new FileLoader();
        assertNotNull(f);
        List<String> paths=f.getImagesInformation();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
        ImageContainer iC = new ImageContainer(paths.get(0),new Date(),1000,"dummyfile");
        assert(iC.getPath().compareTo(paths.get(0)) != 0);
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        for (int childCount = rView.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterImages.ViewHolderImages holder = (AdapterImages.ViewHolderImages) rView.getChildViewHolder(rView.getChildAt(i));
            assertNotNull(holder.photo.getDrawable());
        }
    }

    @Test
    public void testDefaultPath(){
        String name = "test1.png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        String absolutePath = path + "/" + name;
        FileLoader f=new FileLoader();
        List<String> paths=f.getImagesInformation();
        boolean error = true;
        for (String currentPath : paths){
            if (absolutePath.compareTo(currentPath) == 0){
                error = false;
            }
        }
        assertFalse(error);
    }

    @Test
    public void testSubfolder(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/GalleryTest");
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if(!success)
            System.out.println("ERROR: Not able to create a test image!");

        String name = "test.png";
        String subfolderpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()+ "/GalleryTest";
        TestHelper.createFile(name, subfolderpath);
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

        List<String> paths=f.getImagesInformation();
        boolean error = true;
        for (String currentPath : paths){
            if (path.compareTo(currentPath) == 0){
                error = false;
            }
        }
        assertFalse(error);

        TestHelper.deleteFile(name, subfolderpath);
        TestHelper.deleteFile("GalleryTest", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
    }
}