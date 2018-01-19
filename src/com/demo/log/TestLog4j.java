package com.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 引入maven->log，配置log4j.properties即可
 * 具体如何找到slf4j的实现类，实现类又如何找到配置文件的原理暂时不知道
 * @author Administrator
 *
 */
public class TestLog4j {
	private static Logger logger = LoggerFactory.getLogger(TestLog4j.class);
	
	public static void main(String[] args) {
		logger.info("Hello {}", "log4j");
	}
}
