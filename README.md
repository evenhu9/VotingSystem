# 用户投票系统 (User Voting System)

这是一个基于 Spring Boot 实现的全功能用户投票系统。它提供了一个完整的 RESTful API 后端和一个简洁的单页应用前端。

## ✨ 功能特性

* **用户认证与角色管理**:
    * 系统包含三种用户角色：管理员 (ADMIN)、普通用户 (USER) 和游客 (VISITOR)。
    * 使用 Spring Security 进行安全的认证和授权管理。
    * 管理员拥有最高权限，可以管理投票、用户和系统设置。
    * 普通用户可以参与投票和分享。
    * 游客只能浏览投票列表。
* **投票管理 (管理员)**:
    * 创建新的投票活动，包含标题、描述、开始和结束时间。
    * 撤销尚未开始的投票。
    * 直接修改投票的票数。
    * 查看所有投票的统计数据。
* **用户投票 (普通用户)**:
    * 浏览所有可用的投票活动。
    * 对在有效期内的投票进行投票。
    * 为防止刷票，每个用户对同一个投票每天只能投一票。
    * 获取投票的分享链接.
* **用户管理 (管理员)**:
    * 查看所有注册用户列表。
    * 禁用或启用用户账户。
* **IP 白名单**:
    * 通过 IP 白名单限制对登录接口的访问，增加系统安全性.
    * 管理员可以查看和添加新的 IP 地址到白名单.
* **自定义注解权限控制**:
    * 使用 AOP 和自定义注解 `@AdminOnly` 优雅地实现管理员权限的验证.
* **前后端分离**:
    * 后端提供 RESTful API.
    * 前端使用原生 JavaScript 和 Tailwind CSS 构建，界面友好.

## 🛠️ 技术栈

* **后端**:
    * Spring Boot 2.7.18
    * Spring Web (MVC)
    * Spring Security
    * Spring Data JPA
    * Spring AOP
    * H2 Database (内存数据库)
    * Lombok
* **前端**:
    * HTML
    * 原生 JavaScript (ES6)
    * Tailwind CSS

## 🚀 快速开始

### 环境要求

* JDK 1.8 或更高版本
* Maven 3.2+

### 运行步骤

1. **克隆或下载项目**
   ```bash
   git clone <repository-url>
   cd votingsystem
   ```
2. **使用 Maven 运行**
   项目将通过 Maven 自动下载所有依赖并启动。
   ```bash
   mvn spring-boot:run
   ```
3. **访问应用**
   应用启动后，运行在 `8081` 端口。
    * **前端页面**: 打开浏览器访问 `http://localhost:8081`
    * **H2 数据库控制台**: 访问 `http://localhost:8081/h2-console`
        * JDBC URL: `jdbc:h2:mem:testdb`
        * Username: `sa`
        * Password: `password`

### 默认用户

项目在启动时会自动创建以下用户。

* **管理员**:
    * 用户名: `admin`
* **普通用户**:
    * 用户名: `user`
* **游客**:
    * 用户名: `visitor`

## 📦 API 端点

以下是系统的主要 API 接口。

### 公共接口

* `GET /api/votes`: 获取所有投票列表。

### 用户接口 ( 需要权限 `ROLE_USER`)

* `POST /api/votes/{id}/vote`: 对指定 ID 的投票进行投票。
* `GET /api/votes/{id}/share`: 获取指定投票的分享链接。
* `GET /api/users/me`: 获取当前登录用户的信息。

### 管理员接口 ( 需要权限 `ROLE_ADMIN`)

#### 投票管理

* `POST /api/admin/votes`: 创建一个新的投票。
* `DELETE /api/admin/votes/{id}`: 撤销一个投票。
* `PUT /api/admin/votes/{id}/count`: 更新投票的票数。
* `GET /api/admin/votes/stats`: 获取投票统计数据。

#### 用户管理

* `GET /api/admin/users`: 获取所有用户列表。
* `PUT /api/admin/users/{id}/disable`: 禁用一个用户。
* `PUT /api/admin/users/{id}/enable`: 启用一个用户。

#### IP 白名单管理

* `GET /api/admin/ip-whitelist`: 查看所有白名单 IP。
* `POST /api/admin/ip-whitelist`: 添加一个新的 IP 到白名单。

## 📝 项目结构

```
votingsystem
│
├── src
│   ├── main
│   │   ├── java/com/example/votingsystem
│   │   │   ├── aop            # AOP 切面，用于权限检查
│   │   │   ├── config         # Spring Security 配置
│   │   │   ├── controller     # API 控制器
│   │   │   ├── dto            # 数据传输对象
│   │   │   ├── entity         # JPA 实体类
│   │   │   ├── filter         # Servlet 过滤器 (IP白名单)
│   │   │   ├── repository     # Spring Data JPA 仓库
│   │   │   └── service        # 业务逻辑服务
│   │   │
│   │   └── resources
│   │       ├── static/index.html   # 前端页面
│   │       ├── application.properties # 配置文件
│   │       └── data.sql           # 初始数据脚本
│   │
│   └── test                   # 测试代码
│
├── .gitignore
├── pom.xml                    # Maven 配置文件
└── README.md                  # 本文件
```