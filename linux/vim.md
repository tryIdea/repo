# vim命令总结

标签（空格分隔）： vim

---

情景一：用户无权限执行 root 命令

    # User privilege specification
    guohl   ALL=(ALL) ALL

上面这个例子中：

 - guohl：允许使用 sudo 的用户名
 - ALL：允许从任何终端（任何机器）使用 sudo
 - (ALL)：允许以任何用户执行 sudo 命令
 - ALL：允许 sudo 权限执行任何命令
 
情景二：vim 编辑后发现忘记使用 sudo
 `:w !sudo tee %`





