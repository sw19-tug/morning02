package com.gallery.android.gallery;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PictureSortingUnitTest {

    enum SortOrder{
        ASCENDING,DESCENDING
    }

    @Test
    public void hasMemberVariables() throws ParseException
    {
        Date date, date1, date2;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        date = date_format.parse("2018-04-05");

        ImageContainer image = new ImageContainer("test1",date, 5000,"testimage");

        assertTrue(image.getPath().equals("test1"));
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

        ImageContainer image = new ImageContainer("test", date, 5000,"testimage");
        ImageContainer image1 = new ImageContainer("test1", date1, 5000,"testimage1");
        ImageContainer image2 = new ImageContainer( "test2", date2, 5000,"testimage2");

        decendingimageList.add(0,image);
        decendingimageList.add(1,image1);
        decendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image2);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image1);

        piclist.sortByDate(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.getListImages(), is(equalTo(decendingimageList)));

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

        ImageContainer image = new ImageContainer( "test",date, 5000,"testimage");
        ImageContainer image1 = new ImageContainer("test1", date1, 6000,"testimage1");
        ImageContainer image2 = new ImageContainer("test2", date2, 6000,"testimage2");
        ImageContainer image3 = new ImageContainer( "test3",date3, 8000,"testimage3");
        ImageContainer image4 = new ImageContainer( "test4", date4,2000,"testimage4");
        ImageContainer image5 = new ImageContainer( "test5",date5, 1000,"testimage5");

        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);
        ascendingimageList.add(3,image3);
        ascendingimageList.add(4,image4);
        ascendingimageList.add(5,image5);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image4);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image2);
        piclist.getListImages().add(image1);
        piclist.getListImages().add(image3);
        piclist.getListImages().add(image5);

        piclist.sortByDate(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.getListImages(), is(equalTo(ascendingimageList)));
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

        ImageContainer image = new ImageContainer("test",date,  5000,"testimage");
        ImageContainer image1 = new ImageContainer("test1",date1,  4500,"testimage1");
        ImageContainer image2 = new ImageContainer( "test2",date2, 4000,"testimage2");
        decendingimageList.add(0,image);
        decendingimageList.add(1,image1);
        decendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image2);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image1);

        piclist.sortBySize(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.getListImages(), is(equalTo(decendingimageList)));

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

        ImageContainer image = new ImageContainer( "test",date, 3000,"testimage");
        ImageContainer image1 = new ImageContainer( "test1", date1,4500,"testimage1");
        ImageContainer image2 = new ImageContainer( "test2", date2,7000,"testimage2");

        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image2);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image1);

        piclist.sortBySize(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.getListImages(), is(equalTo(ascendingimageList)));

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

        ImageContainer image = new ImageContainer( "zenia", date, 3000,"testimage");
        ImageContainer image1 = new ImageContainer( "gustav", date1, 4500,"testimage1");
        ImageContainer image2 = new ImageContainer("albert", date2, 7000,"testimage2");

        descendingimageList.add(0,image);
        descendingimageList.add(1,image1);
        descendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image2);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image1);

        piclist.sortByName(AdapterImages.SortOrder.DESCENDING);
        assertThat(piclist.getListImages(), is(equalTo(descendingimageList)));

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

        ImageContainer image = new ImageContainer("albert",date, 3000,"testimage");
        ImageContainer image1 = new ImageContainer( "gustav",date1, 4500,"testimage1");
        ImageContainer image2 = new ImageContainer( "zenia", date2, 200,"testimage2");

        ascendingimageList.add(0,image);
        ascendingimageList.add(1,image1);
        ascendingimageList.add(2,image2);

        AdapterImages piclist = new AdapterImages();

        piclist.getListImages().add(image2);
        piclist.getListImages().add(image);
        piclist.getListImages().add(image1);

        piclist.sortByName(AdapterImages.SortOrder.ASCENDING);
        assertThat(piclist.getListImages(), is(equalTo(ascendingimageList)));

    }

}