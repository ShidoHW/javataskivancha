package Auxiliary;

import actions_with_db.Connect;
import actions_with_db.Requests;
import actions_with_db.TableInfo;
import converters.Converter;
import json_reader.JsonReader;
import player_structure.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Auxiliary {

    public static void deleteAllTabels(Connect d) throws SQLException {
        d.dropAndCreateTable(Requests.drop(TableInfo.PROGRESS.tableName()));
        d.dropAndCreateTable(Requests.drop(TableInfo.ITEM.tableName()));
        d.dropAndCreateTable(Requests.drop(TableInfo.PLAYER.tableName()));
        d.dropAndCreateTable(Requests.drop(TableInfo.CURRENCIE.tableName()));
    }

    public static void createAllTabels(Connect d) throws SQLException {
        d.dropAndCreateTable(TableInfo.CURRENCIE.create());
        d.dropAndCreateTable(TableInfo.ITEM.create());
        d.dropAndCreateTable(TableInfo.PLAYER.create());
        d.dropAndCreateTable(TableInfo.PROGRESS.create());

    }

    public static void insertData(Connect data, String fileName, int n) throws SQLException, IOException {
        List<Player> l = JsonReader.getpostgres(fileName);
        System.out.println(l);
        Converter.insertInformation(l, data, n);
    }



}
