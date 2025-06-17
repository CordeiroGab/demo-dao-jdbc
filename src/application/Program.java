package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();


        System.out.println("=== TESTE 1 : seller findByid ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TESTE 2 : seller findByDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list  = sellerDao.findByDepartment(department);
        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println("\n=== TESTE 3 : seller findAll ===");
        list  = sellerDao.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }

    }

}
