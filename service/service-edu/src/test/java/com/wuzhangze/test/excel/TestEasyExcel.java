package com.wuzhangze.test.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author OldWu
 * @date 2021/8/23 13:55
 */
public class TestEasyExcel {
    static List<DemoData> demoDatas = new ArrayList<>();
    public static void main(String[] args) {
        // 实现读操作
        String fileName = "C:\\Users\\86182\\Desktop\\write.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new TestReadListener()).sheet("学生列表").doRead();
        System.out.println(demoDatas);
    }

    static class TestReadListener extends AnalysisEventListener<DemoData> {
        /**
         * 每一条数据解析都会调用
         * @param demoData 读取的一行数据
         */
        @Override
        public void invoke(DemoData demoData, AnalysisContext analysisContext) {
            demoDatas.add(demoData);
        }

        /**
         * 所有的数据解析完成调用
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            System.out.println("finish");
        }
    }
}
