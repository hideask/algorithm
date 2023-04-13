package FactoryPattern.SimpleFactory;

/**
 * description:TestFactory
 * create user: songj
 * date : 2020/9/16 21:54
 */
public class TestFactory {
    public static void main(String[] args) {
        PhoneFactory phoneFactory = new PhoneFactory();
        HuaWei huaWei = (HuaWei) phoneFactory.createPhone("HuaWei");
        Vivo vivo = (Vivo) phoneFactory.createPhone("Vivo");
    }
}
