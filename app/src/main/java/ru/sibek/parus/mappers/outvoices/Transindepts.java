package ru.sibek.parus.mappers.outvoices;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.rest.ParusDate;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;

public class Transindepts {

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
        List<TransindeptItem> items = this.getItems();
        for (TransindeptItem item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TransindeptProvider.Columns.NCOMPANY, item.getNcompany());
            contentValues.put(TransindeptProvider.Columns.SJUR_PERS, item.getSjurPers());
            contentValues.put(TransindeptProvider.Columns.NDOCTYPE, item.getNdoctype());
            contentValues.put(TransindeptProvider.Columns.SDOCTYPE, item.getSdoctype());
            contentValues.put(TransindeptProvider.Columns.SPREF, item.getSpref().trim());
            contentValues.put(TransindeptProvider.Columns.SNUMB, item.getSnumb().trim());
            contentValues.put(TransindeptProvider.Columns.DDOC_DATE, ParusDate.parse(item.getDdocDate()).getTime());
            contentValues.put(TransindeptProvider.Columns.HASH, ParusDate.parse(item.getDmodifdate()).getTime());
            contentValues.put(TransindeptProvider.Columns.NSTATUS, item.getNstatus());
            contentValues.put(TransindeptProvider.Columns.LOCAL_NSTATUS, item.getNstatus());
            contentValues.put(TransindeptProvider.Columns.SSTATUS_IN_STATUS, item.getSstatusInStatus());
            contentValues.put(TransindeptProvider.Columns.NRN, item.getNrn());
            contentValues.put(TransindeptProvider.Columns.DWORK_DATE, item.dworkDate);
            contentValues.put(TransindeptProvider.Columns.SAGENT, item.getSagent());
            contentValues.put(TransindeptProvider.Columns.SAGENT_NAME, item.getSagentName());
            contentValues.put(TransindeptProvider.Columns.NSTORE, item.getNstore());
            contentValues.put(TransindeptProvider.Columns.SSTORE, item.getSstore());
            contentValues.put(TransindeptProvider.Columns.SSTOPER, item.getSstoper());
            contentValues.put(TransindeptProvider.Columns.SMOL, item.getSmol());
            contentValues.put(TransindeptProvider.Columns.SSHEEPVIEW, item.getSsheepview());
            contentValues.put(TransindeptProvider.Columns.SIN_STORE, item.getSinStore());
            contentValues.put(TransindeptProvider.Columns.SIN_MOL, item.getSinMol());
            contentValues.put(TransindeptProvider.Columns.SIN_STOPER, item.getSinStoper());
            contentValues.put(TransindeptProvider.Columns.SFACEACC, item.getSfaceacc());
            contentValues.put(TransindeptProvider.Columns.NSUMMWITHNDS, item.getNsummwithnds());
            contentValuesList.add(contentValues);
            //Log.d("DMODIF>>", ParusDate.parse(item.getDdocDate()).getTime() + "!!!!" + ParusDate.parse(item.getDmodifdate()).getTime());
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);


    }

    @Override
    public String toString() {
        return "Transindepts{" +
                "items=" + items.size() +
                '}';
    }

    public class TransindeptItem {

        @Expose
        private Integer nrn;
        @Expose
        private Long nstore;
        @Expose
        private Integer ncompany;
        @SerializedName("sjur_pers")
        @Expose
        private String sjurPers;
        @Expose
        private String sfaceacc;
        @Expose
        private Integer ndoctype;
        @Expose
        private String sdoctype;
        @Expose
        private String dmodifdate;
        @Expose
        private String spref;
        @Expose
        private String snumb;
        @Expose
        private String sagent;
        @SerializedName("sagent_name")
        @Expose
        private String sagentName;
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

        public String getSfaceacc() {
            return sfaceacc;
        }

        public void setSfaceacc(String sfaceacc) {
            this.sfaceacc = sfaceacc;
        }

        public Long getNstore() {
            return nstore;
        }

        public void setNstore(Long nstore) {
            this.nstore = nstore;
        }

        public String getSagent() {
            return sagent;
        }

        public void setSagent(String sagent) {
            this.sagent = sagent;
        }

        public String getSagentName() {
            return sagentName;
        }

        public void setSagentName(String sagentName) {
            this.sagentName = sagentName;
        }

        public String getDmodifdate() {
            return dmodifdate;
        }

        public void setDmodifdate(String dmodifdate) {
            this.dmodifdate = dmodifdate;
        }

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