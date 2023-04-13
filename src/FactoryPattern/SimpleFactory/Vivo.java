package FactoryPattern.SimpleFactory;

/**
 * description:Vivo
 * create user: songj
 * date : 2020/9/16 21:31
 */
public class Vivo implements Phone {
    public Vivo() {
        make();
    }

    @Override
    public void make() {
        System.out.println("vivo手机被创建");
    }
}
