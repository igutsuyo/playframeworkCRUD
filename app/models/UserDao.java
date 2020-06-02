package models;

import java.sql.*;
import java.util.List;

public interface UserDao {
 
    //一覧
    List<User> search(Connection conn) throws SQLException;
    //詳細
    User find(Long id, Connection conn) throws SQLException;
    //作成
    User create(User user, Connection conn) throws SQLException;
    //編集
    User update(Long id, User user,Connection conn) throws SQLException;
    //削除
    User delete(Long id, Connection conn) throws SQLException;


}