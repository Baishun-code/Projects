package com.psp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * td_tx_service
 * @author 
 */
public class TdTxService implements Serializable {
    private String serName;

    private String interfaceType;

    private String reqUri;

    private Date insertDt;

    private Date updateDt;

    private static final long serialVersionUID = 1L;

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public Date getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(Date insertDt) {
        this.insertDt = insertDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}