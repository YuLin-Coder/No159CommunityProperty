package com.so.demosboot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import com.so.demosboot.common.utils.StringUtils;
import com.so.demosboot.modules.sys.entity.WyHouseLeader;
import com.so.demosboot.modules.sys.entity.WyPlotInfo;
import com.so.demosboot.modules.sys.service.WyHouseInfoService;
import com.so.demosboot.modules.sys.service.WyHouseLeaderService;
import com.so.demosboot.modules.sys.service.WyPlotInfoService;


/**
 * 户主信息Controller
 * @author so
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/sys/wyHouseLeader")
public class WyHouseLeaderController{

	@Autowired
	private WyHouseLeaderService wyHouseLeaderService;
	@Autowired
	private WyPlotInfoService wyPlotInfoService;
	@Autowired
	private WyHouseInfoService wyHouseInfoService;
	
	@ModelAttribute
	public WyHouseLeader get(@RequestParam(required=false) String id) {
		WyHouseLeader entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wyHouseLeaderService.getById(id);
		}else{
			entity = new WyHouseLeader();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(WyHouseLeader wyHouseLeader, Model model) {
		PageHelper.startPage(wyHouseLeader.getPageNo(),10);
		List<WyHouseLeader> list = wyHouseLeaderService.findList(wyHouseLeader);
		PageInfo<WyHouseLeader> pageInfo = new PageInfo<WyHouseLeader>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "sys/wyHouseLeaderList";
	}

	@RequestMapping(value = "form")
	public String form(WyHouseLeader wyHouseLeader, Model model) {
		if (StringUtils.isNotEmpty(wyHouseLeader.getId())){
			wyHouseLeader = wyHouseLeaderService.getById(wyHouseLeader.getId());
			model.addAttribute("wyHouseLeader",wyHouseLeader);
		}
		List<WyPlotInfo> findList = wyPlotInfoService.findList(new WyPlotInfo());
		model.addAttribute("wyPlotInfos",findList);
		return "sys/wyHouseLeaderForm";
	}

	@RequestMapping(value = "save")
	public String save(WyHouseLeader wyHouseLeader,RedirectAttributes redirectAttributes) {
		if(StringUtils.isEmpty(wyHouseLeader.getId())){
			wyHouseLeader.setIsOut("0");//添加时默认未搬出
		}
		wyHouseLeaderService.save(wyHouseLeader);
		redirectAttributes.addFlashAttribute("msg", "保存记录成功！");
		return "redirect:"+"/sys/wyHouseLeader";
	}
	
	@RequestMapping(value = "delete")
	public String delete(WyHouseLeader wyHouseLeader, RedirectAttributes redirectAttributes) {
		wyHouseLeaderService.delete(wyHouseLeader.getId());
		redirectAttributes.addFlashAttribute("msg", "删除记录成功！");
		return "redirect:"+"/sys/wyHouseLeader";
	}
	
	@RequestMapping(value = "moneyInfo")
	public String moneyInfo(WyHouseLeader wyHouseLeader, Model model) {
		PageHelper.startPage(wyHouseLeader.getPageNo(),10);
		List<WyHouseLeader> list = wyHouseLeaderService.findList(wyHouseLeader);
		PageInfo<WyHouseLeader> pageInfo = new PageInfo<WyHouseLeader>(list, 10);
		model.addAttribute("pageInfo",pageInfo);
		return "sys/wyHouseLeaderMoney";
	}

}