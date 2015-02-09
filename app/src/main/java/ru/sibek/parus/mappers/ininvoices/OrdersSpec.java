package ru.sibek.parus.mappers.ininvoices;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.sqlite.ininvoices.InvoiceSpecProvider;

public class OrdersSpec /*implements IContentValues*/ {

    @Expose
    private List<ItemInvoiceSpec> items = new ArrayList<ItemInvoiceSpec>();

    public List<ItemInvoiceSpec> getItems() {
        return items;
    }

    public void setItems(List<ItemInvoiceSpec> items) {
        this.items = items;
    }

    //@Override
    public ContentValues[] toContentValues(String invoiceID) {
        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<ItemInvoiceSpec> items = this.getItems();
        for (ItemInvoiceSpec item : items) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(InvoiceSpecProvider.Columns.INVOICE_ID, invoiceID);
            contentValues.put(InvoiceSpecProvider.Columns.NRN, item.getNrn());
            contentValues.put(InvoiceSpecProvider.Columns.NPRN, item.getNprn());
            contentValues.put(InvoiceSpecProvider.Columns.SNOMEN, item.getSnomen());
            contentValues.put(InvoiceSpecProvider.Columns.SNOMENNAME, item.getSnomenname());
            //contentValues.put(InvoiceSpecProvider.Columns.DDOC_DATE, ParusDate.parse(item.getDdocDate()).getTime());
            contentValues.put(InvoiceSpecProvider.Columns.SSERNUMB, item.getSsernumb());
            contentValues.put(InvoiceSpecProvider.Columns.NSUMMTAX, item.getNsummtax());
            contentValues.put(InvoiceSpecProvider.Columns.NQUANT, item.getNquant());
            contentValues.put(InvoiceSpecProvider.Columns.NPRICE, item.getNprice());
            contentValues.put(InvoiceSpecProvider.Columns.SSTORE, item.getSstore());
            contentValues.put(InvoiceSpecProvider.Columns.NSTORE, item.getNstore());
            contentValues.put(InvoiceSpecProvider.Columns.SMEAS_MAIN, item.getSmeasMain());
            contentValues.put(InvoiceSpecProvider.Columns.SNOTE, item.getSnote());
            contentValues.put(InvoiceSpecProvider.Columns.NRACK, item.getNrack());
            contentValues.put(InvoiceSpecProvider.Columns.SRACK, item.getSrack());
            contentValues.put(InvoiceSpecProvider.Columns.SCELL, item.getScell());
            contentValues.put(InvoiceSpecProvider.Columns.NDISTRIBUTION_SIGN, item.getNdistribution_sign());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }


    public class ItemInvoiceSpec {

        @Expose
        private Integer nrn;
        @Expose
        private Integer nprn;
        @Expose
        private Integer ncompany;
        @Expose
        private Integer ncrn;
        @Expose
        private Integer nnomen;
        @Expose
        private String snomen;
        @Expose
        private String snote;
        @Expose
        private String ssernumb;
        @Expose
        private String snomenname;
        @SerializedName("nnomen_type")
        @Expose
        private Integer nnomenType;
        @SerializedName("nnomen_cat")
        @Expose
        private Integer nnomenCat;
        @SerializedName("nnomen_liquid")
        @Expose
        private Integer nnomenLiquid;
        @Expose
        private Integer nmodif;
        @Expose
        private String smodif;
        @Expose
        private String smodifname;
        @Expose
        private Integer ntaxgr;
        @Expose
        private String staxgr;

        @Expose
        private Integer nrack;
        @Expose
        private String srack;
        @Expose
        private String scell;

        @Expose
        private Integer ndistribution_sign;


        @Expose
        private Integer nstore;
        @Expose
        private String sstore;
        @SerializedName("smeas_main")
        @Expose
        private String smeasMain;
        @SerializedName("nm_meas_category")
        @Expose
        private Integer nmMeasCategory;
        @Expose
        private double nquant;
        @Expose
        private Integer nquantalt;
        @SerializedName("nquant_volume")
        @Expose
        private Integer nquantVolume;
        @Expose
        private Integer ndensity;
        @SerializedName("nquant_weight")
        @Expose
        private Integer nquantWeight;
        @Expose
        private Double nprice;
        @Expose
        private Integer npricemeas;
        @Expose
        private Double nsumm;
        @Expose
        private Double nsummtax;
        @SerializedName("nsumm_nds")
        @Expose
        private Double nsummNds;
        @SerializedName("nautocalc_sign")
        @Expose
        private Integer nautocalcSign;
        @Expose
        private String scurrency;
        @SerializedName("nmu_weight")
        @Expose
        private Integer nmuWeight;
        @SerializedName("nmu_size")
        @Expose
        private Integer nmuSize;
        @Expose
        private Integer ndiscount;

