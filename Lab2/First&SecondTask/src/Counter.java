public class Counter {
    public int counter;
    public NormalSemaphore semaphore;
    public Counter(){
        this.semaphore = new NormalSemaphore(5);
    }
    public void increment(){
        this.semaphore.podnies();
        System.out.println("Klient odlozyl kosz");
    }
    public void decrement(){
        this.semaphore.opusc();
        System.out.println("Klient zabral koszyk");
    }
}
