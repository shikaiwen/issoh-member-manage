package org.jeecg.modules.issohadmin.assetsrequest.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequestDetail;
import org.jeecg.modules.issohadmin.assetsrequest.entity.IssohAssetsRequest;
import org.jeecg.modules.issohadmin.assetsrequest.vo.IssohAssetsRequestPage;
import org.jeecg.modules.issohadmin.assetsrequest.service.IIssohAssetsRequestService;
import org.jeecg.modules.issohadmin.assetsrequest.service.IIssohAssetsRequestDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 資産申請記録
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
@Api(tags="資産申請記録")
@RestController
@RequestMapping("/assetsrequest/issohAssetsRequest")
@Slf4j
public class IssohAssetsRequestController {
	@Autowired
	private IIssohAssetsRequestService issohAssetsRequestService;
	@Autowired
	private IIssohAssetsRequestDetailService issohAssetsRequestDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param issohAssetsRequest
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "資産申請記録-分页列表查询")
	@ApiOperation(value="資産申請記録-分页列表查询", notes="資産申請記録-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(IssohAssetsRequest issohAssetsRequest,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IssohAssetsRequest> queryWrapper = QueryGenerator.initQueryWrapper(issohAssetsRequest, req.getParameterMap());
		Page<IssohAssetsRequest> page = new Page<IssohAssetsRequest>(pageNo, pageSize);
		IPage<IssohAssetsRequest> pageList = issohAssetsRequestService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param issohAssetsRequestPage
	 * @return
	 */
	@AutoLog(value = "資産申請記録-添加")
	@ApiOperation(value="資産申請記録-添加", notes="資産申請記録-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody IssohAssetsRequestPage issohAssetsRequestPage) {
		IssohAssetsRequest issohAssetsRequest = new IssohAssetsRequest();
		BeanUtils.copyProperties(issohAssetsRequestPage, issohAssetsRequest);
		issohAssetsRequestService.saveMain(issohAssetsRequest, issohAssetsRequestPage.getIssohAssetsRequestDetailList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param issohAssetsRequestPage
	 * @return
	 */
	@AutoLog(value = "資産申請記録-编辑")
	@ApiOperation(value="資産申請記録-编辑", notes="資産申請記録-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody IssohAssetsRequestPage issohAssetsRequestPage) {
		IssohAssetsRequest issohAssetsRequest = new IssohAssetsRequest();
		BeanUtils.copyProperties(issohAssetsRequestPage, issohAssetsRequest);
		IssohAssetsRequest issohAssetsRequestEntity = issohAssetsRequestService.getById(issohAssetsRequest.getId());
		if(issohAssetsRequestEntity==null) {
			return Result.error("未找到对应数据");
		}
		issohAssetsRequestService.updateMain(issohAssetsRequest, issohAssetsRequestPage.getIssohAssetsRequestDetailList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "資産申請記録-通过id删除")
	@ApiOperation(value="資産申請記録-通过id删除", notes="資産申請記録-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		issohAssetsRequestService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "資産申請記録-批量删除")
	@ApiOperation(value="資産申請記録-批量删除", notes="資産申請記録-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.issohAssetsRequestService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "資産申請記録-通过id查询")
	@ApiOperation(value="資産申請記録-通过id查询", notes="資産申請記録-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		IssohAssetsRequest issohAssetsRequest = issohAssetsRequestService.getById(id);
		if(issohAssetsRequest==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(issohAssetsRequest);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "資産申請明細通过主表ID查询")
	@ApiOperation(value="資産申請明細主表ID查询", notes="資産申請明細-通主表ID查询")
	@GetMapping(value = "/queryIssohAssetsRequestDetailByMainId")
	public Result<?> queryIssohAssetsRequestDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<IssohAssetsRequestDetail> issohAssetsRequestDetailList = issohAssetsRequestDetailService.selectByMainId(id);
		return Result.ok(issohAssetsRequestDetailList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param issohAssetsRequest
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IssohAssetsRequest issohAssetsRequest) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<IssohAssetsRequest> queryWrapper = QueryGenerator.initQueryWrapper(issohAssetsRequest, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<IssohAssetsRequest> queryList = issohAssetsRequestService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<IssohAssetsRequest> issohAssetsRequestList = new ArrayList<IssohAssetsRequest>();
      if(oConvertUtils.isEmpty(selections)) {
          issohAssetsRequestList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          issohAssetsRequestList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<IssohAssetsRequestPage> pageList = new ArrayList<IssohAssetsRequestPage>();
      for (IssohAssetsRequest main : issohAssetsRequestList) {
          IssohAssetsRequestPage vo = new IssohAssetsRequestPage();
          BeanUtils.copyProperties(main, vo);
          List<IssohAssetsRequestDetail> issohAssetsRequestDetailList = issohAssetsRequestDetailService.selectByMainId(main.getId());
          vo.setIssohAssetsRequestDetailList(issohAssetsRequestDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "資産申請記録列表");
      mv.addObject(NormalExcelConstants.CLASS, IssohAssetsRequestPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("資産申請記録数据", "导出人:"+sysUser.getRealname(), "資産申請記録"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<IssohAssetsRequestPage> list = ExcelImportUtil.importExcel(file.getInputStream(), IssohAssetsRequestPage.class, params);
              for (IssohAssetsRequestPage page : list) {
                  IssohAssetsRequest po = new IssohAssetsRequest();
                  BeanUtils.copyProperties(page, po);
                  issohAssetsRequestService.saveMain(po, page.getIssohAssetsRequestDetailList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
