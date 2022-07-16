package com.mu.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.resource.CompositeImageDescriptor;

/**
 * ini�����ļ���д��
 * @author MUZUKI
 *
 */
public class IniFileOpration {
	//ini�ļ��Ĵ��λ�� 
	private String FilePath = "src/com/mu/config/IniRW.ini";
	private Map<String, String> configMap = new HashMap<>();
	
	public Map<String, String> getIniFile() {
		try {
			//�����ļ������� 
			FileInputStream fis = new FileInputStream(FilePath);
			//����Properties���Զ�����������ini�ļ��е����� 
			Properties pps = new Properties();
			//���ļ����м������� 
			pps.load(fis);
			//ͨ��getProperty("������")��ȡkey��Ӧ��ֵ 
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
			//�����ļ������� 
			FileInputStream fis = new FileInputStream(FilePath);
			//�����ļ������ 
			OutputStream opt = null;
			//����Properties���Զ�����������ini�ļ��е����� 
			Properties pps = new Properties();
			//���ļ����м������� 
			pps.load(fis);
			//���ض�ȡ�ļ��� 
			opt = new FileOutputStream(FilePath);
			
			//ͨ��setProperty(key,value)��ֵ���Ḳ����ͬkey��ֵ 
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				pps.setProperty(key, val);
			}
			
			//�޸�ֵ (�ز�����) 
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
