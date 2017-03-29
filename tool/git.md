####打包v3.6.6标签的所有文件：
git archive --format=zip --output v3.6.6.zip v3.6.6

####Linux下查看系统版本号信息的方法
cat /etc/issue

#### Git 中文文件名支持
git config --global core.quotepath false

#### 查看用户名和邮箱地址
git config user.name
git config user.email

#### 修改用户名和邮箱地址
git config --global user.name "username"
git config --global user.email "email"

#### 查看远程仓库信息
git remote show origin

#### 相反命令
git add database/alter.sql 《===》  git reset HEAD database/alter.sql
git stash  <===>  git stash pop stash@{0}
修改pom.xml文件 《===》 git checkout pom.xml

#### 更改默认的编辑器
git config –global core.editor vim

#### 修改最后一次commit的信息
git commit --amend

#### Git diff图形化工具 bcompare配置
git difftool --tool-help
git config --global diff.tool bc3
git config --global difftool.bc3.path "/usr/bin/bcompare"

#### 每次拉取和推送的时候不用每次输入密码的命令行
git config credential.helper osxkeychain sourcetree
