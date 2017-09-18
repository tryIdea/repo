#### 下载项目依赖jar包的所有源码
mvn dependency:sources 
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true

#### 查看所有依赖树
maven dependency:tree

mvn dependency:tree -Dverbose -Dincludes=<groupId>:<artifactId>

####maven jetty配置
<!-- maven的jetty服务器插件 -->  
    <plugins>  
        <plugin>  
          <groupId>org.mortbay.jetty</groupId>  
          <artifactId>jetty-maven-plugin</artifactId>  
          <configuration>  
            <scanIntervalSeconds>10</scanIntervalSeconds>  
            <webApp>  
              <contextPath>/</contextPath>  
            </webApp>  
            <!-- 修改jetty的默认端口 -->  
            <connectors>  
               <connector implementation="org.eclipse.jetty.server.nio.SelectChannelConnector">  
                  <port>80</port>  
                  <maxIdleTime>60000</maxIdleTime>  
               </connector>  
            </connectors>  
          </configuration>  
        </plugin>  
    </plugins>  


#### 555
    ```
   export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games"
   export MANPATH="/usr/local/man:$MANPATH"
   export JAVA_HOME=/usr/local/bin/jdk1.8.0_65/
   export PATH=$JAVA_HOME/bin:$PATH
   export MAVEN_HOME=/usr/local/apache-maven-3.3.9
   export PATH=$MAVEN_HOME/bin:$PATH
   export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  ```

#### Maven安装第三方Jar包到本地仓库:maven-install-plugin
  mvn install:install-file -Dfile= -DgroupId= -DartifactId= -Dversion= -Dpackaging=
  mvn install:install-file  -Dfile=e:\tmp\hijson\HiJson.jar  -DgroupId=com.kunlunsoft5  -DartifactId=Hijson -Dversion=1.0.0 -Dpackaging=jar  

#### Maven引入本地Jar包并打包进War包中

<dependency>
    <groupId>*</groupId>
    <artifactId>*</artifactId>
    <version>*</version>
    <systemPath>${project.basedir}/lib/*.jar</systemPath>
</dependency>


<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.10</version>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>compile</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.directory}/${project.build.finalName}/WEB-INF/lib</outputDirectory>
                <includeScope>system</includeScope>
            </configuration>
        </execution>
    </executions>
</plugin>
  
  
