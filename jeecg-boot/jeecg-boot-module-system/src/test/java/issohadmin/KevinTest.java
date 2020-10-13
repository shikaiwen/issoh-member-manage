package issohadmin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.JeecgApplication;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import org.jeecg.modules.issohadmin.assets.mapper.IssohAssetsMapper;
import org.jeecg.modules.issohadmin.contactor.mapper.IssohContactorMapper;
import org.jeecg.modules.issohadmin.contactor.vo.IssohContactorVo;
import org.jeecg.modules.issohadmin.memberarrange.entity.IssohMemberArrange;
import org.jeecg.modules.issohadmin.memberarrange.mapper.IssohMemberArrangeMapper;
import org.jeecg.modules.issohadmin.memberarrange.vo.IssohMemberArrangeVo;
import org.jeecg.modules.issohadmin.projects.entity.IssohProjects;
import org.jeecg.modules.issohadmin.projects.mapper.IssohProjectsMapper;
import org.jeecg.modules.issohadmin.resume.entity.IssohResume;
import org.jeecg.modules.issohadmin.resume.service.IIssohResumeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgApplication.class)
public class KevinTest {



	@Resource
	IssohAssetsMapper issohAssetsMapper;

	@Resource
	IssohContactorMapper issohContactorMapper;
	@Resource
	IssohMemberArrangeMapper issohMemberArrangeMapper;
	@Resource
	IIssohResumeService iIssohResumeService;
	@Resource
	IssohProjectsMapper issohProjectsMapper;


	@Test
	public void resume() {
		IssohResume resume = new IssohResume();
		resume.setConfig(new HashMap<>());

		resume.setMemberCode("YY00");
		List<IssohResume> list = iIssohResumeService.list();
		System.out.println(list);
		iIssohResumeService.save(resume);

//		iIssohResumeService.save(resume);

	}

	@Test
	public void projectsPage() {

		Page<IssohProjects> page = new Page<>(1, 10);
		QueryWrapper<IssohProjects> queryWrapper = new QueryWrapper<>();
		Page<IssohProjects> issohContactorVoPage = issohProjectsMapper.mySelectPage(page, queryWrapper, null);

//		Page<IssohAssets> issohAssetsPage = issohAssetsMapper.mySelectPage(page, queryWrapper, Collections.emptyMap());
//		issohAssetsPage.getRecords().forEach(System.out::println);
		System.out.println(issohContactorVoPage);
	}


	@Test
	public void memberArrangePage() {

		Page<IssohMemberArrange> page = new Page<>(1, 10);

		QueryWrapper<IssohMemberArrange> queryWrapper = new QueryWrapper<>();
		Page<IssohMemberArrangeVo> issohContactorVoPage = issohMemberArrangeMapper.mySelectPage(page, queryWrapper, null);

//		Page<IssohAssets> issohAssetsPage = issohAssetsMapper.mySelectPage(page, queryWrapper, Collections.emptyMap());
//		issohAssetsPage.getRecords().forEach(System.out::println);
		System.out.println(issohContactorVoPage);
	}







	@Test
	public void contactorPage() {

		Page<IssohContactorVo> page = new Page<>(1, 10);

		QueryWrapper<IssohContactorVo> queryWrapper = new QueryWrapper<>();

		Page<IssohContactorVo> issohContactorVoPage = issohContactorMapper.mySelectPage(page, queryWrapper, null);

//		Page<IssohAssets> issohAssetsPage = issohAssetsMapper.mySelectPage(page, queryWrapper, Collections.emptyMap());
//		issohAssetsPage.getRecords().forEach(System.out::println);
		System.out.println(issohContactorVoPage);
	}


	@Test
	public void ts1() {

		Page<IssohAssets> page = new Page<IssohAssets>(1, 10);

		QueryWrapper<IssohAssets> queryWrapper = new QueryWrapper<>();

		Page<IssohAssets> issohAssetsPage = issohAssetsMapper.mySelectPage(page, queryWrapper, Collections.emptyMap());

		issohAssetsPage.getRecords().forEach(System.out::println);
		System.out.println(issohAssetsPage);
	}




}
