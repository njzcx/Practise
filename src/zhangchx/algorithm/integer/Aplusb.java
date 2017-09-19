package zhangchx.algorithm.integer;

/**
 * 数字相加，不能使用+号
 * @author Snow
 *
 */
public class Aplusb {
	public static void main(String[] args) {
		System.out.println(aplusb2(10, 2));
		
	}
	
	public static int aplusb(int a, int b) {
		while (b != 0) {
            int _a = a ^ b;
            int _b = (a & b) << 1;
            a = _a;
            b = _b;
        }
        return a;
	}
	
	public static int aplusb2(int a, int b) {
		int a_tmp = 0;
    	a_tmp = a ^ b;
        b = (a & b) << 1;
        if (b != 0) {
        	a_tmp = aplusb2(a_tmp, b);
        }
        return a_tmp;
	}
}
