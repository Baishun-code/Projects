package com.psp.mapper;

import com.psp.entity.TdUserContactInfo;
import org.apache.ibatis.annotations.Param;

public interface TdUserContactInfoMapper {
    int insert(TdUserContactInfo record);

    int insertSelective(TdUserContactInfo record);

    TdUserContactInfo queryUserInfoByUserId(@Param("userId") String userId,
                                            @Param("type") String type);
}