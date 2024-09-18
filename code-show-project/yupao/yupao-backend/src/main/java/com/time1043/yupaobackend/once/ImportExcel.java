package com.time1043.yupaobackend.once;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;

import java.util.List;
import java.util.Map;

/**
 * 工具 导入Excel数据
 *
 * @author oswin
 */
public class ImportExcel {

    public static void main(String[] args) {
        String fileName = "/opt/code/java-code/hello-java/code-show-project/yupao/yupao-backend/data/user.xlsx";
        //readByListener(fileName);
        synchronousRead(fileName);
    }

    /**
     * 读取Excel数据，监听器模式
     *
     * @param fileName Excel文件路径
     */
    public static void readByListener(String fileName) {
        // https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E4%BB%A3%E7%A0%81
        EasyExcel.read(fileName, ZSXQTableUserInfo.class, new TableListener()).sheet().doRead();
    }

    /**
     * 读取Excel数据，同步读取模式
     *
     * @param fileName Excel文件路径
     */
    public static void synchronousRead(String fileName) {
        // https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E5%90%8C%E6%AD%A5%E7%9A%84%E8%BF%94%E5%9B%9E
        List<ZSXQTableUserInfo> totalDataList = EasyExcel.read(fileName).head(ZSXQTableUserInfo.class).sheet().doReadSync();
        for (ZSXQTableUserInfo zsxqTableUserInfo : totalDataList) {
            System.out.println(zsxqTableUserInfo);
        }
    }
}
