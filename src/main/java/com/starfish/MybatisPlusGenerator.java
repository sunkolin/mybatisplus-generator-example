package com.starfish;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.Collections;

/**
 * MybatisPlusGenerator
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-12-02
 */
public class MybatisPlusGenerator {

    // 数据源配置
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/starfish?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&useSSL=false";

    private static final String DB_USERNAME = "root";

    private static final String DB_PASSWORD = "123456789";

    // MySQL 8.x 驱动
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // MySQL 5.x 驱动
    // private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    // 生成代码的输出目录（默认是项目根目录下的 src/main/java，可自定义）
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/java";

    // Mapper.xml 文件输出目录（src/main/resources/mapper）
    private static final String MAPPER_XML_OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/resources/mapper";

    // 父包名（根据自己项目的包结构修改）
    private static final String PARENT_PACKAGE = "com.starfish";

    // 模块名（可选，如无模块可设为 ""）
    private static final String MODULE_NAME = "";

    // 作者（生成代码的注释中显示）
    private static final String AUTHOR = "sunkolin";

    // 需要生成代码的表名（支持多个表，用逗号分隔；生成所有表填 "*"）
    private static final String[] TABLE_NAMES = {"t_user"};

    // 表前缀（生成 Entity 时会去掉前缀，如 "sys_user" -> Entity 名为 "User"）
    private static final String TABLE_PREFIX = "t_";

    public static void main(String[] args) {
        // 1. 数据源配置
        DataSourceConfig.Builder dataSource = new DataSourceConfig.Builder(DB_URL, DB_USERNAME, DB_PASSWORD).driverClassName(DB_DRIVER);

        // 2. 快速生成器主配置
        FastAutoGenerator.create(dataSource)
                // 3. 全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 作者·
                            .outputDir(OUTPUT_DIR) // 代码输出目录
                            .disableOpenDir() // 生成后不打开文件夹（可选，true 为打开）
                            .dateType(DateType.ONLY_DATE) // 日期类型（java.time 包，JDK8+）
//                            .dateType(DateType.TIME_PACK) // 日期类型（java.time 包，JDK8+）
                            .commentDate("yyyy-MM-dd");// 注释日期格式
                })
                // 4. 包配置（指定生成的类放在哪个包下）
                .packageConfig(builder -> {
                    builder.parent(PARENT_PACKAGE) // 父包名
                            .moduleName(MODULE_NAME) // 模块名（如 parent=com.example.demo，module=system -> 完整包名 com.example.demo.system）
                            .entity("entity") // Entity 包名（默认 entity）
                            .mapper("mapper") // Mapper 接口包名（默认 mapper）
                            .service("service") // Service 接口包名（默认 service）
                            .serviceImpl("service.impl") // Service 实现类包名（默认 service.impl）
                            .controller("controller") // Controller 包名（默认 controller）
                            .xml("mapper") // Mapper.xml 包名（默认 mapper）
                            .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_OUTPUT_DIR)); // Mapper.xml 输出目录
                })
                // 5. 策略配置（表、字段、类的生成规则）
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAMES) // 要生成的表名
                            .addTablePrefix(TABLE_PREFIX) // 去掉表前缀
                            .entityBuilder() // Entity 生成策略
                            .enableFileOverride()
                            .formatFileName("%sEntity").enableLombok() // 启用 Lombok（需引入 Lombok 依赖）
                            .enableChainModel() // 启用链式调用（如 user.setId(1).setName("xxx")）
                            .enableTableFieldAnnotation() // 为字段添加 @TableField 注解
                            .addTableFills( // 自动填充配置（如创建时间、更新时间）
                                    new Property("createTime", FieldFill.INSERT), new Property("updateTime", FieldFill.INSERT_UPDATE), new Property("modifyTime", FieldFill.INSERT_UPDATE)).mapperBuilder() // Mapper 生成策略
                            .superClass(BaseMapper.class) // 继承 BaseMapper（MP 自带）
                            .enableBaseResultMap() // 启用 BaseResultMap（XML 中生成结果映射）
                            .enableBaseColumnList() // 启用 BaseColumnList（XML 中生成查询字段列表）
                            .mapperBuilder()
                            .enableFileOverride()
                            .controllerBuilder() // Controller 生成策略
                            .enableFileOverride()
                            .enableRestStyle() // 启用 REST 风格（如 @GetMapping、@PostMapping）
                            .enableHyphenStyle() // 启用连字符路由（如 /sys-user/1 -> 对应 sysUser/1）
                            .serviceBuilder() // Service 生成策略
                            .enableFileOverride()
                            .formatServiceFileName("%sService") // Service 接口名格式（默认 %sService）
                            .formatServiceImplFileName("%sServiceImpl"); // Service 实现类名格式（默认 %sServiceImpl）
                })
                // 6. 模板引擎配置
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行生成
                .execute();
    }

}
