package test;

/**
 * Created by lu on 2016/11/20.
 */
public class PrintClass implements TestInterface {

    private String testString;

    public PrintClass(){
        this.testString="no param";
    }

    public PrintClass(String testString){
        this.testString=testString;
    }

    @Override
    public void test(){
        System.out.println(testString);
    }

    @Override
    public void test(String param) {

    }





}
