package com.veli.mybatis.generator.plugin;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * @author yangwei
 */
public class MyBatisPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.experimental.Accessors");
        topLevelClass.addImportedType("lombok.Data");

        // 获取表注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable() + " " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author yangwei");
        topLevelClass.addJavaDocLine(" */");

        //添加domain的注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Accessors(chain = true)");

        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (introspectedColumn.isAutoIncrement()) {
            topLevelClass.addImportedType("javax.persistence.Id");
            field.addAnnotation("@Id");
        }

        genRemark(field, topLevelClass, introspectedColumn);
        return true;
    }

    private void genRemark(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();

        if (remarks != null && !"".equals(remarks)) {

            topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
            field.addAnnotation("@ApiModelProperty(\"" + String.join(" ", introspectedColumn.getRemarks()) + "\")");
        }
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        // 不生成Mapper.xml文件，自己去自定义
        return false;
    }

    @Override
    public boolean clientGenerated(Interface inter, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType superinterface = new FullyQualifiedJavaType("Mapper<" + introspectedTable.getBaseRecordType() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("tk.mybatis.mapper.common.Mapper");

        inter.addSuperInterface(superinterface);
        inter.addImportedType(imp);

        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 不生成Example对象
        return false;
    }

    // region -- 不生成默认的接口方法
    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }
    // endregion
}