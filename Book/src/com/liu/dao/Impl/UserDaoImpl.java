package com.liu.dao.Impl;

import com.liu.bean.User;
import com.liu.dao.UserDao;
import com.liu.utils.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
@SuppressWarnings("all")
public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public User queryUserByUsername(String username)throws SQLException {
        String sql = "select * from t_user where username = ?";
        User query = queryRunner.query(sql, new BeanHandler<>(User.class),username);
        return query;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException{
        String sql = "select * from t_user where username = ? and password = ?";
        User query = queryRunner.query(sql, new BeanHandler<>(User.class),username,password);
        return query;
    }

    @Override
    public void save(User user) throws SQLException{
        String sql = "insert into t_user values(null,?,?,?)";
//        queryRunner.update(sql,user.getUsername(),user.getPassword(),user.getEmail());
        Long id = queryRunner.insert(sql, new ScalarHandler<Long>(), user.getUsername(), user.getPassword(), user.getEmail());
        user.setId(id.intValue());
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public void updateById(User user) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public User findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<User> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}
