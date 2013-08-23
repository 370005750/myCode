package cc.openhome;

public class Anemonefish extends Fish {
   public Anemonefish(String name) {
       super(name);
   }
   
    @Override
    public void swim() {
        System.out.printf("小丑鱼 %s 游泳%n", name);
    }
}
