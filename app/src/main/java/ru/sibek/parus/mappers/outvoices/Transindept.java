package ru.sibek.parus.mappers.outvoices;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Transindept {

    @Expose
    private List<TransindeptItem> items = new ArrayList<TransindeptItem>();

    /**
     * @return The items
     */
    public List<TransindeptItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<TransindeptItem> items) {
        this.items = items;
    }

    public ContentValues[] toContentValues() {

        final List<ContentValues> contentValuesList = new ArrayList<>();
       /* List<ItemInvoice> items = this.getItems();
        for (ItemInvoice item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(InvoiceProvider.Columns.SNUMB, item.getSnumb());
            contentValues.put(InvoiceProvider.Columns.SDOCTYPE, item.getSdoctype());
            contentValues.put(InvoiceProvider.Columns.SPREF, item.getSpref());
            contentValues.put(InvoiceProvider.Columns.DDOC_DATE, ParusDate.parse(item.getDdocDate()).getTime());
            contentValues.put(InvoiceProvider.Columns.HASH, ParusDate.parse(item.getDmodifdate()).getTime());
            contentValues.put(InvoiceProvider.Columns.SAGENT, item.getSagent());
            contentValues.put(InvoiceProvider.Columns.NSUMMTAX, item.getNsummtax());
            contentValues.put(InvoiceProvider.Columns.NSTATUS, item.getNstatus());
            contentValues.put(InvoiceProvider.Columns.LOCAL_NSTATUS, item.getNstatus());
            contentValues.put(InvoiceProvider.Columns.SSTATUS, item.getSstatus());
            contentValues.put(InvoiceProvider.Columns.NRN, item.getNrn());
            contentValues.put(InvoiceProvider.Columns.NCOMPANY, item.getNcompany());
            contentValuesList.add(contentValues);
            Log.d("DMODIF>>", ParusDate.parse(item.getDdocDate()).getTime() + "!!!!" + ParusDate.parse(item.getDmodifdate()).getTime());
        }*/

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }

    public class TransindeptItem {

        @Expose
        private Integer nrn;
        @Expose
        private Integer ncompany;
        @SerializedName("sjur_pers")
        @Expose
        private String sjurPers;
        @Expose
        private Integer ndoctype;
        @Expose
        private String sdoctype;
        @Expose
        private String spref;
        @Expose
        private String snumb;
        @SerializedName("ddoc_date")
        @Expose
        private String ddocDate;
        @Expose
        private Integer nstatus;
        @SerializedName("sstatus_in_status")
        @Expose
        private String sstatusInStatus;
        @SerializedName("dwork_date")
        @Expose
        private String dworkDate;
        @Expose
        private Double nsummwithnds;
        @Expose
        private String sstoper;
        @Expose
        private String sstore;
        @Expose
        private String smol;
        @Expose
        private String ssheepview;
        @SerializedName("sin_store")
        @Expose
        private String sinStore;
        @SerializedName("sin_mol")
        @Expose
        private String sinMol;
        @SerializedName("sin_stoper")
        @Expose
        private String sinStoper;

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
         * @return The ncompany
         */
        public Integer getNcompany() {
            return ncompany;
        }

        /**
         * @param ncompany The ncompany
         */
        public void setNcompany(Integer ncompany) {
            this.ncompany = ncompany;
        }

        /**
         * @return The sjurPers
         */
        public String getSjurPers() {
            return sjurPers;
        }

        /**
         * @param sjurPers The sjur_pers
         */
        public void setSjurPers(String sjurPers) {
            this.sjurPers = sjurPers;
        }

        /**
         * @return The ndoctype
         */
        public Integer getNdoctype() {
            return ndoctype;
        }

        /**
         * @param ndoctype The ndoctype
         */
        public void setNdoctype(Integer ndoctype) {
            this.ndoctype = ndoctype;
        }

        /**
         * @return The sdoctype
         */
        public String getSdoctype() {
            return sdoctype;
        }

        /**
         * @param sdoctype The sdoctype
         */
        public void setSdoctype(String sdoctype) {
            this.sdoctype = sdoctype;
        }

        /**
         * @return The spref
         */
        public String getSpref() {
            return spref;
        }

        /**
         * @param spref The spref
         */
        public void setSpref(String spref) {
            this.spref = spref;
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
         * @return The ddocDate
         */
        public String getDdocDate() {
            return ddocDate;
        }

        /**
         * @param ddocDate The ddoc_date
         */
        public void setDdocDate(String ddocDate) {
            this.ddocDate = ddocDate;
        }

        /**
         * @return The nstatus
         */
        public Integer getNstatus() {
            return nstatus;
        }

        /**
         * @param nstatus The nstatus
         */
        public void setNstatus(Integer nstatus) {
            this.nstatus = nstatus;
        }

        /**
         * @return The sstatusInStatus
         */
        public String getSstatusInStatus() {
            return sstatusInStatus;
        }

        /**
         * @param sstatusInStatus The sstatus_in_status
         */
        public void setSstatusInStatus(String sstatusInStatus) {
            this.sstatusInStatus = sstatusInStatus;
        }

        /**
         * @return The dworkDate
         */
        public String getDworkDate() {
            return dworkDate;
        }

        /**
         * @param dworkDate The dwork_date
         */
        public void setDworkDate(String dworkDate) {
            this.dworkDate = dworkDate;
        }

        /**
         * @return The nsummwithnds
         */
        public Double getNsummwithnds() {
            return nsummwithnds;
        }

        /**
         * @param nsummwithnds The nsummwithnds
         */
        public void setNsummwithnds(Double nsummwithnds) {
            this.nsummwithnds = nsummwithnds;
        }

        /**
         * @return The sstoper
         */
        public String getSstoper() {
            return sstoper;
        }

        /**
         * @param sstoper The sstoper
         */
        public void setSstoper(String sstoper) {
            this.sstoper = sstoper;
        }

        /**
         * @return The sstore
         */
        public String getSstore() {
            return sstore;
        }

        /**
         * @param sstore The sstore
         */
        public void setSstore(String sstore) {
            this.sstore = sstore;
        }

        /**
         * @return The smol
         */
        public String getSmol() {
            return smol;
        }

        /**
         * @param smol The smol
         */
        public void setSmol(String smol) {
            this.smol = smol;
        }

        /**
         * @return The ssheepview
         */
        public String getSsheepview() {
            return ssheepview;
        }

        /**
         * @param ssheepview The ssheepview
         */
        public void setSsheepview(String ssheepview) {
            this.ssheepview = ssheepview;
        }

        /**
         * @return The sinStore
         */
        public String getSinStore() {
            return sinStore;
        }

        /**
         * @param sinStore The sin_store
         */
        public void setSinStore(String sinStore) {
            this.sinStore = sinStore;
        }

        /**
         * @return The sinMol
         */
        public String getSinMol() {
            return sinMol;
        }

        /**
         * @param sinMol The sin_mol
         */
        public void setSinMol(String sinMol) {
            this.sinMol = sinMol;
        }

        /**
         * @return The sinStoper
         */
        public String getSinStoper() {
            return sinStoper;
        }

        /**
         * @param sinStoper The sin_stoper
         */
        public void setSinStoper(String sinStoper) {
            this.sinStoper = sinStoper;
        }

    }
}