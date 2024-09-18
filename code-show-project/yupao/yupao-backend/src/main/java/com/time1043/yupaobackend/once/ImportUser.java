package com.time1043.yupaobackend.once;

import com.alibaba.excel.EasyExcel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务 导入用户数据 (包含数据清洗)
 *
 * @author oswin
 */
public class ImportUser {
    public static void main(String[] args) {
        String fileName = "/opt/code/java-code/hello-java/code-show-project/yupao/yupao-backend/data/user.xlsx";
        List<ZSXQTableUserInfo> userInfoList = EasyExcel.read(fileName).head(ZSXQTableUserInfo.class).sheet().doReadSync();

        // 判断用户重复
        Map<String, List<ZSXQTableUserInfo>> listMap = userInfoList.stream().collect(Collectors.groupingBy(ZSXQTableUserInfo::getUsername));
        System.out.println("sum: " + userInfoList.size());
        System.out.println("repeat: " + listMap.keySet().size());

    }
}
