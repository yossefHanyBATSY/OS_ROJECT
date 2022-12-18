

public enum State {
    THINK(0, "Think"),
    HUNGRY(1, "hungry"),
    EAT(2, "Eat");

    private int indx;
    private String stat;

    private State(int indx, String stat) {
        this.indx = indx;
        this.stat = stat;
    }

    public int getIndx() {
        return this.indx;
    }

    public String toString() {
        return this.stat;
    }

    public static State getState(int indx) {
        State[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            State state = var1[var3];
            if (state.getIndx() == indx) {
                return state;
            }
        }

        return null;
    }
}
