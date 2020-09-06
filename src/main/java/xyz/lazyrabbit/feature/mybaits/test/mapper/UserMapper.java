package xyz.lazyrabbit.feature.mybaits.test.mapper;

import xyz.lazyrabbit.feature.mybaits.annotation.Insert;
import xyz.lazyrabbit.feature.mybaits.annotation.Select;
import xyz.lazyrabbit.feature.mybaits.test.entity.User;

import java.util.List;

public interface UserMapper {

    //    @Insert("insert into t_user (id,name) value (#{id},#{name})")
    @Insert("insert into t_user (id,username) value (?,?)")
    void save(Integer id, String name);

    @Select("select * from t_user")
    List<User> findAll();
}
