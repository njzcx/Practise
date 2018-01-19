package com.zhangchx.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 统计数据服务
 * @author zhangchx
 *
 */
public class StatisticsService {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new StatisticsService().readAndCount("C:\\Users\\Administrator\\Desktop\\雾霾补助.txt");
	}
	
	/**
	 * 读取文件
	 * @throws IOException 
	 */
	private String readFile (String filepath) throws IOException {
		File file = new File(filepath);
		StringBuffer content = new StringBuffer();
		if (!file.exists()) {
			System.out.println("文件不存在");
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while((temp=reader.readLine()) != null) {
				content.append(temp);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content.toString();
	}
	
	/**
	 * 统计相同数据出现的次数
	 */
	private void countSame(String[] datas) {
		Map<String,Integer> counter = new HashMap<String,Integer>();
		for (String data : datas) {
			if (counter.containsKey(data)) {
				counter.put(data, counter.get(data)+1);
			} else {
				counter.put(data, 1);
			}
		}
		Set<String> keys = counter.keySet();
		for (String key : keys) {
			int count = counter.get(key);
			System.out.println(key + "的计数结果:" + count);
		}
	}

	/**
	 * 读取文件并统计"、"分隔后数据的数量
	 * @param filepath
	 * @throws IOException
	 */
	public void readAndCount(String filepath) throws IOException {
		String content = readFile(filepath);
		String[] datas = content.split("、");
		countSame(datas);
	}
}
