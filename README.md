# fastjson fileread

<br>

## 写在前面

<br>

前段时间有幸做了一道fastjson的题目，顺带研究了一下fastjson的利用链。但是苦于网络上有关fastjson的利用脚本很少，所以就萌生了利用空闲时间写一个fastjson的gui版利用脚本。不仅巩固一下我的java学习，也为其他人比赛和研究提供一些方便


<img src="2.png" width="880" height="480">


<br>


目前只支持commons-io 2.x的文件读取链子，适用版本：fastjson <=1.2.80 ,common-io 2.x

建议环境：JDK20
其他没测试过

--------------------------------------------------------
<br>

## 更新日志

### 2025-2-24

* 对GUI进行修改，添加RCE tab。

* 修复了请求代码的规范性。如果有需要可以在src/main/java/com/huarui/coi.java路径下修改请求体。
已经在1.1.0 Realeases修复

### 2025-2-18
* 关于作者打包时忘记写Main-Class属性，导致打包出来没有主类清单这件事。.。。
已经在1.0.1 Releases修复
(下一条链子正在捣鼓)

--------------------------------------------------------

<br>

## 关于test

### fastjson1.2.80e

提供一个可以测试脚本的实例。包含fastjson1.2.80和commons-io 2.7依赖。测试点在ip/json路由下。当然你也可以当作ctf题去做

*请使用jdk1.8来运行本实例*

--------------------------------------------------------

<br>

## 关于漏洞 CVE-2022-25845-In-Spring

<br>

**参考致谢** ：

[luelueking/CVE-2022-25845-In-Spring](https://github.com/luelueking/CVE-2022-25845-In-Spring)

[fastjson1.2.80 in Springtboot新链学习记录](https://xz.aliyun.com/t/16708)

[ph0ebus/CVE-2022-25845-In-Spring](https://github.com/ph0ebus/CVE-2022-25845-In-Spring)

<br>

还在慢慢慢慢地磨蹭，如果你看到这里作者还没做好第二条链子，请速度去拷打作者。
