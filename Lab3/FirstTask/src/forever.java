public class forever extends Thread{
    private int which;
    private int drukarka;
    public Monitor_Drukarek mon;
    public forever(Monitor_Drukarek mon, int which){
        this.mon = mon;
        this.which = which;
        this.drukarka = -1;
    }
    public void run(){
        try {
            drukarka = this.mon.reserve();
            System.out.println("Wątek "+ this.which + " drukuje sobie na drukarce " + this.drukarka );
            this.mon.release(this.drukarka);
            System.out.println("Wątek "+ this.which + " zwolnik drukarke");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
