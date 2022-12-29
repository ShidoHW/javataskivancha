package console_driver;

import actions_with_db.Requests;
import actions_with_db.TableInfo;

import java.util.*;

public enum Reader {

    DELETE("delete") {
        @Override
        public Map<String, List<String>> read(String tableName) {
            List<String> collumns = TableInfo.value(tableName).collumns();
            Map<String, List<String>> map = new TreeMap();

            for (String col:collumns) {
                System.out.print(col+": ");
                String val = scan.nextLine();
                if(val.equals(""))
                    continue;
                map.put(col, List.of(val));
            }
            return map;
        }
    },
    READ("read") {
        @Override
        public Map<String, List<String>> read(String tableName) {
            List<String> collumns = TableInfo.value(tableName).collumns();
            Map<String, List<String>> map = new TreeMap();

            for (String col:collumns) {
                System.out.print(col+": ");
                String val = scan.nextLine();
                if(val.equals(""))
                    continue;
                map.put(col, List.of(val));
            }
            return map;
        }
    },
    CREATE("add") {
        @Override
        public Map<String, List<String>> read(String tableName) {
            List<String> collumns = TableInfo.value(tableName).collumns();
            Map<String, List<String>> map = new TreeMap();
            for (String col:collumns) {
                System.out.print(col+": ");
                String val = scan.nextLine();
                map.put(col, List.of(val));
            }
            return map;
        }
    },
    UPDATE("update") {
        @Override
        public Map<String, List<String>> read(String tableName) {
            List<String> collumns = TableInfo.value(tableName).collumns();
            Map<String, List<String>> map = new TreeMap();

            for (String col:collumns) {
                System.out.print(col+": ");
                String val = scan.nextLine();
                if(val.equals(""))
                    continue;

                String[] s = val.trim().split("to");

                map.put(col, List.of(s[0], s[1]));
            }
            return map;
        }
    };


    private String typeReader;
    private static Scanner scan = new Scanner(System.in);

    Reader(String type) {
        this.typeReader = type;
    }



    public abstract Map<String, List<String>> read(String tableName);

    public static Reader value(String type) {
        return Arrays.stream(values())
        .filter(it -> it.typeReader.equals(type))
        .findFirst()
        .orElseThrow();
    }
}

