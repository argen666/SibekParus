package ru.sibek.parus.mappers.complectations;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.rest.ParusDate;
import ru.sibek.parus.sqlite.complectations.ComplectationProvider;

/**
 * Created by argen666 on 18.05.2015.
 */
public class Complectations {

    @Expose
    private List<ComplectationItem> items = new ArrayList<ComplectationItem>();

    /**
     * @return The items
     */
    public List<ComplectationItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<ComplectationItem> items) {
        this.items = items;
    }

    public ContentValues[] toContentValues() {

        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<ComplectationItem> items = this.getItems();
        for (ComplectationItem item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ComplectationProvider.Columns.SDOCTYPE, item.getSdoctype());
            contentValues.put(ComplectationProvider.Columns.SPREF, item.getSpref().trim());
            contentValues.put(ComplectationProvider.Columns.SNUMB, item.getSnumb().trim());
            contentValues.put(ComplectationProvider.Columns.DDOC_DATE, ParusDate.parse(item.getDdocdate()).getTime());
            contentValues.put(ComplectationProvider.Columns.HASH, "0");
            contentValues.put(ComplectationProvider.Columns.NSTATE, item.getNstate());
            //contentValues.put(ComplectationProvider.Columns.LOCAL_NSTATUS, item.getNstatus());
            contentValues.put(ComplectationProvider.Columns.NRN, item.getNrn());
            contentValues.put(ComplectationProvider.Columns.DEND_DATE_PLAN, ParusDate.parse(item.getDendDatePlan()).getTime());
            contentValues.put(ComplectationProvider.Columns.SPROD_ORDER, item.getSprodOrder());
            contentValues.put(ComplectationProvider.Columns.SMATRES_NAME, item.getSmatresName());
            contentValues.put(ComplectationProvider.Columns.NSTORE, item.getNstore());
            contentValues.put(ComplectationProvider.Columns.SSTORE, item.getSstore());
            contentValues.put(ComplectationProvider.Columns.SMATRES_UMEAS, item.getSmatresUmeas());
            contentValues.put(ComplectationProvider.Columns.SSUBDIV, item.getSsubdiv());
            contentValues.put(ComplectationProvider.Columns.NSUBDIV, item.getNsubdiv());
            contentValues.put(ComplectationProvider.Columns.SSUBDIV, item.getSsubdiv());
            contentValues.put(ComplectationProvider.Columns.SDOCTYPE, item.getSdoctype());
            contentValues.put(ComplectationProvider.Columns.NQUANT, item.getNquant());
            contentValues.put(ComplectationProvider.Columns.SALIVE, item.getSALIVE());
            contentValuesList.add(contentValues);
            //Log.d("DMODIF>>", ParusDate.parse(item.getDdocDate()).getTime() + "!!!!" + ParusDate.parse(item.getDmodifdate()).getTime());
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);


    }

    @Override
    public String toString() {
        return "Complectations{" +
                "items=" + items.size() +
                '}';
    }


    public class ComplectationItem {

        @Expose
        private Integer nrn;
        @Expose
        private String sdoctype;
        @Expose
        private String spref;
        @Expose
        private String snumb;
        @Expose
        private String ddocdate;
        @Expose
        private String salive;
        @Expose
        private Integer nstate;
        @SerializedName("sprod_order")
        @Expose
        private String sprodOrder;
        @SerializedName("smatres_name")
        @Expose
        private String smatresName;
        @Expose
        private Integer nquant;
        @SerializedName("smatres_umeas")
        @Expose
        private String smatresUmeas;
        @Expose
        private Integer nstore;
        @Expose
        private String sstore;
        @Expose
        private Integer nsubdiv;
        @Expose
        private String ssubdiv;
        @SerializedName("dend_date_plan")
        @Expose
        private String dendDatePlan;

        public String getSALIVE() {
            return salive;
        }

        public void setSALIVE(String salive) {
            this.salive = salive;
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
         * @return The ddocdate
         */
        public String getDdocdate() {
            return ddocdate;
        }

        /**
         * @param ddocdate The ddocdate
         */
        public void setDdocdate(String ddocdate) {
            this.ddocdate = ddocdate;
        }

        /**
         * @return The nstate
         */
        public Integer getNstate() {
            return nstate;
        }

        /**
         * @param nstate The nstate
         */
        public void setNstate(Integer nstate) {
            this.nstate = nstate;
        }

        /**
         * @return The sprodOrder
         */
        public String getSprodOrder() {
            return sprodOrder;
        }

        /**
         * @param sprodOrder The sprod_order
         */
        public void setSprodOrder(String sprodOrder) {
            this.sprodOrder = sprodOrder;
        }

        /**
         * @return The smatresName
         */
        public String getSmatresName() {
            return smatresName;
        }

        /**
         * @param smatresName The smatres_name
         */
        public void setSmatresName(String smatresName) {
            this.smatresName = smatresName;
        }

        /**
         * @return The nquant
         */
        public Integer getNquant() {
            return nquant;
        }

        /**
         * @param nquant The nquant
         */
        public void setNquant(Integer nquant) {
            this.nquant = nquant;
        }

        /**
         * @return The smatresUmeas
         */
        public String getSmatresUmeas() {
            return smatresUmeas;
        }

        /**
         * @param smatresUmeas The smatres_umeas
         */
        public void setSmatresUmeas(String smatresUmeas) {
            this.smatresUmeas = smatresUmeas;
        }

        /**
         * @return The nstore
         */
        public Integer getNstore() {
            return nstore;
        }

        /**
         * @param nstore The nstore
         */
        public void setNstore(Integer nstore) {
            this.nstore = nstore;
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
         * @return The nsubdiv
         */
        public Integer getNsubdiv() {
            return nsubdiv;
        }

        /**
         * @param nsubdiv The nsubdiv
         */
        public void setNsubdiv(Integer nsubdiv) {
            this.nsubdiv = nsubdiv;
        }

        /**
         * @return The ssubdiv
         */
        public String getSsubdiv() {
            return ssubdiv;
        }

        /**
         * @param ssubdiv The ssubdiv
         */
        public void setSsubdiv(String ssubdiv) {
            this.ssubdiv = ssubdiv;
        }

        /**
         * @return The dendDatePlan
         */
        public String getDendDatePlan() {
            return dendDatePlan;
        }

        /**
         * @param dendDatePlan The dend_date_plan
         */
        public void setDendDatePlan(String dendDatePlan) {
            this.dendDatePlan = dendDatePlan;
        }


    }
}
