package com.squareup.otto.parus;

/**
 * Created by Developer on 24.05.2015.
 */
public class InvoiceChangedEvent {
    private Integer NRN;
    private String SMSG;

    public InvoiceChangedEvent(Integer NRN, String SMSG) {
        this.NRN = NRN;
        this.SMSG = SMSG;
    }

    public Integer getNRN() {
        return NRN;
    }

    public String getSMSG() {
        return SMSG;
    }
}
