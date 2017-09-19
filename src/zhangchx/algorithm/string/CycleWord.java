package zhangchx.algorithm.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 循环单词
 * The words are same rotate words if rotate the word to the right by loop, and
 * get another. Count how many different rotate word sets in dictionary.
 * 
 * Given dict = ["picture", "turepic", "icturep", "word", "ordw", "lint"] return 3.
 * 
 * "picture", "turepic", "icturep" are same ratote words. 
 * "word", "ordw" are same too. 
 * "lint" is the third word that different from the previous two words.
 * 
 * @author Snow
 * 
 */
public class CycleWord {
	
	public static void main(String[] args) {
		String[] dict = {"picture", "turepic", "icturep", "word", "ordw", "lint"};
		new CycleWord().countRotateWords(Arrays.asList(dict));
	}
	
	/*---------------------下面不是循环单词的的实现------------------------------*/
	/*------------------下面是对有数组中，有相同字符的单词进行统计的方法实现---------------------*/
	/**
	 * Map-Map-List结构
	 * 第一个map保存words中的长度，第二map保存排序后的word，第三个list保存长度字母一致下的word集合
	 * @param words
	 * @return
	 */
	public int countRotateWords(List<String> words) {
		Map<Integer, Map> lenMap = new HashMap<Integer, Map>();
		Iterator<String> it = words.iterator();
		while (it.hasNext()) {
			String word = it.next();
			Map wordSortedMap = lenMap.get(word.length());
			if (wordSortedMap == null) {
				wordSortedMap = new HashMap();
				List list = new ArrayList();
				list.add(word);
				wordSortedMap.put(sortString(word), list);
				lenMap.put(word.length(), wordSortedMap);
				continue;
			}
			List list = (List) wordSortedMap.get(sortString(word));
			if (list == null) {
				list = new ArrayList();
				list.add(word);
				wordSortedMap.put(sortString(word), list);
				continue;
			}
			list.add(word);
		}
		return showMapMapList(lenMap);
    }
	
	private int showMapMapList(Map<Integer, Map> lenMap) {
		int i = 0;
		Set lenKeySet = lenMap.keySet();
		Iterator it = lenKeySet.iterator();
		while (it.hasNext()) {
			Map wordSortedMap = lenMap.get(it.next());
			Set wordSortedKeySet = wordSortedMap.keySet();
			Iterator it2 = wordSortedKeySet.iterator();
			while (it2.hasNext()) {
				i++;
				List list = (List) wordSortedMap.get(it2.next());
				System.out.println("数量：" + list.size() + "，值为" + Arrays.toString(list.toArray(new String[list.size()])));
			}
		}
		return i;
	}

	/**
	 * 字符串排序算法
	 * @param source
	 * @return
	 */
	private String sortString(String source) {
		char[] sourceChar = source.toCharArray();
		Arrays.sort(sourceChar);
		String result = new String(sourceChar);
		return result;
	}
}
