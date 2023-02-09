import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class FastCodeGenerator {

    // 初始化数据
    final static String table_name_list = "financial,type_io";
    final static String module_name = "erp-financial";
    final static String url = "localhost:3306";
    final static String database = "erp_financial";
    final static String username = "root";
    final static String password = "toor";

    final static String db_url = "jdbc:mysql://" + url + "/" + database + "?characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=true";

    final static String author = "jd";
    final static String package_name = "pers." + author;
    final static String package_name_output = "pers\\" + author;

    final static String projectPath = System.getProperty("user.dir");

    final static String output_path = projectPath + "\\" + module_name + "\\src\\main\\java";
    final static String output_mapper_path = projectPath + "\\" + module_name + "\\src\\main\\java\\" + package_name_output + "\\mapper";

    public static void main(String[] args) {
        String[] list = table_name_list.split(",");
        for (int i = 0; i < list.length; i++) {
            generator_single(list[i]);
        }
    }

    public static void generator_single(String table_name) {
        FastAutoGenerator.create(db_url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(output_path); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(package_name) // 设置父包名
//                            .moduleName(module_name) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, output_mapper_path)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(table_name);// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
