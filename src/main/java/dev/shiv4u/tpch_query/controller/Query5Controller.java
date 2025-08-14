package dev.shiv4u.tpch_query.controller;

import dev.shiv4u.tpch_query.dtos.Query5RequestDTO;
import dev.shiv4u.tpch_query.dtos.Query5ResultDTO;
import dev.shiv4u.tpch_query.models.*;
import dev.shiv4u.tpch_query.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tpch")
@RequiredArgsConstructor
public class Query5Controller {

    private final DataLoaderService dataLoaderService;
    private final Query5Service query5Service;
    private final ResultWriterService resultWriterService;
    private final TpchDataGeneratorService tpchDataGeneratorService;

    @PostMapping("/query5")
    public List<Query5ResultDTO> runQuery(@RequestBody Query5RequestDTO query5RequestDTO) throws Exception {

        // 1️⃣ Generate TPCH data
        tpchDataGeneratorService.generateTpchData(
                query5RequestDTO.getDbgenPath(),
                query5RequestDTO.getTablePath(),
                query5RequestDTO.getScaleFactor()
        );

        String basePath = query5RequestDTO.getTablePath();

        // 2️⃣ Load all required data
        List<Customer> customers = dataLoaderService.loadCustomers(basePath + "/customer.tbl");
        List<Orders> orders = dataLoaderService.loadOrders(basePath + "/orders.tbl");
        List<LineItem> lineItems = dataLoaderService.loadLineItems(basePath + "/lineitem.tbl");
        List<Supplier> suppliers = dataLoaderService.loadSuppliers(basePath + "/supplier.tbl");
        List<Nation> nations = dataLoaderService.loadNations(basePath + "/nation.tbl");
        List<Region> regions = dataLoaderService.loadRegions(basePath + "/region.tbl");

        // 3️⃣ Execute Query 5
        Map<String, Double> resultMap = query5Service.executeQuery5(
                query5RequestDTO.getRegionName(),
                query5RequestDTO.getStartDate(),
                query5RequestDTO.getEndDate(),
                query5RequestDTO.getNumThreads(),
                customers,
                orders,
                lineItems,
                suppliers,
                nations,
                regions
        );

        // 4️⃣ Write results to file
        resultWriterService.writeResults(query5RequestDTO.getResultPath() + "/query5_result.txt", resultMap);

        // 5️⃣ Convert map to list of DTOs
        List<Query5ResultDTO> resultList = new ArrayList<>();
        resultMap.forEach((nation, revenue) -> {
            Query5ResultDTO dto = new Query5ResultDTO();
            dto.setNationName(nation);
            dto.setRevenue(revenue);
            resultList.add(dto);
        });
        return resultList;
    }
}
