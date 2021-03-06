package cc.openhome;

public class RPG {
    public static void showBlood(SwordsMan swordsMan) {
        System.out.printf("%s 血量 %d%n",
                swordsMan.getName(), swordsMan.getBlood());
    }

    public static void showBlood(Magician magician) {
        System.out.printf("%s 血量 %d%n",
                magician.getName(), magician.getBlood());
    }

    public static void main(String[] args) {
        SwordsMan swordsMan = new SwordsMan();
        swordsMan.setName("Justin");
        swordsMan.setLevel(1);
        swordsMan.setBlood(200);
        System.out.printf("剑士：(%s, %d, %d)%n", swordsMan.getName(),
                swordsMan.getLevel(), swordsMan.getBlood());

        Magician magician = new Magician();
        magician.setName("Monica");
        magician.setLevel(1);
        magician.setBlood(100);
        System.out.printf("魔法师：(%s, %d, %d)%n", magician.getName(),
                magician.getLevel(), magician.getBlood());
        
        showBlood(swordsMan);
        showBlood(magician);
    }
}
