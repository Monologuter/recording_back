package com.ruoyi.common.utils;


public class ConfigProperUtil
{
	private static PropertyUtil initproper = null;
	 
    public static final String LICENSE_CONTENT = "license_content";
	static
	{
		initproper = new PropertyUtil(ConfigProperUtil.class.getResource("/application.yml").getPath());
	}

	public static String getPropertyString(String key)
	{
		return initproper.getPropertyString(key);
	}

	public static int getPropertyInt(String key)
	{
		return initproper.getPropertyInt(key);
	}
}