package org.jeecg.modules.issohadmin.company.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 会社一覧
 * @Author: jeecg-boot
 * @Date:   2020-09-08
 * @Version: V1.0
 */
@Data
public class IssohCompanyTree implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 对应SysDepart中的id字段,前端数据树中的key*/
    private String key;
    /** 对应SysDepart中的id字段,前端数据树中的value*/
    private String value;
    /** 对应depart_name字段,前端数据树中的title*/
    private String title;

	/**主键*/
    private String id;
	/**会社名*/
    private String name;
	/**担当者*/
    private String charger;
	/**携帯番号*/
    private String mobile;
	/**電話番号*/
    private String tel;
	/**担当者メール*/
    private String email;
	/**名刺*/
    private String businessCardList;
	/**创建人*/
    private String createBy;
	/**创建日期*/
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
    private Date updateTime;
	/**所属部门*/
    private String sysOrgCode;


}
