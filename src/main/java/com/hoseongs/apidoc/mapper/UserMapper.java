package com.hoseongs.apidoc.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hoseongs.apidoc.model.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from user")
	ArrayList<User> getUsers();
	
	@Select("select * from user where id = #{id}")
	User getUser(String id);
	
	@Insert("insert user(id, nickName, password, createDate) values(#{id}, #{nickName}, #{password}, now())")
	int joinUser(User user);

	@Select("select password from user where id = #{id}")
	String getPwd(String id);

	@Update("update user set refreshToken = #{refreshToken} where id = #{id}")
	void setToken(@Param("id") String id, @Param("refreshToken") String refreshToken);

	@Select("select * from user where refreshToken = #{refreshToken}")
	User getUserFromToken(String refreshToken);

}
