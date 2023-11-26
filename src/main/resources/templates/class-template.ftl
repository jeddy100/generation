package com.example.generation.${package};

public class ${className} {

<#list columns as column>
    private ${column.type} ${column.name};

    public ${column.type} get${column.capitalizedName}() {
    return ${column.name};
    }

    public void set${column.capitalizedName}(${column.type} ${column.name}) {
    this.${column.name} = ${column.name};
    }
</#list>

public ${className}() {
// default constructor
}
}
