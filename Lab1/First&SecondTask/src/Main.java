public class Main {
    public static void main(String[] args){
        Counter c = new Counter();

        Runner first = new Runner(c,true);
        Runner second = new Runner(c,false);

        try{
            first.start();
            second.start();
            first.join();
            second.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c.counter);
    }

}
