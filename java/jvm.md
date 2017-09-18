---

#### 启动tomcat时开启gc日志
在catalina.sh里添加如下变量即可
`JAVA_OPTS='-verbose:gc -Xloggc:/home/ubuntu/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps'` 

`JAVA_OPTS='-verbose:gc -Xloggc:/home/ubuntu/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=100 -XX:GCLogFileSize=100K '` 

#### JVM中哪些flag可以被jinfo动态修改
java -XX:+PrintFlagsFinal -version|grep manageable


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

#### 频繁GC问题或内存溢出问题定位过程
一、使用jps查看线程ID

二、使用jstat -gc 3331 250 20 查看gc情况，一般比较关注PERM区的情况，查看GC的增长情况。

三、使用jstat -gccause：额外输出上次GC原因

四、使用jmap -dump:format=b,file=heapDump 3331生成堆转储文件

五、使用jhat或者可视化工具（Eclipse Memory Analyzer 、IBM HeapAnalyzer）分析堆情况。

六、结合代码解决内存溢出或泄露问题。

#### 死锁问题定位过程
一、使用jps查看线程ID

二、使用jstack 3331：查看线程情况

