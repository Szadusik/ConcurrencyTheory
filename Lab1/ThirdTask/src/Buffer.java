public class Buffer {
    private String ob;

    public Buffer(){
        this.ob = "";
    }
    synchronized public void put(String message){
        while(!ob.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ob = message;
        notifyAll();
        System.out.println("Message put : " + message);
    }

    synchronized public String take(){
        while(ob.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String help = ob;
        ob = "";
        notifyAll();
        System.out.println("Message taken : " + help);
        return help;
    }
}
