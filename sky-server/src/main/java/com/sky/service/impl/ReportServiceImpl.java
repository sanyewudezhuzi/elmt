package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.ReportMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkSpaceService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private WorkSpaceService workSpaceService;

    /**
     * 营业额统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        // 获取时间集合
        List<LocalDate> timeList = new ArrayList<>();
        timeList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            timeList.add(begin);
        }

        // 获取统计集合
        List<Double> turnoverList = timeList.stream().map((i) -> {
            LocalDateTime beginTime = LocalDateTime.of(i, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(i, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = reportMapper.sumByMap(map);
            return turnover == null ? 0.0 : turnover;
        }).collect(Collectors.toList());
        // 封装数据
        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(timeList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 获取时间集合
        List<LocalDate> timeList = new ArrayList<>();
        timeList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            timeList.add(begin);
        }

        List<Integer> newUserList = new ArrayList<>(); // 新增用户数
        List<Integer> totalUserList = new ArrayList<>(); // 总用户数

        // 计算个数
        timeList.forEach((i) -> {
            LocalDateTime beginTime = LocalDateTime.of(i, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(i, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", null);
            map.put("end", endTime);
            Integer totalUser = reportMapper.getUserCount(map);
            totalUserList.add(totalUser == null ? 0 : totalUser);
            map.put("begin", beginTime);
            Integer newUser = reportMapper.getUserCount(map);
            newUserList.add(newUser == null ? 0 : newUser);
        });

        // 封装数据
        return UserReportVO
                .builder()
                .dateList(StringUtils.join(timeList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        // 获取时间集合
        List<LocalDate> timeList = new ArrayList<>();
        timeList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            timeList.add(begin);
        }

        List<Integer> orderCountList = new ArrayList<>(); // 每日订单数
        List<Integer> validOrderCountList = new ArrayList<>(); // 每日有效订单数

        // 获取数据
        timeList.forEach((i) -> {
            LocalDateTime beginTime = LocalDateTime.of(i, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(i, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", null);
            Integer orderCount = reportMapper.getOrderCount(map);
            orderCountList.add(orderCount == null ? 0 : orderCount);
            map.put("status", Orders.COMPLETED);
            Integer validOrderCount = reportMapper.getOrderCount(map);
            validOrderCountList.add(validOrderCount == null ? 0 : validOrderCount);
        });

        // 完善数据
        // 时间区间内的总订单数
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        // 时间区间内的总有效订单数
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
        // 订单完成率
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        // 返回数据
        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(timeList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 查询销量排名top10接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO gettop10(LocalDate begin, LocalDate end) {
        // 获取时间集合
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 获取数据
        List<GoodsSalesDTO> goodsSales = reportMapper.getSalesTop10(beginTime, endTime);

        // 封装数据
        List<String> nameList = goodsSales.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = goodsSales.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        // 返回数据
        return SalesTop10ReportVO
                .builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    /**
     * 导出Excel报表接口
     *
     * @param response
     */
    @Override
    public void export(HttpServletResponse response) throws Exception {
        // 获取数据
        LocalDateTime begin = LocalDateTime.of(LocalDate.now().plusDays(-30), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(-1), LocalTime.MAX);
        BusinessDataVO data = workSpaceService.businessData(begin, end);

        // 获取文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("./template/运营数据报表模板.xlsx");
        // 基于提供好的模板文件创建一个新的Excel表格对象
        XSSFWorkbook excel = new XSSFWorkbook(is);
        // 获得Excel文件中的一个sheet页
        XSSFSheet sheet = excel.getSheet("Sheet1");
        // 写入第二行第二列的单元格
        sheet.getRow(1).getCell(1).setCellValue(begin.toLocalDate() + "至" + end.toLocalDate());
        // 获得第四行
        XSSFRow row = sheet.getRow(3);
        // 获取单元格
        row.getCell(2).setCellValue(data.getTurnover());
        row.getCell(4).setCellValue(data.getOrderCompletionRate());
        row.getCell(6).setCellValue(data.getNewUsers());
        // 获得第五行
        row = sheet.getRow(4);
        // 获取单元格
        row.getCell(2).setCellValue(data.getValidOrderCount());
        row.getCell(4).setCellValue(data.getUnitPrice());

        for (int i = 0; i < 30; i++) {
            // 准备明细数据
            data = workSpaceService.businessData(begin.plusDays(i), end.plusDays(i - 29));
            row = sheet.getRow(7 + i);
            log.info("row: {}", row);
            log.info("row.getCell(1): {}", row.getCell(1));
            row.getCell(1).setCellValue(LocalDate.from(begin.plusDays(i)).toString());
            row.getCell(2).setCellValue(data.getTurnover());
            row.getCell(3).setCellValue(data.getValidOrderCount());
            row.getCell(4).setCellValue(data.getOrderCompletionRate());
            row.getCell(5).setCellValue(data.getUnitPrice());
            row.getCell(6).setCellValue(data.getNewUsers());
        }
        // 通过输出流将文件下载到客户端浏览器中
        ServletOutputStream out = response.getOutputStream();
        excel.write(out);
        // 关闭资源
        out.close();
        excel.close();
    }

}
