package ru.sibek.parus.mappers.outvoices;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.outinvoices.TransindeptSpecProvider;

/**
 * Created by Developer on 24.04.2015.
 */
public class TransindeptSpec {
    @Expose
    private List<TransindeptSpecItem> items = new ArrayList<TransindeptSpecItem>();

    /**
     * @return The items
     */
    public List<TransindeptSpecItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<TransindeptSpecItem> items) {
        this.items = items;
    }


    public ContentValues[] toContentValues(String transID) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<TransindeptSpecItem> items = this.getItems();
        for (TransindeptSpecItem item : items) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(TransindeptSpecProvider.Columns.TRANSINDEPT_ID, transID);
            contentValues.put(TransindeptSpecProvider.Columns.NRN, item.getNrn());
            contentValues.put(TransindeptSpecProvider.Columns.NPRN, item.getNprn());
            contentValues.put(TransindeptSpecProvider.Columns.SNOMEN, item.getSnomen());
            contentValues.put(TransindeptSpecProvider.Columns.SNOMENNAME, item.getSnomenname());
            contentValues.put(TransindeptSpecProvider.Columns.NQUANT, item.getNquant());
            contentValues.put(TransindeptSpecProvider.Columns.NSTOREQUANT, item.getNstorequant());
            contentValues.put(TransindeptSpecProvider.Columns.SMEAS_MAIN, item.getSmeasMain());
            contentValues.put(TransindeptSpecProvider.Columns.NRACK, item.getNrack());
            contentValues.put(TransindeptSpecProvider.Columns.SRACK, item.getSrack());
            contentValues.put(TransindeptSpecProvider.Columns.SCELL, item.getScell());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }


    public class TransindeptSpecItem {

        @Expose
        private Integer nrn;
        @Expose
        private Integer nprn;
        @Expose
        private String snomen;
        @Expose
        private String snomenname;
        @Expose
        private Double nquant;
        @Expose
        private Double nstorequant;
        @SerializedName("smeas_main")
        @Expose
        private String smeasMain;
        @Expose
        private Integer srack;
        @Expose
        private Integer scell;
        @Expose
        private Integer nrack;

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
         * @return The nprn
         */
        public Integer getNprn() {
            return nprn;
        }

        /**
         * @param nprn The nprn
         */
        public void setNprn(Integer nprn) {
            this.nprn = nprn;
        }

        /**
         * @return The snomen
         */
        public String getSnomen() {
            return snomen;
        }

        /**
         * @param snomen The snomen
         */
        public void setSnomen(String snomen) {
            this.snomen = snomen;
        }

        /**
         * @return The snomenname
         */
        public String getSnomenname() {
            return snomenname;
        }

        /**
         * @param snomenname The snomenname
         */
        public void setSnomenname(String snomenname) {
            this.snomenname = snomenname;
        }

        public Double getNquant() {
            return nquant;
        }

        public void setNquant(Double nquant) {
            this.nquant = nquant;
        }

        public Double getNstorequant() {
            return nstorequant;
        }

        public void setNstorequant(Double nstorequant) {
            this.nstorequant = nstorequant;
        }

        /**
         * @return The smeasMain
         */
        public String getSmeasMain() {
            return smeasMain;
        }

        /**
         * @param smeasMain The smeas_main
         */
        public void setSmeasMain(String smeasMain) {
            this.smeasMain = smeasMain;
        }

        /**
         * @return The srack
         */
        public Integer getSrack() {
            return srack;
        }

        /**
         * @param srack The srack
         */
        public void setSrack(Integer srack) {
            this.srack = srack;
        }

        /**
         * @return The scell
         */
        public Integer getScell() {
            return scell;
        }

        /**
         * @param scell The scell
         */
        public void setScell(Integer scell) {
            this.scell = scell;
        }

        /**
         * @return The nrack
         */
        public Integer getNrack() {
            return nrack;
        }

        /**
         * @param nrack The nrack
         */
        public void setNrack(Integer nrack) {
            this.nrack = nrack;
        }

    }
}
