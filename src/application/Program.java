package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

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

        System.out.println("\n=== TESTE 4 : seller insert ===");
        Seller newSeller = new Seller(null, "Greg", "Greg@gmail.com", new Date(), 4000.0,department);
        sellerDao.insert(newSeller);
        System.out.println("Insert !! New ID: " + newSeller.getId());

        System.out.println("\n=== TESTE 5 : seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update !!");

        System.out.println("\n=== TESTE 6 : seller delete ===");
        System.out.println("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete !!");

        sc.close();


    }

}
