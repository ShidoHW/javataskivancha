package console_driver;


import actions_with_db.Connect;
import actions_with_db.Requests;
import actions_with_db.TableInfo;
import converters.Converter;
import player_structure.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public enum CRUD {
    DELETE("delete") {
        @Override
        public void execute(Connect connect, String tableName, Map<String, List<String>> command) throws SQLException {
            List<String> collumns = new ArrayList<>(command.keySet());
            List<Object> values = new ArrayList<>();
            for (String col:collumns) {
                values.add(command.get(col).get(0));
            }
            connect.update(Requests.remove(tableName, collumns, values));

        }
    },
    READ("read") {
        @Override
        public void execute(Connect connect, String tableName, Map<String, List<String>> command) throws SQLException {

            List<String> collumns = new ArrayList<>(command.keySet());
            List<Object> values = new ArrayList<>();
            for (String col:collumns) {
                values.add(command.get(col).get(0));
            }
            ResultSet rs = connect.getResultSet(Requests.read(tableName, collumns, values));
            System.out.println("Найденые значения:");
            System.out.println();
            List<String> col = TableInfo.value(tableName).collumns();

            while (rs.next()){
                for (String c:col) {
                    System.out.println(c+": "+rs.getObject(c));
                }
                System.out.println();
            }

        }
    },
    CREATE("create") {
        @Override
        public void execute(Connect connect, String tableName, Map<String, List<String>> command) throws SQLException {
            List<String> collumns = new ArrayList<>(command.keySet());
            List<Object> values = new ArrayList<>();
            for (String col:collumns) {
                values.add(command.get(col).get(0));
            }
            connect.update(Requests.insert(tableName, collumns, values));

        }
    },
    UPDATE("update") {
        @Override
        public void execute(Connect connect, String tableName, Map<String, List<String>> command) throws SQLException {
            List<String> collumns1 = new ArrayList<>();
            List<Object> values1 = new ArrayList<>();
            List<String> collumns2 = new ArrayList<>();
            List<Object> values2 = new ArrayList<>();

            for (String col:command.keySet()) {
                List<String> vals = command.get(col);
                if(!vals.get(0).equals("_")){
                    collumns1.add(col);
                    values1.add(vals.get(0));
                }
                if(!vals.get(1).equals("_")){
                    collumns2.add(col);
                    values2.add(vals.get(1));
                }
            }
            connect.update(Requests.update(tableName, collumns2, values2, collumns1, values1));

        }
    };


    private String typeCommand;

    CRUD(String command) {
        this.typeCommand = command;
    }



    public abstract void execute(Connect connect, String tableName, Map<String, List<String>> command) throws SQLException;

    public static CRUD value(String typeCommand) {
        return Arrays.stream(values())
                .filter(it -> it.typeCommand.equals(typeCommand))
                .findFirst()
                .orElseThrow();
    }
}

