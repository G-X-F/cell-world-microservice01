package com.meux.icarbonx.utis;

import java.util.ArrayList;
import java.util.List;

public class Avatas {
    //数组 转 列表
    public static List<Integer> Array2List(Integer[] array) {
        List<Integer> list = new ArrayList<>();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
        }
        return list;
    }

    //列表 转 数组
    public static Integer[] List2Array(List<Integer> list) {
        Integer[] array = new Integer[0];
        if (list != null && list.size() > 0) {
            array = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
        }
        return array;
    }

    /**
     * 得到的list 不包含0
     *
     * @param array
     * @return
     */
    public static List<Integer> Array2List(String[] array) {
        List<Integer> list = new ArrayList<>();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                list.add(Integer.parseInt(array[i]));
            }
        }
        return list;
    }
}
