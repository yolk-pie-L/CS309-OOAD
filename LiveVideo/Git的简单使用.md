# Git的简单使用

**李开**



## Git创建本地仓库

选择一个空文件夹，执行一下命令

```bash
git init
```

## Git克隆远程仓库到本地

选择一个空文件夹，执行一下命令

```bash
> git clone https://github.com/yolk-pie-L/CS309-OOAD.git
```

## Git拉取同步并上传

```bash
> git pull origin main
// 拉取远程仓库 main
> git add .
> git commit -m "First commit"
> git push origin main
```

## Git创建本地分支

```bash
> git branch dev
// 创建dev分支
> git branch -a
// 查看所有分支
> git checkout dev
// 切换到分支dev	
```

## Git合并分支

```
> git checkout master   # 切换到master分支
> git merge dev         # 将dev分支合并到master上
```

## Git删除文件

```
> git rm readme.md
```

## Git删除远程分支（慎用）

```
> git push origin --delete dev3
```

## Git常见问题

1. Git checkout 指令报错

   + 请查看当前目录下是否有文件未上传，如果有请将其Push到远程分支，再进行切换；

2. Git 本地分支无法使用

   + 请确保你的本地分支跟随一个确定的远程分支，请不要随意在远程仓库中创建远程分支
