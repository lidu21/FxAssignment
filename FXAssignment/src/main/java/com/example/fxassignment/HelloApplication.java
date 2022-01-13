package com.example.fxassignment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {
    DbConnection db = new DbConnection();
    @Override
    public void start(Stage stage) {
       GridPane gp = new GridPane();
       Label sid = new Label("SID");
       Label id = new Label("ID");
        Label fname = new Label("First name");
        Label lname = new Label("lasstName");
        Label Dept = new Label("department");
        Label sec = new Label("Section");
        TextField SID = new TextField();
        TextField ID = new TextField();
        TextField Fname = new TextField();
        TextField Lname = new TextField();
        ComboBox cb = new ComboBox();
        cb.getItems().addAll("CS", "SE");
        ComboBox cb2 = new ComboBox();
        cb2.getItems().addAll("SecA", "SecB", "SecC");
        String lbl = SID.getText();
        String lbl1 = ID.getText();
        String lbl2 = Fname.getText();
        String lbl3 = Lname.getText();
        String lbl4 = cb.getSelectionModel().getSelectedItem().toString();
        String lbl5 = cb2.getSelectionModel().getSelectedItem().toString();
        gp.add(sid, 5,1);
        gp.add(id, 5,3);
        gp.add(fname, 5,5);
        gp.add(lname, 5,7);
        gp.add(Dept, 5,9);
        gp.add(sec, 5,11);

        gp.add(SID, 8,1);
        gp.add(ID, 8,3);
        gp.add(Fname, 8,5);
        gp.add(Lname, 8,7);



        Button insert = new Button("Insert");
        gp.add(insert, 8,13);
insert.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {

        String sql = "Insert into DEPT_TB1 (SID, STUDID, FIRSTNAME, LASTNAME,SECTION, DEPARTMENT ) Values (?,?,?,?,?,?)";

        Connection con;
        PreparedStatement prs;
        try {
            con = db.connMethod();
            prs = con.prepareStatement(sql);
            prs.setString(1, lbl);
            prs.setString(2, lbl1);
            prs.setString(3, lbl2);
            prs.setString(4, lbl3);
            prs.setString(5, lbl4);
            prs.setString(6, lbl5);

            int i = prs.executeUpdate();
            if (i == 1) {

                //AlertDialog.("info","Data Inserted succsecfully");
                System.out.println("Data Inserted succsecfully");
            }

        } catch (SQLException ex) {



        } catch (ClassNotFoundException ex) {

        } finally {

        }

    }
});
Button update = new Button("update");
Button approve = new Button("approve");
update.setOnAction(new EventHandler<ActionEvent>() {

    @Override
    public void handle(ActionEvent actionEvent) {

        if (ID.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Please insert the id of the student to update");
            a.showAndWait();
        } else {
            Connection con = null;
            try {
                con = db.connMethod();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String query = "select * from DEPT_TB1 FIRSTNAME= 'Aman' = '" + ID.getText() + "'";

            ResultSet res = null;

            try {
                res = con.createStatement().executeQuery(query);
                while (res.next()) {
                    SID.setText(res.getString(1));

                    ID.setText(res.getString(2));
                    Fname.setText(res.getString(3));
                    Lname.setText(res.getString(4));
                    gp.add(approve, 10,17);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }

        }
    }
});
/*approve.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent actionEvent) {
        String sql = "update DEPT_TB1 set FIRSTNAME =? where FIRSTNAME = 'Aman'";

        Connection con;
        PreparedStatement prs;
        try {
            con = db.connMethod();
            prs = con.prepareStatement(sql);
            prs.setString(1, Fname.getText());


            int i = prs.executeUpdate();
            if (i == 1) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("updated successfully");
                a.showAndWait();
               // System.out.println("Data Inserted succsecfully");
            }

        } catch (SQLException ex) {



        } catch (ClassNotFoundException ex) {

        } finally {

        }

    }
});*/
     //   gp.getChildren().addAll(SID,ID,Lname,cb,cb2);
gp.add(update, 10,13);
        gp.add(cb, 8,9);
        gp.add(cb2, 8,11);
        gp.setVgap(5);
        gp.setHgap(2);
        Scene scene = new Scene(gp, 320, 240);
        stage.setTitle("FXAssignment!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}