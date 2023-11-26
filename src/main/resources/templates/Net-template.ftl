using System;
namespace ${package};
{

    public class ${className} {

        <#list columns as column>
            public ${column.type} ${column.name} { get; set; }
        </#list>


    }
}
