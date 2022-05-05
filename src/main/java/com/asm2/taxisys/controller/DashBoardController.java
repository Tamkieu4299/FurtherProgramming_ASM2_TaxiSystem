package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.DriverService;
import com.asm2.taxisys.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;

//    @GetMapping(path = "/total-revenue-period")
//    public Map<String, Object> getRevenue(@RequestParam("start") String start,
//                                          @RequestParam("end") String end) throws ParseException {
//        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(start);
//        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(end);
//
//        double revenue = 0;
//        List<Invoice> invoices = invoiceService.getAllInvoicesBetween(sd, ed);
//        for (Invoice invoice : invoices) {
//            revenue += invoice.getTotalCharge();
//        }
//        Map<String, Object> result = new HashMap<>(3);
//        result.put("Begin date", start);
//        result.put("End date", end);
//        result.put("Revenue", revenue);
//
//        return result;
//    }
//
//    @GetMapping(path = "/customer-revenue-period/{id}")
//    public Map<String, Object> getRevenueByCustomer(@RequestParam("start") String start,
//                                                           @RequestParam("end") String end,
//                                                           @PathVariable Long id) throws ParseException {
//        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(start);
//        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(end);
//
//        double revenue = 0;
//        List<Invoice> invoices = invoiceService.getAllInvoicesByCustomerBetween(id, sd, ed);
//        for (Invoice invoice : invoices) {
//            revenue += invoice.getTotalCharge();
//        }
//        Map<String, Object> result = new HashMap<>(4);
//        result.put("Customer's ID", id);
//        result.put("Begin date", start);
//        result.put("End date", end);
//        result.put("Revenue", revenue);
//
//        return result;
//    }

//    @GetMapping(path = "/driver-revenue-period/{id}")
//    public Map<String, Object> getRevenueByDriver(@RequestParam("start") String start,
//                                                    @RequestParam("end") String end,
//                                                    @PathVariable Long id) throws ParseException {
//        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(start);
//        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(end);
//
//        double revenue = 0;
//        List<Invoice> invoices = invoiceService.getAllInvoicesByDriverBetween(id, sd, ed);
//        for (Invoice invoice : invoices) {
//            revenue += invoice.getTotalCharge();
//        }
//        Map<String, Object> result = new HashMap<>(4);
//        result.put("Driver's ID", id);
//        result.put("Begin date", start);
//        result.put("End date", end);
//        result.put("Revenue", revenue);
//
//        return result;
//    }

    @GetMapping(path = "/car-use-month")
    public Map<String, Integer> usesOfCarInMonth(@RequestParam("month") String month,
                                                @RequestParam("year") String year) throws ParseException{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM", Locale.ENGLISH);
        cal.setTime(format.parse(year+"-"+month));

        List<Invoice> invoices = invoiceService.getAllInvoices();

        Map<String, Set<ZonedDateTime>> checkRepeatDate = new HashMap<>();
        Map<String, Integer> result = new HashMap<>();
        for(Invoice invoice: invoices){
            String carLicensePlate =carService.getById(driverService.getById(invoice.getDriver()).getCarId()).getLicencePlate();
            if(!result.containsKey(carLicensePlate)) {
                result.put(carLicensePlate, 0);
                Set<ZonedDateTime> days = new HashSet<>();
                days.add(invoice.getTime());
                checkRepeatDate.put(carLicensePlate, days);
            }
            else
                if(checkRepeatDate.get(carLicensePlate).add(invoice.getTime())) result.put(carLicensePlate, result.get(carLicensePlate)+1);
        }
        return result;
    }


}
