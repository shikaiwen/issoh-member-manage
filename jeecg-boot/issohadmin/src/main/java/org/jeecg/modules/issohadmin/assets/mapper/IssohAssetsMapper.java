package org.jeecg.modules.issohadmin.assets.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 資産備品
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
public interface IssohAssetsMapper extends BaseMapper<IssohAssets> {


    public Page<IssohAssets> mySelectPage(Page<IssohAssets> page,
                                          @Param(Constants.WRAPPER)QueryWrapper<IssohAssets> queryWrapper,@Param("extraParam") Map extraParam
                                          );
}
