https://github.com/liaohuqiu/wechat-helper
需要知道：git  ssh 


#CentOS下搭建git服务器

添加git用户，Centos 没有任何交互动作！创建用户完毕后，必须修改密码否则无法登陆
sudo adduser git
sudo passwd git

为git用户添加 sudo 权限
- 修改'/etc/sudoers'
- 增加'git  ALL=(ALL)  ALL' 

切换到git用户
su git
mkdir -p repo/ali/

安装git
sudo yum install git

初始化git仓库
git init --bare cloudEyes.git
显示结果Initialized empty Git repository in /home/git/repo/ali/cloudEyes.git/

获得主机ip
ifconfig   10.63.66.233

访问git服务器的url为：git@10.63.66.233:/home/git/repo/ali/cloudEyes.git


在客户端安装git,tortoisegit


在git客户端克隆仓库
git clone git@10.63.66.233:/home/git/repo/ali/cloudEyes.git


SSH免密码登录

查看ssh是否安装
ps -e | grep ssh

若没有sshd,则安装ssh服务端
sudo yum install openssh-server

切换到除git之外的其他用户下，生成公钥和私钥
ssh-keygen -t rsa

将公钥追加到git用户下~/.ssh/authorized_keys文件中，此时在当前用户下可以无密码登录到git用户
ssh-copy-id -i ~/.ssh/id_rsa.pub git@localhost
ssh git@localhost

收集所有需要登录的用户的公钥，就是他们自己的id_rsa.pub文件，把所有公钥导入到/home/git/.ssh/authorized_keys文件里，一行一个。
vim编辑器中将光标移动的行尾并进行编辑快捷键：shift+A

禁用shell登录
出于安全考虑，创建的git用户不允许登录shell，这可以通过编辑/etc/passwd文件完成。找到类似下面的一行：
git:x:1001:1001:,,,:/home/git:/bin/bash
改为：
git:x:1001:1001:,,,:/home/git:/usr/bin/git-shell



