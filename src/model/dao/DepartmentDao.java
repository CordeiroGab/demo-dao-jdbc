package model.dao;

import model.entity.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department obj);
    void update (Department obj);
    void deleteById (Department id);
    Department findById(Integer id);
    List<Department> findAll();
}
