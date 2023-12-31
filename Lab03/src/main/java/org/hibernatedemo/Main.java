package org.hibernatedemo;

import org.hibernatedemo.dao.ManufacturerDAO;
import org.hibernatedemo.dao.PhoneDAO;
import org.hibernatedemo.domain.Manufacturer;
import org.hibernatedemo.domain.Phone;
import org.hibernatedemo.exception.InvalidOperationException;

import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws InvalidOperationException {
        PhoneDAO phoneDAO = new PhoneDAO();
        ManufacturerDAO manufactureDAO = new ManufacturerDAO();

        System.out.println("============= START =============");

        System.out.println("------------- BEGIN ADD MANUFACTURE -------------");
        Manufacturer apple = new Manufacturer("Apple", "USA", 150);
        Manufacturer samsung = new Manufacturer("Samsung", "Korea", 200);
        Manufacturer xiaomi = new Manufacturer("Xiaomi", "China", 90);
        Manufacturer huawei = new Manufacturer("Huawei", "China", 100);
        manufactureDAO.add(apple);
        manufactureDAO.add(samsung);
        manufactureDAO.add(xiaomi);
        manufactureDAO.add(huawei);
        System.out.println("------------- END ADD MANUFACTURE -------------");

        System.out.println("------------- BEGIN ADD PHONE -------------");
        Phone iphone12 = new Phone("iPhone 12", 15000000L, "Black", "USA", 100, apple);
        Phone iphone12Pro = new Phone("iPhone 12 Pro", 20000000L, "Black", "USA", 100, apple);
        Phone iphone12ProMax = new Phone("iPhone 12 Pro Max", 25000000L, "Black", "USA", 100, apple);
        Phone samsungGalaxyS21 = new Phone("Samsung Galaxy S21", 15000000L, "Black", "Korea", 100, samsung);
        Phone samsungGalaxyS21Plus = new Phone("Samsung Galaxy S21 Plus", 20000000L, "Black", "Korea", 100, samsung);
        Phone samsungGalaxyS21Ultra = new Phone("Samsung Galaxy S21 Ultra", 25000000L, "Black", "Korea", 100, samsung);
        Phone xiaomiMi11 = new Phone("Xiaomi Mi 11", 15000000L, "Black", "China", 100, xiaomi);
        Phone xiaomiMi11Pro = new Phone("Xiaomi Mi 11 Pro", 20000000L, "Black", "China", 100, xiaomi);
        Phone xiaomiMi11Ultra = new Phone("Xiaomi Mi 11 Ultra", 25000000L, "Black", "China", 100, xiaomi);
        phoneDAO.add(iphone12);
        phoneDAO.add(iphone12Pro);
        phoneDAO.add(iphone12ProMax);
        phoneDAO.add(samsungGalaxyS21);
        phoneDAO.add(samsungGalaxyS21Plus);
        phoneDAO.add(samsungGalaxyS21Ultra);
        phoneDAO.add(xiaomiMi11);
        phoneDAO.add(xiaomiMi11Pro);
        phoneDAO.add(xiaomiMi11Ultra);

        apple.setPhones(List.of(iphone12, iphone12Pro, iphone12ProMax));
        samsung.setPhones(List.of(samsungGalaxyS21, samsungGalaxyS21Plus, samsungGalaxyS21Ultra));
        xiaomi.setPhones(List.of(xiaomiMi11, xiaomiMi11Pro, xiaomiMi11Ultra));
        manufactureDAO.update(apple);
        manufactureDAO.update(samsung);
        manufactureDAO.update(xiaomi);
        System.out.println("------------- END ADD PHONE -------------");

        System.out.println();
        System.out.println("------------- BEGIN GET ALL PHONE -------------");
        phoneDAO.getAll().forEach(System.out::println);
        System.out.println("------------- END GET ALL PHONE -------------");
        System.out.println();

        System.out.println("------------- BEGIN GET ALL MANUFACTURE -------------");
        manufactureDAO.getAll().forEach(System.out::println);
        System.out.println("------------- END GET ALL MANUFACTURE -------------");
        System.out.println();

        System.out.println("------------- BEGIN GET PHONE BY ID -------------");
        System.out.println("------------- GET PHONE BY ID 1 -------------");
        System.out.println(phoneDAO.get(1L));
        System.out.println("------------- GET PHONE BY ID 2 -------------");
        System.out.println(phoneDAO.get(2L));
        System.out.println("------------- END GET PHONE BY ID -------------");
        System.out.println();

        System.out.println("------------- BEGIN GET MANUFACTURE BY ID -------------");
        System.out.println("------------- GET MANUFACTURE BY ID 1 -------------");
        System.out.println(manufactureDAO.get(1L));
        System.out.println("------------- GET MANUFACTURE BY ID 2 -------------");
        System.out.println(manufactureDAO.get(2L));
        System.out.println("------------- END GET MANUFACTURE BY ID -------------");

        System.out.println();

        System.out.println("------------- BEGIN UPDATE PHONE -------------");
        System.out.println("------------- UPDATE PHONE ID 1 -------------");
        Phone phone1 = phoneDAO.get(1L);
        phone1.setName("iPhone 12 Pro Max Sieu Cap Vip Pro");
        phone1.setPrice(60000000L);
        phone1.setColor("Gold");
        phone1.setCountry("Viet Nam");
        phone1.setQuantity(1);
        phoneDAO.update(phone1);
        System.out.println(phoneDAO.get(1L));
        System.out.println("------------- END UPDATE PHONE -------------");

        System.out.println();

        System.out.println("------------- BEGIN UPDATE MANUFACTURE -------------");
        System.out.println("------------- UPDATE MANUFACTURE ID 1 -------------");
        Manufacturer manufacture1 = manufactureDAO.get(1L);
        manufacture1.setName("Apple Sieu Cap Vip Pro Vu Tru");
        manufacture1.setLocation("Viet Nam");
        manufacture1.setEmployee(10000);
        manufactureDAO.update(manufacture1);
        System.out.println(manufactureDAO.get(1L));
        System.out.println("------------- END UPDATE MANUFACTURE -------------");

        System.out.println();

        System.out.println("------------- BEGIN DELETE PHONE -------------");
        System.out.println("------------- DELETE PHONE ID 1 -------------");
        phoneDAO.remove(1L);
        phoneDAO.getAll().forEach(System.out::println);
        System.out.println("------------- END DELETE PHONE -------------");

        System.out.println();

        System.out.println("------------- BEGIN DELETE MANUFACTURE -------------");
        System.out.println("------------- DELETE MANUFACTURE ID 1 -------------");
        manufactureDAO.remove(1L);
        manufactureDAO.getAll().forEach(System.out::println);
        System.out.println("------------- END DELETE MANUFACTURE -------------");

        System.out.println();

        System.out.println("------------- PHONE HAVE HIGHEST PRICE -------------");
        System.out.println(phoneDAO.getHighestPricePhone());
        System.out.println("------------- END PHONE HAVE HIGHEST PRICE -------------");

        System.out.println();

        System.out.println("------------- PHONE SORT BY COUNTRY -------------");
        phoneDAO.sortPhoneByCountryAndPrice().forEach(System.out::println);
        System.out.println("------------- END PHONE SORT BY COUNTRY -------------");

        System.out.println();

        System.out.println("------------- MANUFACTURES HAVE MORE THAN 100 EMPLOYEE -------------");
        System.out.println("Manufacturers have more than 100 employee: " + manufactureDAO.checkManuHaveMoreThanEmployees(100));
        System.out.println("------------- END MANUFACTURES HAVE MORE THAN 100 EMPLOYEE -------------");

        System.out.println();

        System.out.println("------------- SUM QUANTITY OF EMPLOYEE -------------");
        System.out.println("Sum quantity of employee: " + manufactureDAO.getSumEmployee());
        System.out.println("------------- END SUM QUANTITY OF EMPLOYEE -------------");

        System.out.println();

        System.out.println("------------- the last manufacturer based in CHINA -------------");
        System.out.println("Sum quantity of employee: " + manufactureDAO.getLastLocationManufacturer("CHINA"));
        System.out.println("------------- the last manufacturer based in CHINA -------------");



        System.out.println("============= END =============");
    }
}
