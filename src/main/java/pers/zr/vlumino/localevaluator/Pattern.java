package pers.zr.vlumino.localevaluator;

public class Pattern {
    public static void printVlumino() {
        System.out.println(
                " __   __   _      _   _  __  __    ___    _  _     ___   \n" +
                        " \\ \\ / /  | |    | | | ||  \\/  |  |_ _|  | \\| |   / _ \\  \n" +
                        "  \\ V /   | |__  | |_| || |\\/| |   | |   | .` |  | (_) | \n" +
                        "  _\\_/_   |____|  \\___/ |_|__|_|  |___|  |_|\\_|   \\___/  \n"
        );
    }

    public static void printChinese() {
        System.out.println("   ___    _  _     ___    _  _     ___     ___     ___   \n" +
                "  / __|  | || |   |_ _|  | \\| |   | __|   / __|   | __|  \n" +
                " | (__   | __ |    | |   | .` |   | _|    \\__ \\   | _|   \n" +
                "  \\___|  |_||_|   |___|  |_|\\_|   |___|   |___/   |___|  \n"
        );
    }

    public static void printChess() {
        System.out.println("   ___    _  _     ___     ___     ___   \n" +
                "  / __|  | || |   | __|   / __|   / __|  \n" +
                " | (__   | __ |   | _|    \\__ \\   \\__ \\  \n" +
                "  \\___|  |_||_|   |___|   |___/   |___/  \n"
        );
    }

    public static void main(String[] args) {
        Pattern.printVlumino();
        Pattern.printChinese();
        Pattern.printChess();
    }
}
