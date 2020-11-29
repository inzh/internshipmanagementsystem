package cn.kinzh.internshipmanagementsystem.log.dao;

import cn.kinzh.internshipmanagementsystem.log.dto.ErrorLogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogQuery;
import cn.kinzh.internshipmanagementsystem.log.entity.MyLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kennyhao
 */
@Mapper
public interface LogDao {

    /**
     * 保存日志
     * @param log
     */
    @Insert("insert into my_log(user_name,ip,description,params,type,exception_detail,browser,method,time,create_time)values(#{userName},#{ip},#{description},#{params},#{type},#{exceptionDetail},#{browser},#{method},#{time},now())")
    void save(MyLog log);

    /**
     * 分页返回所有用户日志
     * @param logQuery 查询条件
     * @return
     */
    List<LogDto> getFuzzyLogByPage( @Param("logQuery") LogQuery logQuery);


    /**
     * 分页返回所有错误日志
     * @param logQuery 查询条件
     * @return
     */
    List<ErrorLogDto> getFuzzyErrorLogByPage(@Param("logQuery") LogQuery logQuery);


    /**
     * 删除所有日志
     * @param type 日志类型
     */
    @Delete("delete from my_log where type = #{type}")
    void delAllByInfo(String type);
}
