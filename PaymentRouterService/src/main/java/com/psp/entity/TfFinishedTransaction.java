package com.psp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tf_finished_transaction
 * @author 
 */
@Data
public class TfFinishedTransaction implements Serializable {
    private String serialNo;

    private Date insertDt;

    private static final long serialVersionUID = 1L;


    public TfFinishedTransaction(String serialNo, Date insertDt) {
        this.serialNo = serialNo;
        this.insertDt = insertDt;
    }
}