package com.qf.service;

import com.qf.domain.Stock;
import com.qf.domain.StockInRecord;
import com.qf.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ios on 17/11/15.
 */
@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Transactional
    public boolean insertStock(Stock stock) {
        int count = stockMapper.insertStock(stock);
        StockInRecord stockInRecord = new StockInRecord();
        stockInRecord.setCount(stock.getCountOfPieces());
        stockInRecord.setStockId(stock.getId());
        int count1 = stockMapper.insertStockInRecord(stockInRecord);
        return count == 1 && count1 == 1;
    }

    public boolean updateStock(Stock stock) {
        int count = stockMapper.updateStock(stock);
        return count == 1;
    }

    @Transactional
    public boolean increaseStockCount(Stock stock) {
        int count = stockMapper.increaseStockCount(stock);
        StockInRecord stockInRecord = new StockInRecord();
        stockInRecord.setStockId(stock.getId());
        stockInRecord.setCount(stock.getCountOfPieces());
        int count1 = stockMapper.insertStockInRecord(stockInRecord);
        return count == 1 && count1 == 1;
    }
}
