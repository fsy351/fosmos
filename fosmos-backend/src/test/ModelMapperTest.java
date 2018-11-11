
import com.fosmos.config.ConstConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class ModelMapperTest {

    @Getter
    @Setter
    @ToString
    static class DataCls {

        private String name;
        private Integer age;
        private Date today;


        private BigDecimal money;
        private Double doublex;

    }

    @Getter
    @Setter
    @ToString
    static class DataClsVO {

        private String name;
        private String age;
        private String today;
        private String money;
        private String doublex;

    }

    public static void main(String[] args) {

        DataCls data = new DataCls();
        data.age = 15;
        data.doublex = 20.45;
        data.money = new BigDecimal("170.4789");

        data.today = new Date();
        data.name = "dataclsxxx";

        ModelMapper mapper = new ModelMapper();


        mapper.addConverter(new AbstractConverter<Date, String>() {
            @Override
            protected String convert(Date d) {
                return DateFormatUtils.format(d,ConstConfig.DATE_TIME_FMT);
            }
        });

        mapper.addConverter(new AbstractConverter<String, Date>() {
            @Override
            protected Date convert(String s) {
                try {
                    return DateUtils.parseDate("2015-01-03",ConstConfig.DATE_TIME_FMT,ConstConfig.DATE_FMT);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        DataClsVO vo = mapper.map(data,DataClsVO.class);

        DataCls cls = mapper.map(vo,DataCls.class);

        System.out.println(vo);
        System.out.println(cls);
    }
}
