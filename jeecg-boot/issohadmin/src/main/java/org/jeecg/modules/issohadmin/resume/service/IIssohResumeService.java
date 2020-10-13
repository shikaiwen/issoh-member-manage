package org.jeecg.modules.issohadmin.resume.service;

import org.jeecg.modules.issohadmin.resume.entity.IssohResume;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 履歴書
 * @Author: jeecg-boot
 * @Date:   2020-10-11
 * @Version: V1.0
 */
public interface IIssohResumeService extends IService<IssohResume> {

    public IssohResume findByMemberId(String memberId);
}
