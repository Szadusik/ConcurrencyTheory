package FirstTask;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    final private One2OneChannelInt producer, consumer, linker;

    public Buffer(One2OneChannelInt producer, One2OneChannelInt consumer, One2OneChannelInt linker) {
        this.consumer = consumer;
        this.producer = producer;
        this.linker = linker;
    }

    public void run() {
        while (true) {
            linker.out().write(0);
            consumer.out().write(producer.in().read());
        }
    }
}
