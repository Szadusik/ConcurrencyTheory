public class main {
    public static void main(String[] args){
        Monitor_Drukarek mon = new Monitor_Drukarek(3);
        for(int i=0;i<5;i++){
            forever p = new forever(mon,i);
            p.start();
        }
    }
}
