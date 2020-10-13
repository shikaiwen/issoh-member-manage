package org.jeecg.modules.issohadmin.resume.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.resume.entity.IssohResume;
import org.jeecg.modules.issohadmin.resume.service.IIssohResumeService;

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
 * @Description: 履歴書
 * @Author: jeecg-boot
 * @Date:   2020-10-11
 * @Version: V1.0
 */
@Api(tags="履歴書")
@RestController
@RequestMapping("/resume/issohResume")
@Slf4j
public class IssohResumeController extends JeecgController<IssohResume, IIssohResumeService> {
	@Autowired
	private IIssohResumeService issohResumeService;


	 /**
	  * 通过id查询
	  * @return
	  */
	 @AutoLog(value = "履歴書-通过id查询")
	 @ApiOperation(value="履歴書-通过id查询", notes="履歴書-通过id查询")
	 @GetMapping(value = "/find")
	 public Result<?> find(IssohResume issohResume,HttpServletRequest req){

		 QueryWrapper<IssohResume> queryWrapper = QueryGenerator.initQueryWrapper(issohResume, req.getParameterMap());
		 List<IssohResume> list = issohResumeService.list(queryWrapper);

		 IssohResume resume = null;
		 if (CollectionUtils.isNotEmpty(list)) {
			 resume = list.get(0);
		 }
		 return Result.ok(resume);
	 }

	/**
	 * 分页列表查询
	 *
	 * @param issohResume
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "履歴書-分页列表查询")
	@ApiOperation(value="履歴書-分页列表查询", notes="履歴書-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohResume issohResume,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssohResume> queryWrapper = QueryGenerator.initQueryWrapper(issohResume, req.getParameterMap());
		Page<IssohResume> page = new Page<IssohResume>(pageNo, pageSize);
		IPage<IssohResume> pageList = issohResumeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohResume
	 * @return
	 */
	@AutoLog(value = "履歴書-添加")
	@ApiOperation(value="履歴書-添加", notes="履歴書-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohResume issohResume) {
		issohResumeService.save(issohResume);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohResume
	 * @return
	 */
	@AutoLog(value = "履歴書-编辑")
	@ApiOperation(value="履歴書-编辑", notes="履歴書-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohResume issohResume) {
		issohResumeService.updateById(issohResume);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "履歴書-通过id删除")
	@ApiOperation(value="履歴書-通过id删除", notes="履歴書-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohResumeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "履歴書-批量删除")
	@ApiOperation(value="履歴書-批量删除", notes="履歴書-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohResumeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "履歴書-通过id查询")
	@ApiOperation(value="履歴書-通过id查询", notes="履歴書-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohResume issohResume = issohResumeService.getById(id);
		if(issohResume==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohResume);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohResume
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohResume issohResume) {
        return super.exportXls(request, issohResume, IssohResume.class, "履歴書");
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
        return super.importExcel(request, response, IssohResume.class);
    }

}
