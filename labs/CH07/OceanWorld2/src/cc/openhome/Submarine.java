package cc.openhome;

public class Submarine implements Swimmer {
    private String name;
    
    public Submarine(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void swim() {
        System.out.printf("潜水艇 %s 潜行%n", name);
    }
}
