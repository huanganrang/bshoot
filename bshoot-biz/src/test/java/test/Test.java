package test;

/**
 * Created by zhou on 2016/1/1.
 */
public class Test {

    public static void main(String[] args){
        StringBuffer sb = new StringBuffer("1,2,3,");
        System.out.println(sb.substring(0,sb.length()-1));
    }
}
