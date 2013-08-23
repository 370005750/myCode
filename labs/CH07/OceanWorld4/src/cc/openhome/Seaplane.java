package cc.openhome;

public class Seaplane implements Swimmer, Flyer {
    private String name;
    
    public Seaplane(String name) {
        this.name = name;
    }
    
    @Override
    public void fly() {
        System.out.printf("海上飞机 %s 在飞%n", name);
    }

    @Override
    public void swim() {
        System.out.printf("海上飞机 %s 航行海面%n", name);
    }
}
