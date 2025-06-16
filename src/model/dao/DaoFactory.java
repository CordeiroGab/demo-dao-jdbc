package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;
import model.entity.Seller;

import java.sql.SQLException;
import java.util.List;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        try {
            return new SellerDaoJDBC(DB.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
