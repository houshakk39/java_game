public enum Direction {
    NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private final int frameLineNumber; // Numéro de ligne dans la spritesheet pour chaque direction

    Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}