package gr.ckaramolegkos.tictactoe.model;

public enum Difficulty {
    EASY,
    HARD;

    public static Difficulty fromName(String name) {
        if (name == null) {
            return EASY;
        }
        try {
            return Difficulty.valueOf(name);
        } catch (IllegalArgumentException e) {
            return EASY;
        }
    }
}
