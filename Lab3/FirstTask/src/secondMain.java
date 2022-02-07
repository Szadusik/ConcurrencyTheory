public class secondMain {
    public static void main(String[] args){
        Kelner tab = new Kelner(6);
        for(int i=0;i<6;i++){
            secondForever p = new secondForever(tab,i);
            p.start();
        }
    }
}
