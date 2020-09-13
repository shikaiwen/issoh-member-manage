package org.jeecg.modules.issohadmin.contactor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.jeecg.modules.issohadmin.contactor.entity.IssohContactor;
import org.jeecg.modules.issohadmin.contactor.mapper.IssohContactorMapper;
import org.jeecg.modules.issohadmin.contactor.service.IIssohContactorService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.issohadmin.contactor.vo.IssohContactorVo;
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
 * @Description: 連絡情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Api(tags="連絡情報")
@RestController
@RequestMapping("/contactor/issohContactor")
@Slf4j
public class IssohContactorController extends JeecgController<IssohContactor, IIssohContactorService> {
	@Autowired
	private IIssohContactorService issohContactorService;
	 @Autowired
	 private IssohContactorMapper issohContactorMapper;
	
	/**
	 * 分页列表查询
	 *
	 * @param issohContactor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "連絡情報-分页列表查询")
	@ApiOperation(value="連絡情報-分页列表查询", notes="連絡情報-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohContactor issohContactor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		QueryWrapper<IssohContactor> queryWrapper = QueryGenerator.initQueryWrapper(issohContactor, req.getParameterMap());
		queryWrapper.setTableAlias("a");
		Page<IssohContactorVo> page = new Page<>(pageNo, pageSize);
//		queryWrapper.getCustomSqlSegment()

		String companyName = req.getParameter("companyName");
		Map paramMap = new HashMap();

		if(StringUtils.isNotEmpty( companyName )){
			paramMap.put("companyName", companyName);
		}

		Page<IssohContactorVo> issohContactorVoPage = issohContactorMapper.mySelectPage(page, queryWrapper, paramMap);

//		IPage<IssohContactorVo> pageList = issohContactorService.page(page, queryWrapper);
		return Result.ok(issohContactorVoPage);
	}
	
	/**
	 *   添加
	 *
	 * @param issohContactor
	 * @return
	 */
	@AutoLog(value = "連絡情報-添加")
	@ApiOperation(value="連絡情報-添加", notes="連絡情報-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohContactor issohContactor) {
		issohContactorService.save(issohContactor);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohContactor
	 * @return
	 */
	@AutoLog(value = "連絡情報-编辑")
	@ApiOperation(value="連絡情報-编辑", notes="連絡情報-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohContactor issohContactor) {
		issohContactorService.updateById(issohContactor);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "連絡情報-通过id删除")
	@ApiOperation(value="連絡情報-通过id删除", notes="連絡情報-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohContactorService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "連絡情報-批量删除")
	@ApiOperation(value="連絡情報-批量删除", notes="連絡情報-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohContactorService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "連絡情報-通过id查询")
	@ApiOperation(value="連絡情報-通过id查询", notes="連絡情報-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohContactor issohContactor = issohContactorService.getById(id);
		if(issohContactor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohContactor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohContactor
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohContactor issohContactor) {
        return super.exportXls(request, issohContactor, IssohContactor.class, "連絡情報");
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
        return super.importExcel(request, response, IssohContactor.class);
    }

}
