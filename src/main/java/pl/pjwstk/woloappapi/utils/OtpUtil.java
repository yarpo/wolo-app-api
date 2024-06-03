package pl.pjwstk.woloappapi.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {
    public String generateOtp(){
        var random = new Random();
        var randomNumber = random.nextInt(999999);
        StringBuilder result = new StringBuilder(Integer.toString(randomNumber));
        while(result.length()<6){
            result.insert(0, "0");
        }
        return result.toString();
    }
}
