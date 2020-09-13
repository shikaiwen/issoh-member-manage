package org.jeecg.modules.issohadmin.assets.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 資産備品
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
public interface IIssohAssetsService extends IService<IssohAssets> {

    public Page<IssohAssets> selectPage(Page<IssohAssets> page, QueryWrapper<IssohAssets> queryWrapper);

}
