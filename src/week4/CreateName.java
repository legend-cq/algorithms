package week4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;

public class CreateName {
	{
		String name = "陈歆祎";
	}
	public static ArrayList<Character> excludes = new ArrayList<Character>();
	{
		excludes.add('半');
		excludes.add('山');
		excludes.add('醉');
		excludes.add('白');
		excludes.add('孤');
		excludes.add('青');
		excludes.add('小');
		excludes.add('初');
		excludes.add('优');
		excludes.add('恨');
		excludes.add('慧');
		excludes.add('玉');
		excludes.add('琴');
		excludes.add('欢');
		excludes.add('莉');
		excludes.add('波');
		excludes.add('飞');
		excludes.add('淑');
		excludes.add('新');
		excludes.add('天');
		excludes.add('痴');
		excludes.add('尔');
		excludes.add('案');
		excludes.add('莲');
		excludes.add('柳');
		excludes.add('巧');
//		excludes.add('梦');
	}
	
	public static void main(String[] args) throws IOException {
		List<Character> chars =new ArrayList<Character>();
		HashSet<Character> charSet = new HashSet<Character>();
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("C:\\chen\\name\\girlsName.txt"),"UTF-8"));  
		String line = null;  
		while ((line = br.readLine()) != null) {  
		    char[] charArray = line.toCharArray();
			for(int i=0;i<charArray.length;i++) {
				if(isChinese(charArray[i])) {
					if(!chars.contains(charArray[i]) && !excludes.contains(charArray[i])) {
						chars.add(charArray[i]);
					}
					charSet.add(charArray[i]);
				}
			}  
		}  
		br.close();
//		for (Character string : chars) {
//			System.out.print(" ");
//			System.out.print(string);
//		}
		for(int i = 0; i < 20;i++) {
			System.out.println(createName(chars));
		}
	}
	
	public static String createName(List<Character> chars) {
		int p1 = StdRandom.uniform(chars.size());
		char n1 = chars.get(p1);
		int p2 = 0;
		char n2 = '\u0000';
		do {
			p2 = StdRandom.uniform(chars.size());
			n2 = chars.get(p2);
		} while(n1 == n2);
		return "陈" + n1 + n2;
	}
	
	// GENERAL_PUNCTUATION 判断中文的“号  
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号  
    private static final boolean isChinese(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
//                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                ) {  
            return true;  
        }  
        return false;  
    }  
  
    public static final boolean isChinese(String strName) {  
        char[] ch = strName.toCharArray();  
        for (int i = 0; i < ch.length; i++) {  
            char c = ch[i];  
            if (isChinese(c)) {  
                return true;  
            }  
        }  
        return false;  
    }   
  
    public static final boolean isChineseCharacter(String chineseStr) {  
        char[] charArray = chineseStr.toCharArray();  
        for (int i = 0; i < charArray.length; i++) {  
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    /** 
     * @deprecated; 弃用。和方法isChineseCharacter比效率太低。 
     * */  
    public static final boolean isChineseCharacter_f2() {  
        String str = "！？";  
        for (int i = 0; i < str.length(); i++) {  
            if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+")) {  
                return true;  
            }  
        }  
        return false;  
    }  
}
