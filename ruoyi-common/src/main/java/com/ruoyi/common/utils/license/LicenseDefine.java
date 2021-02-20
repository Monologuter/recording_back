package com.ruoyi.common.utils.license;

/**
 *
 * @dm
 * @com.wf.ew.common.utils
 * @LicenseDefine.java
 * @author liubaoping
 * @date 2020年4月9日 下午2:47:20
 * @TODO @
 */
public interface LicenseDefine {
	/**
	 * 分机数量
	 */
	public static final String EXTENSION_NUM = "extension_num";
	/**
	 * license 结束日期
	 */
	public static final String EXPIR_TIME = "expir_time";
	/**
	 * 最大调度台用户数(指最大登录PC端用户数)
	 */
	public static final String MAX_DISPATCH_USER_NUM = "max_dispatch_user_num";
	/**
	 * 最大视频会议室数(指系统允许建最大视频会议室的数量)'
	 */
	public static final String MAX_VIDEO_CONFERENCE_NUM = "max_video_conference_num";
	/**
	 * 视频会议室最大容纳数(指系统允许每个视频会议室容纳多少方成员参会)
	 */
	public static final String VIDEO_CONFERENCE_USER_MAX_NUM = "video_conference_user_max_num";
	/**
	 * 最大语音会议室数(指系统允许建最大语音会议室的数量)
	 */
	public static final String MAX_AUDIO_CONFERENCE_NUM = "max_audio_conference_num";
	/**
	 * 语音会议室最大容纳数(指系统允许每个语音会议室容纳多少方成员参会)
	 */
	public static final String AUDIO_CONFERENCE_USER_MAX_NUM = "audio_conference_user_max_num";
	/**
	 * 最大调度广播数(指系统允许建多少个广播组（广播组账号）
	 */
	public static final String MAX_BROADCAST_NUM = "max_broadcast_num";
	/**
	 * 最大调度用户数（分机）(指最大支持APP用户或者微信用户数)
	 */
	public static final String MAX_APP_WECHAT_NUM = "max_app_wechat_num";

	// 系统配置表
	public static final String CTI_URL = "cti_url";
	public static final String DEFAULT_USER_PWD = "default_user_pwd";
	public static final String BAIDU_ID = "baidu_id";
	public static final String NETTY_TCPPORT = "netty_tcpport";
	public static final String APP_NGINX = "app_nginx";
	public static final String VIDEO_GATEWAY_URL = "video_gateway_url";

}
