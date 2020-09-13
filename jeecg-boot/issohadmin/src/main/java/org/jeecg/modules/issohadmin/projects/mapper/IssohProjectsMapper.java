package org.jeecg.modules.issohadmin.projects.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.issohadmin.memberarrange.entity.IssohMemberArrange;
import org.jeecg.modules.issohadmin.projects.entity.IssohProjects;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: プロジェクト
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
public interface IssohProjectsMapper extends BaseMapper<IssohProjects> {

    public Page<IssohProjects> mySelectPage(Page<IssohProjects> page,
                                                 @Param(Constants.WRAPPER) QueryWrapper queryWrapper, @Param("extraParam") Map extraParam
    );

}
