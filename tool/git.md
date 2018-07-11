####打包v3.6.6标签的所有文件：
git archive --format=zip --output v3.6.6.zip v3.6.6

#### Git 中文文件名支持
git config --global core.quotepath false

#### 查看用户名和邮箱地址
git config user.name
git config user.email

git config --global --list

git config --list

#### 修改用户名和邮箱地址
git config --global user.name "username"
git config --global user.email "email"

##### 拒绝提交包含混合换行符的文件
git config --global core.safecrlf true

#### 相反命令
git add database/alter.sql 《===》  git reset HEAD database/alter.sql
git stash  <===>  git stash pop stash@{0}
修改pom.xml文件 《===》 git checkout pom.xml

#### 更改默认的编辑器
git config –global core.editor vim

#### 修改最后一次commit的信息
git commit --amend

### 更新前一个commit的内容，保留提交日志不变
git commit --amend --no-edit

#### Git diff图形化工具 bcompare配置
git difftool --tool-help  //查看可用的difftool
git config --global diff.tool bc3
git config --global difftool.bc3.path "/usr/bin/bcompare"

#### KDiff3作为merge tool和diff tool
git config --global --add merge.tool kdiff3
git config --global --add mergetool.kdiff3.path "/C/Program Files/KDiff3/kdiff3.exe"
git config --global --add mergetool.kdiff3.trustExitCode false

git config --global --add diff.guitool kdiff3
git config --global --add difftool.kdiff3.path "/C/Program Files/KDiff3/kdiff3.exe"
git config --global --add difftool.kdiff3.trustExitCode false

#### 每次拉取和推送的时候不用每次输入密码的命令行
git config credential.helper osxkeychain sourcetree

#### git-commit-template
curl https://gist.githubusercontent.com/yulijia/fe2522fe138b6ed41ff4/raw/5fa0007d1863f70cf4631f2dc1513c8676cd4ab8/.git-commit-template.txt >> ~/.git-commit-template.txt
git config --global commit.template ~/.git-commit-template.txt

#### 将已有github项目http换成ssh
ssh-keygen -t rsa -C "wb-dufugang@alibaba-inc.com"
1. git remote rm origin
2. git remote add origin git@gitlab.alibaba-inc.com:yunque-group/yunque.git
3. git push origin
ssh -T git@gitlab.alibaba-inc.com
git remote show origin

#### 分支相关
git branch //查看分支状况
git branch -h //查看其他的命令
git checkout develop-branch //用来切换分支
git log //查看历史
git stash //用来临时存放暂不打算提交的文件
git tag //为代码的历史记录某个点打个标签，一般用来标记发布的版本。
git cherry-pick //用于将另一个分支的某次提交代码，合并到当前分支。
git rebase //用于将另一个分支的最新代码，全量合并到当前分支。
git fetch //获取远程仓库的代码
git pull //相当于git fetch + git merge
git push //常会用到的命令，用于将本地的文件推送到服务器上

#### git仓库迁移
git clone --bare git@10.63.64.10:/home/git/repo/alibaba/cloudEyes.git
git push --mirror http://gitlab.alibaba-inc.com/yunque-group/yunque.git

#### git撤销已经push到远端的commit
git reset --hard <版本号>
git push origin <分支名> --force
