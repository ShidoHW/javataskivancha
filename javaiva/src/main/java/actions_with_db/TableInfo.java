package actions_with_db;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum TableInfo {
    PLAYER("player") {
        @Override
        public String tableName() {
            return PLAYER.tableName;
        }

        @Override
        public Map<String, String> typecollumns() {
            return Map.of("playerId", int.class.toString(),
                    "nickname", String.class.toString());
        }



        @Override
        public List<String> collumns() {
            return List.of("playerId", "nickname");
        }

        @Override
        public String create() {
            return "CREATE TABLE " + PLAYER.tableName
                    + "("
                    + "playerId int NOT NULL,"
                    + "nickname varchar(45) DEFAULT NULL,"
                    + " PRIMARY KEY (playerId)" +
                    ")";
        }
    },
    CURRENCIE("currencies") {
        @Override
        public String tableName() {
            return CURRENCIE.tableName;
        }

        @Override
        public Map<String, String> typecollumns() {
            return Map.of("id",int.class.toString(),
                    "playerId", int.class.toString(),
                    "resourceId", int.class.toString(),
                    "name", String.class.toString(),
                    "count", int.class.toString());
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "name", "count");
        }

        @Override
        public String create() {
            return "CREATE TABLE " + CURRENCIE.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  name varchar(45) DEFAULT NULL," +
                    "  count int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    },
    ITEM("items") {
        @Override
        public String tableName() {
            return ITEM.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "count", "level");
        }
        @Override
        public Map<String, String> typecollumns() {
            return Map.of("id",int.class.toString(),
                    "playerId", int.class.toString(),
                    "resourceId", int.class.toString(),
                    "level", int.class.toString(),
                    "count", int.class.toString());
        }
        @Override
        public String create() {
            return "CREATE TABLE " + ITEM.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  count int DEFAULT NULL," +
                    "  level int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    },
    PROGRESS("progresses") {
        @Override
        public String tableName() {
            return PROGRESS.tableName;
        }

        @Override
        public List<String> collumns() {
            return List.of("id", "playerId", "resourceId", "score", "maxScore");
        }
        @Override
        public Map<String, String> typecollumns() {
            return Map.of("id",int.class.toString(),
                    "playerId", int.class.toString(),
                    "resourceId", int.class.toString(),
                    "score", int.class.toString(),
                    "maxScore", int.class.toString());
        }
        @Override
        public String create() {
            return "CREATE TABLE " +PROGRESS.tableName +
                    "(" +
                    "  id int NOT NULL," +
                    "  playerId int NOT NULL," +
                    "  resourceId int NOT NULL," +
                    "  score int DEFAULT NULL," +
                    "  maxScore int DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ")";
        }
    };


    private String tableName;

    public abstract String tableName();

    public abstract Map <String,String>typecollumns();
    public abstract List<String> collumns();
    public abstract String create();



    TableInfo(String tableName) {
        this.tableName = tableName;
    }



    public static TableInfo value(String tableName) {
        return Arrays.stream(values())
                .filter(it -> it.tableName.equals(tableName))
                .findFirst()
                .orElseThrow();
    }
}
