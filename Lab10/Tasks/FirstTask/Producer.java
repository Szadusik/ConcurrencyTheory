package FirstTask;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt[] out;
    private final One2OneChannelInt[] buffer;
    private final int size;

    public Producer(One2OneChannelInt[] out, One2OneChannelInt[] buffer, int size) {
        this.out = out;
        this.buffer = buffer;
        this.size = size;
    }

    public void run() {
        int i;
        final Guard[] guards = new Guard[buffer.length];
        for (i = 0; i < out.length; i++)
            guards[i] = buffer[i].in();

        Alternative alternative = new Alternative(guards);
        for (i = 0; i < size; i++) {
            int index = alternative.select();
            buffer[index].in().read();
            out[index].out().write((int) ((Math.random() * 100) + 1));
        }
    }
}
