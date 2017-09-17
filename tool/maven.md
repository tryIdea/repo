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


    ####
    ```
   export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games"
   export MANPATH="/usr/local/man:$MANPATH"
   export JAVA_HOME=/usr/local/bin/jdk1.8.0_65/
   export PATH=$JAVA_HOME/bin:$PATH
   export MAVEN_HOME=/usr/local/apache-maven-3.3.9
   export PATH=$MAVEN_HOME/bin:$PATH
   export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
  ```
