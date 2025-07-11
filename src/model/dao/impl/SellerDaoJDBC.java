package model.dao.impl;

import db.DB;
import db.DbIntegrityException;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller"
                    + "(Name, Email, birth_date, base_salary, department_id) "
                    + "VALUES"
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);

                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbIntegrityException("Unexpected error ! No rows affected!");
            }

        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatenebt(st);
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
                    "UPDATE seller "
                        + "SET Name = ? , Email = ?, birth_date = ? , base_salary = ?, department_id = ? "
                        + "WHERE id = ? ",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            int rowsAffected = st.executeUpdate();

        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatenebt(st);
        }


    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ? ");

            st.setInt(1, id);

            st.executeUpdate();
        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatenebt(st);
        }

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    +  "ON seller.id = department.Id "
                    + "WHERE seller.Id = ?" );

            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dep = instantiateDeparment(rs);
                Seller obj = instantiateSeller(rs , dep );
                return obj;
            }
            return null;

        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());

        }
        finally {
            DB.closeStatenebt(st);
            DB.closeResultSet(rs);
        }

    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
       Seller obj = new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setBaseSalary((rs.getDouble("base_salary")));
        obj.setBirthDate(rs.getDate("birth_date"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDeparment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("department_id"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.department_id = department.Id "
                            + "ORDER BY Name " );


            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("department_id"));

                if(dep == null){
                    dep = instantiateDeparment(rs);
                    map.put(rs.getInt("department_id"), dep);
                }

                Seller obj = instantiateSeller(rs , dep );
                list.add(obj);
            }
            return list;

        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());

        }
        finally {
            DB.closeStatenebt(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.department_id = department.Id "
                    + "WHERE department_id = ? "
                    + "ORDER BY Name " );

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("department_id"));

                if(dep == null){
                    dep = instantiateDeparment(rs);
                    map.put(rs.getInt("department_id"), dep);
                }

                Seller obj = instantiateSeller(rs , dep );
               list.add(obj);
            }
            return list;

        }catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());

        }
        finally {
            DB.closeStatenebt(st);
            DB.closeResultSet(rs);
        }
    }
}
