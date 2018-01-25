package zhangchx.algorithm.string;

public class LeftPad {
	
	public static void main(String[] args) {
		System.out.println(leftPad("foo", 5));
	}
	/**
     * @param originalStr: the string we want to append to with spaces
     * @param size: the target length of the string
     * @return: A string
     */
    static public String leftPad(String originalStr, int size) {
        // Write your code here
        return leftPad(originalStr, size, ' ');
    }

    /*
     * @param originalStr: the string we want to append to
     * @param size: the target length of the string
     * @param padChar: the character to pad to the left side of the string
     * @return: A string
     */
    static public String leftPad(String originalStr, int size, char padChar) {
        // write your code here
        if (originalStr.length() >= size) {
            return originalStr;
        }
        int padcount = size - originalStr.length();
        StringBuffer sb = new StringBuffer();
        while(padcount-- > 0) {
            sb.append(padChar);
        }
        sb.append(originalStr);
        return sb.toString();
    }
}
