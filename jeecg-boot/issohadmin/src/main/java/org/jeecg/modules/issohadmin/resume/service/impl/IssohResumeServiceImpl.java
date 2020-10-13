package org.jeecg.modules.issohadmin.resume.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.jeecg.modules.issohadmin.resume.entity.IssohResume;
import org.jeecg.modules.issohadmin.resume.mapper.IssohResumeMapper;
import org.jeecg.modules.issohadmin.resume.service.IIssohResumeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * @Description: 履歴書
 * @Author: jeecg-boot
 * @Date:   2020-10-11
 * @Version: V1.0
 */
@Service
public class IssohResumeServiceImpl extends ServiceImpl<IssohResumeMapper, IssohResume> implements IIssohResumeService {

    public IssohResume findByMemberId(String memberId){
        List<IssohResume> list = this.baseMapper.selectByMap(Collections.singletonMap("member_id", memberId));
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
