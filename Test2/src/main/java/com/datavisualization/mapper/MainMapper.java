package com.datavisualization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datavisualization.domain.TestUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MainMapper extends BaseMapper<TestUser> {
    @Select("select id,name,age from testuser")
    List<TestUser> getUserList();
}
