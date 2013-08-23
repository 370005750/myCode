package cc.openhome;

import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        //  补上程序代码
        int number = (int) (Math.random() * 10);
        int guess;
        
        do {
            System.out.print("猜数字（0 ~ 9）:");
            guess = // 补上程序代码
        } while(guess != number);
        
        System.out.println("猜中了...XD");
    }
}
