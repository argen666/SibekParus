package ru.sibek.parus.mappers;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.sibek.parus.rest.ParusDate;
import ru.sibek.parus.sqlite.InvoiceProvider;

public class Invoices/* implements IContentValues*/ {


    @Expose
    private List<ItemInvoice> items = new ArrayList<ItemInvoice>();

    public List<ItemInvoice> getItems() {
        return items;
    }

    public void setItems(List<ItemInvoice> items) {
        this.items = items;
    }


    //@Override
    public ContentValues[] toContentValues() {

        final List<ContentValues> contentValuesList = new ArrayList<>();
        List<ItemInvoice> items = this.getItems();
        for (ItemInvoice item:items)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(InvoiceProvider.Columns.SNUMB,item.getSnumb());
            contentValues.put(InvoiceProvider.Columns.SDOCTYPE,item.getSdoctype());
            contentValues.put(InvoiceProvider.Columns.SPREF,item.getSpref());
            contentValues.put(InvoiceProvider.Columns.DDOC_DATE, ParusDate.parse(item.getDdocDate()).getTime());
            contentValues.put(InvoiceProvider.Columns.SAGENT,item.getSagent());
            contentValues.put(InvoiceProvider.Columns.NSUMMTAX,item.getNsummtax());
            contentValues.put(InvoiceProvider.Columns.NSTATUS,item.getNstatus());
            contentValues.put(InvoiceProvider.Columns.LOCAL_NSTATUS, item.getNstatus());
            contentValues.put(InvoiceProvider.Columns.SSTATUS,item.getSstatus());
            contentValues.put(InvoiceProvider.Columns.NRN,item.getNrn());
            contentValues.put(InvoiceProvider.Columns.NCOMPANY,item.getNcompany());
            contentValuesList.add(contentValues);
        }

