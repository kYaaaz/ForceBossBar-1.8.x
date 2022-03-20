package me.force.bossbar.check;

public class IsInteger {
    private final String var;
    public IsInteger(String var) {
        this.var = var;
    }
    public boolean check() {
        try {
            Integer.parseInt(var);
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }
}
