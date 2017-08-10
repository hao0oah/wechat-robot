package io.github.biezhi.wechat.robot;

import io.github.biezhi.wechat.Utils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author biezhi
 *         18/06/2017
 */
public abstract class BaseTest {

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    // 测试枚举
    @Test
    public void testEnum(){
        TYPE animal = TYPE.ANIMAL;
        System.out.println(animal);
        System.out.println(animal.MAN);
    }

    // 测试map初始容量
    @Test
    public void testMap(){
        Map<String, Object> data = new HashMap<String, Object>(2,1);
        data.put("key", "apiKey");
        data.put("info", "question");
        System.out.println(data.size());
    }

    // 测试随机数
    @Test
    public void testRandom(){
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i=0;i<10000000;i++) {
            int randoms = random.nextInt(10);
        }
        long end = System.currentTimeMillis();
        System.out.println("1耗时："+(end-start));


        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000 ; i++) {
            double a = Math.random() * 9;
            a = Math.ceil(a);
            int randomNum = new Double(a).intValue();
        }
        end = System.currentTimeMillis();
        System.out.println("2耗时："+(end-start));
    }
    @Test
    public void testRandom2(){
        long start = System.currentTimeMillis();
        String str = Utils.getRandomNumber(100000);
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(start-end)+"毫秒"+"产生数据为："+str);
    }

    @Test
    public void testDouble(){
        double a = 100.00;
        String b = String.format("%.2f",a);
        System.out.println(b);
    }

    @Test
    public void testSplit(){
        String str = "1$2$3$4$5$";
        String[] strs = str.split("\\$");
        System.out.println(strs.length);
        for (String s : strs){
            System.out.print(s + " ");
        }
    }

    @Test
    public void testString() {
        String s1 = "abc : false";
        String s2 = "abc";

        System.out.println(s1 == s2 + " : " + s1.equals(s2));
    }

}

// 测试静态类，静态方法
class StaticTest{
    int age;
    public static void doo(){
//        静态方法中不能调用非静态方法，不能使用this变量
//        this.doo2();
    }

    public void doo2(){
        // 普通方法中可以调用静态方法，也可以使用this变量
        this.doo();
        this.age = 1;
    }

}

enum TYPE{
    // 枚举的类型必须在成员变量的前面定义,默认为 public static final 类型
    MAN(0,"人类"),ANIMAL(1,"动物");

    private int code;
    private String name;

    TYPE(int code,String name) {
        System.out.println("init "+name);
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TYPE{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
