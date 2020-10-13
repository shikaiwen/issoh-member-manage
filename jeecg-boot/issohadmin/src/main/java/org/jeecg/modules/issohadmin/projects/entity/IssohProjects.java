package org.jeecg.modules.issohadmin.projects.entity;

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
 * @Description: プロジェクト
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Data
@TableName("issoh_projects")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_projects对象", description="プロジェクト")
public class IssohProjects implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;


    /**プロジェクト名*/
    @Excel(name = "プロジェクト名", width = 15)
    @ApiModelProperty(value = "プロジェクト名")
    private java.lang.String name;


    @Excel(name = "プロジェクトコード", width = 15)
    @ApiModelProperty(value = "プロジェクトコード")
    private java.lang.String code;

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






	/**案件情報*/
	@Excel(name = "案件情報", width = 15)
    @ApiModelProperty(value = "案件情報")
    private java.lang.String proDesc;
	/**開始時間*/
	@Excel(name = "開始時間", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "開始時間")
    private java.util.Date startTime;
	/**終了予定時間*/
	@Excel(name = "終了予定時間", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "終了予定時間")
    private java.util.Date endTime;
	/**現場ロケーション*/
	@Excel(name = "現場ロケーション", width = 15)
    @ApiModelProperty(value = "現場ロケーション")
    private java.lang.String workLocation;
	/**現場アドレス*/
	@Excel(name = "現場アドレス", width = 15)
    @ApiModelProperty(value = "現場アドレス")
    private java.lang.String workLocationAddr;
	/**案件所属会社*/
	@Excel(name = "案件所属会社", width = 15)
    @ApiModelProperty(value = "案件所属会社")
    private java.lang.String companyId;
	/**案件担当者*/
	@Excel(name = "案件担当者", width = 15)
    @ApiModelProperty(value = "案件担当者")
    private java.lang.String contactorId;
}
