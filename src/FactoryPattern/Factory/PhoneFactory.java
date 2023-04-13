package FactoryPattern.Factory;

/**
 * description:PhoneFactory
 * create user: songj
 * date : 2020/9/16 21:00
 */
public class PhoneFactory {

    public static Phone createPhone(String type) {
        if ("HuaWei".equals(type)) {
            return new HuaWei();
        } else if ("Vivo".equals(type)) {
            return new Vivo();
        } else {
            return null;
        }
    }

}
