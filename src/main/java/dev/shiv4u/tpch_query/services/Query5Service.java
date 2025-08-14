package dev.shiv4u.tpch_query.services;

import dev.shiv4u.tpch_query.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Query5Service {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public Map<String, Double> executeQuery5(String regionName, String startDate, String endDate, int numThreads,
                                             List<Customer> customers,
                                             List<Orders> orders,
                                             List<LineItem> lineitems,
                                             List<Supplier> suppliers,
                                             List<Nation> nations,
                                             List<Region> regions) throws Exception {

        // Partition orders for parallel processing
        List<List<Orders>> partitions = partitionData(orders, numThreads);
        List<Callable<Map<String, Double>>> tasks = new ArrayList<>();

        for (List<Orders> part : partitions) {
            tasks.add(() -> processPartition(
                    part,
                    regionName,
                    startDate,
                    endDate,
                    customers,
                    lineitems,
                    suppliers,
                    nations,
                    regions
            ));
        }

        Map<String, Double> finalResult = new HashMap<>();
        for (Future<Map<String, Double>> f : executor.invokeAll(tasks)) {
            mergeResults(finalResult, f.get());
        }
        return finalResult;
    }

    private Map<String, Double> processPartition(List<Orders> ordersPartition,
                                                 String regionName,
                                                 String startDate,
                                                 String endDate,
                                                 List<Customer> customers,
                                                 List<LineItem> lineitems,
                                                 List<Supplier> suppliers,
                                                 List<Nation> nations,
                                                 List<Region> regions) {

        Map<String, Double> partialResult = new HashMap<>();

        // Convert date strings to SQL Date
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);

        // Pre-build lookup maps for faster joins
        Map<Integer, Customer> customerMap = customers.stream()
                .collect(Collectors.toMap(Customer::getCustKey, c -> c));

        Map<Integer, List<LineItem>> lineItemMap = lineitems.stream()
                .collect(Collectors.groupingBy(LineItem::getLOrderkey));

        Map<Integer, Supplier> supplierMap = suppliers.stream()
                .collect(Collectors.toMap(Supplier::getSuppKey, s -> s));

        Map<Integer, Nation> nationMap = nations.stream()
                .collect(Collectors.toMap(Nation::getNationKey, n -> n));

        // Find region IDs for given region name
        Set<Integer> regionKeys = regions.stream()
                .filter(r -> r.getName().equalsIgnoreCase(regionName))
                .map(Region::getRegionKey)
                .collect(Collectors.toSet());

        // Process each order in the partition
        for (Orders order : ordersPartition) {
            // Filter by date
            if (order.getOrderDate().before(start) || !order.getOrderDate().before(end)) {
                continue;
            }

            Customer customer = customerMap.get(order.getCustKey());
            if (customer == null) continue;

            Nation custNation = nationMap.get(customer.getNationKey());
            if (custNation == null) continue;

            // Check if customer's nation belongs to target region
            if (!regionKeys.contains(custNation.getRegionKey())) {
                continue;
            }

            // Get all lineitems for this order
            List<LineItem> items = lineItemMap.get(order.getOrderKey());
            if (items == null) continue;

            for (LineItem item : items) {
                Supplier supplier = supplierMap.get(item.getLSuppkey());
                if (supplier == null) continue;

                Nation suppNation = nationMap.get(supplier.getNationKey());
                if (suppNation == null) continue;

                // Nation name for aggregation
                String nationName = suppNation.getName();

                // Revenue calculation: l_extendedprice * (1 - l_discount)
                double revenue = item.getLExtendedprice() * (1 - item.getLDiscount());

                partialResult.merge(nationName, revenue, Double::sum);
            }
        }
        return partialResult;
    }

    private void mergeResults(Map<String, Double> finalResult, Map<String, Double> partial) {
        partial.forEach((nation, rev) -> finalResult.merge(nation, rev, Double::sum));
    }

    private <T> List<List<T>> partitionData(List<T> data, int numThreads) {
        int chunkSize = (int) Math.ceil((double) data.size() / numThreads);
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < data.size(); i += chunkSize) {
            partitions.add(data.subList(i, Math.min(i + chunkSize, data.size())));
        }
        return partitions;
    }
}
