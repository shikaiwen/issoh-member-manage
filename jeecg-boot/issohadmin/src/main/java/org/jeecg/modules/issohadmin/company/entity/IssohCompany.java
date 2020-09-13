package org.jeecg.modules.issohadmin.company.entity;

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
 * @Description: 会社一覧
 * @Author: jeecg-boot
 * @Date:   2020-09-08
 * @Version: V1.0
 */
@Data
@TableName("issoh_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_company对象", description="会社一覧")
public class IssohCompany implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**会社名*/
	@Excel(name = "会社名", width = 15)
    @ApiModelProperty(value = "会社名")
    private java.lang.String name;
	/**担当者*/
	@Excel(name = "担当者", width = 15)
    @ApiModelProperty(value = "担当者")
    private java.lang.String charger;
	/**携帯番号*/
	@Excel(name = "携帯番号", width = 15)
    @ApiModelProperty(value = "携帯番号")
    private java.lang.String mobile;
	/**電話番号*/
	@Excel(name = "電話番号", width = 15)
    @ApiModelProperty(value = "電話番号")
    private java.lang.String tel;
	/**担当者メール*/
	@Excel(name = "担当者メール", width = 15)
    @ApiModelProperty(value = "担当者メール")
    private java.lang.String email;
	/**名刺*/
	@Excel(name = "名刺", width = 15)
    @ApiModelProperty(value = "名刺")
    private java.lang.String businessCardList;
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
}
