package ru.sibek.parus.mappers.outvoices;

import com.google.gson.annotations.Expose;

/**
 * Created by argen666 on 12.05.2015.
 */
public class Nquant {
    @Expose
    String NQUANT;

    public String getNQUANT() {
        return NQUANT;
    }

    public void setNQUANT(String NQUANT) {
        this.NQUANT = NQUANT;
    }
}
