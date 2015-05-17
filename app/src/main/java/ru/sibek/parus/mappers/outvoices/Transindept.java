package ru.sibek.parus.mappers.outvoices;

import com.google.gson.annotations.Expose;

/**
 * Created by argen666 on 12.05.2015.
 */
public class Transindept {
    @Expose
    String SSTORE;
    @Expose
    String SIN_STORE;

    public String getSSTORE() {
        return SSTORE;
    }

    public void setSSTORE(String SSTORE) {
        this.SSTORE = SSTORE;
    }

    public String getSIN_STORE() {
        return SIN_STORE;
    }

    public void setSIN_STORE(String SIN_STORE) {
        this.SIN_STORE = SIN_STORE;
    }
}
