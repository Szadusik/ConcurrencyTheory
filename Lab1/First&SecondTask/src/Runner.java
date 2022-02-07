public class Runner extends Thread {
    public Counter ob;
    public boolean which;

    public Runner(Counter ob, boolean which){
        this.ob = ob;
        this.which = which;
    }

    public void run(){
        {
            if (this.which) {
                for (int i = 0; i < 100000000; i++) {
                    this.ob.increment();
                }
            } else {
                for (int i = 0; i < 100000000; i++) {
                    this.ob.decrement();
                }
            }
        }
    }

}
