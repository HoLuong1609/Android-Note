package com.example.mobileteam1.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginControl {

    private LoginModel model;
    private LoginView view;

    public LoginControl(LoginView view) {
        this.view = view;
        view.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                model = view.getUser();
                if (checkUser(model)) {
                    view.showMessage("Login succesfully!");
                } else {
                    view.showMessage("Invalid username and/or password!");
                }
            } catch (Exception ex) {
                view.showMessage(ex.getStackTrace().toString());
            }
        }
    }

    public boolean checkUser(LoginModel user) throws Exception {
        String dbUrl = "jdbc:mysql://localhost:3306/usermanagement";
        String dbClass = "com.mysql.jdbc.Driver";
        String query = "Select * FROM users WHERE username ='"
                + user.getUserName()
                + "' AND password ='" + user.getPassword() + "'";
        try {
            Class.forName(dbClass);
            Connection con = DriverManager.getConnection(dbUrl,
                    "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            PTITif(rs.next()) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
}
