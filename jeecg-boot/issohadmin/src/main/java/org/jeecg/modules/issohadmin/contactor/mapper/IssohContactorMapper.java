package org.jeecg.modules.issohadmin.contactor.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.issohadmin.contactor.entity.IssohContactor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.issohadmin.contactor.vo.IssohContactorVo;
import org.jeecg.modules.issohadmin.member.entity.IssohMember;
import org.jeecg.modules.issohadmin.member.vo.IssohMemberVo;

/**
 * @Description: 連絡情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
public interface IssohContactorMapper extends BaseMapper<IssohContactor> {

    public Page<IssohContactorVo> mySelectPage(Page page,
                                               @Param(Constants.WRAPPER) QueryWrapper queryWrapper, @Param("extraParam") Map extraParam
    );
}
