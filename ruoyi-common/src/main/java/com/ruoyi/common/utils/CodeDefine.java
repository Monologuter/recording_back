package com.ruoyi.common.utils;

public class CodeDefine {
	
	public static final String ERROR_SUCC = "0";
	 public static final String ERROR_SUCC_MSG = "成功";
	
	 public static final String ERROR_FAIL = "-1";
	 public static final String ERROR_FAIL_MSG = "失败";
	//-------------------GB28181 开始-------------------------------------
	public static final String CATALOG = "<CmdType>Catalog</CmdType>";// 查询通讯录
	public static final String PRESETQUERY = "<CmdType>PresetQuery</CmdType>";// 设备预置位查询
	public static final String ALARM = "<CmdType>Alarm</CmdType>";// 报警（预测）
	public static final String DEVICESTATUS = "<CmdType>DeviceStatus</CmdType>";// 设备状态（预测）
	public static final String KEEPALIVE = "<CmdType>Keepalive</CmdType>";//GB28181服务器心跳

	// 云台控制
	public static final String MONITOR_LEFT = "left";// 向左
	public static final String MONITOR_LEFT_PTZCMD = "A50F01021F0000D5";// 向左

	public static final String MONITOR_RIGHT = "right";// 向右
	public static final String MONITOR_RIGHT_PTZCMD = "A50F01011F0000D5";// 向右

	public static final String MONITOR_DOWN = "down";// 向下
	public static final String MONITOR_DOWN_PTZCMD = "A50F01041F0000D5";// 向下

	public static final String MONITOR_UP = "up";// 向上
	public static final String MONITOR_UP_PTZCMD = "A50F01081F0000D5";// 向上

	public static final String MONITOR_IN = "in";// 放大
	public static final String MONITOR_IN_PTZCMD = "A50F0110000010D5";// 放大

	public static final String MONITOR_OUT = "out";// 缩小
	public static final String MONITOR_OUT_PTZCMD = "A50F0120000010E5";// 缩小

	public static final String MONITOR_STOP = "stop";// 停止
	public static final String MONITOR_STOP_PTZCMD = "A50F01051F0000D5";// 停止
	// -------------------GB28181 结束-------------------------------------
}
