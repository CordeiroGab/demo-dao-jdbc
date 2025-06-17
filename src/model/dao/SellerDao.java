package model.dao;

import model.entity.Department;
import model.entity.Seller;


import java.util.List;

public interface SellerDao {

    void insert(Seller obj);
    void update (Seller obj);
    void deleteById (Seller id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
