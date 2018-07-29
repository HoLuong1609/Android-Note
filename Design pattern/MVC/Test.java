package com.example.mobileteam1.socket;

public class Test {

    public static void main(String[] args) {
        LoginView view = new LoginView();
        LoginControl controller = new LoginControl(view);
        view.setVisible(true);
    }
}
