public class Main {
    public static void main(String[] args){
        Buffer h = new Buffer();
        Producer first = new Producer(h);
        Producer sec = new Producer(h);
        Producer th = new Producer(h);

        Consumer four = new Consumer(h);
        Consumer five = new Consumer(h);
        Consumer sith = new Consumer(h);

        Thread t1 = new Thread(first);
        Thread t2 = new Thread(sec);
        Thread t3 = new Thread(th);

        Thread t4 = new Thread(four);
        Thread t5 = new Thread(five);
        Thread t6 = new Thread(sith);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}
