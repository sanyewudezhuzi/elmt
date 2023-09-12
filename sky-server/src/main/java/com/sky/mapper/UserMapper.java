package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据openid获取用户信息
     *
     * @param openid
     * @return
     */
    @Select("select id, openid, name, phone, sex, id_number, avatar, create_time " +
            "from user " +
            "where openid = #{openid}")
    User getUserByOpenId(String openid);

    /**
     * 保存用户
     *
     * @param user
     */
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into user(openid, create_time) " +
            "values(#{openid}, #{createTime})")
    void save(User user);

}
