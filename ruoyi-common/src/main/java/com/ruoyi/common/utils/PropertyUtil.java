package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ruoyi.common.utils.license.IoUtil;

public class PropertyUtil
{
	private Map<String, String> property = new LinkedHashMap<String, String>();
	private Map<String, List<String>> propertyNotes = new ConcurrentHashMap<String, List<String>>();
	private String filepath;
	private String split = "=";

	public PropertyUtil(String filepath)
	{
		this.filepath = filepath;
		load();
	}

	public PropertyUtil(String filepath, String split)
	{
		this.filepath = filepath;
		this.split = split;
		load();
	}

	private void load()
	{
		List<String> list = IoUtil.readFileByLines(this.filepath);
		List<String> al = null;
		for (String value : list)
		{
			if(value.trim().isEmpty())
			{
				continue;
			}
			if (value.startsWith("#"))
			{
				al = (al == null ? new ArrayList<String>() : al);
				al.add(value);
			}
			else
			{
				int i = value.indexOf(split);
				String key = value;
				String v = "";
				if (i > 0)
				{
					key = value.substring(0, i);
					v = value.substring(i + 1);
				}
				property.put(key, v);
				if (al != null)
				{
					propertyNotes.put(key, al);
					al = null;
				}
			}
		}
	}

	public void update()
	{
		List<String> bodys = new ArrayList<String>();
		for (String key : property.keySet())
		{
			List<String> al = propertyNotes.get(key);
			if (null != al)
			{
				bodys.addAll(al);
			}
			bodys.add(key + split + property.get(key));
		}
		IoUtil.writeFileByLines(this.filepath, bodys);
	}

	public Map<String, String> getProperty()
	{
		return property;
	}

	public String getPropertyString(String key)
	{
		return property.get(key);
	}

	public int getPropertyInt(String key)
	{
		return Integer.parseInt(property.get(key));
	}

	public boolean getPropertyBoolean(String key)
	{
		return Boolean.parseBoolean(property.get(key));
	}

	public void setProperty(String key, String value)
	{
		property.put(key, value);
	}

	public void setProperty(String key, String value, String note)
	{
		property.put(key, value);
		if (null != note && !note.trim().isEmpty())
		{
			List<String> al = propertyNotes.get(key);
			if (al == null)
			{
				al = new ArrayList<String>();
			}
			al.add("#" + note);
			propertyNotes.put(key, al);
		}
	}
}