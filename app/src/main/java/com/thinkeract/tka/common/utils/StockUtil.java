package com.thinkeract.tka.common.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by minHeng on 2017/4/17 14:23.
 * mail:minhengyan@gmail.com
 */

public class StockUtil {

    //取幂集
    public static HashSet<List<String>> getPowSet(String codeStr) {
        if(TextUtils.isEmpty(codeStr)) return new HashSet<>();
        String[] codeStrArr = codeStr.split(";");
        List<String> list = Arrays.asList(codeStrArr);
        if (list.size() > 0) {
            HashSet<List<String>> result = new HashSet<>();
            for (int i = 0; i < Math.pow(2, list.size()); i++) {// 集合子集个数=2的该集合长度的乘方
                List<String> subSet = new ArrayList<>();
                int index = i;// 索引从0一直到2的集合长度的乘方-1
                for (int j = 0; j < list.size(); j++) {
                    // 通过逐一位移，判断索引那一位是1，如果是，再添加此项
                    if ((index & 1) == 1) {// 位与运算，判断最后一位是否为1
                        subSet.add(list.get(j));
                    }
                    index >>= 1;// 索引右移一位
                }
                Collections.sort(subSet,new CompareStringList<String>());
                result.add(subSet); // 把子集存储起来
            }
            List<String> empty = new ArrayList<>();
            if(result.contains(empty))
                result.remove(empty);
            return result;
        } else {
            return new HashSet<>();
        }
    }

    private static class CompareStringList<T> implements Comparator<T> {
        @Override
        public int compare(T lhs, T rhs) {
            String[] itemArrA = ((String) lhs).split(":");
            String[] itemArrB = ((String) rhs).split(":");
            int itemIntA = Integer.parseInt(itemArrA[0]);
            int itemIntB = Integer.parseInt(itemArrB[0]);
            return  itemIntA - itemIntB;
        }
    }


}
