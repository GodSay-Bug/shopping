package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * 完成测试按钮
	 * 1.url地址 :findUserAll
	 * 2.参数信息: null
	 * 3.返回值结果: List<User>
	 *
	 */
	@RequestMapping("/findUserAll")
	public List<User> findUserAll(){
		return userService.findUserAll();
	}

	/**
	 * 业务说明: jt-web服务器获取jt-sso数据 JSONP跨域请求
	 * url地址: http://sso.jt.com/user/check/{param}/{type}
	 * 参数:    param: 需要校验的数据   type:校验的类型
	 * 返回值:  SysResult对象
	 * 真实的返回值: callback(SysResult的JSON)
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
								 @PathVariable Integer type,
								 String callback){
		//true 表示数据存在     false 表示数据可以使用
		boolean flag = userService.checkUser(param,type);
		SysResult.success(flag);
		return new JSONPObject(callback, SysResult.success(flag));
	}
	
}
