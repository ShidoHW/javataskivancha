package console_driver;

import actions_with_db.Connect;
import converters.Converter;
import player_structure.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleProcessor {

    public static void processing(Connect c, List<Player> cash) throws SQLException {
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.print("type command: ");
            String command = scan.nextLine();

            if(command.contains("exit"))
                break;
            System.out.print("name table: ");
            String tableName = scan.nextLine();

            Map<String, List<String>> map = Reader.value(command).read(tableName);
            CRUD.value(command).execute(c, tableName, map);
            System.out.println();

            cash = Converter.upload(c);
        }
    }
}
