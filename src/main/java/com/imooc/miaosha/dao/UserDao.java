package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Felix
 * @date 2019/5/4 22:00
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String SELECT_FIELDS = " id, name ";
    String INSERT_FIELDS = " id, name ";
    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, " where id=#{id}"})
    public User getById(@Param("id") int id);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", "values(#{id},#{name})"})
    public int insert(User user);

   /* 这样写简单，但是不利于以后的修改
   @Select("select * from user where id = #{id}")
    public User getById(@Param("id")int id	);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    public int insert(User user);
    */
}
