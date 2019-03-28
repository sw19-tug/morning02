package com.gallery.android.gallery;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PictureSortingUnitTest {

    enum SortOrder{
        ASCENDING,DESCENDING
    }

    @Test
    public void hasMemberVariables() throws ParseException
    {
        //List<dummyimageclass> imageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        date = date_format.parse("2018-04-05");

        ImageContainer image = new ImageContainer(date, "test1", 5000);

        assertTrue(image.getFilename().equals("test1"));
        assertTrue(image.getSize() == 5000);

    }

    @Test
    public void inDecendingDateOrder() throws ParseException
    {

        List<ImageContainer> decendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date = date_format.parse("2018-08-07");
        date1 = date_format.parse("2018-04-05");
        date2 = date_format.parse("2008-01-04");

        ImageContainer image = new ImageContainer(date, "test", 5000);
        ImageContainer image1 = new ImageContainer(date1, "test1", 5000);
        ImageContainer image2 = new ImageContainer(date2, "test2", 5000);

        decendingimageList.add(0,image);
        decendingimageList.add(1,image1);
        decendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

            piclist.pictures.add(image2);
            piclist.pictures.add(image);
            piclist.pictures.add(image1);

        piclist.sortByDate(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.pictures, is(equalTo(decendingimageList)));

    }

    @Test
    public void inAscendingDateOrder() throws ParseException
    {

        List<ImageContainer> ascendingimageList = new ArrayList<>();
        Date date, date1, date2,date3,date4,date5;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date = date_format.parse("2010-03-12");
        date1 = date_format.parse("2012-08-06");
        date2 = date_format.parse("2013-08-07");
        date3 = date_format.parse("2013-08-22");
        date4 = date_format.parse("2014-10-08");
        date5 = date_format.parse("2018-02-23");


        ImageContainer image = new ImageContainer(date, "test", 5000);
        ImageContainer image1 = new ImageContainer(date1, "test1", 6000);
        ImageContainer image2 = new ImageContainer(date2, "test2", 6000);
        ImageContainer image3 = new ImageContainer(date3, "test3", 8000);
        ImageContainer image4 = new ImageContainer(date4, "test4", 2000);
        ImageContainer image5 = new ImageContainer(date5, "test5", 1000);


        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);
        ascendingimageList.add(3,image3);
        ascendingimageList.add(4,image4);
        ascendingimageList.add(5,image5);

        AdapterImages piclist = new AdapterImages();

        piclist.pictures.add(image4);
        piclist.pictures.add(image);
        piclist.pictures.add(image2);
        piclist.pictures.add(image1);
        piclist.pictures.add(image3);
        piclist.pictures.add(image5);

        piclist.sortByDate(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.pictures, is(equalTo(ascendingimageList)));

    }

    @Test
    public void inDecendingSizeOrder() throws ParseException
    {

        List<ImageContainer> decendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date2 = date_format.parse("2018-08-07");
        date1 = date_format.parse("2018-04-05");
        date = date_format.parse("2008-01-04");

        ImageContainer image = new ImageContainer(date, "test", 5000);
        ImageContainer image1 = new ImageContainer(date1, "test1", 4500);
        ImageContainer image2 = new ImageContainer(date2, "test2", 4000);

        decendingimageList.add(0,image);
        decendingimageList.add(1,image1);
        decendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.pictures.add(image2);
        piclist.pictures.add(image);
        piclist.pictures.add(image1);

        piclist.sortBySize(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.pictures, is(equalTo(decendingimageList)));

    }

    @Test
    public void inAscendingSizeOrder() throws ParseException
    {

        List<ImageContainer> ascendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date2 = date_format.parse("2018-08-07");
        date1 = date_format.parse("2018-04-05");
        date = date_format.parse("2008-01-04");

        ImageContainer image = new ImageContainer(date, "test", 3000);
        ImageContainer image1 = new ImageContainer(date1, "test1", 4500);
        ImageContainer image2 = new ImageContainer(date2, "test2", 7000);

        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.pictures.add(image2);
        piclist.pictures.add(image);
        piclist.pictures.add(image1);

        piclist.sortBySize(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.pictures, is(equalTo(ascendingimageList)));

    }

    @Test
    public void inDescendingNameOrder() throws ParseException
    {

        List<ImageContainer> descendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date2 = date_format.parse("2018-08-07");
        date1 = date_format.parse("2018-04-05");
        date = date_format.parse("2008-01-04");

        ImageContainer image = new ImageContainer(date, "zenia", 3000);
        ImageContainer image1 = new ImageContainer(date1, "gustav", 4500);
        ImageContainer image2 = new ImageContainer(date2, "albert", 7000);

        descendingimageList.add(0,image);
        descendingimageList.add(1,image1);
        descendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.pictures.add(image2);
        piclist.pictures.add(image);
        piclist.pictures.add(image1);

        piclist.sortByName(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.pictures, is(equalTo(descendingimageList)));

    }

    @Test
    public void inAscendingNameOrder() throws ParseException
    {

        List<ImageContainer> ascendingimageList = new ArrayList<>();
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        date2 = date_format.parse("2018-08-07");
        date = date_format.parse("2018-04-05");
        date1 = date_format.parse("2008-01-04");

        ImageContainer image = new ImageContainer(date, "albert", 3000);
        ImageContainer image1 = new ImageContainer(date1, "gustav", 4500);
        ImageContainer image2 = new ImageContainer(date2, "zenia", 200);

        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.pictures.add(image2);
        piclist.pictures.add(image);
        piclist.pictures.add(image1);

        piclist.sortByName(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.pictures, is(equalTo(ascendingimageList)));

    }



}
