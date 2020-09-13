package org.jeecg.modules.issohadmin.assetsrequest.service;

import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 資産申請記録
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface IIssohAssetsRequestService extends IService<IssohAssetsRequest> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(IssohAssetsRequest issohAssetsRequest,List<IssohAssetsRequestDetail> issohAssetsRequestDetailList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(IssohAssetsRequest issohAssetsRequest,List<IssohAssetsRequestDetail> issohAssetsRequestDetailList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
