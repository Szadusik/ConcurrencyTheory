package FirstTask;

public class forever extends Thread {
    private final Process process;

    public forever(Process p) {
        this.process = p;
    }

    public void run() {
        for(;;) {
            try {
                process.move();
                sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
