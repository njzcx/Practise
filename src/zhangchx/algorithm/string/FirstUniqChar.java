package zhangchx.algorithm.string;

public class FirstUniqChar {
	public static void main(String[] args) {
		System.out.println(method_one("{{;;lintcodelintcode}}"));
	}
	
	public static int method_one(String s) {
		if (s == null || s.length() == 0) {
            return -1;
        }
        if (s.length() == 1) {
            return 0;
        }
        char[] cs = s.toCharArray();
        
        for (int i = 0; i < cs.length; i++) {
            int j = 0;
            for (; j < cs.length; j++) {
                if (cs[i] == cs[j] && i != j) {
                    break;
                }
            }
            if (j == cs.length) {
                return i;
            }
        }
        return -1;
	}
}
