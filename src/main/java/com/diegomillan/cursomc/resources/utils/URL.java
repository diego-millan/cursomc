package com.diegomillan.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
    public static List<Integer> decodeInList(String str) {
        return Arrays.asList(str.split(",")).stream().map(
            x -> Integer.parseInt(x)).collect(Collectors.toList());
    }

    public  static String decodeParam(String str) {
        String decodedStr = "";
        try {
            decodedStr = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {}

        return decodedStr;
    }
}
