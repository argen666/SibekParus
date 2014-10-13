package ru.sibek.parus.mappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Invoices {


    @Expose
    private List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }





public class Item {

    @Expose
    private Long nrn;
    @Expose
    private Long ncompany;
    @Expose
    private Long ncrn;
    @SerializedName("njur_pers")
    @Expose
    private Long njurPers;
    @SerializedName("sjur_pers")
    @Expose
    private String sjurPers;
    @Expose
    private Long ndoctype;
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
    private Long nstatus;
    @Expose
    private String sstatus;
    @SerializedName("dwork_date")
    @Expose
    private String dworkDate;
    @SerializedName("nservact_sign")
    @Expose
    private Long nservactSign;
    @SerializedName("nvalid_doctype")
    @Expose
    private Long nvalidDoctype;
    @SerializedName("svalid_doctype")
    @Expose
    private String svalidDoctype;
    @SerializedName("svalid_docnumb")
    @Expose
    private String svalidDocnumb;
    @SerializedName("dvalid_docdate")
    @Expose
    private String dvalidDocdate;
    @Expose
    private Long nstore;
    @Expose
    private String sstore;
    @Expose
    private Long nfaceacc;
    @Expose
    private String sfaceacc;
    @SerializedName("nfaceacc_cat")
    @Expose
    private Long nfaceaccCat;
    @SerializedName("nfa_currency")
    @Expose
    private Long nfaCurrency;
    @SerializedName("sfa_currency")
    @Expose
    private String sfaCurrency;
    @Expose
    private Long nagent;
    @Expose
    private String sagent;
    @SerializedName("sagent_name")
    @Expose
    private String sagentName;
    @SerializedName("nagent_cat")
    @Expose
    private Long nagentCat;
    @Expose
    private Long ncurrency;
    @Expose
    private String scurrency;
    @Expose
    private Long ncurcours;
    @Expose
    private Long ncurbasecours;
    @SerializedName("nfa_cours")
    @Expose
    private Long nfaCours;
    @SerializedName("nfa_basecours")
    @Expose
    private Long nfaBasecours;
    @Expose
    private Double nsumm;
    @Expose
    private Double nsummtax;
    @Expose
    private Long nplanpaysumm;
    @Expose
    private Long nfactpaysumm;
    @SerializedName("nsumm_orders")
    @Expose
    private Double nsummOrders;
    @Expose
    private Long nstoreoper;
    @Expose
    private String sstoreoper;
    @Expose
    private Long nsigntax;
    @SerializedName("dindoc_date")
    @Expose
    private String dindocDate;
    @Expose
    private Long ndiscount;
    @SerializedName("nis_signed")
    @Expose
    private Long nisSigned;
    @Expose
    private String sparty;
    @SerializedName("doutdoc_date")
    @Expose
    private String doutdocDate;

    public Long getNrn() {
        return nrn;
    }

    public void setNrn(Long nrn) {
        this.nrn = nrn;
    }

    public Long getNcompany() {
        return ncompany;
    }

    public void setNcompany(Long ncompany) {
        this.ncompany = ncompany;
    }

    public Long getNcrn() {
        return ncrn;
    }

    public void setNcrn(Long ncrn) {
        this.ncrn = ncrn;
    }

    public Long getNjurPers() {
        return njurPers;
    }

    public void setNjurPers(Long njurPers) {
        this.njurPers = njurPers;
    }

    public String getSjurPers() {
        return sjurPers;
    }

    public void setSjurPers(String sjurPers) {
        this.sjurPers = sjurPers;
    }

    public Long getNdoctype() {
        return ndoctype;
    }

    public void setNdoctype(Long ndoctype) {
        this.ndoctype = ndoctype;
    }

    public String getSdoctype() {
        return sdoctype;
    }

    public void setSdoctype(String sdoctype) {
        this.sdoctype = sdoctype;
    }

    public String getSpref() {
        return spref;
    }

    public void setSpref(String spref) {
        this.spref = spref;
    }

    public String getSnumb() {
        return snumb;
    }

    public void setSnumb(String snumb) {
        this.snumb = snumb;
    }

    public String getDdocDate() {
        return ddocDate;
    }

    public void setDdocDate(String ddocDate) {
        this.ddocDate = ddocDate;
    }

    public Long getNstatus() {
        return nstatus;
    }

    public void setNstatus(Long nstatus) {
        this.nstatus = nstatus;
    }

