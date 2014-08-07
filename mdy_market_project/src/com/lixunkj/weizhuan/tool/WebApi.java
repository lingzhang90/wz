package com.lixunkj.weizhuan.tool;

public class WebApi {
	// private String baseUrl = "http://weizhuan.duapp.com/index.php?s=";
	private String baseUrl = "http://0o0oco0o0co0t0vocollol0l011l1tl1l1o1ol1u01l10o11lcl0l0oo0l01l.lixunkj.com/index.php?s=";
//	 private String baseUrl = "http://192.168.0.178/www4/index.php?s=";

	// 用户注册协议
	public String userAgreement() {
		return baseUrl + "Page/agreement";
	}

	// 注册的接口
	public String userReg() {
		return baseUrl + "Public/reg";
	}

	// 登录接口
	public String userLogin() {
		return baseUrl + "Public/login";
	}

	// 找回密码接口
	public String userGetPass() {
		return baseUrl + "Public/getpass";
	}

	// 获取用户信息
	public String userGetUserInfo() {
		return baseUrl + "User/getuserinfo";
	}

	// 通知公告
	public String announce() {
		return baseUrl + "Content/lists/pid/1";
	}

	// 推广教程
	public String tuiguang() {
		return baseUrl + "Content/lists/pid/2";
	}

	// 常见问题
	public String problem() {
		return baseUrl + "Content/lists/pid/3";
	}

	// 关于程序
	public String about() {
		return baseUrl + "Page/about";
	}

	// 联系客服
	public String connect() {
		return baseUrl + "Page/connect";
	}

	// 提现接口
	public String pay() {
		return baseUrl + "Pay/index";
	}

	// 提现记录
	public String banklog() {
		return baseUrl + "Pay/log";
	}

	// 推广人数记录
	public String inviteUser() {
		return baseUrl + "Invite/user";
	}

	// 用户等级升级
	public String UserGradeUpdate() {
		return baseUrl + "User/updategrade";
	}

	// 用户签到
	public String userSign() {
		return baseUrl + "User/sign";
	}

	// 用户签到
	public String setUserInfo() {
		return baseUrl + "User/setuserinfo";
	}

	// 客户端升级
	public String version() {
		return baseUrl + "Version/check";
	}

	// 修改密码
	public String UserChangePass() {
		return baseUrl + "User/changepass";
	}

	// 积分明细
	public String coinLog() {
		return baseUrl + "Coinlog/index";
	}

	// 排行榜
	public String rank() {
		return baseUrl + "Rank/index";
	}

	// 生成推广包路径
	public String createPackageUrl() {
		return baseUrl + "Public/promotePackage.action";
	}

	// 用户退出
	public String logout() {
		return baseUrl + "Public/logout";
	}

	// 用户修改提现密码
	public String userChangePayPass() {
		return baseUrl + "User/changepaypass";
	}

	// 用户找回提现密码
	public String userGetPayPass() {
		return baseUrl + "User/getpaypass";
	}

	// 用户启动进入应用的操作
	public String enterApp() {
		return baseUrl + "Public/enterapp";
	}

	// 下载最新版本
	public String download() {
		return baseUrl + "Version/download/os=android";
	}

	// 用户启动进入应用的操作
	public String lottery() {
		return baseUrl + "User/lottery";
	}

	// 自动生成推广包
	public String extpackage() {
		// return "http://192.168.0.118/weizhuan/Promote.aspx";
		return "http://120.192.75.240/Promote.aspx";
	}

	// 加积分
	public String addCoin() {
		return baseUrl + "User/taskcoin";
	}

	public String getAdList() {
		return baseUrl + "Public/adlist";
	}

}
