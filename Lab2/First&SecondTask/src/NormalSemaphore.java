public class NormalSemaphore {
    public int recources;
    public NormalSemaphore(int recources){
        this.recources = recources;
    }

    synchronized public void podnies(){
        this.recources++;
        notifyAll();
    }

    synchronized public void opusc(){
        while(this.recources == 0){
            try{
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.recources--;
    }
}
