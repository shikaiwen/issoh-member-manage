package org.jeecg.modules.issohadmin.assetsrequest.service.impl;

import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import org.jeecg.modules.issohadmin.assetsrequest.mapper.IssohAssetsRequestDetailMapper;
import org.jeecg.modules.issohadmin.assetsrequest.service.IIssohAssetsRequestDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 資産申請明細
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@Service
public class IssohAssetsRequestDetailServiceImpl extends ServiceImpl<IssohAssetsRequestDetailMapper, IssohAssetsRequestDetail> implements IIssohAssetsRequestDetailService {
	
	@Autowired
	private IssohAssetsRequestDetailMapper issohAssetsRequestDetailMapper;
	
	@Override
	public List<IssohAssetsRequestDetail> selectByMainId(String mainId) {
		return issohAssetsRequestDetailMapper.selectByMainId(mainId);
	}
}
