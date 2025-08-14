package dev.shiv4u.tpch_query.services;

import dev.shiv4u.tpch_query.models.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {
    public List<Customer> loadCustomers(String filePath) throws IOException {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Customer customer = new Customer();
                customer.setCustKey(Integer.parseInt(p[0]));
                customer.setName(p[1]);
                customer.setAddress(p[2]);
                customer.setNationKey(Integer.parseInt(p[3]));
                customer.setPhone(p[4]);
                customer.setAcctBal(Double.parseDouble(p[5]));
                customer.setMktSegment(p[6]);
                customer.setComment(p[7]);
                customers.add(customer);
            }
        }
        return customers;
    }

    public List<Orders> loadOrders(String filePath) throws IOException {
        List<Orders> orders = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Orders order = new Orders();
                order.setOrderKey(Integer.parseInt(p[0]));
                order.setCustKey(Integer.parseInt(p[1]));
                order.setOrderStatus(p[2]);
                order.setTotalPrice(Double.parseDouble(p[3]));
                order.setOrderDate(Date.valueOf(p[4]));
                order.setOrderPriority(p[5]);
                order.setClerk(p[6]);
                order.setShipPriority(Integer.parseInt(p[7]));
                order.setComment(p[8]);
                orders.add(order);
            }
        }
        return orders;
    }

    public List<LineItem> loadLineItems(String filePath) throws IOException {
        List<LineItem> lineItems = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                LineItem item = new LineItem();
                item.setLOrderkey(Integer.parseInt(p[0]));
                item.setLLinenumber(Integer.parseInt(p[1]));
                item.setLPartkey(Integer.parseInt(p[2]));
                item.setLSuppkey(Integer.parseInt(p[3]));
                item.setLQuantity(Double.parseDouble(p[4]));
                item.setLExtendedprice(Double.parseDouble(p[5]));
                item.setLDiscount(Double.parseDouble(p[6]));
                item.setLTax(Double.parseDouble(p[7]));
                item.setLReturnflag(p[8]);
                item.setLLinestatus(p[9]);
                item.setLShipdate(Date.valueOf(p[10]));
                item.setLCommitdate(Date.valueOf(p[11]));
                item.setLReceiptdate(Date.valueOf(p[12]));
                item.setLShipinstruct(p[13]);
                item.setLShipmode(p[14]);
                item.setLComment(p[15]);
                lineItems.add(item);
            }
        }
        return lineItems;
    }

    public List<Supplier> loadSuppliers(String filePath) throws IOException {
        List<Supplier> suppliers = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Supplier supplier = new Supplier();
                supplier.setSuppKey(Integer.parseInt(p[0]));
                supplier.setName(p[1]);
                supplier.setAddress(p[2]);
                supplier.setNationKey(Integer.parseInt(p[3]));
                supplier.setPhone(p[4]);
                supplier.setAcctBal(Double.parseDouble(p[5]));
                supplier.setComment(p[6]);
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    public List<Nation> loadNations(String filePath) throws IOException {
        List<Nation> nations = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Nation nation = new Nation();
                nation.setNationKey(Integer.parseInt(p[0]));
                nation.setName(p[1]);
                nation.setRegionKey(Integer.parseInt(p[2]));
                nation.setComment(p[3]);
                nations.add(nation);
            }
        }
        return nations;
    }

    public List<Region> loadRegions(String filePath) throws IOException {
        List<Region> regions = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                Region region = new Region();
                region.setRegionKey(Integer.parseInt(p[0]));
                region.setName(p[1]);
                region.setComment(p[2]);
                regions.add(region);
            }
        }
        return regions;
    }
}
