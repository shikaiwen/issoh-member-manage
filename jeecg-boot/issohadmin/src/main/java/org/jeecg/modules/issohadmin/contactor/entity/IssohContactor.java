package org.jeecg.modules.issohadmin.contactor.entity;

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
 * @Description: 連絡情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Data
@TableName("issoh_contactor")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_contactor对象", description="連絡情報")
public class IssohContactor implements Serializable {
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
	/**名前*/
	@Excel(name = "名前", width = 15)
    @ApiModelProperty(value = "名前")
    private java.lang.String name;
	/**職務*/
	@Excel(name = "職務", width = 15)
    @ApiModelProperty(value = "職務")
    private java.lang.String positionName;
	/**携帯番号*/
	@Excel(name = "携帯番号", width = 15)
    @ApiModelProperty(value = "携帯番号")
    private java.lang.String mobile;
	/**電話番号*/
	@Excel(name = "電話番号", width = 15)
    @ApiModelProperty(value = "電話番号")
    private java.lang.String tel;
	/**メール*/
	@Excel(name = "メール", width = 15)
    @ApiModelProperty(value = "メール")
    private java.lang.String email;
	/**所属会社*/
	@Excel(name = "所属会社", width = 15)
    @ApiModelProperty(value = "所属会社")
    private java.lang.String companyId;
	/**名刺*/
	@Excel(name = "名刺", width = 15)
    @ApiModelProperty(value = "名刺")
    private java.lang.String businessCards;
}
