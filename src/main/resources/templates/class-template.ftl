package com.example.generation.${package};

<#list columns as column>
     <#if column.imp?has_content>
import ${column.imp}
     </#if>
</#list>

public class ${className} {

<#list columns as column>
    private ${column.type} ${column.name};
</#list>

<#list columns as column>
    public ${column.type} get${column.capitalizedName}() {
        return ${column.name};
    }
    public void set${column.capitalizedName}(${column.type} ${column.name}) {
        this.${column.name} = ${column.name};
    }

</#list>

    public ${className} (<#list columns as column>${column.type} ${column.name}<#if column_has_next>,</#if></#list>){
    <#list columns as column>
        this.${column.name} = ${column.name};
    </#list>
    }

    public ${className}() {
    }
}
