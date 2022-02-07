public class Runner extends Thread {
    public Counter ob;
    public boolean which;

    public Runner(Counter ob, boolean which){
        this.ob = ob;
        this.which = which;
    }

    public void run(){
        {
            for (int i = 0; i < 10; i++) {
                this.ob.decrement();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.ob.increment();
            }
        }
    }

}
