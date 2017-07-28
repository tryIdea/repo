---

#### 启动tomcat时开启gc日志
在catalina.sh里添加如下变量即可
`JAVA_OPTS='-verbose:gc -Xloggc:/home/ubuntu/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps'` 

在系统层面能够影响应用性能的一般包括三个因素：CPU、内存和IO，可以从这三方面进行程序的性能瓶颈分析。

频繁GC -> Stop the world，使你的应用响应变慢
OOM，直接造成内存溢出错误使得程序退出。OOM又可以分为以下几种：
Heap space：堆内存不足
PermGen space：永久代内存不足
Native thread：本地线程没有足够内存可分配
查看jvm内存使用状况：jmap -heap
查看jvm内存存活的对象：jmap -histo:live
把heap里所有对象都dump下来，无论对象是死是活：jmap -dump:format=b,file=xxx.hprof
先做一次full GC，再dump，只包含仍然存活的对象信息：jmap -dump:format=b,live,file=xxx.hprof

