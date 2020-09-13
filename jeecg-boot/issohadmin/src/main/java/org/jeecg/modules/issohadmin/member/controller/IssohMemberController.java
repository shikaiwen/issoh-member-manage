package org.jeecg.modules.issohadmin.member.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.assets.entity.IssohAssets;
import org.jeecg.modules.issohadmin.assets.mapper.IssohAssetsMapper;
import org.jeecg.modules.issohadmin.member.entity.IssohMember;
import org.jeecg.modules.issohadmin.member.mapper.IssohMemberMapper;
import org.jeecg.modules.issohadmin.member.service.IIssohMemberService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.issohadmin.member.vo.IssohMemberVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 社員
 * @Author: jeecg-boot
 * @Date:   2020-08-30
 * @Version: V1.0
 */
@Api(tags="社員")
@RestController
@RequestMapping("/member/issohMember")
@Slf4j
public class IssohMemberController extends JeecgController<IssohMember, IIssohMemberService> {
	@Autowired
	private IIssohMemberService issohMemberService;

	 @Resource
	 IssohMemberMapper issohMemberMapper;

	/**
	 * 分页列表查询
	 *
	 * @param issohMember
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "社員-分页列表查询")
	@ApiOperation(value="社員-分页列表查询", notes="社員-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohMember issohMember,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

//		QueryWrapper<IssohMember> queryWrapper = QueryGenerator.initQueryWrapper(issohMember, req.getParameterMap());
//		Page<IssohMember> page = new Page<IssohMember>(pageNo, pageSize);
//		IPage<IssohMember> pageList = issohMemberService.page(page, queryWrapper);




		QueryWrapper<IssohMember> queryWrapper = new QueryWrapper<>();
		queryWrapper.setTableAlias("a");

		Map<String, String> extraMap = Collections.emptyMap();
//				Collections.singletonMap("realname", req.getParameter("realname"));
		QueryGenerator.initQueryWrapperAlias(issohMember, req.getParameterMap(),queryWrapper);
//		QueryWrapper<IssohAssets> queryWrapper = QueryGenerator.initQueryWrapper(issohAssets, req.getParameterMap());
//		queryWrapper.getCustomSqlSegment()
		Page<IssohMember> page = new Page<>(pageNo, pageSize);

		Page<IssohMemberVo> pageList = issohMemberMapper.mySelectPage(page, queryWrapper, extraMap);
		System.out.println(JSON.toJSONString(pageList, true));
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohMember
	 * @return
	 */
	@AutoLog(value = "社員-添加")
	@ApiOperation(value="社員-添加", notes="社員-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohMember issohMember) {
		issohMemberService.save(issohMember);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohMember
	 * @return
	 */
	@AutoLog(value = "社員-编辑")
	@ApiOperation(value="社員-编辑", notes="社員-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohMember issohMember) {
		issohMemberService.updateById(issohMember);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "社員-通过id删除")
	@ApiOperation(value="社員-通过id删除", notes="社員-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohMemberService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "社員-批量删除")
	@ApiOperation(value="社員-批量删除", notes="社員-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohMemberService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "社員-通过id查询")
	@ApiOperation(value="社員-通过id查询", notes="社員-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohMember issohMember = issohMemberService.getById(id);
		if(issohMember==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohMember);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohMember
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohMember issohMember) {
        return super.exportXls(request, issohMember, IssohMember.class, "社員");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, IssohMember.class);
    }

}