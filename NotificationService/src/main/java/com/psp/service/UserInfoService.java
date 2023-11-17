package com.psp.service;

import com.psp.entity.TdUserContactInfo;

public interface UserInfoService {

    TdUserContactInfo getUserInfo(String userId, String type);


}
