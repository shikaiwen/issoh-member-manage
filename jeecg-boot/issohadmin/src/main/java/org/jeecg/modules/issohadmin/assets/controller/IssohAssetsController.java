package org.jeecg.modules.issohadmin.assets.controller;

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
import org.jeecg.modules.issohadmin.assets.service.IIssohAssetsService;

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
 * @Description: 資産備品
 * @Author: jeecg-boot
 * @Date:   2020-08-31
 * @Version: V1.0
 */
@Api(tags="資産備品")
@RestController
@RequestMapping("/assets/issohAssets")
@Slf4j
public class IssohAssetsController extends JeecgController<IssohAssets, IIssohAssetsService> {
	@Autowired
	private IIssohAssetsService issohAssetsService;

	 @Resource
	 IssohAssetsMapper issohAssetsMapper;
	/**
	 * 分页列表查询
	 *
	 * @param issohAssets
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "資産備品-分页列表查询")
	@ApiOperation(value="資産備品-分页列表查询", notes="資産備品-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohAssets issohAssets,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		QueryWrapper<IssohAssets> queryWrapper = new QueryWrapper<>();
		queryWrapper.setTableAlias("a");

		Map<String, String> extraMap = Collections.singletonMap("realname", req.getParameter("realname"));
		QueryGenerator.initQueryWrapperAlias(issohAssets, req.getParameterMap(),queryWrapper);
//		QueryWrapper<IssohAssets> queryWrapper = QueryGenerator.initQueryWrapper(issohAssets, req.getParameterMap());
//		queryWrapper.getCustomSqlSegment()
		Page<IssohAssets> page = new Page<IssohAssets>(pageNo, pageSize);
//		IPage<IssohAssets> pageList = issohAssetsService.page(page, queryWrapper);

//		queryWrapper.getExpression().getNormal().get(0).getSqlSegment();
//		QueryWrapper<IssohAssets> queryWrapper = new QueryWrapper<>();
//		queryWrapper.getCustomSqlSegment()
//		queryWrapper.getSqlSelect()
//		queryWrapper.getParamNameValuePairs()

		Page<IssohAssets> issohAssetsPage = issohAssetsMapper.mySelectPage(page, queryWrapper, extraMap);

		return Result.ok(issohAssetsPage);
	}
	
	/**
	 *   添加
	 *
	 * @param issohAssets
	 * @return
	 */
	@AutoLog(value = "資産備品-添加")
	@ApiOperation(value="資産備品-添加", notes="資産備品-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohAssets issohAssets) {
		issohAssetsService.save(issohAssets);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohAssets
	 * @return
	 */
	@AutoLog(value = "資産備品-编辑")
	@ApiOperation(value="資産備品-编辑", notes="資産備品-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohAssets issohAssets) {
		issohAssetsService.updateById(issohAssets);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "資産備品-通过id删除")
	@ApiOperation(value="資産備品-通过id删除", notes="資産備品-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohAssetsService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "資産備品-批量删除")
	@ApiOperation(value="資産備品-批量删除", notes="資産備品-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohAssetsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "資産備品-通过id查询")
	@ApiOperation(value="資産備品-通过id查询", notes="資産備品-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohAssets issohAssets = issohAssetsService.getById(id);
		if(issohAssets==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohAssets);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohAssets
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohAssets issohAssets) {
        return super.exportXls(request, issohAssets, IssohAssets.class, "資産備品");
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
        return super.importExcel(request, response, IssohAssets.class);
    }

}
