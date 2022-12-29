package actions_with_db;

import java.util.List;

public class Requests {
    public static String insert(String tableName, List<Object> values){
        return insert(tableName, TableInfo.value(tableName).collumns(), values);
    }
    public static String insert(String tableName, List<String> collumns, List<Object> values){
        String request ="INSERT INTO "+ tableName +"(";
        List<Object> val = values;
        for (int i = 0; i < collumns.size()-1; i++) {
            request +=collumns.get(i)+",";
        }
        request +=collumns.get(collumns.size()-1)+") VALUES(";

        for (int i = 0; i < val.size()-1; i++) {

            request+= getStr(val.get(i), TableInfo.value(tableName).typecollumns().get(collumns.get(i)))+",";
        }
        request+= getStr(val.get(val.size()-1), TableInfo.value(tableName).typecollumns().get(collumns.get(val.size()-1)))+")";
        return request;
    }

    public static String remove(String tableName, List<Object> values){
        return remove(tableName, TableInfo.value(tableName).collumns(), values);
    }
    public static String remove(String tableName, List<String> collumns, List<Object> values){
        List<Object> val = values;
        String request = "DELETE FROM " + tableName + " WHERE ";
        for (int i = 0; i < collumns.size()-1; i++) {
            request += collumns.get(i)+" = "+getStr(val.get(i), TableInfo.value(tableName).typecollumns().get(collumns.get(i)))+" AND ";
        }
        request +=collumns.get(collumns.size()-1)+" = "+getStr(val.get(val.size()-1), TableInfo.value(tableName).typecollumns().get(collumns.get(val.size()-1)));
        return request;
    }
    public static String readAll(String tableName){
        return "SELECT * FROM " + tableName;
    }

    public static String read(String tableName, String collumn, int key){
        return "SELECT * FROM " + tableName + " WHERE "+ collumn + " = "+Integer.toString(key);
    }

    public static String read(String tableName, List<String> collumns, List<Object> values){
        List<Object> val = values;
        String request ="SELECT * FROM " + tableName + " WHERE ";
        for (int i = 0; i < collumns.size()-1; i++) {
            request += collumns.get(i)+" = "+getStr(val.get(i), TableInfo.value(tableName).typecollumns().get(collumns.get(i)))+" AND ";
        }
        request +=collumns.get(collumns.size()-1)+" = "+getStr(val.get(val.size()-1), TableInfo.value(tableName).typecollumns().get(collumns.get(val.size()-1)));
        return request;
    }

    public static String drop(String tableName){
        return "DROP TABLE "+tableName;
    }
    public static String update(String tableName, List<String> collumns, List<Object> values, List<String> conditionCollumns, List<Object> conditionValues){
        String s = "UPDATE "+tableName+" SET ";

        for (int i = 0; i < collumns.size()-1; i++) {
            s+= collumns.get(i)+" = "+getStr(values.get(i), TableInfo.value(tableName).typecollumns().get(collumns.get(i)))+" , ";
        }

        s+= collumns.get(collumns.size()-1)+" = "+getStr(values.get(values.size()-1), TableInfo.value(tableName).typecollumns().get(collumns.get(values.size()-1)))+" WHERE ";

        for (int i = 0; i < conditionCollumns.size()-1; i++) {
            s+=conditionCollumns.get(i)+" = "+conditionValues.get(i)+" AND ";
        }

        s+=conditionCollumns.get(conditionCollumns.size()-1)+" = "+getStr(conditionValues.get(values.size()-1), TableInfo.value(tableName).typecollumns().get(conditionCollumns.get(conditionValues.size()-1)));

        return s;
    }


    private static String getStr(Object val, String type){
        if(type.equals(String.class.toString()))
            return "'"+ val.toString().replace("'","")+"'";
        return val.toString();
    }


}
