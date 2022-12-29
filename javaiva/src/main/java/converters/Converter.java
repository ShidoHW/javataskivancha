package converters;

import actions_with_db.Connect;
import actions_with_db.Requests;
import actions_with_db.TableInfo;
import player_structure.Currencies;
import player_structure.Items;
import player_structure.Player;
import player_structure.Progresses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Converter {

    public static List<Player> upload(Connect connect) throws SQLException {

        Map<Integer, String> pn = new TreeMap<>();
        List<Player> postgres = new ArrayList<>();
        ResultSet set = Connect.getResultSet(Requests.readAll(TableInfo.PLAYER.tableName()));

        while (set.next()){
            pn.put(set.getInt(TableInfo.PLAYER.collumns().get(0)), set.getString(TableInfo.PLAYER.collumns().get(1)));
        }

        for (Integer key:pn.keySet()) {
            List<Currencies> lc = new ArrayList<>();
            set = Connect.getResultSet(Requests.read(TableInfo.CURRENCIE.tableName(), "playerId", key));

            while (set.next()){
                Currencies c = new Currencies();
                c.setId(set.getInt(TableInfo.CURRENCIE.collumns().get(0)));
                c.setPlayerId(key);
                c.setResourceId(set.getInt(TableInfo.CURRENCIE.collumns().get(2)));
                c.setName(set.getString(TableInfo.CURRENCIE.collumns().get(3)));
                c.setCount(set.getInt(TableInfo.CURRENCIE.collumns().get(4)));
                lc.add(c);
            }

            List<Items> li = new ArrayList<>();
            set = Connect.getResultSet(Requests.read(TableInfo.ITEM.tableName(), "playerId", key));

            while (set.next()){
                Items i = new Items();
                i.setId(set.getInt(TableInfo.ITEM.collumns().get(0)));
                i.setPlayerId(key);
                i.setResourceId(set.getInt(TableInfo.ITEM.collumns().get(2)));
                i.setCount(set.getInt(TableInfo.ITEM.collumns().get(3)));
                i.setLevel(set.getInt(TableInfo.ITEM.collumns().get(4)));
                li.add(i);
            }

            List<Progresses> lp = new ArrayList<>();
            set = Connect.getResultSet(Requests.read(TableInfo.PROGRESS.tableName(), "playerId", key));

            while (set.next()){
                Progresses p = new Progresses();
                p.setId(set.getInt(TableInfo.PROGRESS.collumns().get(0)));
                p.setPlayerId(key);
                p.setResourceId(set.getInt(TableInfo.PROGRESS.collumns().get(2)));
                p.setScore(set.getInt(TableInfo.PROGRESS.collumns().get(3)));
                p.setMaxScore(set.getInt(TableInfo.PROGRESS.collumns().get(4)));
                lp.add(p);
            }
            Player p = new Player(key, pn.get(key), lp, lc, li);
            postgres.add(p);

        }


        return postgres;
    }

    public static void insertInformation(List<Player> postgres, Connect connect, int n) throws SQLException {
        int count = 0;
        System.out.println("Insert");
        for (Player player: postgres) {
            if(count == n)
                break;
            connect.update(Requests.insert(TableInfo.PLAYER.tableName(), List.of(player.getPlayerId(), player.getNickname())));
            for (Currencies currencie : player.getCurrencies()) {
                connect.update(Requests.insert(TableInfo.CURRENCIE.tableName(),List.of(currencie.getId(), currencie.getPlayerId(), currencie.getResourceId(), currencie.getName(), currencie.getCount())));
            }
            for (Items item : player.getItems()) {
                connect.update(Requests.insert(TableInfo.ITEM.tableName(), List.of(item.getId(), item.getPlayerId(), item.getResourceId(), item.getCount(), item.getLevel())));
            }
            for (Progresses progress: player.getProgresses()) {
                connect.update(Requests.insert(TableInfo.PROGRESS.tableName(), List.of(progress.getId(), progress.getPlayerId(), progress.getResourceId(), progress.getScore(), progress.getMaxScore())));
            }

            count++;
            if(count%100==0)
                System.out.println(count/100+"%");

        }

        System.out.println("finish");
    }

}
