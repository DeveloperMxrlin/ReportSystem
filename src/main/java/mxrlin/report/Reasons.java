package mxrlin.report;

public enum Reasons {

    HACKING(1, "Using third party applications to cheat."),
    SKIN(2, "Using an inappropriate skin."),
    BULLYING(3, "Insulting others with not allowed words."),
    PRIVATE_INFORMATIONS(4, "Giving away private Informations.");

    private final int id;
    private final String formatted;

    private Reasons (int id, String formatted){
        this.id = id;
        this.formatted = formatted;
    }

    public int getId() {
        return id;
    }

    public String getFormatted() {
        return formatted;
    }

    public static Reasons getReasonByID(int id){
        for(Reasons reason : Reasons.values()){
            if(reason.getId() == id) return reason;
        }
        return null;
    }

}
