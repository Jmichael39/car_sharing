package carsharing;

import static controller.MainMenu.run;

public class Main {
    public static void main(String[] args) {
        String DbName = args.length > 0 ? args[1] : "carsharing";
        run(DbName);
    }
}