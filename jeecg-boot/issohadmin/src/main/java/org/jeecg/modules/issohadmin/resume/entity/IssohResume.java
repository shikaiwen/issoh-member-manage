package org.jeecg.modules.issohadmin.resume.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.issohadmin.component.MapTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 履歴書
 * @Author: jeecg-boot
 * @Date:   2020-10-11
 * @Version: V1.0
 */
@Data
@TableName(value = "issoh_resume",autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="issoh_resume对象", description="履歴書")
public class IssohResume implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;


    /**社員ID*/
    @Excel(name = "社員ID", width = 15)
    @ApiModelProperty(value = "社員ID")
    private String memberId;
    /**社員名前*/
    @Excel(name = "社員名前", width = 15)
    @ApiModelProperty(value = "社員名前")
    private String memberName;
    /**社員コード*/
    @Excel(name = "社員コード", width = 15)
    @ApiModelProperty(value = "社員コード")
    private String memberCode;
    /**履歴情報*/
    @Excel(name = "履歴情報", width = 15)
    @ApiModelProperty(value = "履歴情報")
    @TableField(typeHandler = MapTypeHandler.class)
    private Map config;

//    skills for search
    private String searchSkills;

    public void setSearchSkills(){

        Map config = this.getConfig();
        if(config == null){
            return;
        }
        JSONObject jsonObject = new JSONObject(config);
        JSONArray skills = jsonObject.getJSONArray("skills");
        List<String> list = new ArrayList<>();
        skills.stream().forEach(item->{

            JSONObject itemJ = new JSONObject((Map) item);
            String level = itemJ.getString("level");
            if("1".equals(level) || "2".equals(level)){
                String name = itemJ.getString("name");
                list.add(name.toLowerCase());
            }
        });
        this.searchSkills = StringUtils.join(list.toArray(),",");
    }

    public String getSearchSkills(){
        Map config = this.getConfig();
        if(config == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject(config);
        JSONArray skills = jsonObject.getJSONArray("skills");
        List<String> list = new ArrayList<>();
        skills.stream().forEach(item->{

            JSONObject itemJ = new JSONObject((Map) item);
            String level = itemJ.getString("level");
            if("1".equals(level) || "2".equals(level)){
                String name = itemJ.getString("name");
                list.add(name.toLowerCase());
            }
        });

        return StringUtils.join(list.toArray(),",");
    }

	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;

}
