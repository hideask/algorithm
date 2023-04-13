package FactoryPattern.Factory;

/**
 * description:HuaWei
 * create user: songj
 * date : 2020/9/16 21:31
 */
public class HuaWei implements Phone {
    public HuaWei() {
        make();
    }

    @Override
    public void make() {
        System.out.println("华为手机被创建");
    }
}
