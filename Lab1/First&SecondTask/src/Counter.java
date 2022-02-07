public class Counter {
    public int counter = 0;
    //First task is the same code but without synchronized keyword
    synchronized public void increment(){
        counter+=1;
    }
    synchronized public void decrement(){
        counter-=1;
    }
}
