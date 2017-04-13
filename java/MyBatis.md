在MyBatis中有flushCache、useCache这两个配置属性，分为下面几种情况：

（1）当为select语句时：

flushCache默认为false，表示任何时候语句被调用，都不会去清空本地缓存和二级缓存。

useCache默认为true，表示会将本条语句的结果进行二级缓存。

（2）当为insert、update、delete语句时：

flushCache默认为true，表示任何时候语句被调用，都会导致本地缓存和二级缓存被清空。

useCache属性在该情况下没有。

<selectKey resultType="java.lang.Long" order="AFTER" keyProperty="id">
  SELECT LAST_INSERT_ID()
</selectKey>
