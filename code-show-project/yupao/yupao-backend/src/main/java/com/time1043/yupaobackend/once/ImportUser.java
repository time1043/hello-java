package com.time1043.yupaobackend.once;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Map<String, List<ZSXQTableUserInfo>> listMap = userInfoList.stream()   // 先转换为map
                .filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUsername()))  // 过滤掉空用户名
                .collect(Collectors.groupingBy(ZSXQTableUserInfo::getUsername));   // 按用户名分组
        System.out.println("sum: " + userInfoList.size());
        System.out.println("enum: " + listMap.keySet().size());

        // 输出重复的用户名
        for (Map.Entry<String, List<ZSXQTableUserInfo>> stringListEntry : listMap.entrySet()) {
            if (stringListEntry.getValue().size() > 1) {
                System.out.println("=====================================");
                System.out.println("username: " + stringListEntry.getKey());
            }
        }
    }
}
