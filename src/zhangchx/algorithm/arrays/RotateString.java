package zhangchx.algorithm.arrays;

import org.junit.Test;

public class RotateString {
	@Test
	public void test() throws Exception {
		rotateString("abcdefg".toCharArray(), 3);
	}
	
	public void rotateString(char[] str, int offset) {
        if (offset > str.length) {
            throw new IllegalArgumentException("错误参数");
        }
        
        char[] newstr = new char[str.length];
        System.arraycopy(str, str.length - offset, newstr, 0, offset);
        System.arraycopy(str, 0, newstr, offset, str.length - offset);
        str = newstr;
        System.out.print("offset=" + offset + " => \"" + new String(str) + "\"");
    }
}
