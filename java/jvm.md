---

#### 启动tomcat时开启gc日志
在catalina.sh里添加如下变量即可
`JAVA_OPTS='-verbose:gc -Xloggc:/home/ubuntu/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps'` 