    public String getSstatus() {
        return sstatus;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

    public String getDworkDate() {
        return dworkDate;
    }

    public void setDworkDate(String dworkDate) {
        this.dworkDate = dworkDate;
    }

    public Long getNservactSign() {
        return nservactSign;
    }

    public void setNservactSign(Long nservactSign) {
        this.nservactSign = nservactSign;
    }

    public Long getNvalidDoctype() {
        return nvalidDoctype;
    }

    public void setNvalidDoctype(Long nvalidDoctype) {
        this.nvalidDoctype = nvalidDoctype;
    }

    public String getSvalidDoctype() {
        return svalidDoctype;
    }

    public void setSvalidDoctype(String svalidDoctype) {
        this.svalidDoctype = svalidDoctype;
    }

    public String getSvalidDocnumb() {
        return svalidDocnumb;
    }

    public void setSvalidDocnumb(String svalidDocnumb) {
        this.svalidDocnumb = svalidDocnumb;
    }

    public String getDvalidDocdate() {
        return dvalidDocdate;
    }

    public void setDvalidDocdate(String dvalidDocdate) {
        this.dvalidDocdate = dvalidDocdate;
    }

    public Long getNstore() {
        return nstore;
    }

    public void setNstore(Long nstore) {
        this.nstore = nstore;
    }

    public String getSstore() {
        return sstore;
    }

    public void setSstore(String sstore) {
        this.sstore = sstore;
    }

    public Long getNfaceacc() {
        return nfaceacc;
    }

    public void setNfaceacc(Long nfaceacc) {
        this.nfaceacc = nfaceacc;
    }

    public String getSfaceacc() {
        return sfaceacc;
    }

    public void setSfaceacc(String sfaceacc) {
        this.sfaceacc = sfaceacc;
    }

    public Long getNfaceaccCat() {
        return nfaceaccCat;
    }

    public void setNfaceaccCat(Long nfaceaccCat) {
        this.nfaceaccCat = nfaceaccCat;
    }

    public Long getNfaCurrency() {
        return nfaCurrency;
    }

    public void setNfaCurrency(Long nfaCurrency) {
        this.nfaCurrency = nfaCurrency;
    }

    public String getSfaCurrency() {
        return sfaCurrency;
    }

    public void setSfaCurrency(String sfaCurrency) {
        this.sfaCurrency = sfaCurrency;
    }

    public Long getNagent() {
        return nagent;
    }

    public void setNagent(Long nagent) {
        this.nagent = nagent;
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

    public Long getNagentCat() {
        return nagentCat;
    }

    public void setNagentCat(Long nagentCat) {
        this.nagentCat = nagentCat;
    }

    public Long getNcurrency() {
        return ncurrency;
    }

    public void setNcurrency(Long ncurrency) {
        this.ncurrency = ncurrency;
    }

    public String getScurrency() {
        return scurrency;
    }

    public void setScurrency(String scurrency) {
        this.scurrency = scurrency;
    }

    public Long getNcurcours() {
        return ncurcours;
    }

    public void setNcurcours(Long ncurcours) {
        this.ncurcours = ncurcours;
    }

    public Long getNcurbasecours() {
        return ncurbasecours;
    }

    public void setNcurbasecours(Long ncurbasecours) {
        this.ncurbasecours = ncurbasecours;
    }

    public Long getNfaCours() {
        return nfaCours;
    }

    public void setNfaCours(Long nfaCours) {
        this.nfaCours = nfaCours;
    }

    public Long getNfaBasecours() {
        return nfaBasecours;
    }

    public void setNfaBasecours(Long nfaBasecours) {
        this.nfaBasecours = nfaBasecours;
    }

    public Double getNsumm() {
        return nsumm;
    }

    public void setNsumm(Double nsumm) {
        this.nsumm = nsumm;
    }

    public Double getNsummtax() {
        return nsummtax;
    }

    public void setNsummtax(Double nsummtax) {
        this.nsummtax = nsummtax;
    }

    public Long getNplanpaysumm() {
        return nplanpaysumm;
    }

    public void setNplanpaysumm(Long nplanpaysumm) {
        this.nplanpaysumm = nplanpaysumm;
    }

    public Long getNfactpaysumm() {
        return nfactpaysumm;
    }

    public void setNfactpaysumm(Long nfactpaysumm) {
        this.nfactpaysumm = nfactpaysumm;
    }

    public Double getNsummOrders() {
        return nsummOrders;
    }

    public void setNsummOrders(Double nsummOrders) {
        this.nsummOrders = nsummOrders;
    }

    public Long getNstoreoper() {
        return nstoreoper;
    }

    public void setNstoreoper(Long nstoreoper) {
        this.nstoreoper = nstoreoper;
    }

    public String getSstoreoper() {
        return sstoreoper;
    }

    public void setSstoreoper(String sstoreoper) {
        this.sstoreoper = sstoreoper;
    }

    public Long getNsigntax() {
        return nsigntax;
    }

    public void setNsigntax(Long nsigntax) {
        this.nsigntax = nsigntax;
    }

    public String getDindocDate() {
        return dindocDate;
    }

    public void setDindocDate(String dindocDate) {
        this.dindocDate = dindocDate;
    }

    public Long getNdiscount() {
        return ndiscount;
    }

    public void setNdiscount(Long ndiscount) {
        this.ndiscount = ndiscount;
    }

    public Long getNisSigned() {
        return nisSigned;
    }

    public void setNisSigned(Long nisSigned) {
        this.nisSigned = nisSigned;
    }

    public String getSparty() {
        return sparty;
    }

    public void setSparty(String sparty) {
        this.sparty = sparty;
    }

    public String getDoutdocDate() {
        return doutdocDate;
    }

    public void setDoutdocDate(String doutdocDate) {
        this.doutdocDate = doutdocDate;
    }



}
}
