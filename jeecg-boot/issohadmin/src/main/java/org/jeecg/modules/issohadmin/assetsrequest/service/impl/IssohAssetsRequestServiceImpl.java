package org.jeecg.modules.issohadmin.assetsrequest.service.impl;

import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequest;
import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import org.jeecg.modules.issohadmin.assetsrequest.mapper.IssohAssetsRequestDetailMapper;
import org.jeecg.modules.issohadmin.assetsrequest.mapper.IssohAssetsRequestMapper;
import org.jeecg.modules.issohadmin.assetsrequest.service.IIssohAssetsRequestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 資産申請記録
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@Service
public class IssohAssetsRequestServiceImpl extends ServiceImpl<IssohAssetsRequestMapper, IssohAssetsRequest> implements IIssohAssetsRequestService {

	@Autowired
	private IssohAssetsRequestMapper issohAssetsRequestMapper;
	@Autowired
	private IssohAssetsRequestDetailMapper issohAssetsRequestDetailMapper;
	
	@Override
	@Transactional
	public void saveMain(IssohAssetsRequest issohAssetsRequest, List<IssohAssetsRequestDetail> issohAssetsRequestDetailList) {
		issohAssetsRequestMapper.insert(issohAssetsRequest);
		if(issohAssetsRequestDetailList!=null && issohAssetsRequestDetailList.size()>0) {
			for(IssohAssetsRequestDetail entity:issohAssetsRequestDetailList) {
				//外键设置
				entity.setIssohAssetsRequestId(issohAssetsRequest.getId());
				issohAssetsRequestDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(IssohAssetsRequest issohAssetsRequest,List<IssohAssetsRequestDetail> issohAssetsRequestDetailList) {
		issohAssetsRequestMapper.updateById(issohAssetsRequest);
		
		//1.先删除子表数据
		issohAssetsRequestDetailMapper.deleteByMainId(issohAssetsRequest.getId());
		
		//2.子表数据重新插入
		if(issohAssetsRequestDetailList!=null && issohAssetsRequestDetailList.size()>0) {
			for(IssohAssetsRequestDetail entity:issohAssetsRequestDetailList) {
				//外键设置
				entity.setIssohAssetsRequestId(issohAssetsRequest.getId());
				issohAssetsRequestDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		issohAssetsRequestDetailMapper.deleteByMainId(id);
		issohAssetsRequestMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			issohAssetsRequestDetailMapper.deleteByMainId(id.toString());
			issohAssetsRequestMapper.deleteById(id);
		}
	}
	
}
