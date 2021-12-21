package xyz.lazyrabbit.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class MybatisPlusGenerator {

    /**
     * 模板存放目录
     * /templates/vm/
     */
    private static String TEMPLATES_DIR = "/templates/";
    private static String PACKAGE_NAME = "xyz.lazyrabbit";
    private static String MODULE_NAME = "generator";
    private static String PROJECT_PATH = System.getProperty("user.dir") + "/rabb-springboot-mybatisplus";
    private static String AUTHOR = "lazyrabbit";
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8";
    private static String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DATABASE_USERNAME = "root";
    private static String DATABASE_PASSWORD = "123456";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局策略配置
        mpg.setGlobalConfig(getGlobalConfig(mpg));
        // 数据源配置，通过该配置，指定需要生成代码的具体数据库
        mpg.setDataSource(getDataSourceConfig());
        // 包名配置，通过该配置，指定生成代码的包路径
        mpg.setPackageInfo(getPackageConfig());
        // 注入配置，通过该配置，可注入自定义参数等操作以实现个性化操作
        mpg.setCfg(getInjectionConfig());
        // 模板配置，可自定义代码生成的模板
        mpg.setTemplate(getTemplateConfig());
        // 数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        mpg.setStrategy(getStrategyConfig());
        // 设置模板引擎
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    /**
     * 全局配置
     *
     * @param mpg
     */
    public static GlobalConfig getGlobalConfig(AutoGenerator mpg) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(PROJECT_PATH + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        return gc;
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DATABASE_URL);
        dsc.setDriverName(DATABASE_DRIVER);
        dsc.setUsername(DATABASE_USERNAME);
        dsc.setPassword(DATABASE_PASSWORD);
        return dsc;
    }

    /**
     * 获取包配置
     *
     * @return
     */
    public static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PACKAGE_NAME);
        return pc;
    }

    /**
     * 获取模板配置
     *
     * @return
     */
    public static TemplateConfig getTemplateConfig() {
        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        TemplateConfig tc = new TemplateConfig();
        tc.setController(TEMPLATES_DIR + "controller.java");
        tc.setService(TEMPLATES_DIR + "service.java");
        tc.setServiceImpl(TEMPLATES_DIR + "serviceImpl.java");
        tc.setEntity(TEMPLATES_DIR + "entity.java");
        tc.setMapper(TEMPLATES_DIR + "mapper.java");
        // 		tc.setXml(TEMPLATES_DIR+"mapper.xml.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // 关闭默认 xml 生成，调整生成至根目录
        tc.setXml(null);
        return tc;
    }

    public static InjectionConfig getInjectionConfig() {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(TEMPLATES_DIR + "mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECT_PATH + "/src/main/resources/mapper/" + MODULE_NAME
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig(TEMPLATES_DIR + "list.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return PROJECT_PATH + "/src/main/resources/static/" + tableInfo.getEntityName() + "ListIndex.html";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public static StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setTablePrefix(new String[] { "t_"});// 此处可以修改为您的表前缀
        // 需要包含的表名，允许正则表达式（与exclude二选一配置）
        strategy.setInclude("t_user");
        strategy.setTablePrefix(MODULE_NAME + "_");
        return strategy;
    }
}
