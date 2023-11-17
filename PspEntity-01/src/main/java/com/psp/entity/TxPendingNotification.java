package com.psp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tx_pending_notification
 * @author 
 */
@Data
public class TxPendingNotification implements Serializable {
    private String userId;

    private int serialNo;

    private String acctId;

    private String notiType;

    private String content;

    private String notiStatus;

    private Date insertDt;

    private static final long serialVersionUID = 1L;
}