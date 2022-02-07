package FirstTask;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt[] in;
    private final int size;

    public Consumer(One2OneChannelInt[] in, int size) {
        this.in = in;
        this.size = size;
    }

    public void run() {
        int i;
        Guard[] events = new Guard[in.length];

        for (i = 0; i < in.length; i++)
            events[i] = in[i].in();

        Alternative alternative = new Alternative(events);
        for (i = 0; i < size; i++) {
            int item = in[alternative.select()].in().read();
            System.out.println("I got : "+ item);
        }
        System.exit(0);
    }
}

