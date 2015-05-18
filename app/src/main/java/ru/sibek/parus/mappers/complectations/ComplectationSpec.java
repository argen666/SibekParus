package ru.sibek.parus.mappers.complectations;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.complectations.ComplectationSpecProvider;

/**
 * Created by argen666 on 18.05.2015.
 */
public class ComplectationSpec {

    @Expose
    private List<ComplectationSpecItem> items = new ArrayList<ComplectationSpecItem>();

    /**
     * @return The items
     */
    public List<ComplectationSpecItem> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<ComplectationSpecItem> items) {
        this.items = items;
    }


    public ContentValues[] toContentValues(String transID) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<ComplectationSpecItem> items = this.getItems();
        for (ComplectationSpecItem item : items) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(ComplectationSpecProvider.Columns.COMPLECTATION_ID, transID);
            contentValues.put(ComplectationSpecProvider.Columns.NRN, item.getNrn());
            contentValues.put(ComplectationSpecProvider.Columns.NPRN, item.getNprn());
            contentValues.put(ComplectationSpecProvider.Columns.SMATRES_CODE, item.getSmatresCode());
            contentValues.put(ComplectationSpecProvider.Columns.SMATRES_NAME, item.getSmatresName());
            contentValues.put(ComplectationSpecProvider.Columns.SMATRES_NOMEN, item.getSmatresNomen());
            contentValues.put(ComplectationSpecProvider.Columns.SMATRES_MODIF, item.getSmatresModif());
            contentValues.put(ComplectationSpecProvider.Columns.SPART_OF_CODE, item.getSpartOfCode());
            contentValues.put(ComplectationSpecProvider.Columns.SPART_OF_NAME, item.getSpartOfName());
            contentValues.put(ComplectationSpecProvider.Columns.SPART_OF_NOMEN, item.getSpartOfNomen());
            contentValues.put(ComplectationSpecProvider.Columns.SPART_OF_MODIF, item.getSpartOfModif());
            contentValues.put(ComplectationSpecProvider.Columns.NQUANT_SPEC, item.getNquantSpec());
            contentValues.put(ComplectationSpecProvider.Columns.NQUANT_PROD, item.getNquantProd());
            contentValues.put(ComplectationSpecProvider.Columns.NQUANT_PLAN, item.getNquantPlan());
            contentValues.put(ComplectationSpecProvider.Columns.NQUANT_CMPL, item.getNquantCmpl());
            contentValues.put(ComplectationSpecProvider.Columns.NQUANT_DLVR, item.getNquantDlvr());
            contentValues.put(ComplectationSpecProvider.Columns.SMATRES_UMEAS, item.getSmatresUmeas());
            contentValues.put(ComplectationSpecProvider.Columns.SRACK, item.getSrack());
            contentValues.put(ComplectationSpecProvider.Columns.SQUANT, item.getSquant());


            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }


    @Override
    public String toString() {
        return "ComplectationSpec{" +
                "items=" + items.size() +
                '}';
    }

    public class ComplectationSpecItem {

        @Expose
        private Integer nrn;
        @Expose
        private Integer nprn;
        @SerializedName("smatres_code")
        @Expose
        private String smatresCode;
        @SerializedName("smatres_name")
        @Expose
        private String smatresName;
        @SerializedName("smatres_nomen")
        @Expose
        private String smatresNomen;
        @SerializedName("smatres_modif")
        @Expose
        private String smatresModif;
        @SerializedName("spart_of_code")
        @Expose
        private String spartOfCode;
        @SerializedName("spart_of_name")
        @Expose
        private String spartOfName;
        @SerializedName("spart_of_nomen")
        @Expose
        private String spartOfNomen;
        @SerializedName("spart_of_modif")
        @Expose
        private String spartOfModif;
        @SerializedName("nquant_spec")
        @Expose
        private Double nquantSpec;
        @SerializedName("nquant_prod")
        @Expose
        private Double nquantProd;
        @SerializedName("nquant_plan")
        @Expose
        private Double nquantPlan;
        @SerializedName("nquant_cmpl")
        @Expose
        private Double nquantCmpl;
        @SerializedName("nquant_dlvr")
        @Expose
        private Double nquantDlvr;
        @SerializedName("smatres_umeas")
        @Expose
        private String smatresUmeas;
        @Expose
        private String srack;
        @Expose
        private String squant;

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
         * @return The smatresCode
         */
        public String getSmatresCode() {
            return smatresCode;
        }

        /**
         * @param smatresCode The smatres_code
         */
        public void setSmatresCode(String smatresCode) {
            this.smatresCode = smatresCode;
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
         * @return The smatresNomen
         */
        public String getSmatresNomen() {
            return smatresNomen;
        }

        /**
         * @param smatresNomen The smatres_nomen
         */
        public void setSmatresNomen(String smatresNomen) {
            this.smatresNomen = smatresNomen;
        }

        /**
         * @return The smatresModif
         */
        public String getSmatresModif() {
            return smatresModif;
        }

        /**
         * @param smatresModif The smatres_modif
         */
        public void setSmatresModif(String smatresModif) {
            this.smatresModif = smatresModif;
        }

        /**
         * @return The spartOfCode
         */
        public String getSpartOfCode() {
            return spartOfCode;
        }

        /**
         * @param spartOfCode The spart_of_code
         */
        public void setSpartOfCode(String spartOfCode) {
            this.spartOfCode = spartOfCode;
        }

        /**
         * @return The spartOfName
         */
        public String getSpartOfName() {
            return spartOfName;
        }

        /**
         * @param spartOfName The spart_of_name
         */
        public void setSpartOfName(String spartOfName) {
            this.spartOfName = spartOfName;
        }

        /**
         * @return The spartOfNomen
         */
        public String getSpartOfNomen() {
            return spartOfNomen;
        }

        /**
         * @param spartOfNomen The spart_of_nomen
         */
        public void setSpartOfNomen(String spartOfNomen) {
            this.spartOfNomen = spartOfNomen;
        }

        /**
         * @return The spartOfModif
         */
        public String getSpartOfModif() {
            return spartOfModif;
        }

        /**
         * @param spartOfModif The spart_of_modif
         */
        public void setSpartOfModif(String spartOfModif) {
            this.spartOfModif = spartOfModif;
        }

        /**
         * @return The nquantSpec
         */
        public Double getNquantSpec() {
            return nquantSpec;
        }

        /**
         * @param nquantSpec The nquant_spec
         */
        public void setNquantSpec(Double nquantSpec) {
            this.nquantSpec = nquantSpec;
        }

        /**
         * @return The nquantProd
         */
        public Double getNquantProd() {
            return nquantProd;
        }

        /**
         * @param nquantProd The nquant_prod
         */
        public void setNquantProd(Double nquantProd) {
            this.nquantProd = nquantProd;
        }

        /**
         * @return The nquantPlan
         */
        public Double getNquantPlan() {
            return nquantPlan;
        }

        /**
         * @param nquantPlan The nquant_plan
         */
        public void setNquantPlan(Double nquantPlan) {
            this.nquantPlan = nquantPlan;
        }

        /**
         * @return The nquantCmpl
         */
        public Double getNquantCmpl() {
            return nquantCmpl;
        }

        /**
         * @param nquantCmpl The nquant_cmpl
         */
        public void setNquantCmpl(Double nquantCmpl) {
            this.nquantCmpl = nquantCmpl;
        }

        /**
         * @return The nquantDlvr
         */
        public Double getNquantDlvr() {
            return nquantDlvr;
        }

        /**
         * @param nquantDlvr The nquant_dlvr
         */
        public void setNquantDlvr(Double nquantDlvr) {
            this.nquantDlvr = nquantDlvr;
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
         * @return The srack
         */
        public String getSrack() {
            return srack;
        }

        /**
         * @param srack The srack
         */
        public void setSrack(String srack) {
            this.srack = srack;
        }

        /**
         * @return The squant
         */
        public String getSquant() {
            return squant;
        }

        /**
         * @param squant The squant
         */
        public void setSquant(String squant) {
            this.squant = squant;
        }


    }

}
