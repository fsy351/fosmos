
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/*
 * <p>
 * 测试生成代码
 * </p>
 *
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

    private static final String CONN_STR = "jdbc:mysql://localhost:3306/cims?useUnicode=true&amp;characterEncoding=UTF-8";

    //@Test
    public void generateCode() {
        String packageName = "com.fosmos";
        String[] tableNames = new String[] {
                "sys_dict",
                "sys_acl",
                "sys_acl_mdl",
                "sys_role",
                "sys_role_acl",
                "sys_user",
                "sys_user_role",
                "sys_dept"
        };
        generateByTables(packageName, tableNames);
    }

    private void generateByTables(String packageName, String... tableNames) {
        GlobalConfig gc = new GlobalConfig();

        gc.setActiveRecord(false)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setAuthor("fosmos")
                .setOutputDir("./tmp")
                .setFileOverride(true)
                .setServiceName("%sService");

        DataSourceConfig dc = new DataSourceConfig();
        dc.setDbType(DbType.MYSQL.MYSQL)
                .setTypeConvert(new MySqlTypeConvert())
                .setUrl(CONN_STR)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.jdbc.Driver");

        StrategyConfig sc = new StrategyConfig();
        sc.setCapitalMode(true)
                .setSuperControllerClass("BaseController")
                .setSuperMapperClass("BaseMapper")
                .setSuperServiceClass("BaseService")
                .setSuperEntityClass("BaseEntity")
                //.setSuperEntityColumns()
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("sys_")
                .setRestControllerStyle(true)
                .setInclude(tableNames);

        new AutoGenerator().setGlobalConfig(gc)
                .setDataSource(dc)
                .setStrategy(sc)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController(packageName+".controller")
                                .setEntity(packageName+".domain")
                )
                .execute();
    }
}
