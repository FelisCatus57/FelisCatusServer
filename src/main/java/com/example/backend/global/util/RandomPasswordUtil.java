package com.example.backend.global.util;

import java.util.Random;

// 임의의 랜덤 비밀번호 생성을 위한 클래스
public class RandomPasswordUtil {

    public static String generateRandomPassword() {
        int idx = 0;
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
        };	//배열안의 문자 숫자는 원하는대로

        StringBuffer password = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8 ; i++) {
            double rd = random.nextDouble();
            idx = (int) (charSet.length * rd);

            password.append(charSet[idx]);
        }
        System.out.println(password);
        return password.toString();
        //StringBuffer 를 String 으로 변환해서 return 하려면 toString()을 사용하면 된다.
    }
}
