## mysql笔记

#### 启动mysql :
	mysqld --console
#### 关闭mysql :
	mysqladmin -u root shutdown
#### Install the server as a service using this command :
	mysqld [--install  | --install-manual [MySQL]]
	mysqld [--install  | --install-manual [MySQL]]
#### 启动服务停止服务 :
- NET START MySQL
- NET STOP MySQL
#### 移除服务 :
	mysqld --remove
#### Using Option Files :
	[Using Option Files](http://dev.mysql.com/doc/refman/5.6/en/option-files.html)
#### 终极解决方案 :
	mysqld --verbose --help
#### Testing The MySQL Installation :
* mysqlshow
* mysqlshow -u root mysql
* mysqladmin version status proc

#### Connecting to the MySQL Server :
*	mysql
	This command invokes mysql without specifying any connection parameters explicitly:
	Because there are no parameter options, the default values apply:

	- The default host name is localhost. On Unix, this has a special meaning, as described later.

	- The default user name is ODBC on Windows or your Unix login name on Unix.

	- No password is sent if neither -p nor --password is given.

	For mysql, the first nonoption argument is taken as the name of the default database. If there is no such option, mysql does not select a default database.

* 	mysql --host=localhost --user=myname --password=mypass mydb
	mysql -h localhost -u myname -pmypass mydb
*	mysql -uroot -p mydb
#### 查看MySQL的当前用户 :
	SELECT USER();
#### 查看所有用户 :
	SELECT user,host,password FROM mysql.user;

#### 增加新用户 :
	grant ALL PRIVILEGES on *.* to 'envesgo_oa'@'192.168.0.132'
	identified by 'envesgo';

#### 授权任何用户访问数据库 :
	grant ALL PRIVILEGES on *.* to 'envesgo_oa'@'%'identified by 'envesgo';
	flush privileges;

	select user ,password , host from mysql.user;

#### 删除mysql的匿名账户，新添加的用户才起作用
	delete from user where user='';
	flush privileges;

####Handling Connection Errors
*Important*
If you are using multilanguage databases then you must specify the character set in the connection string. If you do not specify the character set, the connection defaults to the latin1 charset. You can specify the character set as part of the connection string, for example:
	MySqlConnection myConnection = new MySqlConnection("server=127.0.0.1;uid=root;" +
    	"pwd=12345;database=test;Charset=latin1;");

- 0: Cannot connect to server.

- 1045: Invalid user name and/or password.


#### ubuntu下启动与停止mysql数据库
- 启动：sudo /etc/init.d/mysql start 
- 停止：sudo /etc/init.d/mysql stop 
- 重启：sudo /etc/init.d/mysql restart

#### 允许ubuntu下mysql远程连接
#####第一步：

vim /etc/mysql/my.cnf找到bind-address = 127.0.0.1
注释掉这行，如：#bind-address = 127.0.0.1

或者改为： bind-address = 0.0.0.0
允许任意IP访问；

或者自己指定一个IP地址。

重启 MySQL：sudo /etc/init.d/mysql restart

#####第二步：

授权用户能进行远程连接

   grant all privileges on *.* to root@"%" identified by "password" with grant option;


   flush privileges;

   第一行命令解释如下，*.*：第一个*代表数据库名；第二个*代表表名。这里的意思是所有数据库里的所有表都授权给用户。root：授予root账号。“%”：表示授权的用户IP可以指定，这里代表任意的IP地址都能访问MySQL数据库。“password”：分配账号对应的密码，这里密码自己替换成你的mysql root帐号密码。

   第二行命令是刷新权限信息，也即是让我们所作的设置马上生效。
wb-dufugang@alibaba-inc.com


#### 显示表的注释，都有哪些字段
show create table t_user\G;
desc t_user;

#### 显示线程数
show processlist;

#### 显示可以show的命令
 show status;

 ####当前数据库的状态
 status;

 ####当前登陆的用户
 select user();

 #### 更改数据库的主键
 Alter table t_kelude_req change id id int(11);#删除自增长
Alter table t_kelude_req drop primary key;#删除主建

##### 新增主键k3_id
ALTER TABLE t_kelude_req add COLUMN k3_id int(11) auto_increment COMMENT '主键', add primary key(k3_id);

##### 删除需求数据
DELETE from t_kelude_req;

#### 删除重复记录
DELETE
FROM
	t_kelude_req
WHERE
	k3_id IN (
		SELECT
			*
		FROM(
			SELECT
				max(k3_id)
			FROM
				t_kelude_req
			GROUP BY
				id, version_id
			HAVING
				count(id) > 1
	) AS temp
);

###############上边的语句错误

DELETE
FROM
	faq_child
WHERE
	id NOT IN (
		SELECT
			id
		FROM
			(
				SELECT
					id
				FROM
					faq_child
				GROUP BY
					child_name
			) AS temp
)

#################有效率的删除重复数据
DELETE t from t_cc_info as t INNER JOIN (SELECT
			max(id) id
		FROM
			t_cc_info
		GROUP BY
			cc_id
		HAVING
			count(*) > 1) as ids on (t.id = ids.id);

#### 获取数据库中所有表的结构和前三条记录
mysqldump -v -uroot -p cloudEyes --where "1=1 limit 3" --lock-all-tables > workorder_limit_3.sql;


####mysql丢失root密码
方法1： 用SET PASSWORD命令

　　mysql -u root

　　mysql> SET PASSWORD FOR 'root'@'localhost' = PASSWORD('newpass');

方法2：用mysqladmin

　　mysqladmin -u root password "newpass"

　　如果root已经设置过密码，采用如下方法

　　mysqladmin -u root password oldpass "newpass"

方法3： 用UPDATE直接编辑user表

　　mysql -u root

　　mysql> use mysql;

　　mysql> UPDATE user SET Password = PASSWORD('newpass') WHERE user = 'root';

　　mysql> FLUSH PRIVILEGES;

在丢失root密码的时候，可以这样

　　mysqld_safe --skip-grant-tables&

　　mysql -u root mysql

　　mysql> UPDATE user SET password=PASSWORD("new password") WHERE user='root';

　　mysql> FLUSH PRIVILEGES;

####查询缓存
RESET QUERY CACHE  清空查询缓存 ？
FLUSH QUERY CACHE
FLUSH TABLES
SET SESSION query_cache_type=off
SELECT SQL_NO_CACHE count(1) from t_work_order;  指定当前语句不缓存
SELECT SQL_NO_CACHE count(*), now() from t_work_order;
count(*)   count(id)  count(1)

####mycli History and Search
ctrl+r

#### 查看sql的查询计划
1. set profiling=1
2. show profiles
3. show profile for [all] query id
【注：这里显示的单位精确至微秒，精确到微秒。1微秒=10的-6次方秒=0.000001秒】

#### 排除mysql内部缓存对于索引调优的影响，首先在当前会话里即时设置全局缓存
- set global query_cache_size=1024*1024*16;     //分配设置缓存大小 
- set global query_cache_type=1;                          //0表示OFF，1表示ON

#### 以下情况无法缓存其记录集
- 查询语句中加了SQL_NO_CACHE参数； 
- 查询语句中含有获得值的函数，包涵自定义函数，如：CURDATE()、GET_LOCK()、RAND()、CONVERT_TZ等；
- 对系统数据库的查询：mysql、information_schema
- 查询语句中使用SESSION级别变量或存储过程中的局部变量；
- 查询语句中使用了LOCK  IN SHARE MODE、FOR UPDATE的语句
- 查询语句中类似SELECT …INTO 导出数据的语句；
- 事务隔离级别为：Serializable情况下，所有查询语句都不能缓存；至于什么是事务隔离级别
- 对临时表的查询操作；
- 存在警告信息的查询语句；
- 不涉及任何表或视图的查询语句；
- 某用户只有列级别权限的查询语句；
对于大数据并发访问，几乎没有什么命中率，所以DBA一般都会关闭缓存

#### 创建唯一索引
CREATE UNIQUE INDEX uk_cc_id ON t_cc_info (cc_id)

#### sql常见问题
- limit分页优化两种方式1、left join 查询  2、将上一页的最大值当成参数作为查询条件 
- 隐式转换
- 函数作用于表字段，索引失效
- MySQL不能利用索引进行混合排序

#### help_topic
SELECT * from mysql.help_topic;

#### 给某一张表添加一个列
alter table app_user add starLevel INT(11) NULL default 6;

#### 给user表的username添加唯一约束
Alter table user add unique(username);

#### 更改app_activity表中digest的字段,允许为空
ALTER TABLE app_activity MODIFY digest VARCHAR(255) null;










