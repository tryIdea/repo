# vim命令总结

标签（空格分隔）： vim

---

####  情景一：用户无权限执行 root 命令
    # User privilege specification
    guohl   ALL=(ALL) ALL

上面这个例子中：

 - guohl：允许使用 sudo 的用户名
 - ALL：允许从任何终端（任何机器）使用 sudo
 - (ALL)：允许以任何用户执行 sudo 命令
 - ALL：允许 sudo 权限执行任何命令
 
#### 情景二：vim 编辑后发现忘记使用 sudo
 `:w !sudo tee %`

#### vim useful options
`set backspace=indent,eol,start` 
Without this, Vim restricts you from backspacing over autoindents, line breaks, and insert starts

`set showcmd` 
Displays partial Normal Mode commands as you type them

`set formatoptions+=j` 
Delete comment character when joining commented lines

`set display=lastline` 
Show as much of the last line as possible when wrapping

`set autoindent` 
Copy indent from current line when starting a new line

`set ignorecase` 
Ignore case when searching

`set smartcase` 
Ignore case UNLESS the search string has upper-case characters

`set undofile` 
Save a persistent undo history for every file

`set listchars=trail:_,tab:>-,extends:>,precedes:<,nbsp:¬` 
Without this, Vim restricts you from backspacing over autoindents, line breaks, and insert starts



