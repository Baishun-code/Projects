package com.psp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * td_account_info
 * @author 
 */
public class TdAccountInfo implements Serializable {
    private String acctId;

    private String acctOwnerId;

    private String acctStatus;

    private Date openDate;

    private Date closeDate;

    private static final long serialVersionUID = 1L;

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctOwnerId() {
        return acctOwnerId;
    }

    public void setAcctOwnerId(String acctOwnerId) {
        this.acctOwnerId = acctOwnerId;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}