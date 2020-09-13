package org.jeecg.modules.issohadmin.memberarrange.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.memberarrange.entity.IssohMemberArrange;
import org.jeecg.modules.issohadmin.memberarrange.mapper.IssohMemberArrangeMapper;
import org.jeecg.modules.issohadmin.memberarrange.service.IIssohMemberArrangeService;

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
 * @Description: 入場情報
 * @Author: jeecg-boot
 * @Date:   2020-09-13
 * @Version: V1.0
 */
@Api(tags="入場情報")
@RestController
@RequestMapping("/memberarrange/issohMemberArrange")
@Slf4j
public class IssohMemberArrangeController extends JeecgController<IssohMemberArrange, IIssohMemberArrangeService> {
	@Autowired
	private IIssohMemberArrangeService issohMemberArrangeService;

	@Autowired
	IssohMemberArrangeMapper mapper;
	/**
	 * 分页列表查询
	 *
	 * @param issohMemberArrange
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "入場情報-分页列表查询")
	@ApiOperation(value="入場情報-分页列表查询", notes="入場情報-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohMemberArrange issohMemberArrange,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssohMemberArrange> queryWrapper = QueryGenerator.initQueryWrapper(issohMemberArrange, req.getParameterMap());
		Page<IssohMemberArrange> page = new Page<IssohMemberArrange>(pageNo, pageSize);
//		IPage<IssohMemberArrange> pageList = issohMemberArrangeService.page(page, queryWrapper);

		Page<IssohMemberArrange> pageList = mapper.mySelectPage(page, queryWrapper, Collections.emptyMap());

		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohMemberArrange
	 * @return
	 */
	@AutoLog(value = "入場情報-添加")
	@ApiOperation(value="入場情報-添加", notes="入場情報-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohMemberArrange issohMemberArrange) {
		issohMemberArrangeService.save(issohMemberArrange);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohMemberArrange
	 * @return
	 */
	@AutoLog(value = "入場情報-编辑")
	@ApiOperation(value="入場情報-编辑", notes="入場情報-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohMemberArrange issohMemberArrange) {
		issohMemberArrangeService.updateById(issohMemberArrange);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入場情報-通过id删除")
	@ApiOperation(value="入場情報-通过id删除", notes="入場情報-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohMemberArrangeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "入場情報-批量删除")
	@ApiOperation(value="入場情報-批量删除", notes="入場情報-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohMemberArrangeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入場情報-通过id查询")
	@ApiOperation(value="入場情報-通过id查询", notes="入場情報-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohMemberArrange issohMemberArrange = issohMemberArrangeService.getById(id);
		if(issohMemberArrange==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohMemberArrange);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohMemberArrange
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohMemberArrange issohMemberArrange) {
        return super.exportXls(request, issohMemberArrange, IssohMemberArrange.class, "入場情報");
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
        return super.importExcel(request, response, IssohMemberArrange.class);
    }

}
