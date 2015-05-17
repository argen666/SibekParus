package ru.sibek.parus.mappers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by argen666 on 15.05.2015.
 */
public class ContentValuesUtils {
    public static List<ContentValues[]> splitArrayIntoChunk(ContentValues[] cValues, int chunkSize) {
        List<ContentValues[]> retList = new ArrayList<>();
        for (int start = 0; start < cValues.length; start += chunkSize) {
            int end = Math.min(start + chunkSize, cValues.length);
            List<ContentValues> sublist = new ArrayList<>();
            //ContentValues[] chunkValues = new ContentValues[chunkSize];
            for (int i = start; i < end; i++) {
                sublist.add(cValues[i]);
            }
            //System.out.println(sublist.toArray(chunkValues));
            retList.add(sublist.toArray(new ContentValues[sublist.size()]));
        }
        return retList;
    }
}
