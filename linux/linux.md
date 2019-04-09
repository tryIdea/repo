
linux
=====================================
#### sudoers 文件在哪，怎么修改？
在'/etc/sudoers' 中增加'debugger     ALL=(ALL)       ALL'

#### How to set up EPEL repository on CentOS
Set up EPEL on CentOS 7 sudo yum install epel-release For CentOS/RHEL 6.*

```
sudo rpm -Uvh http://mirrors.kernel.org/fedora-epel/6/i386/epel-release-6-8.noarch.rpm
```

#### 常用命令
- w：当前登陆用户
- 查看 man readline 中的”Key Bindings”这一节了解 Bash 中默认的组合键
- id：用户/组 ID 信息
- 如果你偏好 vi风格的组合键，可以 set -o vi。
- 回到上一个工作目录： cd -
- stat：文件信息
- file：确定文件类型
- cat /etc/issue : 发行版本信息
- cat /proc/version : 正在运行的内核版本

#### 几个特殊的命令
- cmatrix
- aafire
- sl
- bb

#### Linux把用户加入某个组（不退出当前所属组 同时属于多个组）
usermod -a -G groupname username

#### 查看某用户所属组
groups username

#### 想知道你的系统有几种shell，可以通过以下命令查看：
chsh -l
cat /etc/shells

#### 查看当前正在使用的shell：
- ps-p $$
- echo $0

#### 设置当前用户使用 zsh
chsh -s /bin/zsh。 #重启shell后生效

#### 启动apache2 服务
Linux系统为Ubuntu
- /etc/init.d/apache2 start
- /etc/init.d/apache2 restart
- /etc/init.d/apache2 stop

#### 后台执行任务，即使关闭窗口

```
nohup sh importCcInfo.sh > /usr/local/apache-tomcat-7.0.63/logs/nohup.out 2>& 1 &

nohup command >/dev/null 2>&1 &
```

#### 查看命令行快捷键
man readline

#### 查看ascii码表
man ascii

#### 快速定位文件
1. updatedb
1. locate

#### 查看端口占用,端口是否开放
1. lsof -i:8088
1. lsof -i tcp:8080
1. start /min telnet 119.75.216.20 80

#### 追加环境变量
echo "export PATH=$PATH:/root" >> /etc/rc.local

#### 获取字符串的MD5值
echo -n '-KavlU'|md5sum|cut -d ' ' -f 1

#### 查看指定时间段的日志

```
sed -n '/2017-04-27 07:[0-9][0-9]:[0-9][0-9]/,/2017-04-27 08:[0-9][0-9]:[0-9][0-9]/p' catalina.out
```
