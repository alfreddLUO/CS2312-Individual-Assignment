public class ExMemberIDalreadyinUse extends Exception{
    ExMemberIDalreadyinUse(Member m) {
        super("Member ID already in use: "+m.getID()+' '+m.getName());
    }

}