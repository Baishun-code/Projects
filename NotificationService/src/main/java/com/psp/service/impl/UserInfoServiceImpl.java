package com.psp.service.impl;

import com.psp.entity.TdUserContactInfo;
import com.psp.mapper.TdUserContactInfoMapper;
import com.psp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private TdUserContactInfoMapper tdUserContactInfoMapper;

    @Override
    public TdUserContactInfo getUserInfo(String userId, String type) {
        return tdUserContactInfoMapper.queryUserInfoByUserId(userId, type);
    }
}
