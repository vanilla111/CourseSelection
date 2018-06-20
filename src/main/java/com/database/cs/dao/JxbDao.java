package com.database.cs.dao;

import com.database.cs.entity.JXB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface JxbDao {

    void saveNewJxb(JXB jxb);

    @Select("select * from jxb where class_room=#{cr} and hash_day=#{hd} and year=#{year} and status=0")
    List<JXB> findByClassRoomAndHashDayAndYear(@Param("cr") String classRoom, @Param("hd") Integer hashDay, @Param("year") Integer year);

    @Select("select * from jxb where teacher_id=#{tid} and year=#{year}")
    List<JXB> findByTeaIdAndYear(@Param("tid") String teaId, @Param("year") int year);
}
