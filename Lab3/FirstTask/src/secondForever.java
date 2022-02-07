public class secondForever extends Thread{
    public Kelner mon;
    private int which;
    public secondForever(Kelner mon,int which){
        this.mon = mon;
        this.which = which;
    }

    @Override
    public void run() {
        try {
            System.out.println("Klient "+ this.which + " chce stilik");
            this.mon.chceStolik(this.which);
            System.out.println("Zwolnienie stolika");
            this.mon.zwalniam();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
