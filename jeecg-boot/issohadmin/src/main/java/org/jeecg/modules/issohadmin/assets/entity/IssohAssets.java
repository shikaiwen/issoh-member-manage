package org.jeecg.modules.issohadmin.assets.entity;

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
 * @Description: 資産備品
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
@Data
@TableName("issoh_assets")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_assets对象", description="資産備品")
public class IssohAssets implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
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
	/**コード*/
	@Excel(name = "コード", width = 15)
    @ApiModelProperty(value = "コード")
    private java.lang.String code;
	/**備品名称*/
	@Excel(name = "備品名称", width = 15)
    @ApiModelProperty(value = "備品名称")
    private java.lang.String name;
	/**タイプ*/
	@Excel(name = "タイプ", width = 15)
    @ApiModelProperty(value = "タイプ")
    private java.lang.String type;
	/**写真*/
	@Excel(name = "写真", width = 15)
    @ApiModelProperty(value = "写真")
    private java.lang.String img;
	/**備考*/
	@Excel(name = "備考", width = 15)
    @ApiModelProperty(value = "備考")
    private java.lang.String remarks;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
}