        public Integer getNrack() {
            return nrack;
        }

        public void setNrack(Integer nrack) {
            this.nrack = nrack;
        }

        public Integer getNdistribution_sign() {
            return ndistribution_sign;
        }

        public void setNdistribution_sign(Integer ndistribution_sign) {
            this.ndistribution_sign = ndistribution_sign;
        }

        public String getSnote() {
            return snote;
        }

        public void setSnote(String snote) {
            this.snote = snote;
        }

        public String getSsernumb() {
            return ssernumb;
        }

        public void setSsernumb(String ssernumb) {
            this.ssernumb = ssernumb;
        }

        public String getSrack() {
            return srack;
        }

        public void setSrack(String srack) {
            this.srack = srack;
        }

        public String getScell() {
            return scell;
        }

        public void setScell(String scell) {
            this.scell = scell;
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
         * @return The ncrn
         */
        public Integer getNcrn() {
            return ncrn;
        }

        /**
         * @param ncrn The ncrn
         */
        public void setNcrn(Integer ncrn) {
            this.ncrn = ncrn;
        }

        /**
         * @return The nnomen
         */
        public Integer getNnomen() {
            return nnomen;
        }

        /**
         * @param nnomen The nnomen
         */
        public void setNnomen(Integer nnomen) {
            this.nnomen = nnomen;
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

        /**
         * @return The nnomenType
         */
        public Integer getNnomenType() {
            return nnomenType;
        }

        /**
         * @param nnomenType The nnomen_type
         */
        public void setNnomenType(Integer nnomenType) {
            this.nnomenType = nnomenType;
        }

        /**
         * @return The nnomenCat
         */
        public Integer getNnomenCat() {
            return nnomenCat;
        }

        /**
         * @param nnomenCat The nnomen_cat
         */
        public void setNnomenCat(Integer nnomenCat) {
            this.nnomenCat = nnomenCat;
        }

        /**
         * @return The nnomenLiquid
         */
        public Integer getNnomenLiquid() {
            return nnomenLiquid;
        }

        /**
         * @param nnomenLiquid The nnomen_liquid
         */
        public void setNnomenLiquid(Integer nnomenLiquid) {
            this.nnomenLiquid = nnomenLiquid;
        }

        /**
         * @return The nmodif
         */
        public Integer getNmodif() {
            return nmodif;
        }

        /**
         * @param nmodif The nmodif
         */
        public void setNmodif(Integer nmodif) {
            this.nmodif = nmodif;
        }

        /**
         * @return The smodif
         */
        public String getSmodif() {
            return smodif;
        }

        /**
         * @param smodif The smodif
         */
        public void setSmodif(String smodif) {
            this.smodif = smodif;
        }

        /**
         * @return The smodifname
         */
        public String getSmodifname() {
            return smodifname;
        }

        /**
         * @param smodifname The smodifname
         */
        public void setSmodifname(String smodifname) {
            this.smodifname = smodifname;
        }

        /**
         * @return The ntaxgr
         */
        public Integer getNtaxgr() {
            return ntaxgr;
        }

        /**
         * @param ntaxgr The ntaxgr
         */
        public void setNtaxgr(Integer ntaxgr) {
            this.ntaxgr = ntaxgr;
        }

        /**
         * @return The staxgr
         */
        public String getStaxgr() {
            return staxgr;
        }

        /**
         * @param staxgr The staxgr
         */
        public void setStaxgr(String staxgr) {
            this.staxgr = staxgr;
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
         * @return The nmMeasCategory
         */
        public Integer getNmMeasCategory() {
            return nmMeasCategory;
        }

        /**
         * @param nmMeasCategory The nm_meas_category
         */
        public void setNmMeasCategory(Integer nmMeasCategory) {
            this.nmMeasCategory = nmMeasCategory;
        }

        /**
         * @return The nquant
         */
        public Double getNquant() {
            return nquant;
        }

        /**
         * @param nquant The nquant
         */
        public void setNquant(Double nquant) {
            this.nquant = nquant;
        }

        /**
         * @return The nquantalt
         */
        public Integer getNquantalt() {
            return nquantalt;
        }

        /**
         * @param nquantalt The nquantalt
         */
        public void setNquantalt(Integer nquantalt) {
            this.nquantalt = nquantalt;
        }

        /**
         * @return The nquantVolume
         */
        public Integer getNquantVolume() {
            return nquantVolume;
        }

        /**
         * @param nquantVolume The nquant_volume
         */
        public void setNquantVolume(Integer nquantVolume) {
            this.nquantVolume = nquantVolume;
        }

        /**
         * @return The ndensity
         */
        public Integer getNdensity() {
            return ndensity;
        }

        /**
         * @param ndensity The ndensity
         */
        public void setNdensity(Integer ndensity) {
            this.ndensity = ndensity;
        }

        /**
         * @return The nquantWeight
         */
        public Integer getNquantWeight() {
            return nquantWeight;
        }

        /**
         * @param nquantWeight The nquant_weight
         */
        public void setNquantWeight(Integer nquantWeight) {
            this.nquantWeight = nquantWeight;
        }

        /**
         * @return The nprice
         */
        public Double getNprice() {
            return nprice;
        }

        /**
         * @param nprice The nprice
         */
        public void setNprice(Double nprice) {
            this.nprice = nprice;
        }

        /**
         * @return The npricemeas
         */
        public Integer getNpricemeas() {
            return npricemeas;
        }

        /**
         * @param npricemeas The npricemeas
         */
        public void setNpricemeas(Integer npricemeas) {
            this.npricemeas = npricemeas;
        }

        /**
         * @return The nsumm
         */
        public Double getNsumm() {
            return nsumm;
        }

        /**
         * @param nsumm The nsumm
         */
        public void setNsumm(Double nsumm) {
            this.nsumm = nsumm;
        }

        /**
         * @return The nsummtax
         */
        public Double getNsummtax() {
            return nsummtax;
        }

        /**
         * @param nsummtax The nsummtax
         */
        public void setNsummtax(Double nsummtax) {
            this.nsummtax = nsummtax;
        }

        /**
         * @return The nsummNds
         */
        public Double getNsummNds() {
            return nsummNds;
        }

        /**
         * @param nsummNds The nsumm_nds
         */
        public void setNsummNds(Double nsummNds) {
            this.nsummNds = nsummNds;
        }

        /**
         * @return The nautocalcSign
         */
        public Integer getNautocalcSign() {
            return nautocalcSign;
        }

        /**
         * @param nautocalcSign The nautocalc_sign
         */
        public void setNautocalcSign(Integer nautocalcSign) {
            this.nautocalcSign = nautocalcSign;
        }

        /**
         * @return The scurrency
         */
        public String getScurrency() {
            return scurrency;
        }

        /**
         * @param scurrency The scurrency
         */
        public void setScurrency(String scurrency) {
            this.scurrency = scurrency;
        }

        /**
         * @return The nmuWeight
         */
        public Integer getNmuWeight() {
            return nmuWeight;
        }

        /**
         * @param nmuWeight The nmu_weight
         */
        public void setNmuWeight(Integer nmuWeight) {
            this.nmuWeight = nmuWeight;
        }

        /**
         * @return The nmuSize
         */
        public Integer getNmuSize() {
            return nmuSize;
        }

        /**
         * @param nmuSize The nmu_size
         */
        public void setNmuSize(Integer nmuSize) {
            this.nmuSize = nmuSize;
        }

        /**
         * @return The ndiscount
         */
        public Integer getNdiscount() {
            return ndiscount;
        }

        /**
         * @param ndiscount The ndiscount
         */
        public void setNdiscount(Integer ndiscount) {
            this.ndiscount = ndiscount;
        }

    }

    @Override
    public String toString() {
        return "InvoicesSpec{" +
                "items=" + items.size() +
                '}';
    }
}