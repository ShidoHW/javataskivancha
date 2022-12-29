import Auxiliary.Auxiliary;
import actions_with_db.Connect;
import console_driver.ConsoleProcessor;
import converters.Converter;
import player_structure.Player;
import web_app.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        Connect con = new Connect();
        /*
        Auxiliary.deleteAllTabels(con);
        Auxiliary.createAllTabels(con);
        Auxiliary.insertData(con,"src\\main\\java\\json\\player.json",100);
        System.out.println(Converter.upload(con)); */




        //List<Player> cash = Converter.upload(con);
        //ConsoleProcessor.processing(con, cash);



        Server.start(con);














    }


}
