package main.java.com.moku.api.mapper;

import main.java.com.moku.api.entity.Employee;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.type.JdbcType;
import java.util.List;

/**
 * 查询结果集名称和对象属性(实体entity)不同，手动映射封装resultMap标签
 *
 * @return Employee
 */
public interface EmployeeMapper {

    void insertEmployee(Employee employee);

    Employee queryOne(long id);

    List<Employee> queryAll();
}
