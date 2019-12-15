package com.galen.amazon.pojo;

import java.util.List;

/**
 * @Author: Galen
 * @Description: 折线图数据
 * @Date: 2019/3/14-22:08
 **/
public class ChartData {
    /**
     * 单折线
     *
     * {
     *   columns: ['日期', '访问用户'],
     *   rows: [
     *     { '日期': '2018-05-22', '访问用户': 32371},
     *     { '日期': '2018-05-23', '访问用户': 12328},
     *     { '日期': '2018-05-24', '访问用户': 92381}
     *   ]
     * }
     *
     * 双折线
     * {
     *   columns: ['日期', '访问用户', '下单用户'],
     *   rows: [
     *     { '日期': '2018-05-22', '访问用户': 32371, '下单用户': 19810 },
     *     { '日期': '2018-05-23', '访问用户': 12328, '下单用户': 4398 },
     *     { '日期': '2018-05-24', '访问用户': 92381, '下单用户': 52910 }
     *   ]
     * }
     */

    private String[] columns;

    private List<Object> rows;


    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<Object> getRows() {
        return rows;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }
}
