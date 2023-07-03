# Snow-Admin
## 1.项目介绍

Snow_Admin是使用SpringBoot开发的后台管理系统，基于Spring Security实现的RBAC权限控制，是记录逐步开发实现所有功能的项目，随着时间后续会扩展整合其他技术栈。

## 2.项目背景

项目来源于想整理自身Java开发的知识，于是萌发独立写一个后台管理系统项目，并记录一步一步开发的过程。

## 3.项目目标

从创建项目开始，整合框架和程序开发，实现包含后端接口和前端页面的完整后台管理系统。
具体的开发步骤，分为文章。

## 4.环境配置

- JDK 1.8
- MySQL 5.7
- Maven 3.5.0

## 5.技术选型

- 核心框架：Spring Boot 2.5.4
- 安全框架：Spring Security
- 持久层框架：SpringData Jpa
- API文档生成：knife4j 2.0.7
- 数据库连接池：druid 1.1.24
- 工具类：hutool 5.3.4
## 6.项目结构

单体结构，随着开发进度进化

## 7.数据库表

## 8.本地部署

1.git clone https://gitee.com/Dcl_Snow/Snow_Admin.git 将项目代码克隆到本地。

2.MySQL数据库中创建数据库snow-admin，然后导入项目sql目录下的snow-admin.sql文件。

3.修改项目配置文件application-dev.yml中的数据库相关配置，启动服务，访问http://localhost:8080/doc.html 即可查看服务接口文档，并进行接口调试。
