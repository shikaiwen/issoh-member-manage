package org.jeecg.modules.issohadmin.member.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import org.jeecg.modules.issohadmin.member.entity.IssohMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.issohadmin.member.vo.IssohMemberVo;

/**
 * @Description: 社員
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
public interface IssohMemberMapper extends BaseMapper<IssohMember> {


    public Page<IssohMemberVo> mySelectPage(Page<IssohMember> page,
                                            @Param(Constants.WRAPPER) QueryWrapper<IssohMember> queryWrapper, @Param("extraParam") Map extraParam
    );

}
