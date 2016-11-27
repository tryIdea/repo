centos7
=====================================
####sudoers 文件在哪，怎么修改？
- 修改'/etc/sudoers'
- 增加'debugger     ALL=(ALL)       ALL' 

####XAMPP安装路径
XAMPP will be installed to /opt/lampp

####How to set up EPEL repository on CentOS
Set up EPEL on CentOS 7
	sudo yum install epel-release
For CentOS/RHEL 6.*:
	sudo rpm -Uvh http://mirrors.kernel.org/fedora-epel/6/i386/epel-release-6-8.noarch.rpm 
####常用命令
- w：当前登陆用户
- 查看 man readline 中的”Key Bindings”这一节了解 Bash 中默认的组合键
- id：用户/组 ID 信息
- 如果你偏好 vi风格的组合键，可以 set -o vi。
- 回到上一个工作目录： cd -
- stat：文件信息
- file：确定文件类型

####几个特殊的命令
cmatrix    aafire  sl   bb



####Linux把用户加入某个组（不退出当前所属组 同时属于多个组） 
usermod -a -G groupname username 

####查看某用户所属组 
groups username 

####想知道你的系统有几种shell，可以通过以下命令查看：
```
    cat /etc/shells
```

####喜欢的zsh主题
jonathan.zsh-theme
smt.zsh-theme

####设置当前用户使用 zsh
chsh -s /bin/zsh

#### 启动apache2 服务
Linux系统为Ubuntu
- /etc/init.d/apache2 start
- /etc/init.d/apache2 restart
- /etc/init.d/apache2 stop

to be a better man!
