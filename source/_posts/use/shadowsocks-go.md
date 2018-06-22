---
title: ShadowSocks-安装配置[go 语言版本]

tags: 
    - go
    - ss
    - use    
    - install
    - shadowsocks    
      
categories: use   

date: 2017-12-012 13:00:00
---

&emsp;&emsp;因被喝茶，shadowsocks 作者被迫停更了shadowsocks的python服务器版本，go语言版本开始被很多人使用。但python版本停更并非go版本使用频次增多的主要原因,除了语言切换的限制，更多的是因为go语言中自带高并发的特性，使得其体验较好，于是越来越多的同事都自行搭建go版本自用。
&emsp;&emsp;本文尽可能详细说明shadowsocks的搭建使用细节。
&emsp;&emsp;本博文为综合集，文章较长，已分页，可在以下目录中新开链接窗口使用。前两步为提升网络性能，可直接从第三步开始。
      * 升级Linux系统内核
      * 更改Linux底层TCP协议算法[BBR 加速]
      * 安装go
      * 安装git
      * 安装shadowsocks
      * 配置config.json文件
      * 防火墙开放端口
      * 使用客户端测试链接
&ensp;&ensp;

## 说明
&emsp;&emsp;使用shadowsocks的前置条件。
&emsp;&emsp;首先，必须拥有一台境外服务器，如果不是自己的物理所有，则需租赁VPS服务器。
&emsp;&emsp;所谓VPS服务器，即Virtual Private Server -私有虚拟服务器。
&emsp;&emsp;关于租赁服务器，博主常用服务商为vultr、搬瓦工、cloudcone等，这也是目前广告和稳定性同时做的比较好的几家。
&emsp;&emsp;另随互联网产业不断增长，相关服务需求缺口逐渐呈现，越来越多的云服务商参与其中竞争，相对前面几家，有很多的小服务商也推出了性价比更高的vps服务，主要集中在俄国区，各位可以自由选择。
大厂还有谷歌的云服务器、亚马逊的AWS,微软的Azure，各位均可一试。
&emsp;&emsp;本文搭建环境时VPS系统为centos。

## 服务器选择
&ensp;&ensp;选择VPS服务商时需注意地理位置远近，测速工具检测结果，及其服务器架构，其中注意服务器最好选择Kvm,Xen等宽容度较高的架构方便网络层级的扩展，避免OpenVZ,另外也会有服务器厂家会挂Xen卖OpenVZ,在购买之后也要记得及时检测。
&ensp;&ensp;购买服务器之后还有及时ping 服务器ID，若ping不通则马上发工单换ip,不通服务商换IP的价格不同。
&ensp;&ensp;国内云服务器所售的境外机型不推荐选择，性价比太低。

## 关于云服务
&ensp;&ensp;关于云服务厂商所提供的VPS服务，大多为基于OpenStack的云计算平台开源实现，国内的两大云服务厂商也是基于此，不久前滴滴和腾讯云业务分开后，也自行组建了滴滴云服务平台，也是基于OpenStack,但其刚起步不推荐。  
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

配置前两项后可使用谷歌Tcp bbr算法加速，bbr算法可基于ai智能控制数据包上下行数量，减少国际带宽拥堵。也可以不管以上两项直接从安装go开始。

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
### 在重启设置中设置重启进入第二个子系统

### 重启
在vps服务商提供的console界面中重启系统
```
    reboot
```
重启进入系统时选择第二个内核为4的子系统

### 检测
登陆后查看系统内核：
```
    [root@vultr_host /]# uname -sr
    Linux 4.17.2-693.21.1.el7.x86_64
```

系统内核升级成功



## tcp算法改为BBR
### 编辑系统文件
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
下载解压
```
    cd /usr/local     # golang安装到此路径下
    wget https://storage.googleapis.com/golang/go1.6.3.linux-amd64.tar.gz
    tar -xvf go1.6.3linux-amd64.tar.gz
```
添加环境变量
```
    vim /etc/profile    ->  加入
    
    export GOROOT=/usr/local/go
    export PATH=$PATH:$GOROOT/bin
```
重载
```
    source /etc/profile
```
在 ~ 目录设置自己的GOPATH
```
    vim .bash_profile   ->  添加
    export GOPATH=$HOME/shadowsocks
```
重载
```
    source .bash_profile
```
## 安装git
```
    yum install git
```

## 安装shadowsocks
```
    go get github.com/shadowsocks/shadowsocks-go/cmd/shadowsocks-server
    
    cd shadowsocks/
    ls
        bin pkg src
```
```
    cd bin/
    ./shadowsocks-server -h
```

## 配置config.json文件
在bin目录下
```
    {
        "server":"173.82.235.219",
        "port_password":{
             "7973":"123456",
             "7884":"123456",
             "7987":"123456",
             "7938":"123456",
            "7999":"123456"
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
    firewall-cmd --reload
```

## 开启shadowsocks服务
```
    ./shadowsocks-server > log &
```

## 使用客户端测试链接
在[shadowsocks-windows](https://github.com/shadowsocks/shadowsocks-windows/releases)中下载客户端
配置好相关的内容进行测试，启用系统代理，右键点开帮助->显示日志
查看链接情况
点击[谷歌](https://www.google.com/)测试 