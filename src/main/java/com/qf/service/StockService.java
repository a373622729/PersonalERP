package com.qf.service;

import com.qf.domain.Stock;
import com.qf.domain.StockInRecord;
import com.qf.mapper.StockInRecordMapper;
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
    @Autowired
    private StockInRecordMapper stockInRecordMapper;

    @Transactional
    public boolean insertStock(Stock stock) {
        int count = stockMapper.insertStock(stock);
        return count == 1;
    }

    public boolean updateStock(Stock stock) {
        int count = stockMapper.updateStock(stock);
        return count == 1;
    }

    /**
     * 在STOCK中写入新增的数量,并在STOCK_RECORD中插入记录
     * @param stock
     * @return
     */
    @Transactional
    public boolean increaseStockCount(Stock stock, StockInRecord stockInRecord) {
        int count = stockMapper.increaseStockCount(stock);
        int count1 = stockInRecordMapper.insertStockInRecord(stockInRecord);
        return count == 1 && count1 == 1;
    }
}
