package com.demo.tair;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.tair.impl.DefaultTairManager;

public class TairTest {
	
	static Log log = LogFactory.getLog(TairTest.class);
	public static void main(String[] args) {
		log.error("123123");
		DefaultTairManager  defaultTairManager = new DefaultTairManager();
		List<String> cs = new ArrayList<String>();
		cs.add("192.168.1.100:5198");
		defaultTairManager.setConfigServerList(cs);
		defaultTairManager.setGroupName("group_1");
		defaultTairManager.init();

	}
}
