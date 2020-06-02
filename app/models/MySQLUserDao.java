package models;

import java.sql.*;
import java.util.*;
import javax.inject.Singleton;

@Singleton
public class MySQLUserDao implements UserDao {

    // 検索
    public List<User> search(Connection conn) throws SQLException {
        String sql = "select * from user order by id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rset = stmt.executeQuery();

        List<User> users = new ArrayList<>();
        while (rset.next()) {
            users.add(new User(rset.getLong("id"), rset.getString("name"), rset.getInt("age")));
        }
        rset.close();
        stmt.close();

        return users;
    }

    // 詳細
    public User find(Long id, Connection conn) throws SQLException {
        String sql = "select * from user where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rset = stmt.executeQuery();

        User user = null;
        if (rset.next()) {
            user = new User(rset.getLong("id"), rset.getString("name"), rset.getInt("age"));
        }
        rset.close();
        stmt.close();

        return user;
    }

    // 作成
    public User create(User user, Connection conn) throws SQLException {
        String sql = "insert into user(name, age) values(?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        User userCreate = null;
        if (rs.next()) {
            long id = rs.getLong(1);

            userCreate = new User(id, user.getName(), user.getAge());
        }
        stmt.close();

        return userCreate;
    }

    // 編集
    public User update(Long id, User user, Connection conn) throws SQLException {
        String sql = "update user set name = ?, age = ?  where id = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());
        stmt.setLong(3, id);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        User userUpdate = null;
        if (rs.next()) {
            userUpdate = new User(id, user.getName(), user.getAge());
        }
        stmt.close();
        return userUpdate;
    }

    // 削除
    public User delete(Long id, Connection conn) throws SQLException {
        String sql = "delete from user where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rset = stmt.executeQuery();

        User found = null;
        return found;
    }

}