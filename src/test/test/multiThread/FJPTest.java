package test.multiThread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2016/10/28.
 */
public class FJPTest {


    public FJPTask getFJPTask(int k){
        return new FJPTask(k);
    }

    public FJPTaskWithReturn getFJPTaskWithReturn(int k){
        return new FJPTaskWithReturn(k);
    }

    public static void main(String[] args) {
        //休眠时间，10000=1S
        int time = 10000;
        //FJP线程池,线程数50,超出线程数会进行队列
        ForkJoinPool pool = new ForkJoinPool(50);
        FJPTest test=new FJPTest();
        while (true) {
            for(int k=0;k<60;k++){
                FJPTask task=test.getFJPTask(k);
                FJPTaskWithReturn rtask=test.getFJPTaskWithReturn(k);
                //放入线程池执行，异步
                pool.execute(task);
                pool.execute(rtask);
                System.out.println("pool工作中的线程为："+pool.getActiveThreadCount());
            }
            System.out.println("pool工作中的线程为："+pool.getActiveThreadCount());
            while(pool.getActiveThreadCount()!=0){
                System.out.println("pool工作中的线程为："+pool.getActiveThreadCount());
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("pool工作中的线程为(全部完成)："+pool.getActiveThreadCount());



        }


    }

    //RecursiveAction是fjp无返回值扩建接口
    private class FJPTask extends RecursiveAction {

        private static final long serialVersionUID = 1L;
        private int threadid;

        public FJPTask(int i){
            this.threadid=i;
        }

        //任务线程工作
        @Override
        protected void compute() {
            int i= (int)(Math.random()*50000);
            System.out.println("线程"+threadid+"随机工作时长："+i);
            try {
                Thread.sleep(i);
                System.out.println("线程"+threadid+"随机工作完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class FJPTaskWithReturn extends RecursiveTask<Integer>{

        private static final long serialVersionUID = 1L;
        private int threadid;

        public FJPTaskWithReturn(int i){
            this.threadid=i;
        }

        @Override
        protected Integer compute() {
            int i= (int)(Math.random()*10000);
            System.out.println("线程"+threadid+"随机工作时长(with return)："+i);
            try {
                Thread.sleep(i);
                System.out.println("线程"+threadid+"随机工作完成(with return)");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }
    }


}