package com.lixunkj.weizhuan.entities;

import android.text.TextUtils;

public class User {
	public String userName;
	public String userPassword;
	public String uid;
	public String nickname;
	public String grade;
	public String coin;
	public String restcoin;
	public String costcoin;
	public String mobile;
	public String fullname;
	public String sex;
	public String birth;
	public String pid;
	public String coinnum;
	public String banknum;
	public String tuiguangnum;
	
	@Override
	public String toString() {
		return userName + userPassword+uid+nickname+grade+coin+restcoin+costcoin+mobile+fullname+sex+birth+pid+coinnum+banknum+tuiguangnum;
	}
	public String checkLoginInput(){
		if(TextUtils.isEmpty(userName)){
			return "请输入账户";
		} 
		if(!userName.matches("[1][358]\\d{9}")){
			return "请输入正确的手机号";
		}
		if(TextUtils.isEmpty(userPassword)){
			return "请输入密码";
		}
		return "";
	}
	public String checkRegisterInput(){
		if(TextUtils.isEmpty(userName)){
			return "请输入账户";
		} 
		if(!userName.matches("[1][358]\\d{9}")){
			return "请输入正确的手机号";
		}
		return "";
	}
}
