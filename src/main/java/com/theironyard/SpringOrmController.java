package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Controller
public class SpringOrmController {

    @Autowired
    CustomerRepository cRepo;

    @Autowired
    PurchaseRepository pRepo;


    @RequestMapping(path = "/", method= RequestMethod.GET)
    public String home(Model model, String category){

        if(category == null) {
            Iterable<Purchase> purchases = pRepo.findAll();
            model.addAttribute("purchases", purchases);
        }
        else{
            Iterable<Purchase> purchases = pRepo.findByCategory(category);
            model.addAttribute("purchases", purchases);
        }

        return "home";
    }

    @PostConstruct
    public void init() throws FileNotFoundException {
        File customers = new File("ce9a2c4c-customers.csv");
        File purchases = new File("1635ef41-purchases.csv");
        Scanner c = new Scanner(customers);
        Scanner p = new Scanner(purchases);

        if(cRepo.count() == 0) {
            c.useDelimiter("[,|\n]");
            c.nextLine();
            while (c.hasNext()) {
                Customer customer = new Customer(c.next(), c.next());
                cRepo.save(customer);
            }
        }

        if(pRepo.count() == 0) {
            p.useDelimiter("[,|\n]");
            p.nextLine();
            while (p.hasNext()) {
                Customer customer = cRepo.findOne(Integer.valueOf(p.next()));
                Purchase purchase = new Purchase(customer, p.next(), p.next(), Integer.valueOf(p.next()), p.next());
                pRepo.save(purchase);
            }
        }
    }
}
