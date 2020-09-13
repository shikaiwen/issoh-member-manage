package org.jeecg.modules.issohadmin.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import org.jeecg.modules.issohadmin.assets.mapper.IssohAssetsMapper;
import org.jeecg.modules.issohadmin.assets.service.IIssohAssetsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 資産備品
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
@Service
public class IssohAssetsServiceImpl extends ServiceImpl<IssohAssetsMapper, IssohAssets> implements IIssohAssetsService {

    @Override
    public Page<IssohAssets> selectPage(Page<IssohAssets> page, QueryWrapper<IssohAssets> queryWrapper) {

        return null;
    }
}
