package cc.openhome;

public class SwordsMan extends Role {
    public void fight() {
        System.out.println("挥剑攻击");
    }
    
    public String toString() {
        return String.format("剑士 (%s, %d, %d)", this.name, 
                this.level, this.blood);
    }
}
