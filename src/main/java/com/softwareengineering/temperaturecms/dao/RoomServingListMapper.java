package com.softwareengineering.temperaturecms.dao;

import com.softwareengineering.temperaturecms.pojo.RoomServingList;
import com.softwareengineering.temperaturecms.pojo.RoomServingListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomServingListMapper {
    long countByExample(RoomServingListExample example);

    int deleteByExample(RoomServingListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoomServingList record);

    int insertSelective(RoomServingList record);

    List<RoomServingList> selectByExample(RoomServingListExample example);

    RoomServingList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoomServingList record, @Param("example") RoomServingListExample example);

    int updateByExample(@Param("record") RoomServingList record, @Param("example") RoomServingListExample example);

    int updateByPrimaryKeySelective(RoomServingList record);

    int updateByPrimaryKey(RoomServingList record);
}