package SecondTask;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;

public class Main {
    static final int bufferSize = 20;
    static final int totalPortions = 250000;

    public static void main(String[] args) {

        StandardChannelIntFactory factory = new StandardChannelIntFactory();
        One2OneChannelInt[] channels = factory.createOne2One(bufferSize + 1);

        CSProcess[] process = new CSProcess[bufferSize + 2];
        process[0] = new Producer(channels[0], totalPortions);
        process[1] = new Consumer(channels[bufferSize], totalPortions);

        for (int i = 2; i < bufferSize + 2; i++) {
            process[i] = new Buffer(channels[i - 2], channels[i - 1]);
        }

        Parallel parallel = new Parallel(process);
        parallel.run();
    }
}