package com.mu.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties是java.util包下的类，表示一个资源文件，通常都是 键=值，她有一个load("xx.properties")方法
 * DbProperties类用于读取 db.properties 数据库配置信息文件， 此操作的特点：在整个项目中只操作一次 ->
 * 写成单例（只有一个此类的实例） 构造方法必须私有化，对外提供一个操作方法来获取唯一实例
 * 
 * @author MUZUKI
 *
 */

public class DbProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3203655988184617980L;
	// 静态 类一加载就执行
	private static DbProperties dbProperties = new DbProperties();

	// 单例要求构造方法必须私有化
	// 因为private了，所以在此类的外面不能实例化他
	private DbProperties() {
		try ( // 因为此类继承自 Properties类 ，所以他也有 Properties类 中的load()方法
				// DbPropertis.class.getClassLoader()获取加载器，此加载器默认从类路径(bin)下读取文件
				InputStream fis = DbProperties.class.getClassLoader().getResourceAsStream("db.properties");) {
			super.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 对外提供唯一的操作方法，来获取一次实例
	public static DbProperties getInstance() {
		if (dbProperties == null) {
			dbProperties = new DbProperties();
		}
		return dbProperties;
	}

}
