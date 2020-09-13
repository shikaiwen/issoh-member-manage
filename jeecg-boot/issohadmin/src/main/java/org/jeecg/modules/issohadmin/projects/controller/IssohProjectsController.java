package org.jeecg.modules.issohadmin.projects.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.projects.entity.IssohProjects;
import org.jeecg.modules.issohadmin.projects.mapper.IssohProjectsMapper;
import org.jeecg.modules.issohadmin.projects.service.IIssohProjectsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: プロジェクト
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Api(tags="プロジェクト")
@RestController
@RequestMapping("/projects/issohProjects")
@Slf4j
public class IssohProjectsController extends JeecgController<IssohProjects, IIssohProjectsService> {
	@Autowired
	private IIssohProjectsService issohProjectsService;

	@Autowired
	 IssohProjectsMapper mapper;
	/**
	 * 分页列表查询
	 *
	 * @param issohProjects
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "プロジェクト-分页列表查询")
	@ApiOperation(value="プロジェクト-分页列表查询", notes="プロジェクト-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohProjects issohProjects,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssohProjects> queryWrapper = QueryGenerator.initQueryWrapper(issohProjects, req.getParameterMap());
		queryWrapper.setTableAlias("a");
		Page<IssohProjects> page = new Page<IssohProjects>(pageNo, pageSize);

//		IPage<IssohProjects> pageList = issohProjectsService.page(page, queryWrapper);
		Map params = new HashMap();
		String companyName = req.getParameter("companyName");
		String contactorName = req.getParameter("contactorName");

		if(StringUtils.isNotEmpty(companyName)){
			params.put("companyName", companyName);
		}
		if(StringUtils.isNotEmpty(contactorName)){
			params.put("contactorName", contactorName);
		}

		Page<IssohProjects> pageList = mapper.mySelectPage(page, queryWrapper, params);

		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohProjects
	 * @return
	 */
	@AutoLog(value = "プロジェクト-添加")
	@ApiOperation(value="プロジェクト-添加", notes="プロジェクト-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohProjects issohProjects) {
		issohProjectsService.save(issohProjects);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohProjects
	 * @return
	 */
	@AutoLog(value = "プロジェクト-编辑")
	@ApiOperation(value="プロジェクト-编辑", notes="プロジェクト-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohProjects issohProjects) {
		issohProjectsService.updateById(issohProjects);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "プロジェクト-通过id删除")
	@ApiOperation(value="プロジェクト-通过id删除", notes="プロジェクト-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohProjectsService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "プロジェクト-批量删除")
	@ApiOperation(value="プロジェクト-批量删除", notes="プロジェクト-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohProjectsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "プロジェクト-通过id查询")
	@ApiOperation(value="プロジェクト-通过id查询", notes="プロジェクト-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohProjects issohProjects = issohProjectsService.getById(id);
		if(issohProjects==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohProjects);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohProjects
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohProjects issohProjects) {
        return super.exportXls(request, issohProjects, IssohProjects.class, "プロジェクト");
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
        return super.importExcel(request, response, IssohProjects.class);
    }

}
