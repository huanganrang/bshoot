package test;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public class Test {
    public static void combine2(String[] str,int matchFactor,List<String> out) {
        int nCnt = str.length;
        int nBit = 1<<nCnt;
        int counter = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= nBit; i++) {
            for (int j = 0; j < nCnt; j++) {
                if ((1<<j & i ) != 0) {
                    counter++;
                    sb.append(str[j]).append(" ");
                }
            }
            if(counter==matchFactor){
                out.add(sb.toString());
            }
            counter=0;
            sb.delete(0,sb.length()-1);
        }
    }

    public static void main(String[] args) {
        String[] str = new String[]{"A"};
        List<String> out = new ArrayList<String>();
        combine2(str,1,out);
        for(String em:out){
            System.out.println(em);
        }
    }

    public static void stringCombine(String[] str,List<String> out,int matchFactor){
         byte[] bytes = new byte[str.length];
         for(int i=0;i<matchFactor;i++){
             bytes[i]=1;
         }
        for(byte b:bytes){
            System.out.println(b);
        }
    }
   /* public static void a(String[] str,List<String> out,int matchFactor){
        StringBuffer sb = null;
        for(int i=0;i<str.length;i++){
           // stringCombine(str,out,i + 1,matchFactor - 1,matchFactor);
        }
    }

    public static void stringCombine(String[] str,List<String> out,StringBuffer sb,int start,int matchFactor,final int finalMatchFactor){
       for(int i=start;i<str.length-matchFactor+1;i++){
           if(matchFactor>1){
               sb.append(str[i]).append(" ");
               stringCombine(str, out,sb, start + 1, matchFactor - 1, finalMatchFactor);
           }else{
               sb.append(str[i]);
               out.add(sb.toString());
           }
           *//*for(int j=start+1;j<str.length-matchFactor+2;j++){
               for(int k=start+2;k<str.length-matchFactor+3;k++){
                 out.add(str[i]+" "+str[j]+" "+str[k]);
               }
           }*//*
       }
    }

    public static void testStringBuffer() {
        StringBuffer sb = new StringBuffer("1,2,3,");
        System.out.println(sb.substring(0, sb.length() - 1));
    }
}*/
}