package com.starodub.dao;

import com.starodub.model.Role;
import com.starodub.model.User;

import java.sql.*;

import static com.starodub.model.Role.RoleName.USER;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao<User, Long> {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User addUser(User user) {
        String userQuery = "INSERT INTO USERS (EMAIL, PASSWORD, TOKEN, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?, ?);";
        String roleQuery = "INSERT INTO USER_TO_ROLE (FK_USER_ID, FK_ROLE_ID) VALUES (?, ?);";
        PreparedStatement userStatement;
        PreparedStatement roleStatement;

        try {
            connection.setAutoCommit(false);

            userStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, user.getEmail());
            userStatement.setString(2, user.getPassword());
            userStatement.setString(3, user.getToken());
            userStatement.setString(4, user.getFirstName());
            userStatement.setString(5, user.getLastName());
            userStatement.executeUpdate();

            ResultSet rs = userStatement.getGeneratedKeys();
            long userId = 0;

            if(rs.next()) {
                userId = rs.getLong(1);
            } else {
                connection.rollback();
            }

            roleStatement = connection.prepareStatement(roleQuery);
            roleStatement.setLong(1, userId);
            roleStatement.setString(2, USER.toString());
            roleStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.getMessage();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e.getMessage() + "\n" + e1.getMessage());
            }
        }
        return user;
    }


    @Override
    public User findByEmail(String email) {
        String query = "SELECT ID, EMAIL, PASSWORD, TOKEN, FIRST_NAME, LAST_NAME FROM USERS WHERE EMAIL = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        User user = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            user = resultSet.next() ? getUserFromResultSet(resultSet) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByToken(String token) {
        String query = "SELECT U.ID, U.EMAIL, U.PASSWORD, U.TOKEN, U.FIRST_NAME, U.LAST_NAME, R.NAME FROM USERS U " +
                "JOIN USER_TO_ROLE UTR ON U.ID = UTR.FK_USER_ID " +
                "JOIN ROLE R ON UTR.FK_ROLE_ID = R.NAME " +
                "WHERE U.TOKEN = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        User user = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            resultSet = statement.executeQuery();
            user = resultSet.next() ? getUserWithRoles(resultSet) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
        );
    }

    private User getUserWithRoles(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6)
        );
        while (!resultSet.isAfterLast()) {
            Role role = Role.of(resultSet.getString(7));
            user.addRole(role);
            resultSet.next();
        }
        return user;
    }

}
