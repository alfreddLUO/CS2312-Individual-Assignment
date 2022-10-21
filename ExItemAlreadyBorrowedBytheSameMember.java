

public class ExItemAlreadyBorrowedBytheSameMember extends Exception{
    public ExItemAlreadyBorrowedBytheSameMember(){
        super("The item is already borrowed by the same member.");
    }

}
