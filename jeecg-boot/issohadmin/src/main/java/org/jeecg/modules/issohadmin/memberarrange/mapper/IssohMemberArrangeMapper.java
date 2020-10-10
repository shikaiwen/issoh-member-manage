package org.jeecg.modules.issohadmin.memberarrange.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.issohadmin.member.entity.IssohMember;
import org.jeecg.modules.issohadmin.member.vo.IssohMemberVo;
import org.jeecg.modules.issohadmin.memberarrange.entity.IssohMemberArrange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.issohadmin.memberarrange.vo.IssohMemberArrangeVo;

/**
 * @Description: 入場情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
public interface IssohMemberArrangeMapper extends BaseMapper<IssohMemberArrange> {

    public Page<IssohMemberArrangeVo> mySelectPage(Page<IssohMemberArrange> page,
                                                   @Param(Constants.WRAPPER) QueryWrapper queryWrapper, @Param("extraParam") Map extraParam
    );

}
