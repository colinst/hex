---
title: ShadowSocks-从入门到出门[go版本]
copyright: true  
tags: 
    - go
    - ss
    - use    
    - install
    - shadowsocks    
      
categories: use   

date: 2017-12-012 13:00:00
---

&emsp;&emsp;因被喝茶，shadowsocks 作者被迫停更了shadowsocks的python服务器版本，
go语言版本开始被很多人使用。但python版本停更并非go版本使用频次增多的主要原因,
除了语言切换的限制，更多的是因为go语言中自带高并发的特性，使得其体验较好，
于是越来越多的同事都自行搭建go版本自用。  
本文尽可能详细说明shadowsocks的搭建使用细节。
<!-- more -->

## 目录
 

<!-- TOC -->

- [目录](#目录)
- [说明](#说明)
- [服务器选择](#服务器选择)
- [关于云服务](#关于云服务)
- [开始](#开始)
- [Linux内核升级-> linux4](#linux内核升级--linux4)
    - [检查当前系统内核版本：](#检查当前系统内核版本)
    - [检查当前tcp算法](#检查当前tcp算法)
    - [注意](#注意)
    - [使用ELRepo仓库：](#使用elrepo仓库)
    - [列出目前可用的Linux内核包：](#列出目前可用的linux内核包)
    - [安装最新主线稳定内核](#安装最新主线稳定内核)
    - [修改默认启动内核](#修改默认启动内核)
        - [cnetos6](#cnetos6)
        - [centos7](#centos7)
    - [重启](#重启)
    - [检测](#检测)
- [tcp算法改为BBR](#tcp算法改为bbr)
    - [配置生效](#配置生效)
    - [检查](#检查)
- [安装go](#安装go)
- [安装git](#安装git)
- [安装shadowsocks](#安装shadowsocks)
- [配置config.json文件](#配置configjson文件)
- [开放端口](#开放端口)
- [开启shadowsocks服务](#开启shadowsocks服务)
- [使用客户端测试链接](#使用客户端测试链接)
- [备注](#备注)
    - [重置密码](#重置密码)

<!-- /TOC -->


## 说明
&emsp;&emsp;使用shadowsocks的前置条件。  
&emsp;&emsp;首先，必须拥有一台境外服务器，如果不是自己的物理所有，则需租赁VPS服务器。  
&emsp;&emsp;所谓VPS服务器，即Virtual Private Server -私有虚拟服务器。  
&emsp;&emsp;关于租赁服务器，博主常用服务商为vultr、搬瓦工、cloudcone等，
这也是目前广告和稳定性同时做的比较好的几家。  
&emsp;&emsp;另随互联网产业不断增长，相关服务需求缺口逐渐呈现，越来越多的云服务商参与其中竞争，相对前面几家，有很多的小服务商也推出了性价比更高的vps服务，主要集中在俄国区，各位可以自由选择。
大厂还有谷歌的云服务器、亚马逊的AWS,微软的Azure，各位均可一试。  
&emsp;&emsp;本文搭建环境时VPS系统为centos。  

## 服务器选择
&ensp;&ensp;选择VPS服务商时需注意地理位置远近，测速工具检测结果，及其服务器架构，
其中注意服务器最好选择Kvm,Xen等宽容度较高的架构方便网络层级的扩展，避免OpenVZ,
另外也会有服务器厂家会挂Xen卖OpenVZ,在购买之后也要记得及时检测。  
&ensp;&ensp;购买服务器之后还有及时ping 服务器ID，若ping不通则马上发工单换ip,
不同服务商换IP的价格不同。  
&ensp;&ensp;国内云服务器所售的境外机型不推荐选择，性价比太低。  

## 关于云服务
&ensp;&ensp;关于云服务厂商所提供的VPS服务，大多为基于OpenStack的云计算平台开源实现，
国内的两大云服务厂商也是基于此，不久前滴滴和腾讯云业务分开后，也自行组建了滴滴云服务平台，也是基于OpenStack,但其刚起步不推荐。  
OpenStack相关的云计算实现，国内学习资源不多，各位可自行搜索，主栈语言为Python。  
此处提供OpenStack官方地址：  
&ensp;&ensp;[OpenStack 官网](https://www.openstack.org/)  
&ensp;&ensp;[OpenStack github主页](https://github.com/openstack)  


## 开始
现在你已拥有了基于Xen架构的虚拟机(可ping通)，并已使用root账户登陆拥有管理员权限，接下来的步骤为：
    * 升级Linux系统内核  
    * 更改Linux底层TCP协议算法[BBR 加速]  
    * 安装go  
    * 安装git  
    * 安装shadowsocks  
    * 配置config.json文件  
    * 防火墙开放端口  
    * 使用客户端测试链接  

配置前两项后可使用谷歌Tcp bbr算法加速，bbr算法可基于ai智能控制数据包上下行数量，
减少国际带宽拥堵。  
前两步为提升网络性能，可直接从第三步开始。

## Linux内核升级-> linux4
###检查当前系统内核版本：
```
    [root@vultr_host /]# uname -sr
    Linux 3.10.0-693.21.1.el7.x86_64
```
###检查当前tcp算法
```
[root@vultr_host /]# sysctl net.ipv4.tcp_available_congestion_control
net.ipv4.tcp_available_congestion_control = cubic reno
```
此时系统内核使用的tcp算法内核为cubic/reno

### 注意
在centos中升级内核需要注意：
多数linux发行版提供了使用 yum 的包管理系统以及官方支持的内核升级方法，但这所有的仅仅为其仓库下最新可用内核，非
[linux 最新版本](https://www.kernel.org)。
而且，在centos中也无法使用red hat。

但是，CentOS可以使用ELRepo!
ELRepo是一个第三方仓库，可以将内核升级到最新版本。
	
###使用ELRepo仓库：
```
    [root@vultr_host /]# rpm --import http://www.elrepo.org/RPM-GPG-KEY-elrepo.org
    [root@vultr_host /]# rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm
    Retrieving http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm
    Retrieving http://elrepo.org/elrepo-release-7.0-3.el7.elrepo.noarch.rpm
    Preparing...                          ################################# [100%]
    Updating / installing...
       1:elrepo-release-7.0-3.el7.elrepo  ################################# [100%]
```

### 列出目前可用的Linux内核包：
```
    [root@vultr_host /]# yum --disablerepo="*" --enablerepo="elrepo-kernel" list available
    
    Loaded plugins: fastestmirror
    elrepo-kernel                                                                                  | 2.9 kB  00:00:00
    elrepo-kernel/primary_db                                                                       | 1.7 MB  00:00:01
    Determining fastest mirrors
     * elrepo-kernel: mirrors.thzhost.com
    Available Packages
    kernel-lt.x86_64                                           4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-devel.x86_64                                     4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-doc.noarch                                       4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-headers.x86_64                                   4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-tools.x86_64                                     4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-tools-libs.x86_64                                4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-lt-tools-libs-devel.x86_64                          4.4.138-1.el7.elrepo                          elrepo-kernel
    kernel-ml.x86_64                                           4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-devel.x86_64                                     4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-doc.noarch                                       4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-headers.x86_64                                   4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-tools.x86_64                                     4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-tools-libs.x86_64                                4.17.2-1.el7.elrepo                           elrepo-kernel
    kernel-ml-tools-libs-devel.x86_64                          4.17.2-1.el7.elrepo                           elrepo-kernel
    perf.x86_64                                                4.17.2-1.el7.elrepo                           elrepo-kernel
    python-perf.x86_64                                         4.17.2-1.el7.elrepo                           elrepo-kernel
```

### 安装最新主线稳定内核
```
    [root@vultr_host /]# yum --enablerepo=elrepo-kernel install kernel-ml
    
    Loaded plugins: fastestmirror
    base                                                                                           | 3.6 kB  00:00:00
    elrepo                                                                                         | 2.9 kB  00:00:00
    epel/x86_64/metalink                                                                           |  19 kB  00:00:00
    epel                                                                                           | 3.2 kB  00:00:00
    https://epel.mirror.constant.com/7/x86_64/repodata/repomd.xml: [Errno -1] repomd.xml does not match metalink for epel
    Trying other mirror.
    epel                                                                                           | 3.2 kB  00:00:00
    extras                                                                                         | 3.4 kB  00:00:00
    updates                                                                                        | 3.4 kB  00:00:00
    (1/8): base/7/x86_64/group_gz                                                                  | 166 kB  00:00:01
    (2/8): epel/x86_64/group_gz                                                                    |  88 kB  00:00:01
    (3/8): elrepo/primary_db                                                                       | 592 kB  00:00:01
    (4/8): extras/7/x86_64/primary_db                                                              | 149 kB  00:00:00
    epel/x86_64/primary            FAILED
    https://sjc.edge.kernel.org/fedora-buffet/epel/7/x86_64/repodata/e8475f9ad0cd11429e1cf75752a6dd6fe2b5357a523232eff56af83454c9a3cd-primary.xml.gz: [Errno 14] HTTPS Error 404 - Not Found
    Trying other mirror.
    To address this issue please refer to the below wiki article
    
    https://wiki.centos.org/yum-errors
    
    If above article doesn't help to resolve this issue please use https://bugs.centos.org/.
    
    (5/8): epel/x86_64/updateinfo                                                                  | 933 kB  00:00:02
    (6/8): updates/7/x86_64/primary_db                                                             | 2.7 MB  00:00:02
    (7/8): epel/x86_64/primary                                                                     | 3.5 MB  00:00:05
    (8/8): base/7/x86_64/primary_db                                                                | 5.9 MB  00:02:04
    Loading mirror speeds from cached hostfile
     * base: mirror.fileplanet.com
     * elrepo: mirrors.thzhost.com
     * elrepo-kernel: mirrors.thzhost.com
     * epel: mirror.sjc02.svwh.net
     * extras: mirror.fileplanet.com
     * updates: mirror.fileplanet.com
    epel                                                                                                      12585/12585
    Resolving Dependencies
    --> Running transaction check
    ---> Package kernel-ml.x86_64 0:4.17.2-1.el7.elrepo will be installed
    --> Finished Dependency Resolution
    
    Dependencies Resolved
    
    ======================================================================================================================
     Package                  Arch                  Version                            Repository                    Size
    ======================================================================================================================
    Installing:
     kernel-ml                x86_64                4.17.2-1.el7.elrepo                elrepo-kernel                 45 M
    
    Transaction Summary
    ======================================================================================================================
    Install  1 Package
    
    Total download size: 45 M
    Installed size: 201 M
    Is this ok [y/d/N]: y                       // Yes
    
    Downloading packages:
    kernel-ml-4.17.2-1.el7.elrepo.x86_64.rpm                                                       |  45 MB  00:00:06
    Running transaction check
    Running transaction test
    Transaction test succeeded
    Running transaction
    Warning: RPMDB altered outside of yum.
      Installing : kernel-ml-4.17.2-1.el7.elrepo.x86_64                                                               1/1
      Verifying  : kernel-ml-4.17.2-1.el7.elrepo.x86_64                                                               1/1
    
    Installed:
      kernel-ml.x86_64 0:4.17.2-1.el7.elrepo
    
    Complete!
```
### 修改默认启动内核
#### cnetos6
如果是centos6,直接通过/etc/grub.conf文件修改启动顺序  
使用vi 或者vim命令修改  
```
    vi /boot/grub/grub.conf
    
    -----------------
    
    #boot=/dev/sda
    default=1
    timeout=5
    ...
```
其中default默认为0，将其修改为1即可  
#### centos7
centos 7中是通过grub2引导启动顺序。需要执行相关命令。  
1.查看当前系统所有内核
```
    [root@colinvps /]# cat boot/grub2/grub.cfg |grep menuentry
    if [ x"${feature_menuentry_id}" = xy ]; then
      menuentry_id_option="--id"
      menuentry_id_option=""
    export menuentry_id_option
    menuentry 'CentOS Linux 7 Rescue cf9fbca3fb7842b4915d9cd81c418fb1 (4.17.3-1.el7.elrepo.x86_64)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux (4.17.3-1.el7.elrepo.x86_64) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux 7 Rescue 5491904015ae6a4d828c6acbbfc755b1 (3.10.0-862.3.3.el7.x86_64)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux (3.10.0-862.3.3.el7.x86_64) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux (3.10.0-123.4.2.el7.x86_64) 7 (Core)' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux, with Linux 3.10.0-123.el7.x86_64' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-3.10.0-123.el7.x86_64-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    menuentry 'CentOS Linux, with Linux 0-rescue-11264912be38456483e63dfd21d402f4' --class centos --class gnu-linux --class gnu --class os --unrestricted $menuentry_id_option 'gnulinux-0-rescue-11264912be38456483e63dfd21d402f4-advanced-fe0109f2-6f34-48ae-b51e-1f5fa78305b5' {
    [root@colinvps /]#
```
2.设置默认启动内核
```
    grub2-set-default "CentOS Linux (4.17.3-1.el7.elrepo.x86_64) 7 (Core)"
```
3.检查结果
```
    [root@colinvps /]# grub2-editenv list
    saved_entry=CentOS Linux (4.17.3-1.el7.elrepo.x86_64) 7 (Core)
```
设置成功

### 重启
```
    reboot
```
如果在之上修改默认启动内核没有成功，可在VPS服务提供的网页console 界面下使用reboot命令重启  
在网页端口重启可通过方向键选择将进入的系统内核，类似PC下的bios选择系统  
一般选择第二个，主要选择对应安装号码的内核就好，“(4.17.3-1.el7.elrepo.x86_64) 7 (Core)”  

### 检测
登陆后查看系统内核：  
```
    [root@vultr_host /]# uname -sr
    Linux 4.17.2-693.21.1.el7.x86_64
```

系统内核升级成功



## tcp算法改为BBR
编辑系统文件
```
    vi /etc/sysctl.conf
```

在该文件中加入以下两句
```
    net.core.default_qdisc = fq
    net.ipv4.tcp_congestion_control = bbr
```

添加结果
```
    [root@vultr_host /]# cat /etc/sysctl.conf
    # sysctl settings are defined through files in
    # /usr/lib/sysctl.d/, /run/sysctl.d/, and /etc/sysctl.d/.
    #
    # Vendors settings live in /usr/lib/sysctl.d/.
    # To override a whole file, create a new file with the same in
    # /etc/sysctl.d/ and put new settings there. To override
    # only specific settings, add a file with a lexically later
    # name in /etc/sysctl.d/ and put new settings there.
    #
    # For more information, see sysctl.conf(5) and sysctl.d(5).
    
    # Accept IPv6 advertisements when forwarding is enabled
    net.ipv6.conf.all.accept_ra = 2
    net.ipv6.conf.eth0.accept_ra = 2
    net.core.default_qdisc = fq
    net.ipv4.tcp_congestion_control = bbr
```

### 配置生效
```
    [root@vultr_host /]# sysctl -p
    net.ipv6.conf.all.accept_ra = 2
    net.ipv6.conf.eth0.accept_ra = 2
    net.core.default_qdisc = fq
    net.ipv4.tcp_congestion_control = bbr
```

### 检查
```
    [root@vultr_host /]# sysctl net.ipv4.tcp_available_congestion_control
    net.ipv4.tcp_available_congestion_control = reno cubic bbr
```
结果带有bbr  

成功使用BBR算法
```    
    [root@vultr_host /]# lsmod|grep bbr
    tcp_bbr                20480  1
```



## 安装go
下载
```
    [root@colinvps /]# cd /usr/local
    [root@colinvps local]# ls
    bin  etc  games  include  lib  lib64  libexec  sbin  share  src
    [root@colinvps local]# wget https://storage.googleapis.com/golang/go1.6.3.linux-amd64.tar.gz
    --2018-07-03 00:06:49--  https://storage.googleapis.com/golang/go1.6.3.linux-amd64.tar.gz
    Resolving storage.googleapis.com (storage.googleapis.com)... 172.217.14.112, 2607:f8b0:4007:80e::2010
    Connecting to storage.googleapis.com (storage.googleapis.com)|172.217.14.112|:443... connected.
    HTTP request sent, awaiting response... 200 OK
    Length: 84856920 (81M) [application/x-gzip]
    Saving to: ‘go1.6.3.linux-amd64.tar.gz’
    
    100%[===============================================================================================>] 84,856,920  52.5MB/s   in 1.5s
    
    2018-07-03 00:06:51 (52.5 MB/s) - ‘go1.6.3.linux-amd64.tar.gz’ saved [84856920/84856920]
```
解压，这里日志比较长，只给命令
```
    [root@colinvps /]# tar -xvf go1.6.3linux-amd64.tar.gz
```
添加环境变量，加入以下两行
```
    
    [root@colinvps /]# vi /etc/profile
    
    export GOROOT=/usr/local/go
    export PATH=$PATH:$GOROOT/bin
```
重载
```
    [root@colinvps /]# source /etc/profile
```
在 ~ 目录设置自己的GOPATH
```
    [root@colinvps ~]# vi .bash_profile
    export GOPATH=$HOME/shadowsocks
```
重载
```
    [root@colinvps ~]# source .bash_profile
```
## 安装git
```
    [root@colinvps ~]#  yum install git
            
    Loaded plugins: fastestmirror
    Loading mirror speeds from cached hostfile
     * base: mirror.san.fastserv.com
     * elrepo: repos.lax-noc.com
     * extras: mirrors.xmission.com
     * updates: mirror.scalabledns.com
    Resolving Dependencies
    --> Running transaction check
    ---> Package git.x86_64 0:1.8.3.1-14.el7_5 will be installed
    --> Processing Dependency: perl-Git = 1.8.3.1-14.el7_5 for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl >= 5.008 for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: rsync for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(warnings) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(vars) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(strict) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(lib) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(Term::ReadKey) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(Git) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(Getopt::Long) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::stat) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Temp) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Spec) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Path) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Find) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Copy) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(File::Basename) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(Exporter) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: perl(Error) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: /usr/bin/perl for package: git-1.8.3.1-14.el7_5.x86_64
    --> Processing Dependency: libgnome-keyring.so.0()(64bit) for package: git-1.8.3.1-14.el7_5.x86_64
    --> Running transaction check
    ---> Package libgnome-keyring.x86_64 0:3.12.0-1.el7 will be installed
    ---> Package perl.x86_64 4:5.16.3-292.el7 will be installed
    --> Processing Dependency: perl-libs = 4:5.16.3-292.el7 for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Socket) >= 1.3 for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Scalar::Util) >= 1.10 for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl-macros for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl-libs for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(threads::shared) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(threads) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(constant) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Time::Local) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Time::HiRes) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Storable) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Socket) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Scalar::Util) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Pod::Simple::XHTML) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Pod::Simple::Search) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Filter::Util::Call) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: perl(Carp) for package: 4:perl-5.16.3-292.el7.x86_64
    --> Processing Dependency: libperl.so()(64bit) for package: 4:perl-5.16.3-292.el7.x86_64
    ---> Package perl-Error.noarch 1:0.17020-2.el7 will be installed
    ---> Package perl-Exporter.noarch 0:5.68-3.el7 will be installed
    ---> Package perl-File-Path.noarch 0:2.09-2.el7 will be installed
    ---> Package perl-File-Temp.noarch 0:0.23.01-3.el7 will be installed
    ---> Package perl-Getopt-Long.noarch 0:2.40-3.el7 will be installed
    --> Processing Dependency: perl(Pod::Usage) >= 1.14 for package: perl-Getopt-Long-2.40-3.el7.noarch
    --> Processing Dependency: perl(Text::ParseWords) for package: perl-Getopt-Long-2.40-3.el7.noarch
    ---> Package perl-Git.noarch 0:1.8.3.1-14.el7_5 will be installed
    ---> Package perl-PathTools.x86_64 0:3.40-5.el7 will be installed
    ---> Package perl-TermReadKey.x86_64 0:2.30-20.el7 will be installed
    ---> Package rsync.x86_64 0:3.1.2-4.el7 will be installed
    --> Running transaction check
    ---> Package perl-Carp.noarch 0:1.26-244.el7 will be installed
    ---> Package perl-Filter.x86_64 0:1.49-3.el7 will be installed
    ---> Package perl-Pod-Simple.noarch 1:3.28-4.el7 will be installed
    --> Processing Dependency: perl(Pod::Escapes) >= 1.04 for package: 1:perl-Pod-Simple-3.28-4.el7.noarch
    --> Processing Dependency: perl(Encode) for package: 1:perl-Pod-Simple-3.28-4.el7.noarch
    ---> Package perl-Pod-Usage.noarch 0:1.63-3.el7 will be installed
    --> Processing Dependency: perl(Pod::Text) >= 3.15 for package: perl-Pod-Usage-1.63-3.el7.noarch
    --> Processing Dependency: perl-Pod-Perldoc for package: perl-Pod-Usage-1.63-3.el7.noarch
    ---> Package perl-Scalar-List-Utils.x86_64 0:1.27-248.el7 will be installed
    ---> Package perl-Socket.x86_64 0:2.010-4.el7 will be installed
    ---> Package perl-Storable.x86_64 0:2.45-3.el7 will be installed
    ---> Package perl-Text-ParseWords.noarch 0:3.29-4.el7 will be installed
    ---> Package perl-Time-HiRes.x86_64 4:1.9725-3.el7 will be installed
    ---> Package perl-Time-Local.noarch 0:1.2300-2.el7 will be installed
    ---> Package perl-constant.noarch 0:1.27-2.el7 will be installed
    ---> Package perl-libs.x86_64 4:5.16.3-292.el7 will be installed
    ---> Package perl-macros.x86_64 4:5.16.3-292.el7 will be installed
    ---> Package perl-threads.x86_64 0:1.87-4.el7 will be installed
    ---> Package perl-threads-shared.x86_64 0:1.43-6.el7 will be installed
    --> Running transaction check
    ---> Package perl-Encode.x86_64 0:2.51-7.el7 will be installed
    ---> Package perl-Pod-Escapes.noarch 1:1.04-292.el7 will be installed
    ---> Package perl-Pod-Perldoc.noarch 0:3.20-4.el7 will be installed
    --> Processing Dependency: perl(parent) for package: perl-Pod-Perldoc-3.20-4.el7.noarch
    --> Processing Dependency: perl(HTTP::Tiny) for package: perl-Pod-Perldoc-3.20-4.el7.noarch
    ---> Package perl-podlators.noarch 0:2.5.1-3.el7 will be installed
    --> Running transaction check
    ---> Package perl-HTTP-Tiny.noarch 0:0.033-3.el7 will be installed
    ---> Package perl-parent.noarch 1:0.225-244.el7 will be installed
    --> Finished Dependency Resolution
    
    Dependencies Resolved
    
    ============================================================================================
     Package                       Arch          Version                   Repository      Size
    ============================================================================================
    Installing:
     git                           x86_64        1.8.3.1-14.el7_5          updates        4.4 M
    Installing for dependencies:
     libgnome-keyring              x86_64        3.12.0-1.el7              base           109 k
     perl                          x86_64        4:5.16.3-292.el7          base           8.0 M
     perl-Carp                     noarch        1.26-244.el7              base            19 k
     perl-Encode                   x86_64        2.51-7.el7                base           1.5 M
     perl-Error                    noarch        1:0.17020-2.el7           base            32 k
     perl-Exporter                 noarch        5.68-3.el7                base            28 k
     perl-File-Path                noarch        2.09-2.el7                base            26 k
     perl-File-Temp                noarch        0.23.01-3.el7             base            56 k
     perl-Filter                   x86_64        1.49-3.el7                base            76 k
     perl-Getopt-Long              noarch        2.40-3.el7                base            56 k
     perl-Git                      noarch        1.8.3.1-14.el7_5          updates         54 k
     perl-HTTP-Tiny                noarch        0.033-3.el7               base            38 k
     perl-PathTools                x86_64        3.40-5.el7                base            82 k
     perl-Pod-Escapes              noarch        1:1.04-292.el7            base            51 k
     perl-Pod-Perldoc              noarch        3.20-4.el7                base            87 k
     perl-Pod-Simple               noarch        1:3.28-4.el7              base           216 k
     perl-Pod-Usage                noarch        1.63-3.el7                base            27 k
     perl-Scalar-List-Utils        x86_64        1.27-248.el7              base            36 k
     perl-Socket                   x86_64        2.010-4.el7               base            49 k
     perl-Storable                 x86_64        2.45-3.el7                base            77 k
     perl-TermReadKey              x86_64        2.30-20.el7               base            31 k
     perl-Text-ParseWords          noarch        3.29-4.el7                base            14 k
     perl-Time-HiRes               x86_64        4:1.9725-3.el7            base            45 k
     perl-Time-Local               noarch        1.2300-2.el7              base            24 k
     perl-constant                 noarch        1.27-2.el7                base            19 k
     perl-libs                     x86_64        4:5.16.3-292.el7          base           688 k
     perl-macros                   x86_64        4:5.16.3-292.el7          base            43 k
     perl-parent                   noarch        1:0.225-244.el7           base            12 k
     perl-podlators                noarch        2.5.1-3.el7               base           112 k
     perl-threads                  x86_64        1.87-4.el7                base            49 k
     perl-threads-shared           x86_64        1.43-6.el7                base            39 k
     rsync                         x86_64        3.1.2-4.el7               base           403 k
    
    Transaction Summary
    ============================================================================================
    Install  1 Package (+32 Dependent packages)
    
    Total download size: 16 M
    Installed size: 60 M
    Is this ok [y/d/N]: y
    Downloading packages:
    (1/33): git-1.8.3.1-14.el7_5.x86_64.rpm                              | 4.4 MB  00:00:00
    (2/33): libgnome-keyring-3.12.0-1.el7.x86_64.rpm                     | 109 kB  00:00:00
    (3/33): perl-Carp-1.26-244.el7.noarch.rpm                            |  19 kB  00:00:00
    (4/33): perl-Encode-2.51-7.el7.x86_64.rpm                            | 1.5 MB  00:00:00
    (5/33): perl-Error-0.17020-2.el7.noarch.rpm                          |  32 kB  00:00:00
    (6/33): perl-Exporter-5.68-3.el7.noarch.rpm                          |  28 kB  00:00:00
    (7/33): perl-File-Path-2.09-2.el7.noarch.rpm                         |  26 kB  00:00:00
    (8/33): perl-File-Temp-0.23.01-3.el7.noarch.rpm                      |  56 kB  00:00:00
    (9/33): perl-5.16.3-292.el7.x86_64.rpm                               | 8.0 MB  00:00:01
    (10/33): perl-Git-1.8.3.1-14.el7_5.noarch.rpm                        |  54 kB  00:00:00
    (11/33): perl-Filter-1.49-3.el7.x86_64.rpm                           |  76 kB  00:00:00
    (12/33): perl-Getopt-Long-2.40-3.el7.noarch.rpm                      |  56 kB  00:00:00
    (13/33): perl-HTTP-Tiny-0.033-3.el7.noarch.rpm                       |  38 kB  00:00:00
    (14/33): perl-Pod-Escapes-1.04-292.el7.noarch.rpm                    |  51 kB  00:00:00
    (15/33): perl-PathTools-3.40-5.el7.x86_64.rpm                        |  82 kB  00:00:00
    (16/33): perl-Pod-Perldoc-3.20-4.el7.noarch.rpm                      |  87 kB  00:00:00
    (17/33): perl-Pod-Usage-1.63-3.el7.noarch.rpm                        |  27 kB  00:00:00
    (18/33): perl-Pod-Simple-3.28-4.el7.noarch.rpm                       | 216 kB  00:00:00
    (19/33): perl-Scalar-List-Utils-1.27-248.el7.x86_64.rpm              |  36 kB  00:00:00
    (20/33): perl-Socket-2.010-4.el7.x86_64.rpm                          |  49 kB  00:00:00
    (21/33): perl-TermReadKey-2.30-20.el7.x86_64.rpm                     |  31 kB  00:00:00
    (22/33): perl-Storable-2.45-3.el7.x86_64.rpm                         |  77 kB  00:00:00
    (23/33): perl-Text-ParseWords-3.29-4.el7.noarch.rpm                  |  14 kB  00:00:00
    (24/33): perl-Time-HiRes-1.9725-3.el7.x86_64.rpm                     |  45 kB  00:00:00
    (25/33): perl-Time-Local-1.2300-2.el7.noarch.rpm                     |  24 kB  00:00:00
    (26/33): perl-constant-1.27-2.el7.noarch.rpm                         |  19 kB  00:00:00
    (27/33): perl-macros-5.16.3-292.el7.x86_64.rpm                       |  43 kB  00:00:00
    (28/33): perl-parent-0.225-244.el7.noarch.rpm                        |  12 kB  00:00:00
    (29/33): perl-libs-5.16.3-292.el7.x86_64.rpm                         | 688 kB  00:00:00
    (30/33): perl-podlators-2.5.1-3.el7.noarch.rpm                       | 112 kB  00:00:00
    (31/33): perl-threads-1.87-4.el7.x86_64.rpm                          |  49 kB  00:00:00
    (32/33): perl-threads-shared-1.43-6.el7.x86_64.rpm                   |  39 kB  00:00:00
    (33/33): rsync-3.1.2-4.el7.x86_64.rpm                                | 403 kB  00:00:00
    --------------------------------------------------------------------------------------------
    Total                                                       4.9 MB/s |  16 MB  00:00:03
    Running transaction check
    Running transaction test
    Transaction test succeeded
    Running transaction
      Installing : 1:perl-parent-0.225-244.el7.noarch                                      1/33
      Installing : perl-HTTP-Tiny-0.033-3.el7.noarch                                       2/33
      Installing : perl-podlators-2.5.1-3.el7.noarch                                       3/33
      Installing : perl-Pod-Perldoc-3.20-4.el7.noarch                                      4/33
      Installing : 1:perl-Pod-Escapes-1.04-292.el7.noarch                                  5/33
      Installing : perl-Text-ParseWords-3.29-4.el7.noarch                                  6/33
      Installing : perl-Encode-2.51-7.el7.x86_64                                           7/33
      Installing : perl-Pod-Usage-1.63-3.el7.noarch                                        8/33
      Installing : 4:perl-macros-5.16.3-292.el7.x86_64                                     9/33
      Installing : 4:perl-libs-5.16.3-292.el7.x86_64                                      10/33
      Installing : 4:perl-Time-HiRes-1.9725-3.el7.x86_64                                  11/33
      Installing : perl-Exporter-5.68-3.el7.noarch                                        12/33
      Installing : perl-constant-1.27-2.el7.noarch                                        13/33
      Installing : perl-Time-Local-1.2300-2.el7.noarch                                    14/33
      Installing : perl-Socket-2.010-4.el7.x86_64                                         15/33
      Installing : perl-Carp-1.26-244.el7.noarch                                          16/33
      Installing : perl-Storable-2.45-3.el7.x86_64                                        17/33
      Installing : perl-PathTools-3.40-5.el7.x86_64                                       18/33
      Installing : perl-Scalar-List-Utils-1.27-248.el7.x86_64                             19/33
      Installing : perl-File-Temp-0.23.01-3.el7.noarch                                    20/33
      Installing : perl-File-Path-2.09-2.el7.noarch                                       21/33
      Installing : perl-threads-shared-1.43-6.el7.x86_64                                  22/33
      Installing : perl-threads-1.87-4.el7.x86_64                                         23/33
      Installing : perl-Filter-1.49-3.el7.x86_64                                          24/33
      Installing : 1:perl-Pod-Simple-3.28-4.el7.noarch                                    25/33
      Installing : perl-Getopt-Long-2.40-3.el7.noarch                                     26/33
      Installing : 4:perl-5.16.3-292.el7.x86_64                                           27/33
      Installing : 1:perl-Error-0.17020-2.el7.noarch                                      28/33
      Installing : perl-TermReadKey-2.30-20.el7.x86_64                                    29/33
      Installing : rsync-3.1.2-4.el7.x86_64                                               30/33
      Installing : libgnome-keyring-3.12.0-1.el7.x86_64                                   31/33
      Installing : perl-Git-1.8.3.1-14.el7_5.noarch                                       32/33
      Installing : git-1.8.3.1-14.el7_5.x86_64                                            33/33
      Verifying  : perl-HTTP-Tiny-0.033-3.el7.noarch                                       1/33
      Verifying  : libgnome-keyring-3.12.0-1.el7.x86_64                                    2/33
      Verifying  : perl-threads-shared-1.43-6.el7.x86_64                                   3/33
      Verifying  : 4:perl-Time-HiRes-1.9725-3.el7.x86_64                                   4/33
      Verifying  : perl-Exporter-5.68-3.el7.noarch                                         5/33
      Verifying  : perl-constant-1.27-2.el7.noarch                                         6/33
      Verifying  : perl-PathTools-3.40-5.el7.x86_64                                        7/33
      Verifying  : 4:perl-macros-5.16.3-292.el7.x86_64                                     8/33
      Verifying  : 1:perl-parent-0.225-244.el7.noarch                                      9/33
      Verifying  : 4:perl-5.16.3-292.el7.x86_64                                           10/33
      Verifying  : perl-TermReadKey-2.30-20.el7.x86_64                                    11/33
      Verifying  : perl-File-Temp-0.23.01-3.el7.noarch                                    12/33
      Verifying  : 1:perl-Pod-Simple-3.28-4.el7.noarch                                    13/33
      Verifying  : perl-Time-Local-1.2300-2.el7.noarch                                    14/33
      Verifying  : 4:perl-libs-5.16.3-292.el7.x86_64                                      15/33
      Verifying  : perl-Socket-2.010-4.el7.x86_64                                         16/33
      Verifying  : git-1.8.3.1-14.el7_5.x86_64                                            17/33
      Verifying  : perl-Carp-1.26-244.el7.noarch                                          18/33
      Verifying  : 1:perl-Error-0.17020-2.el7.noarch                                      19/33
      Verifying  : perl-Storable-2.45-3.el7.x86_64                                        20/33
      Verifying  : perl-Scalar-List-Utils-1.27-248.el7.x86_64                             21/33
      Verifying  : 1:perl-Pod-Escapes-1.04-292.el7.noarch                                 22/33
      Verifying  : rsync-3.1.2-4.el7.x86_64                                               23/33
      Verifying  : perl-Pod-Usage-1.63-3.el7.noarch                                       24/33
      Verifying  : perl-Git-1.8.3.1-14.el7_5.noarch                                       25/33
      Verifying  : perl-Encode-2.51-7.el7.x86_64                                          26/33
      Verifying  : perl-Pod-Perldoc-3.20-4.el7.noarch                                     27/33
      Verifying  : perl-podlators-2.5.1-3.el7.noarch                                      28/33
      Verifying  : perl-File-Path-2.09-2.el7.noarch                                       29/33
      Verifying  : perl-threads-1.87-4.el7.x86_64                                         30/33
      Verifying  : perl-Filter-1.49-3.el7.x86_64                                          31/33
      Verifying  : perl-Getopt-Long-2.40-3.el7.noarch                                     32/33
      Verifying  : perl-Text-ParseWords-3.29-4.el7.noarch                                 33/33
    
    Installed:
      git.x86_64 0:1.8.3.1-14.el7_5
    
    Dependency Installed:
      libgnome-keyring.x86_64 0:3.12.0-1.el7     perl.x86_64 4:5.16.3-292.el7
      perl-Carp.noarch 0:1.26-244.el7            perl-Encode.x86_64 0:2.51-7.el7
      perl-Error.noarch 1:0.17020-2.el7          perl-Exporter.noarch 0:5.68-3.el7
      perl-File-Path.noarch 0:2.09-2.el7         perl-File-Temp.noarch 0:0.23.01-3.el7
      perl-Filter.x86_64 0:1.49-3.el7            perl-Getopt-Long.noarch 0:2.40-3.el7
      perl-Git.noarch 0:1.8.3.1-14.el7_5         perl-HTTP-Tiny.noarch 0:0.033-3.el7
      perl-PathTools.x86_64 0:3.40-5.el7         perl-Pod-Escapes.noarch 1:1.04-292.el7
      perl-Pod-Perldoc.noarch 0:3.20-4.el7       perl-Pod-Simple.noarch 1:3.28-4.el7
      perl-Pod-Usage.noarch 0:1.63-3.el7         perl-Scalar-List-Utils.x86_64 0:1.27-248.el7
      perl-Socket.x86_64 0:2.010-4.el7           perl-Storable.x86_64 0:2.45-3.el7
      perl-TermReadKey.x86_64 0:2.30-20.el7      perl-Text-ParseWords.noarch 0:3.29-4.el7
      perl-Time-HiRes.x86_64 4:1.9725-3.el7      perl-Time-Local.noarch 0:1.2300-2.el7
      perl-constant.noarch 0:1.27-2.el7          perl-libs.x86_64 4:5.16.3-292.el7
      perl-macros.x86_64 4:5.16.3-292.el7        perl-parent.noarch 1:0.225-244.el7
      perl-podlators.noarch 0:2.5.1-3.el7        perl-threads.x86_64 0:1.87-4.el7
      perl-threads-shared.x86_64 0:1.43-6.el7    rsync.x86_64 0:3.1.2-4.el7
    
    Complete!
```

## 安装shadowsocks
```
    [root@colinvps ~]# go get github.com/shadowsocks/shadowsocks-go/cmd/shadowsocks-server
    
    [root@colinvps ~]# cd shadowsocks
    [root@colinvps shadowsocks]# ls
    bin  pkg  src
```
切换到bin目录下，使shadowsocks-server有可执行权限
```
    [root@colinvps shadowsocks]# cd bin
    ./shadowsocks-server -h
    
    [root@colinvps bin]# chmod +x shadowsocks-server
```

## 配置config.json文件
在bin目录下
```
    [root@colinvps bin]# vi config.json

    {
        "server":"127.0.0.1",
        "port_password":{
             "7973":"123456",
             "7884":"abcdef",
             "7987":"1212112",
             "7938":"1445566",
            "7999":"345123"
        },
        "local_address": "127.0.0.1",
        "local_port":1080,
        "timeout":600,
        "method":"aes-128-cfb",
        "fast_open":false,
        "workers":1
    }
```
config文件参数配置：

## 开放端口
将以上config文件的端口添加到防火墙  
这里使用firewall命令  

查看所有打开的端口： 
```
    [root@colinvps /]# firewall-cmd --zone=public --list-ports
    7884/tcp 7987/tcp 7999/tcp 7938/tcp 7973/tcp
```
添加端口
```
    [root@colinvps /]# firewall-cmd --zone=public --add-port=80/tcp --permanent 
    success
    
    [root@colinvps /]# firewall-cmd --zone=public --list-ports
    7884/tcp 7987/tcp 7999/tcp 7938/tcp 7973/tcp  80/tcp
```
载入生效
```
    [root@colinvps /]# firewall-cmd --reload
```

## 开启shadowsocks服务
```
    [root@colinvps /]# ./shadowsocks-server > log &
    [1] 1642
```
已成功开启  
关闭服务
```
    [root@colinvps ~]# ps aux|grep shadowsocks-server
    root      1642  0.1  1.0 187916  5124 pts/0    Sl   02:11   0:08 ./shadowsocks-server
    root      4749  0.0  0.4 112716  2380 pts/0    S+   04:31   0:00 grep --color=auto shadowsocks-server
    
    [root@colinvps ~]# kill -9 4749
```

## 使用客户端测试链接
在[shadowsocks-windows](https://github.com/shadowsocks/shadowsocks-windows/releases)中下载客户端
shadowsocks-windows客户端配置同服务器config.json文件一致就好，可根据不同端口配置不同密码  
配置好相关的内容进行测试，启用系统代理，右键点开帮助->显示日志
查看链接情况  
点击[谷歌](https://www.google.com/)测试   


##备注
###重置密码
VPS重启镜像后重置密码  
```
    [root@colinvps ~]# passwd root
    New password:
    Retype new password:
    passwd: all authentication tokens updated successfully.
```