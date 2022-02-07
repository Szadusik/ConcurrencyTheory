package SecondTask;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt in;
    private final int size;

    public Consumer( One2OneChannelInt in,  int size) {
        this.in = in;
        this.size = size;
    }

    public void run() {
        for (int idx = 0; idx < size; idx++) {
            int item = in.in().read();
            System.out.println("I got : " + item);
        }
        System.exit(0);
    }
}

