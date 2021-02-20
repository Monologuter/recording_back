/**
 *
 * @ruoyi-common
 * @com.ruoyi.common.utils
 * @License.java
 * @author liubaoping
 * @date 2019年8月14日 下午2:51:57
 * TODO
 */
package com.ruoyi.common.utils.license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.Base64;


/**
 *
 * @ruoyi-common
 * @com.ruoyi.common.utils
 * @License.java
 * @author liubaoping
 * @date 2019年8月14日 下午2:51:57 TODO
 */

public class LicenseUtil {
	private static final Log log = LogFactory.getLog(LicenseUtil.class);
	private static JSONObject licenseJSONObject = new JSONObject();

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method readFileByLines
	 * @return_type List<String>
	 * @author liubaoping
	 * @date 2020年4月9日 下午2:09:45
	 * @TODO
	 * @@param fileName 文件名
	 * @@return
	 */
	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		if (!file.exists()) {
			return list;
		}
		BufferedReader reader = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (!tempString.trim().isEmpty()) {
					list.add(tempString);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	public static boolean reload(String address) {

		try {
			String os = System.getProperty("os.name");
			if (os.toLowerCase().contains("win")) {
				address = "G:\\vig_license";
			}

			// 过期
			List<String> list = readFileByLines(address);
			String data = list.size() > 0 ? list.get(0) : null;
			String public_exponent = "65537";
			String modulus = "105166985757856940860124006681002174962429429144926681382876105610349231191305513176431016966231480560046975568726752978919974759372611580252952528116291339775522360562635620978542186919874173977901433022542102290015536483839931605400787917249090042937992050610968259107533000715632557484696744842425937058327";
			byte[] datas = RSAUtil.decryptByPublicKey(RSAUtil.getPublicKey(modulus, public_exponent),
					Base64.decode(data));
			licenseJSONObject =  JSONObject.parseObject((new String(datas)));

			/*
			 * if (getMacAddress().equalsIgnoreCase(jsonobject.getString("code"))) { return
			 * false; }
			 */
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}

	// 获取mac地址
	public static String getMacAddress() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			byte[] mac = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint()
						|| !netInterface.isUp()) {
					continue;
				} else {
					mac = netInterface.getHardwareAddress();
					if (mac != null) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < mac.length; i++) {
							sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
						}
						if (sb.length() > 0) {
							return sb.toString();
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("MAC地址获取失败", e);
		}
		return "";
	}

	/**
	 * @return the licenseJSONObject
	 */
	public static JSONObject getLicenseJSONObject() {
		return licenseJSONObject;
	}

	/**
	 * @param licenseJSONObject the licenseJSONObject to set
	 */
	public static void setLicenseJSONObject(JSONObject licenseJSONObject) {
		LicenseUtil.licenseJSONObject = licenseJSONObject;
	}

	/**
	 * 判断服务器是否授权license
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getCode
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月11日 下午12:50:02
	 * @TODO
	 * @@return
	 */
	public static boolean getCode() {
		String os = System.getProperty("os.name");
//		if (!os.toLowerCase().contains("win")) {
			if (MmCode.getCode().equalsIgnoreCase(licenseJSONObject.getString("code"))) {
				return true;
			}else {
				return false;
			}
//		}
//		return true;
	}

