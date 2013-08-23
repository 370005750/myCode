package cc.openhome;

public class Ocean {
    public static void doSwim(Swimmer swimmer) {
        swimmer.swim();
    }
    
    public static void main(String[] args) {
        doSwim(new Anemonefish("尼莫"));
        doSwim(new Shark("兰尼"));
        doSwim(new Human("贾斯汀"));
        doSwim(new Submarine("黃色一号"));
    }
}
