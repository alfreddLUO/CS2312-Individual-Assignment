

public class ExItemIDalreadyinUse extends Exception {
     public ExItemIDalreadyinUse(Item it) {
        super("Item ID already in use: "+it.getID()+' '+it.getName());
     }
    
}
