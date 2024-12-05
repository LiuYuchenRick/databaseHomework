package com.example.demo.service;

import com.example.demo.mapper.SaleOrderMapper;
import com.example.demo.mapper.PurchaseRecordMapper;
import com.example.demo.model.SaleOrder;
import com.example.demo.model.PurchaseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;

    public List<SaleOrder> getAllSalesOrders() {
        return saleOrderMapper.findAll();
    }

    public List<PurchaseRecord> getAllPurchaseOrders() {
        return purchaseRecordMapper.findAll();
    }

    public List<SaleOrder> getUserOrders(Long userId) {
        return saleOrderMapper.findByUserId(userId);
    }

    public BigDecimal getTotalSales() {
        return saleOrderMapper.calculateTotalSales();
    }
    public Integer getOrderCount() {
        return saleOrderMapper.countOrders();
    }
    public Integer getPurchaseCount() {
        return purchaseRecordMapper.countPurchases();
    }
} 