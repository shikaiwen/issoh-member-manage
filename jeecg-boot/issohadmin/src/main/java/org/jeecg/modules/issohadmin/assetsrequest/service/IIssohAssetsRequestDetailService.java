package org.jeecg.modules.issohadmin.assetsrequest.service;

import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 資産申請明細
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface IIssohAssetsRequestDetailService extends IService<IssohAssetsRequestDetail> {

	public List<IssohAssetsRequestDetail> selectByMainId(String mainId);
}
