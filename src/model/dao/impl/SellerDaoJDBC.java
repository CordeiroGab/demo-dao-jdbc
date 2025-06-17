package model.dao.impl;

import db.DB;
import db.DbIntegrityException;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Seller id) {

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
