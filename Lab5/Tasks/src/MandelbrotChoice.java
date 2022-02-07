import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MandelbrotChoice extends JFrame {
    private final int MAX_ITER = 2000;
    private final double ZOOM = 150;
    private BufferedImage I;
    private long executionTime;

    public MandelbrotChoice(int poolSize, int taskNumber) throws ExecutionException, InterruptedException{
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        final int x = getWidth();
        final int y = getHeight();
        int[][] bitmap = new int[x][y];
        List<MandelbrotTask> tasks = new ArrayList<>();

        if(taskNumber <= 0){ //Not sliced
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    tasks.add(new MandelbrotTask(bitmap, i, j,false));
                }
            }
        }
        else{ //Sliced
            for (int taskId = 0; taskId < taskNumber; taskId++) {
                int yFrom = y * taskId / taskNumber;
                int yTo = y * (taskId + 1) / taskNumber;
                MandelbrotTask task = new MandelbrotTask(bitmap, yFrom, yTo,true);
                tasks.add(task);
            }
        }

        ExecutorService exec = Executors.newFixedThreadPool(poolSize);
        List<Future<Void>> futures = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (MandelbrotTask t : tasks) {
            futures.add(exec.submit(t));
        }

        for (Future<Void> f : futures) {
            f.get();
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                I.setRGB(i, j, bitmap[i][j] | (bitmap[i][j] << 8));
            }
        }

        this.executionTime = System.currentTimeMillis() - start;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    private class MandelbrotTask implements Callable<Void> {
        private final int[][] bitmap;
        private final boolean sliced;
        private int yFrom;
        private int yTo;
        private int x;
        private int y;

        private MandelbrotTask(int[][] bitmap, int yFrom, int yTo, boolean sliced) {
            this.bitmap = bitmap;
            this.sliced = sliced;
            if(sliced){
                this.yFrom = yFrom;
                this.yTo = yTo;
            }
            else{
                this.x = yFrom;
                this.y = yTo;
            }
        }

        @Override
        public Void call() {
            double zx, zy, cX, cY, tmp;
            int from=0,to=1,size=1;
            if(sliced){
                from = yFrom;
                to = yTo;
                size = this.bitmap.length;
            }
            for (int y = from; y < to; y++) {
                for (int x = 0; x < size; x++) {
                    zx = zy = 0;
                    cX = (x - 400) / ZOOM;
                    cY = (y - 300) / ZOOM;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--;
                    }
                    this.bitmap[x][y] = iter;
                }
            }
            return null;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}

