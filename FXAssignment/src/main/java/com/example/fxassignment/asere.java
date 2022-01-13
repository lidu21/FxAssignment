package com.example.fxassignment;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class asere extends Application {
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
        Button view = new Button("view");
        Button select = new Button("select");
        TableView table = new TableView();
       // String lbl4 = cb.getSelectionModel().getSelectedItem().toString();
   //     String lbl5 = cb2.getSelectionModel().getSelectedItem().toString();
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
                    prs.setString(5, cb.getSelectionModel().getSelectedItem().toString());
                    prs.setString(6, cb2.getSelectionModel().getSelectedItem().toString());

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
                    String query = "select * from DEPT_TB1 where STUDID ='" + ID.getText() + "'";

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
approve.setOnAction(new EventHandler<ActionEvent>() {
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
                          }

        } catch (SQLException ex) {



        } catch (ClassNotFoundException ex) {

        } finally {

        }

    }
});

        view.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data;
             @Override
            public void handle(ActionEvent event)
            {
                DbConnection obj1;
                Connection c;
                ResultSet rs;
                table.setItems(data);
                try {
                    data = FXCollections.observableArrayList();
                    obj1 = new DbConnection();
                    c = obj1.connMethod();
                    String SQL = "SELECT * from DEPT_TB1";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                                                        ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                        table.getColumns().addAll(col);
                   }
                    while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                                  data.add(row);
                    }
                    table.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }
       }
        });
        select.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data2;

            @Override
            public void handle(ActionEvent event) {
                DbConnection obj1;
                Connection c;
                ResultSet rs;
                TableView table2 = new TableView<>();
                gp.getChildren().remove(table);
                table2.setItems(data2);
                try {
                    data2 = FXCollections.observableArrayList();
                    obj1 = new DbConnection();
                    c = obj1.connMethod();
                    String name = Fname.getText();
                    String SQL = "SELECT * from DEPT_TB1 where FIRSTNAME = '"+name+"'";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                                ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                        table2.getColumns().addAll(col);
                              }
while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                                  data2.add(row);

                    }
table2.setItems(data2);
                    gp.add(table2,7,15);
                } catch (Exception e) {
                    e.printStackTrace();
                                    }

            }
        });
        Button selectSection = new Button("SelectSec");
        selectSection.setOnAction(new EventHandler<ActionEvent>() {
            private ObservableList<ObservableList> data2;
            @Override
            public void handle(ActionEvent event) {

                DbConnection obj1 =new DbConnection();
                Connection c;
                ResultSet rs;
                TableView table2 = new TableView<>();
                gp.getChildren().remove(table);
                table2.setItems(data2);
                try {
                    data2 = FXCollections.observableArrayList();
                    c = obj1.connMethod();
                    String sec = cb2.getSelectionModel().getSelectedItem().toString();
                String SQL = "SELECT * from DEPT_TB1 where SECTION = '"+sec+"'";
                    rs = c.createStatement().executeQuery(SQL);
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                        col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                                ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                        table2.getColumns().addAll(col);
                                            }
 while (rs.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            row.add(rs.getString(i));
                        }
                        //System.out.println("Row[1]added " + row);
                        data2.add(row);
                    }
   table2.setItems(data2);
                    gp.add(table2,7,15);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error ");
                }

            }
        });


        gp.add(update, 10,13);
        gp.add(cb, 8,9);
        gp.add(cb2, 8,11);
        gp.add(view,13,13);
        gp.add(select,15,13);
        gp.add(selectSection,15,15);
        gp.add(table,0,0);
        gp.setVgap(5);
        gp.setHgap(2);
        Scene scene = new Scene(gp, 700, 550);
        stage.setTitle("FXAssignment!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }}