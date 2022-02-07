public class BinarySemaphore {
    public boolean occupied;
    public BinarySemaphore(){
        this.occupied = false;
    }

    synchronized public void podnies(){
        this.occupied = false;
        notifyAll();
    }

    synchronized public void opusc(){
        while(this.occupied){
            try{
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.occupied = true;

    }
}
