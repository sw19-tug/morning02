package com.gallery.android.gallery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExportImages {



    public static void compressImage(String[] folder, String compFile) throws IOException {

        final int BUFFER_SIZE = 10000;
        BufferedInputStream origin = null;
        ZipOutputStream external_zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(compFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < folder.length; i++) {
                FileInputStream fi = new FileInputStream(folder[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(folder[i].substring(folder[i].lastIndexOf("/") + 1));
                    external_zip.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        external_zip.write(data, 0, count);
                    }
                }
                finally {
                    origin.close();
                }
            }
        }
        finally {
            external_zip.close();
        }
    }
}
