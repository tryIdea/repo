#### 下载项目依赖jar包的所有源码
mvn dependency:sources
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true

#### 查看所有依赖树
mvn dependency:tree
mvn dependency:tree -Dverbose -Dincludes=<groupId>:<artifactId>

#### Maven安装第三方Jar包到本地仓库:maven-install-plugin
  mvn install:install-file -Dfile= -DgroupId= -DartifactId= -Dversion= -Dpackaging=
  mvn install:install-file  -Dfile=e:\tmp\hijson\HiJson.jar  -DgroupId=com.kunlunsoft5  -DartifactId=Hijson -Dversion=1.0.0 -Dpackaging=jar
#### 发布到远程
mvn deploy:deploy-file -Dfile=/Users/dufugang/project/train-service-api/lib/nls-service-sdk.jar  -DgroupId=com.alibaba.idst  -DartifactId=nls-service-sdk -Dversion=3.3.1 -Dpackaging=jar -DrepositoryId=rdc-releases -Durl=https://repo.rdc.aliyun.com/repository/28238-release-F3OKjA/
#### Maven引入本地Jar包并打包进War包中

<dependency>
    <groupId>*</groupId>
    <artifactId>*</artifactId>
    <version>*</version>
    <systemPath>${project.basedir}/lib/*.jar</systemPath>
</dependency>

#### Maven查看当前生效的settings.xml
mvn help:effective-settings
mvn -X命令可以查看settings.xml文件的读取顺序
mvn help:effective-pom用于查看当前生效的POM内容
mvn help:system
mvn help:describe -Dplugin=archetype
mvn help:describe -DgroupId=org.apache.maven.plugins -DartifactId=maven-archetype-plugin

#### maven生成项目骨架
vn archetype:create-from-project
cd target/generated-sources/archetype/
mvn install
mvn archetype:crawl
mvn deploy(可选）
mvn archetype:generate -DinteractiveMode=false -DarchetypeCatalog=internal,remote -DarchetypeRepository=https://repo.rdc.aliyun.com/repository/28238-snapshot-KOJx4y -DarchetypeGroupId=com.yunli.sca -DarchetypeArtifactId=sca-portal-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=com.yunli -DartifactId=new-demo -Dversion=1.0.0-SNAPSHOT -Dgoals=compilemvn archetype:generate -DinteractiveMode=false -DarchetypeCatalog=internal,remote -DarchetypeRepository=https://repo.rdc.aliyun.com/repository/28238-snapshot-KOJx4y -DarchetypeGroupId=com.yunli.sca -DarchetypeArtifactId=sca-portal-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=com.yunli -DartifactId=new-demo -Dversion=1.0.0-SNAPSHOT -Dgoals=compile
