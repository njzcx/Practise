package com.zhangchx.module;

/**
 * 身份证号码构造类
 * @author zhangchx
 * @date 2017-6-14
 * @email zhangchx@esensoft.com
 */
public class IDNumberCreator {

	public static void main(String[] args) {
		CreateIDNumber18("232622193404111025");
//		System.out.println(String.valueOf(Long.MAX_VALUE));
		/*Date date = new Date();
		SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf_hms = new SimpleDateFormat("hhmmss");
		sdf_ymd.format(date);
		sdf_hms.format(date);*/
	}
	
	/**
	 * 根据id号码(17位)生成第18位校验位
	 * @param id
	 */
	public static void CreateIDNumber18(String id) {
		if (id.length() < 17) {
			return;
		}
		char[] 每位数字S = id.toCharArray();
		int[] 系数 = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		char[] 对应关系 = {'1','0','X','9','8','7','6','5','4','3','2'};
		int 和 = 0;
		for(int i = 0; i < 17; i++) {
			 int 乘积 = Integer.parseInt(String.valueOf(每位数字S[i])) * 系数[i];
			 和 = 和 + 乘积;
		}
		int 余数 = 和%11;
		System.out.println(对应关系[余数]);
	}

}
