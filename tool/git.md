####打包v3.6.6标签的所有文件：
git archive --format=zip --output v3.6.6.zip v3.6.6

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


