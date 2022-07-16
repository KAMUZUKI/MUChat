package com.mu.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties��java.util���µ��࣬��ʾһ����Դ�ļ���ͨ������ ��=ֵ������һ��load("xx.properties")����
 * DbProperties�����ڶ�ȡ db.properties ���ݿ�������Ϣ�ļ��� �˲������ص㣺��������Ŀ��ֻ����һ�� ->
 * д�ɵ�����ֻ��һ�������ʵ���� ���췽������˽�л��������ṩһ��������������ȡΨһʵ��
 * 
 * @author MUZUKI
 *
 */

public class DbProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3203655988184617980L;
	// ��̬ ��һ���ؾ�ִ��
	private static DbProperties dbProperties = new DbProperties();

	// ����Ҫ���췽������˽�л�
	// ��Ϊprivate�ˣ������ڴ�������治��ʵ������
	private DbProperties() {
		try ( // ��Ϊ����̳��� Properties�� ��������Ҳ�� Properties�� �е�load()����
				// DbPropertis.class.getClassLoader()��ȡ���������˼�����Ĭ�ϴ���·��(bin)�¶�ȡ�ļ�
				InputStream fis = DbProperties.class.getClassLoader().getResourceAsStream("db.properties");) {
			super.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// �����ṩΨһ�Ĳ�������������ȡһ��ʵ��
	public static DbProperties getInstance() {
		if (dbProperties == null) {
			dbProperties = new DbProperties();
		}
		return dbProperties;
	}

}
