package org.jeecg.modules.issohadmin.assetsrequest.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 資産申請明細
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@ApiModel(value="issoh_assets_request对象", description="資産申請記録")
@Data
@TableName("issoh_assets_request_detail")
public class IssohAssetsRequestDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**完成状態*/
	@Excel(name = "完成状態", width = 15, dicCode = "asset_request_status")
	@ApiModelProperty(value = "完成状態")
	@Dict(dicCode = "asset_request_status")
	private java.lang.String status;
	/**資産ID*/
	@Excel(name = "資産ID", width = 15)
	@ApiModelProperty(value = "資産ID")
	private java.lang.String assetsId;
	/**資産名*/
	@Excel(name = "資産名", width = 15)
	@ApiModelProperty(value = "資産名")
	private java.lang.String assetsName;
	/**備考*/
	@Excel(name = "備考", width = 15)
	@ApiModelProperty(value = "備考")
	private java.lang.String remarks;
	/**申請id*/
	@ApiModelProperty(value = "申請id")
	private java.lang.String issohAssetsRequestId;
}
