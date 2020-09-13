package org.jeecg.modules.issohadmin.member.entity;

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
 * @Description: 社員
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
@Data
@TableName("issoh_member")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_member对象", description="社員")
public class IssohMember implements Serializable {
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
	/**社員番号*/
	@Excel(name = "社員番号", width = 15)
    @ApiModelProperty(value = "社員番号")
    private java.lang.String code;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private java.lang.String realName;
	/**写真*/
	@Excel(name = "写真", width = 15)
    @ApiModelProperty(value = "写真")
    private java.lang.String image;
	/**性別*/
	@Excel(name = "性別", width = 15, dicCode = "sex")
	@Dict(dicCode = "sex")
    @ApiModelProperty(value = "性別")
    private java.lang.String sex;
	/**生年月日*/
	@Excel(name = "生年月日", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "生年月日")
    private java.util.Date birthday;
	/**在留カード*/
	@Excel(name = "在留カード", width = 15)
    @ApiModelProperty(value = "在留カード")
    private java.lang.String zaiCardImg;
	/**在留カード番号*/
	@Excel(name = "在留カード番号", width = 15)
    @ApiModelProperty(value = "在留カード番号")
    private java.lang.String zaiCardNo;
	/**パスポート*/
	@Excel(name = "パスポート", width = 15)
    @ApiModelProperty(value = "パスポート")
    private java.lang.String passportImg;
	/**パスポート番号*/
	@Excel(name = "パスポート番号", width = 15)
    @ApiModelProperty(value = "パスポート番号")
    private java.lang.String passportNo;
	/**入社時間*/
	@Excel(name = "入社時間", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "入社時間")
    private java.util.Date enterTime;
	/**備考*/
	@Excel(name = "備考", width = 15)
    @ApiModelProperty(value = "備考")
    private java.lang.String remarks;

	private String companyId;

}
