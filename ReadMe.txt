项目模块拆分模板。
可灵活组织项目模块，最终按下列结构聚合为完整web项目。

项目结构：
sz_school_chanpi_m                  project
    aggregator-web                  module  聚合web模块。显示层，公用配置文件。(所有的页面都在此模块中)
    aggregator-class                module  聚合类模块。只是为其他模块类编译使用，不做他用。
    web-chanpin                     module  web模块chanpin。暂时未使用（预留将来扩展使用）
    class-resource                  module  资源系统模块业务包。业务层等，专用配置文件。
    class-teachingplatform          module  教学平台模块业务包。业务层等，专用配置文件。
    class-basicplatform             module  基础平台模块业务包。业务层等，专用配置文件。
    class-interface                 module  与网校，IM的接口模块业务包。业务层等，专用配置文件。
    class-common                    module  所有模块公用包。

依赖关系：
    aggregator-web
            class-resource
               class-common
            class-teachingplatform
               class-common
             class-basicplatform
                 class-common