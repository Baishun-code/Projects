package com.psp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * td_user_contact_info
 * @author 
 */
@Data
public class TdUserContactInfo implements Serializable {
    private String userId;

    private String userName;

    private String contactType;

    private String infoDetail;

    private static final long serialVersionUID = 1L;
}