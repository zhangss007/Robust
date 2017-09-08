package com.meituan.robust.utils;

import com.meituan.robust.mapping.ClassMapping;
import com.meituan.robust.mapping.ProGuardMappingUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hedingxu on 17/8/21.
 */

//todo
// 要处理新的mapping.txt & 旧的mapping.txt
//class diff 需要两个mapping
//判断匿名内部类 需要两个mapping
//判断lambda表达式需要两个mapping
//判断access方法需要两个mapping
//get robust method md5 需要两个mapping
//判断一个class的外部类需要两个mapping
public class RobustProguardMapping {
    private static boolean isProguard = false;
     static Map<String, ClassMapping> proguardClassMappings = new LinkedHashMap<String, ClassMapping>();
     static Map<String, ClassMapping> unProguardClassMappings = new LinkedHashMap<String, ClassMapping>();


    public static void readMapping(String mappingTxtPath) {
        if (null == mappingTxtPath || "".equals(mappingTxtPath)) {
            throw new RuntimeException("mappingTxtPath is null or empty");
        }
        isProguard = true;
        ProGuardMappingUtils.handleMappingFile(mappingTxtPath);
        unProguardClassMappings.putAll(ProGuardMappingUtils.classMappings);
        for (ClassMapping classMapping : unProguardClassMappings.values()) {
            proguardClassMappings.put(classMapping.newClassName, classMapping);
        }
    }

    public static boolean isProguard() {
        return isProguard;
    }

    public static String getUnProguardName(String proguardName) {
        if (isProguard) {
            if (proguardClassMappings.containsKey(proguardName)) {
                return proguardClassMappings.get(proguardName).className;
            }
        }
        return proguardName;
    }

    public static String getProguardName(String unProguardName) {
        if (isProguard) {
            if (unProguardClassMappings.containsKey(unProguardName)) {
                return unProguardClassMappings.get(unProguardName).newClassName;
            }
        }
        return unProguardName;
    }






}
