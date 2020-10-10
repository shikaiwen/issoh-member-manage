package org.jeecg.modules.issohadmin.memberarrange.vo;

import lombok.Data;
import org.jeecg.modules.issohadmin.memberarrange.entity.IssohMemberArrange;

import java.io.Serializable;

/**
 * @Description: 入場情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Data
public class IssohMemberArrangeVo extends IssohMemberArrange implements Serializable {

//    案件名
    private String projectName;

    private String memberName;

    private String companyId;
    private String companyName;

//    我が社営業担当
    private String chargerName;

}
