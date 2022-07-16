package com.mu.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.resource.CompositeImageDescriptor;

/**
 * ini配置文件读写类
 * @author MUZUKI
 *
 */
public class IniFileOpration {
	//ini文件的存放位置 
	private String FilePath = "src/com/mu/config/IniRW.ini";
	private Map<String, String> configMap = new HashMap<>();
	
	public Map<String, String> getIniFile() {
		try {
			//创建文件输入流 
			FileInputStream fis = new FileInputStream(FilePath);
			//创建Properties属性对象用来接收ini文件中的属性 
			Properties pps = new Properties();
			//从文件流中加载属性 
			pps.load(fis);
			//通过getProperty("属性名")获取key对应的值 
			configMap.put("DOWNLOAD_PATH", pps.getProperty("DOWNLOAD_PATH"));
			configMap.put("MESSAGE_PATH", pps.getProperty("MESSAGE_PATH"));
			configMap.put("WINDOW_ALPHA", pps.getProperty("WINDOW_ALPHA"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return configMap;
	}
	
	public void setIniFile(Map<String, String> map) {
		try {
			//创建文件输入流 
			FileInputStream fis = new FileInputStream(FilePath);
			//创建文件输出流 
			OutputStream opt = null;
			//创建Properties属性对象用来接收ini文件中的属性 
			Properties pps = new Properties();
			//从文件流中加载属性 
			pps.load(fis);
			//加载读取文件流 
			opt = new FileOutputStream(FilePath);
			
			//通过setProperty(key,value)赋值，会覆盖相同key的值 
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				pps.setProperty(key, val);
			}
			
			//修改值 (必不可少) 
			pps.store(opt, null);
			opt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		IniFileOpration ifo = new IniFileOpration();
		Map<String, String> map = ifo.getIniFile();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println(key + "," + val);
		}
	}
}