	/**
	 * license日期
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicensePermissions
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午2:49:11
	 * @TODO
	 * @@param type
	 * @@return
	 */
	public static boolean getLicenseTime() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().contains("win")) {
			String expir_time = licenseJSONObject.getString(LicenseDefine.EXPIR_TIME);
			if (StringUtils.isEmpty(expir_time)) {
				return true;
			} else {
				if (DateUtil.compare_date(expir_time,
						DateUtil.longTimeMillisToDateTimeFullDatePattern(System.currentTimeMillis())) >= 0) {// 未过期
					return true;
				} else {
					return false;
				}
			}

		}
		return true;
	}

	/**
	 * 最大调度台用户数(指最大登录PC端用户数)
	 *
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseMAX_DISPATCH_USER_NUM
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午4:37:07
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseMaxDispatchUserNum(int sum) {
		String max_dispatch_user_num = licenseJSONObject.getString(LicenseDefine.MAX_DISPATCH_USER_NUM);
		if (StringUtils.isEmpty(max_dispatch_user_num) || Integer.valueOf(max_dispatch_user_num) == 0) {
			return false;
		}

		/*
		 * HashMap<String, Object> map = new HashMap<String, Object>();
		 * map.put("type","1"); List<DispatchUser> list = dispatchUserService.
		 */

		if (Integer.valueOf(max_dispatch_user_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 最大调度用户数（分机）(指最大支持APP用户或者微信用户数)
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseMaxAppWechatNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:57:15
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseMaxAppWechatNum(int sum) {
		String max_app_wechat_num = licenseJSONObject.getString(LicenseDefine.MAX_APP_WECHAT_NUM);
		if (StringUtils.isEmpty(max_app_wechat_num) || Integer.valueOf(max_app_wechat_num) == 0) {
			return false;
		}
		if (Integer.valueOf(max_app_wechat_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * 最大视频会议室数(指系统允许建最大视频会议室的数量)'
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseMaxVideoConferenceNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:04:29
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseMaxVideoConferenceNum(int sum) {
		String max_video_conference_num = licenseJSONObject.getString(LicenseDefine.MAX_VIDEO_CONFERENCE_NUM);
		if (StringUtils.isEmpty(max_video_conference_num) || Integer.valueOf(max_video_conference_num) == 0) {
			return false;
		}
		if (Integer.valueOf(max_video_conference_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 视频会议室最大容纳数(指系统允许每个视频会议室容纳多少方成员参会)
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseVideoConferenceUserMaxNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:12:32
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseVideoConferenceUserMaxNum(int sum) {
		String video_conference_user_max_num = licenseJSONObject.getString(LicenseDefine.VIDEO_CONFERENCE_USER_MAX_NUM);
		if (StringUtils.isEmpty(video_conference_user_max_num) || Integer.valueOf(video_conference_user_max_num) == 0) {
			return false;
		}
		if (Integer.valueOf(video_conference_user_max_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 最大语音会议室数(指系统允许建最大语音会议室的数量)
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseMaxAudioConferenceNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:19:58
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseMaxAudioConferenceNum(int sum) {
		String max_audio_conference_num = licenseJSONObject.getString(LicenseDefine.MAX_AUDIO_CONFERENCE_NUM);
		if (StringUtils.isEmpty(max_audio_conference_num) || Integer.valueOf(max_audio_conference_num) == 0) {
			return false;
		}
		if (Integer.valueOf(max_audio_conference_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 语音会议室最大容纳数(指系统允许每个语音会议室容纳多少方成员参会)
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseAudioConferenceUserMaxNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:20:16
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseAudioConferenceUserMaxNum(int sum) {
		String audio_conference_user_max_num = licenseJSONObject.getString(LicenseDefine.AUDIO_CONFERENCE_USER_MAX_NUM);
		if (StringUtils.isEmpty(audio_conference_user_max_num) || Integer.valueOf(audio_conference_user_max_num) == 0) {
			return false;
		}
		if (Integer.valueOf(audio_conference_user_max_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 最大调度广播数(指系统允许建多少个广播组（广播组账号）
	 * 
	 * @project_name dm
	 * @package_name com.wf.ew.common.utils
	 * @file_name LicenseUtil.java
	 * @enclosing_method getLicenseMaxBroadcastNum
	 * @return_type boolean
	 * @author liubaoping
	 * @date 2020年4月9日 下午5:25:07
	 * @TODO
	 * @@param sum
	 * @@return
	 */
	public static boolean getLicenseMaxBroadcastNum(int sum) {
		String max_broadcast_num = licenseJSONObject.getString(LicenseDefine.MAX_BROADCAST_NUM);
		if (StringUtils.isEmpty(max_broadcast_num) || Integer.valueOf(max_broadcast_num) == 0) {
			return false;
		}
		if (Integer.valueOf(max_broadcast_num) < sum) {
			return false;
		} else {
			return true;
		}

	}

}
