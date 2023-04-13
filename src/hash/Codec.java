package hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * description:Codec
 * create user: songj
 * 535. TinyURL 的加密与解密
 * TinyURL是一种URL简化服务， 比如：当你输入一个URL https://leetcode.com/problems/design-tinyurl 时，
 * 它将返回一个简化的URL http://tinyurl.com/4e9iAk.
 *
 * 要求：设计一个 TinyURL 的加密 encode 和解密 decode 的方法。你的加密和解密算法如何设计和运作是没有限制的，
 * 你只需要保证一个URL可以被加密成一个TinyURL，并且这个TinyURL可以用解密方法恢复成原本的URL。
 * date : 2021/6/15 15:27
 */
public class Codec {
    String pwdstr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Map map = new HashMap();
    Random random = new Random();

    public String getRandom() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6 ; i ++) {
            stringBuilder.append(pwdstr.charAt(random.nextInt(62)));
        }
        return stringBuilder.toString();
    }
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String key = getRandom();
        while (map.containsKey(key)) {
            key = getRandom();
        }
        map.put(key,longUrl);
        return "http://tinyurl.com/" + key;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return (String) map.get(shortUrl.replace("http://tinyurl.com/",""));
    }
}
