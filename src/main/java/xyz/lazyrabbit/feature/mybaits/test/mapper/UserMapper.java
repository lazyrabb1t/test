package xyz.lazyrabbit.feature.mybaits.test.mapper;

import xyz.lazyrabbit.feature.mybaits.annotation.Select;

public interface UserMapper {

    @Select("insert into t_user (id,name) value (#{id},#{name})")
    void save(Integer id,String name);
}
