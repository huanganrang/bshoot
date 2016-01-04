package test;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public class Test {
    public static void combine2(String[] args) {
        String str[] = { "A", "B", "C", "D", "E" };
        int nCnt = str.length;
        int nBit = 1<<nCnt;

        for (int i = 1; i <= nBit; i++) {
            for (int j = 0; j < nCnt; j++) {
                if ((1<<j & i ) != 0) {
                    System.out.print(str[j]);
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args){

        String[] str = {"2011", "black", "eas", "security", "hard"};

        long begin = System.currentTimeMillis();
        combine(str);
    }

    public static void combine(String[] str) {
        StringBuffer out = new StringBuffer();
        allCombine(str, out, 0);
    }

    public static  void allCombine(String[] in, StringBuffer out, int start) {
        for (int i = start; i < in.length; i++) {
            out.append(in[i]+" ");
            //System.out.println(out + "bag");
            if (i < in.length - 1) {
                allCombine(in, out, i + 1);
            }
            out.setLength(out.length() - in[i].length() - 1);
        }
    }
    public static void testStringBuffer() {
        StringBuffer sb = new StringBuffer("1,2,3,");
        System.out.println(sb.substring(0, sb.length() - 1));
    }
}
