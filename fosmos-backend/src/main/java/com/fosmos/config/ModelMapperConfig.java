package com.fosmos.config;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.util.Date;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){

        Converter dateToStrCvt = new AbstractConverter<Date, String>() {
            @Override
            protected String convert(Date dt) {
                if( dt == null )
                    return null;
                return DateFormatUtils.format(dt,ConstConfig.DATE_TIME_FMT);
            }
        };

        Converter strToDateCvt = new AbstractConverter<String, Date>() {
            @Override
            protected Date convert(String s) {
                try {
                    return DateUtils.parseDate(s,ConstConfig.DATE_TIME_FMT,ConstConfig.DATE_FMT);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(dateToStrCvt);
        mapper.addConverter(strToDateCvt);

        return mapper;
    }

}
