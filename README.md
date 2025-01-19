# First-remote-Tomcat

------------------------------



## (踩坑合集&amp;超详细) 

### 	RE:重启码农的部署人生：从0开始搭建人生的一个Tomcat服务器并部署项目

### 0.前言

​	自己琢磨了两天后，终于是把这一套流程摸索得差不多了，踩了不少坑，掉了不少头发。

​	于是决定写下这篇文档来~~进食后人~~。

### 1.前期准备

​	首先先去阿里云，腾讯云或者什么云弄个服务器，如果你是学生的话大多都是能免费白嫖的。我用的是阿里云（300块代金卷太舒服了）。配置不需要多高，**2核2G/4G**足矣。

​	预装系统建议选择**CentOS**，毕竟你折腾这东西就是为了以后折腾更大的服务器，CentOS就是为了服务器而生的系统,虽然你目前体会不出来区别就是了。

### 2.环境准备

​	服务器启动后，首先，输入`java -version`看有没有java安装,大概率是没有的。

​	如没有，先新建文件夹`  mkdir -p /usr/local/java` 然后转到这个目录。

​	然后到[Java官网](https://www.oracle.com/cn/java/technologies/downloads/)下一个你觉得适合的jdk，预装系统大概率是没有预装JDK的（根据你的系统选择相应版本：CentOS就x64 Compressed Archive即可）。

​	两种方法：

​		法1：wget 下载链接 直接下载到这个目录（服务器会限速，有点慢）。

​		法2：自己下载上传（略快） 。

​	**注意，如果你用的是tomcat11的话最低要JDK11,注意版本！（坑1）**

​		~~可以直接上JDK23~~

​	下载好后执行解压`tar -zxvf 文件名.tar.gz`。

​	接着更改环境变量:` vim /etc/profile`进入文本编辑器编辑。

​	在其中加入:

`# java`

`export JAVA_HOME=/usr/local/java/jdk23`

`export PATH=$PATH:$JAVA_HOME/bin`	

`export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar`

`jdk23`处根据你的版本更改：

​	`source /etc/profile`刷新环境变量。

​	然后执行`java -version`看是否安装成功。

​	正常显示的话就成功安装咯。

### 3.Tomcat安装与配置

​	以tomcat11为例：

​	下载tomcat[官网](https://tomcat.apache.org/download-11.cgi)

​	新建文件夹`mkdir /usr/local/tomcat`

​	然后把下载的文件放进去解压，不再赘述。

​	**接着，在服务器安全组里面允许8080端口！！！！！！！！(坑)**

​	接下来进入tomcat文件夹的bin目录中，执行`./startup.sh`

​	然后执行`netstat -ntlp`查看端口占用情况，看8080端口显示正常：比如**xxxx:8080**。

​	如果报错或者显示::::8080(弄成监听ipv6地址了),就需要更改配置文件 (**其实就算正常显示也要改**) 。

**(1)更改server.xml**

​	打开位于/tomcat11/conf/的server.xml

​	先修改`<Server port="8005" shutdown="SHUTDOWN">`建议把8005改成8006

​	然后找到**Connector**项，改成：

​	`<Connector port="8080" protocol="HTTP/1.1"`

​        `address="0.0.0.0"`

​        `URIEncoding="UTF-8"`

​        `connectionTimeout="20000"`

​        `redirectPort="8443" />`

​	这里是把监听地址改成了所有ipv4地址，需要的话可以把8080改成80，这样访问时就不用写端口了。

​	现在应该可以正常启动和关闭tomcat了,转到bin目录：

​	启动：`./startup.sh`

​	关闭：`./shutdown.sh`

​	如果显示xxxx端口占用就把这个占用你端口的进程杀掉：执行`netstat -ntlp`找到占用的PID，

​	执行`kill -9 <PID>`就行。

**(2)修改catalina.sh**

​	在开头加上：

`export CATALINA_OPTS="-Dcom.sun.management.jmxremote \`

`-Dcom.sun.management.jmxremote.port=1099 \`

`-Dcom.sun.management.jmxremote.ssl=false \`

`-Dcom.sun.management.jmxremote.authenticate=false \`

`-Djava.rmi.server.hostname=你的公网IP"` 

​	这是开启JMX的代码，开启了之后你才可以正常从外网访问。

​	**记得在安全组里把1099端口启用了**

**(3)修改web.xml**<img src="https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/web.xml.png?raw=true" style="zoom: 67%;" />

​	在130行左右的\<servlet>里面加上：

​    `<init-param>`

​      `<param-name>fileEncoding</param-name>`

​      `<param-value>UTF-8</param-value>`

​    `</init-param>`

​	这是防止中文乱码的。

​	**(4)启动**

​	在进入bin目录下执行`./catalina.sh run`**以后启动都要用这个启动**,如果没问题的话，应该就没问题了（~~废话~~）

​	如果遇到1099端口占用导致无法启动or关闭，直接杀掉进程即可。

​	**我提供一个简易的spring项目供测试：Test1，有需要者自取**

​	部署的话没配置idea的话（配置后面再说）就把**war包**扔到webapp就行

### 4.MySQL

​	**1.MySQL安装**

​	这其实没啥讲的，安装：

​	`wget http://repo.mysql.com/mysql57-community-release-el7-8.noarch.rpm`

​	然后：

`	yum -y install mysql-community-server --nogpgcheck`

​	出问题的可以上Mysql官网看你系统内核对应的Mysql版本下载重新安装：

​	`yum clean all`

`	yum makecache`重装前执行这两个

​	如果出错了也可以执行这两个然后再试一次。

​	**2.连接MySQL和处理密码**

​		首先重启一下：

​	`service mysqld restart`

​		然后连接：

`	mysql -u root -p`

​	初始密码执行下面这个看（MySQL8）

​		`	cat /var/log/mysqld.log | grep password`

​	进去之后先改密码：进入

​		`alter user 'root'@'localhost' identified by '密码';`

​	**这里的密码要求至少8位，其中包含，至少有一位大写字母，至少有一位小写字母，至少有一位数字，至少有一位特殊字符，通俗说，就是   大小写字母 、 数字 和 特殊符号 必须同时存在。**

​	要改简单点的话就执行`show variables like 'validate%';`

​	| validate_password.length 是最小密码长度

​	| validate_password.policy  是密码策略，改成0就是无限制，之后再改密码就行

​		**记得在安全组里面允许3306端口！！！这样才能被外部访问！！！**

​		**我同样放了个Test2出来供测试**，记得先把数据库的地址和库名换了即可：库叫test，表叫testuser，有三列，**id是varchar(20)**,name是varchar（20），age是int。

### 5.有用的工具

​	**(1).Screen**

​	很多情况下直接关闭终端或由于网络波动导致断连都会让Tomcat，Mysql断开然后白干，没法保持后台保活，用Screen就能完美解决这个问题。

​	常用命令如下:

​	安装：	`yum install screen`

​	创建窗口:	`screen -S name`就会创建一个叫name的窗口

​	退出到主界面 	按Ctrl + a + d

​	返回方法：

​		列出所有窗口：	`screen -ls`

​		返回指定窗口:	`screen -r ID/name`

​		返回指定窗口，不存在则创建:	`screen -R ID/name`

​	在screen的窗口里运行的进程不会被自动杀掉，可以后台保活。

### 6.实现IDEA部署远程

​	![image-20250119210800264](https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/ex5.png?raw=true)

​	首先，点击编辑配置,并添加一个tomcat远程的模版

​	<img src="https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/ex1.png?raw=true" style="zoom: 50%;" />

​	**URL改成你的公网IP,主机也改成你的公网IP，端口同理.**

​	Tomcat服务器设置中类型选择sftp，然后点击主机的右边那三个点的按钮

​	<img src="https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/ex2.png?raw=true" style="zoom:50%;" />

​	新建一个sftp，然后配置ssh，新建一个ssh配置：

<img src="https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/ex3.png?raw=true" style="zoom: 50%;" />

​	主机是你的公网ip，用户名，密码参考是你的云服务器给的，测试一下连接，没问题即可保存。

​	**然后配置跟路径，写个/就行**	**Web服务器URL就填你公网ip**

​	然后会让你配暂存地址：

<img src="https://github.com/Thanwinde/First-remote-Tomcat/blob/main/Example%20pictures/ex4.png?raw=true" style="zoom:50%;" />

​	**第一个填你tomcat的webapp的地址	第二个和第一个一样就行**

​	**接着在上方部署选项卡里添加你的war包就行**

​	启动后IDEA会自动上传包，**但是不会覆盖旧包，所以需要你手动删除旧包，不然会出错!**

### 7.结尾

​	我暂时想到的就这么多了，后面可能还会补充一些，如果有问题的话欢迎提出！* =^= *

​	





​	



