package org.jeecg.modules.issohadmin.assetsrequest.mapper;

import java.util.List;
import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 資産申請明細
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface IssohAssetsRequestDetailMapper extends BaseMapper<IssohAssetsRequestDetail> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<IssohAssetsRequestDetail> selectByMainId(@Param("mainId") String mainId);
}
