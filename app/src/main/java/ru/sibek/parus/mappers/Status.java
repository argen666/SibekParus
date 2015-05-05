package ru.sibek.parus.mappers;

import com.google.gson.annotations.Expose;

/**
 * Created by argen666 on 05.05.2015.
 */
public class Status {

    @Expose
    private Integer NRN;
    @Expose
    private String SMSG;

    /**
     * @return The NRN
     */
    public Integer getNRN() {
        return NRN;
    }

    /**
     * @param NRN The NRN
     */
    public void setNRN(Integer NRN) {
        this.NRN = NRN;
    }

    /**
     * @return The SMSG
     */
    public String getSMSG() {
        return SMSG;
    }

    /**
     * @param SMSG The SMSG
     */
    public void setSMSG(String SMSG) {
        this.SMSG = SMSG;
    }

}
