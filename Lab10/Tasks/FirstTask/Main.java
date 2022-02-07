package FirstTask;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class Main {
    static final int bufferSize = 20;
    static final int totalPortions = 250000;

    public static void main(String[] args) {
        StandardChannelIntFactory factory = new StandardChannelIntFactory();

        One2OneChannelInt[] producer = factory.createOne2One(bufferSize);
        One2OneChannelInt[] consumer = factory.createOne2One(bufferSize);
        One2OneChannelInt[] buffer = factory.createOne2One(bufferSize);

        CSProcess[] processes = new CSProcess[bufferSize + 2];
        processes[0] = new Producer(producer, buffer, totalPortions);
        processes[1] = new Consumer(consumer, totalPortions);

        for (int i = 2; i < bufferSize + 2; i++)
            processes[i] = new Buffer(producer[i - 2], consumer[i - 2], buffer[i - 2]);


        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}
