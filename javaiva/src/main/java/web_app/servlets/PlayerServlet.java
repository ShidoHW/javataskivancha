package web_app.servlets;

import actions_with_db.Connect;

import web_app.FormirateAnswer;
import web_app.Requests;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PlayerServlet")
public class PlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connect data;

    public PlayerServlet(Connect data) {
        this.data = data;
    }

    //получить
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);


        ResultSet rs = null;

        try {
            rs = data.getResultSet(Requests.read(Requests.PLAYER.tableName(), collumns, values));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String ans = "";
        try {
            ans = FormirateAnswer.answer(Requests.PLAYER.collumns(), rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().println(ans);

    }

//положить
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);


        try {
            data.update(Requests.insert(Requests.PLAYER.tableName(), collumns, values));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    //удалить
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(request);
        List<String> collumns = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values = values(request);
        Requests.cutNullValues(collumns, values);





        try {
            data.update(Requests.remove(Requests.PLAYER.tableName(), collumns, values));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //изменение
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> collumns1 = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values1 = values(request);
        Requests.cutNullValues(collumns1, values1);

        List<String> collumns2 = new ArrayList<>(Requests.PLAYER.collumns());
        List<Object> values2 = values(request, "C");
        Requests.cutNullValues(collumns2, values2);


        try {
            data.update(Requests.update(Requests.PLAYER.tableName(), collumns2, values2, collumns1, values1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    protected List<Object> values(HttpServletRequest request){
        return values(request, "");
    }
    protected List<Object> values(HttpServletRequest request, String s){

        List<Object> values = new ArrayList<>();
        String v;
        v = request.getParameter(s+"playerId");
        values.add(v);
        v = request.getParameter(s+"nickname");
        if(v != null)
            v = "'"+v.replace("'", "")+"'";
        values.add(v);

        return values;
    }
}

