package org.jeecg.modules.issohadmin.company.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.company.entity.IssohCompany;
import org.jeecg.modules.issohadmin.company.entity.IssohCompanyTree;
import org.jeecg.modules.issohadmin.company.service.IIssohCompanyService;

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
 * @Description: 会社一覧
 * @Author: jeecg-boot
 * @Date:   2020-09-08
 * @Version: V1.0
 */
@Api(tags="会社一覧")
@RestController
@RequestMapping("/company/issohCompany")
@Slf4j
public class IssohCompanyController extends JeecgController<IssohCompany, IIssohCompanyService> {
	@Autowired
	private IIssohCompanyService issohCompanyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会社一覧-分页列表查询")
	@ApiOperation(value="会社一覧-分页列表查询", notes="会社一覧-分页列表查询")
	@GetMapping(value = "/getTree")
	public Result<?> getTree(
								   HttpServletRequest req) {

		List<IssohCompany> list = issohCompanyService.list();
		List<IssohCompanyTree> resultList = list.stream().map((e) -> {
			IssohCompanyTree treeObj = new IssohCompanyTree();
			try {
				BeanUtils.copyProperties(treeObj,e);
			} catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
			treeObj.setKey(e.getId());
			treeObj.setValue(e.getId());
			treeObj.setTitle(e.getName());
			return treeObj;
		}).collect(Collectors.toList());
		return Result.ok(resultList);
	}


	/**
	 * 分页列表查询
	 *
	 * @param issohCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会社一覧-分页列表查询")
	@ApiOperation(value="会社一覧-分页列表查询", notes="会社一覧-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohCompany issohCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssohCompany> queryWrapper = QueryGenerator.initQueryWrapper(issohCompany, req.getParameterMap());
		Page<IssohCompany> page = new Page<IssohCompany>(pageNo, pageSize);
		IPage<IssohCompany> pageList = issohCompanyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohCompany
	 * @return
	 */
	@AutoLog(value = "会社一覧-添加")
	@ApiOperation(value="会社一覧-添加", notes="会社一覧-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohCompany issohCompany) {
		issohCompanyService.save(issohCompany);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohCompany
	 * @return
	 */
	@AutoLog(value = "会社一覧-编辑")
	@ApiOperation(value="会社一覧-编辑", notes="会社一覧-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohCompany issohCompany) {
		issohCompanyService.updateById(issohCompany);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会社一覧-通过id删除")
	@ApiOperation(value="会社一覧-通过id删除", notes="会社一覧-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohCompanyService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会社一覧-批量删除")
	@ApiOperation(value="会社一覧-批量删除", notes="会社一覧-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会社一覧-通过id查询")
	@ApiOperation(value="会社一覧-通过id查询", notes="会社一覧-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohCompany issohCompany = issohCompanyService.getById(id);
		if(issohCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohCompany);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohCompany
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohCompany issohCompany) {
        return super.exportXls(request, issohCompany, IssohCompany.class, "会社一覧");
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
        return super.importExcel(request, response, IssohCompany.class);
    }

}
