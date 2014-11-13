package ru.sibek.parus.mappers;


import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.StorageProvider;

public class Storages {

    @Expose
    private List<StorageItem> items = new ArrayList<StorageItem>();

    /**
     * @return The items
     */
    public List<StorageItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<StorageItem> items) {
        this.items = items;
    }


    public ContentValues[] toContentValues() {

        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<StorageItem> items = this.getItems();
        for (StorageItem item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(StorageProvider.Columns.SNUMB, item.getSnumb());
            contentValues.put(StorageProvider.Columns.NRN, item.getNrn());
            contentValues.put(StorageProvider.Columns.NDISTRIBUTION_SIGN, item.getNdistributionSign());
            contentValues.put(StorageProvider.Columns.SNAME, item.getSname());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);

    }

    public class StorageItem {

        @Expose
        private Integer nrn;
        @Expose
        private String snumb;
        @SerializedName("ndistribution_sign")
        @Expose
        private Integer ndistributionSign;
        @Expose
        private String sname;

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
         * @return The snumb
         */
        public String getSnumb() {
            return snumb;
        }

        /**
         * @param snumb The snumb
         */
        public void setSnumb(String snumb) {
            this.snumb = snumb;
        }

        /**
         * @return The ndistributionSign
         */
        public Integer getNdistributionSign() {
            return ndistributionSign;
        }

        /**
         * @param ndistributionSign The ndistribution_sign
         */
        public void setNdistributionSign(Integer ndistributionSign) {
            this.ndistributionSign = ndistributionSign;
        }

        /**
         * @return The sname
         */
        public String getSname() {
            return sname;
        }

        /**
         * @param sname The sname
         */
        public void setSname(String sname) {
            this.sname = sname;
        }

    }


}
