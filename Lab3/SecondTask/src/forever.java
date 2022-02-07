public class forever extends Thread{
    private final Kelner mon;
    private final int ID;
    public forever(Kelner mon, int ID){
        this.mon = mon;
        this.ID = ID;
    }

    public void run(){
        try {
            this.mon.chce_stolik(this.ID);
            System.out.println("Osoba z pary nr " + this.ID + " je przy stoliku");
            this.mon.zwalniam();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
