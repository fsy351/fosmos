package com.fosmos.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelMapperUtil {


    private static ModelMapper modelMapper;

    @Autowired
    private void initModelMapperUtil(ModelMapper mapper) {
        modelMapper = mapper;
    }

    public static List mapList(List source, Class clz) {
        List target = new ArrayList();
        source.forEach(t -> {
            target.add(modelMapper.map(t,clz));
        });
        return target;
    }

//    public static <T> List<T> mapList(List source, Class<T> clz) {
//        List target = new ArrayList();
//        source.forEach(t -> {
//            target.add(modelMapper.map(t,clz));
//        });
//        return target;
//    }
}
