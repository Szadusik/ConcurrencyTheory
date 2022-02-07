import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Kelner mon = new Kelner();
        List<forever> people = new ArrayList<>();
        for(int i=0;i < 20; i++){
            people.add(new forever(mon,i%10));
        }
        for (Thread p : people) {
            p.start();
        }
        for (Thread p : people) {
            p.join();
        }
    }
}
