package com.qf.service;

import com.qf.domain.ProductStockInRecordVO;
import com.qf.domain.StockInRecord;
import com.qf.mapper.StockInRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ios on 17/11/30.
 */
@Service
public class StockInRecordService {

    @Autowired
    private StockInRecordMapper stockInRecordMapper;

    public boolean insertStockInRecord(StockInRecord stockInRecord) {
        int count = stockInRecordMapper.insertStockInRecord(stockInRecord);
        return count == 1;
    }

    /**
     * 获取入库记录的详细信息
     * @param start
     * @param end
     * @return
     */
    public List<ProductStockInRecordVO> getStockInRecordBetween(Timestamp start, Timestamp end) {
        return stockInRecordMapper.getStockInRecordBetween(start, end);
    }
}