        return contentValuesList.toArray(new ContentValues[contentValuesList.size()]);
    }

    public class ItemInvoice {

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ItemInvoice that = (ItemInvoice) o;

            if (ddocDate != null ? !ddocDate.equals(that.ddocDate) : that.ddocDate != null)
                return false;
            if (dindocDate != null ? !dindocDate.equals(that.dindocDate) : that.dindocDate != null)
                return false;
            if (doutdocDate != null ? !doutdocDate.equals(that.doutdocDate) : that.doutdocDate != null)
                return false;
            if (dvalidDocdate != null ? !dvalidDocdate.equals(that.dvalidDocdate) : that.dvalidDocdate != null)
                return false;
            if (dworkDate != null ? !dworkDate.equals(that.dworkDate) : that.dworkDate != null)
                return false;
            if (nagent != null ? !nagent.equals(that.nagent) : that.nagent != null) return false;
            if (nagentCat != null ? !nagentCat.equals(that.nagentCat) : that.nagentCat != null)
                return false;
            if (ncompany != null ? !ncompany.equals(that.ncompany) : that.ncompany != null)
                return false;
            if (ncrn != null ? !ncrn.equals(that.ncrn) : that.ncrn != null) return false;
            if (ncurbasecours != null ? !ncurbasecours.equals(that.ncurbasecours) : that.ncurbasecours != null)
                return false;
            if (ncurcours != null ? !ncurcours.equals(that.ncurcours) : that.ncurcours != null)
                return false;
            if (ncurrency != null ? !ncurrency.equals(that.ncurrency) : that.ncurrency != null)
                return false;
            if (ndiscount != null ? !ndiscount.equals(that.ndiscount) : that.ndiscount != null)
                return false;
            if (ndoctype != null ? !ndoctype.equals(that.ndoctype) : that.ndoctype != null)
                return false;
            if (nfaBasecours != null ? !nfaBasecours.equals(that.nfaBasecours) : that.nfaBasecours != null)
                return false;
            if (nfaCours != null ? !nfaCours.equals(that.nfaCours) : that.nfaCours != null)
                return false;
            if (nfaCurrency != null ? !nfaCurrency.equals(that.nfaCurrency) : that.nfaCurrency != null)
                return false;
            if (nfaceacc != null ? !nfaceacc.equals(that.nfaceacc) : that.nfaceacc != null)
                return false;
            if (nfaceaccCat != null ? !nfaceaccCat.equals(that.nfaceaccCat) : that.nfaceaccCat != null)
                return false;
            if (nfactpaysumm != null ? !nfactpaysumm.equals(that.nfactpaysumm) : that.nfactpaysumm != null)
                return false;
            if (nisSigned != null ? !nisSigned.equals(that.nisSigned) : that.nisSigned != null)
                return false;
            if (njurPers != null ? !njurPers.equals(that.njurPers) : that.njurPers != null)
                return false;
            if (nplanpaysumm != null ? !nplanpaysumm.equals(that.nplanpaysumm) : that.nplanpaysumm != null)
                return false;
            if (nrn != null ? !nrn.equals(that.nrn) : that.nrn != null) return false;
            if (nservactSign != null ? !nservactSign.equals(that.nservactSign) : that.nservactSign != null)
                return false;
            if (nsigntax != null ? !nsigntax.equals(that.nsigntax) : that.nsigntax != null)
                return false;
            if (nstatus != null ? !nstatus.equals(that.nstatus) : that.nstatus != null)
                return false;
            if (nstore != null ? !nstore.equals(that.nstore) : that.nstore != null) return false;
            if (nstoreoper != null ? !nstoreoper.equals(that.nstoreoper) : that.nstoreoper != null)
                return false;
            if (nsumm != null ? !nsumm.equals(that.nsumm) : that.nsumm != null) return false;
            if (nsummOrders != null ? !nsummOrders.equals(that.nsummOrders) : that.nsummOrders != null)
                return false;
            if (nsummtax != null ? !nsummtax.equals(that.nsummtax) : that.nsummtax != null)
                return false;
            if (nvalidDoctype != null ? !nvalidDoctype.equals(that.nvalidDoctype) : that.nvalidDoctype != null)
                return false;
            if (sagent != null ? !sagent.equals(that.sagent) : that.sagent != null) return false;
            if (sagentName != null ? !sagentName.equals(that.sagentName) : that.sagentName != null)
                return false;
            if (scurrency != null ? !scurrency.equals(that.scurrency) : that.scurrency != null)
                return false;
            if (sdoctype != null ? !sdoctype.equals(that.sdoctype) : that.sdoctype != null)
                return false;
            if (sfaCurrency != null ? !sfaCurrency.equals(that.sfaCurrency) : that.sfaCurrency != null)
                return false;
            if (sfaceacc != null ? !sfaceacc.equals(that.sfaceacc) : that.sfaceacc != null)
                return false;
            if (sjurPers != null ? !sjurPers.equals(that.sjurPers) : that.sjurPers != null)
                return false;
            if (snumb != null ? !snumb.equals(that.snumb) : that.snumb != null) return false;
            if (sparty != null ? !sparty.equals(that.sparty) : that.sparty != null) return false;
            if (spref != null ? !spref.equals(that.spref) : that.spref != null) return false;
            if (sstatus != null ? !sstatus.equals(that.sstatus) : that.sstatus != null)
                return false;
            if (sstore != null ? !sstore.equals(that.sstore) : that.sstore != null) return false;
            if (sstoreoper != null ? !sstoreoper.equals(that.sstoreoper) : that.sstoreoper != null)
                return false;
            if (svalidDocnumb != null ? !svalidDocnumb.equals(that.svalidDocnumb) : that.svalidDocnumb != null)
                return false;
            if (svalidDoctype != null ? !svalidDoctype.equals(that.svalidDoctype) : that.svalidDoctype != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = nrn != null ? nrn.hashCode() : 0;
            result = 31 * result + (ncompany != null ? ncompany.hashCode() : 0);
            result = 31 * result + (ncrn != null ? ncrn.hashCode() : 0);
            result = 31 * result + (njurPers != null ? njurPers.hashCode() : 0);
            result = 31 * result + (sjurPers != null ? sjurPers.hashCode() : 0);
            result = 31 * result + (ndoctype != null ? ndoctype.hashCode() : 0);
            result = 31 * result + (sdoctype != null ? sdoctype.hashCode() : 0);
            result = 31 * result + (spref != null ? spref.hashCode() : 0);
            result = 31 * result + (snumb != null ? snumb.hashCode() : 0);
            result = 31 * result + (ddocDate != null ? ddocDate.hashCode() : 0);
            result = 31 * result + (nstatus != null ? nstatus.hashCode() : 0);
            result = 31 * result + (sstatus != null ? sstatus.hashCode() : 0);
            result = 31 * result + (dworkDate != null ? dworkDate.hashCode() : 0);
            result = 31 * result + (nservactSign != null ? nservactSign.hashCode() : 0);
            result = 31 * result + (nvalidDoctype != null ? nvalidDoctype.hashCode() : 0);
            result = 31 * result + (svalidDoctype != null ? svalidDoctype.hashCode() : 0);
            result = 31 * result + (svalidDocnumb != null ? svalidDocnumb.hashCode() : 0);
            result = 31 * result + (dvalidDocdate != null ? dvalidDocdate.hashCode() : 0);
            result = 31 * result + (nstore != null ? nstore.hashCode() : 0);
            result = 31 * result + (sstore != null ? sstore.hashCode() : 0);
            result = 31 * result + (nfaceacc != null ? nfaceacc.hashCode() : 0);
            result = 31 * result + (sfaceacc != null ? sfaceacc.hashCode() : 0);
            result = 31 * result + (nfaceaccCat != null ? nfaceaccCat.hashCode() : 0);
            result = 31 * result + (nfaCurrency != null ? nfaCurrency.hashCode() : 0);
            result = 31 * result + (sfaCurrency != null ? sfaCurrency.hashCode() : 0);
            result = 31 * result + (nagent != null ? nagent.hashCode() : 0);
            result = 31 * result + (sagent != null ? sagent.hashCode() : 0);
            result = 31 * result + (sagentName != null ? sagentName.hashCode() : 0);
            result = 31 * result + (nagentCat != null ? nagentCat.hashCode() : 0);
            result = 31 * result + (ncurrency != null ? ncurrency.hashCode() : 0);
            result = 31 * result + (scurrency != null ? scurrency.hashCode() : 0);
            result = 31 * result + (ncurcours != null ? ncurcours.hashCode() : 0);
            result = 31 * result + (ncurbasecours != null ? ncurbasecours.hashCode() : 0);
            result = 31 * result + (nfaCours != null ? nfaCours.hashCode() : 0);
            result = 31 * result + (nfaBasecours != null ? nfaBasecours.hashCode() : 0);
            result = 31 * result + (nsumm != null ? nsumm.hashCode() : 0);
            result = 31 * result + (nsummtax != null ? nsummtax.hashCode() : 0);
            result = 31 * result + (nplanpaysumm != null ? nplanpaysumm.hashCode() : 0);
            result = 31 * result + (nfactpaysumm != null ? nfactpaysumm.hashCode() : 0);
            result = 31 * result + (nsummOrders != null ? nsummOrders.hashCode() : 0);
            result = 31 * result + (nstoreoper != null ? nstoreoper.hashCode() : 0);
            result = 31 * result + (sstoreoper != null ? sstoreoper.hashCode() : 0);
            result = 31 * result + (nsigntax != null ? nsigntax.hashCode() : 0);
            result = 31 * result + (dindocDate != null ? dindocDate.hashCode() : 0);
            result = 31 * result + (ndiscount != null ? ndiscount.hashCode() : 0);
            result = 31 * result + (nisSigned != null ? nisSigned.hashCode() : 0);
            result = 31 * result + (sparty != null ? sparty.hashCode() : 0);
            result = 31 * result + (doutdocDate != null ? doutdocDate.hashCode() : 0);
            return result;
        }
    }

    @Override
    public String toString() {
        return "Invoices{" +
                "items=" + items.size() +
                '}';
    }
}
