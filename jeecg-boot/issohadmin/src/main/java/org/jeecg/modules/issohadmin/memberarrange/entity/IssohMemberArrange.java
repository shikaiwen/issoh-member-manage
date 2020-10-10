package org.jeecg.modules.issohadmin.memberarrange.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 入場情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Data
@TableName("issoh_member_arrange")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_member_arrange对象", description="入場情報")
public class IssohMemberArrange implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**社員ID*/
	@Excel(name = "社員ID", width = 15)
    @ApiModelProperty(value = "社員ID")
    private String memberId;

	/**入場時間*/
	@Excel(name = "入場時間", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "入場時間")
    private java.util.Date startTime;
	/**退場予定日*/
	@Excel(name = "退場予定日", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "退場予定日")
    private java.util.Date endTimePlan;
	/**退場時間*/
	@Excel(name = "退場時間", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "退場時間")
    private java.util.Date endTime;
	/**顧客評価備考*/
	@Excel(name = "顧客評価備考", width = 15)
    @ApiModelProperty(value = "顧客評価備考")
    private String evaluateRemarks;
	/**プロジェクトID*/
    @ApiModelProperty(value = "プロジェクトID")
    private String projectId;
	
	/**我が社営業担当ID*/
	private java.lang.String chargerId;

}
