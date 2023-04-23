package top.frnks.copchasethief.type;

public enum RandomStringType {
    ALL_UPPERCASE("ALL UPPERCASE"),
    ALL_LOWERCASE("all lowercase"),
    SPONGEBOB_TEXT("sPoNGebOB tExT");

    private final String typeName;
    RandomStringType(String s) {
        this.typeName = s;
    }

    public String toString() {
        return typeName;
    }
}