package ru.sibek.parus.mappers;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.storages.RacksProvider;

public class Racks {

    @Expose
    private List<RackItem> items = new ArrayList<RackItem>();

    /**
     * @return The items
     */
    public List<RackItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<RackItem> items) {
        this.items = items;
    }


    public ContentValues[] toContentValues(String storageID) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<RackItem> items = this.getItems();
        for (RackItem item : items) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(RacksProvider.Columns.STORAGE_ID, storageID);
            contentValues.put(RacksProvider.Columns.NRN, item.getNrn());
            contentValues.put(RacksProvider.Columns.SFULLNAME, item.getSfullnumber());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }


    public class RackItem {

        @Expose
        private Integer nrn;
        @Expose
        private String sfullnumber;

        /**
         * @return The nrn
         */
        public Integer getNrn() {
            return nrn;
        }

        /**
         * @param nrn The nrn
         */
        public void setNrn(Integer nrn) {
            this.nrn = nrn;
        }

        /**
         * @return The sfullnumber
         */
        public String getSfullnumber() {
            return sfullnumber;
        }

        /**
         * @param sfullnumber The sfullnumber
         */
        public void setSfullnumber(String sfullnumber) {
            this.sfullnumber = sfullnumber;
        }

    }


}