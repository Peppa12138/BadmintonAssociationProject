# 羽毛球协会管理系统 - Service层详细文档

## 📋 概述

Service层（业务逻辑层）是系统的核心业务处理层，负责实现具体的业务逻辑、数据验证、异常处理和事务协调。本层位于Controller层和DAO层之间，为上层提供高级业务接口，同时协调下层的数据访问操作。

## 🏗️ 架构设计

### 设计原则
- **业务封装**: 将复杂的业务逻辑封装在Service层，保持Controller层的简洁
- **异常处理**: 统一处理DAO层抛出的SQLException，提供友好的返回值
- **事务管理**: 协调多个DAO操作，确保数据一致性
- **依赖注入**: 通过构造函数注入DAO依赖，便于测试和维护

### 层次结构
```
Controller Layer (控制器层)
        ↓
Service Layer (业务逻辑层) ← 当前文档层
        ↓
DAO Layer (数据访问层)
        ↓
Database (数据库层)
```

---

## 🏃‍♂️ PlayerService - 运动员业务服务

### 类描述
负责处理与运动员相关的所有业务逻辑，包括运动员管理、成绩查询等功能。

### 包路径
```java
package com.badmintonassociation.service;
```

### 依赖关系
```java
// 导入依赖
import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.model.MatchResult;
import java.sql.SQLException;
import java.util.List;
```

### 类定义
```java
public class PlayerService
```

### 成员变量
| 变量名 | 类型 | 描述 | 作用 |
|--------|------|------|------|
| `playerDAO` | `PlayerDAO` | 运动员数据访问对象 | 提供数据库操作接口 |

### 构造方法
```java
public PlayerService(PlayerDAO playerDAO)
```
- **参数**: `playerDAO` - 运动员数据访问对象
- **作用**: 通过依赖注入初始化DAO对象

### 核心业务方法

#### 1. 获取所有运动员
```java
public List<Player> getAllPlayers()
```
- **返回值**: `List<Player>` - 运动员列表，失败时返回null
- **功能**: 获取系统中所有运动员信息
- **异常处理**: 捕获SQLException并打印错误信息
- **业务逻辑**: 直接调用DAO层方法，提供统一的异常处理

#### 2. 创建新运动员
```java
public boolean createPlayer(Player player)
```
- **参数**: `player` - 要创建的运动员对象
- **返回值**: `boolean` - 创建成功返回true，失败返回false
- **功能**: 添加新的运动员到系统中
- **业务逻辑**: 
  - 调用DAO层创建方法
  - 统一的异常处理和结果转换

#### 3. 获取运动员最近成绩
```java
public List<MatchResult> getLatestPlayerResults(int playerId, int limit)
```
- **参数**: 
  - `playerId` - 运动员ID
  - `limit` - 返回记录数限制
- **返回值**: `List<MatchResult>` - 成绩列表，失败时返回null
- **功能**: 获取指定运动员的最近比赛成绩
- **业务逻辑**: 
  - 按时间倒序返回最近的比赛成绩
  - 支持限制返回记录数

### 业务特点
- **简单直接**: 主要作为DAO层的业务包装器
- **异常安全**: 所有方法都有完善的异常处理
- **扩展友好**: 预留了扩展方法的空间

### 扩展建议
```java
// 建议添加的方法
public Player getPlayerById(int playerId);          // 根据ID获取运动员
public boolean updatePlayer(Player player);         // 更新运动员信息
public boolean deletePlayer(int playerId);          // 删除运动员
public List<Player> searchPlayersByName(String name); // 按姓名搜索
public boolean validatePlayerData(Player player);   // 验证运动员数据
```

---

## 🏟️ CourtService - 场地业务服务

### 类描述
负责处理与场地相关的业务逻辑，包括场地管理、可用性检查等功能。

### 包路径
```java
package com.badmintonassociation.service;
```

### 依赖关系
```java
// 导入依赖
import com.badmintonassociation.dao.CourtDAO;
import com.badmintonassociation.model.Court;
import java.sql.SQLException;
import java.util.List;
```

### 类定义
```java
public class CourtService
```

### 成员变量
| 变量名 | 类型 | 描述 | 作用 |
|--------|------|------|------|
| `courtDAO` | `CourtDAO` | 场地数据访问对象 | 提供数据库操作接口 |

### 构造方法
```java
public CourtService(CourtDAO courtDAO)
```
- **参数**: `courtDAO` - 场地数据访问对象
- **作用**: 通过依赖注入初始化DAO对象

### 核心业务方法

#### 1. 获取所有场地
```java
public List<Court> getAllCourts()
```
- **返回值**: `List<Court>` - 场地列表，失败时返回null
- **功能**: 获取系统中所有场地信息
- **异常处理**: 捕获SQLException并打印错误信息
- **业务逻辑**: 提供场地信息供预约和比赛安排使用

### 业务特点
- **基础服务**: 提供基本的场地信息查询功能
- **简洁设计**: 当前实现较为简单，专注核心功能
- **扩展预留**: 为后续功能扩展留有空间

### 扩展建议
```java
// 建议添加的方法
public Court getCourtById(int courtId);              // 根据ID获取场地
public boolean addCourt(Court court);                // 添加新场地
public boolean updateCourt(Court court);             // 更新场地信息
public boolean deleteCourt(int courtId);             // 删除场地
public List<Court> getAvailableCourts(Timestamp startTime, Timestamp endTime); // 获取可用场地
public boolean validateCourtData(Court court);       // 验证场地数据
```

---

## 📅 ReservationService - 预约业务服务

### 类描述
负责处理场地预约相关的复杂业务逻辑，包括预约创建、冲突检测、比赛安排等核心功能。是系统中最复杂的业务服务之一。

### 包路径
```java
package com.badmintonassociation.service;
```

### 依赖关系
```java
// 导入依赖
import com.badmintonassociation.dao.ReservationDAO;
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.model.Reservation;
import com.badmintonassociation.model.Match;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
```

### 类定义
```java
public class ReservationService
```

### 成员变量
| 变量名 | 类型 | 描述 | 作用 |
|--------|------|------|------|
| `reservationDAO` | `ReservationDAO` | 预约数据访问对象 | 处理预约相关数据操作 |
| `matchDAO` | `MatchDAO` | 比赛数据访问对象 | 处理比赛相关数据操作 |

### 构造方法
```java
public ReservationService(ReservationDAO reservationDAO, MatchDAO matchDAO)
```
- **参数**: 
  - `reservationDAO` - 预约数据访问对象
  - `matchDAO` - 比赛数据访问对象
- **作用**: 多DAO依赖注入，支持跨实体业务操作

### 核心业务方法

#### 1. 预约场地 ⭐
```java
public boolean reserveCourt(int courtId, Timestamp startTime, Timestamp endTime, Integer playerId, Integer matchId)
```
- **参数**:
  - `courtId` - 场地ID
  - `startTime` - 开始时间
  - `endTime` - 结束时间
  - `playerId` - 运动员ID（个人预约时使用，比赛预约时为null）
  - `matchId` - 比赛ID（比赛预约时使用，个人预约时为null）
- **返回值**: `boolean` - 预约成功返回true
- **功能**: 统一的场地预约接口，支持个人训练和比赛两种场景
- **业务逻辑**:
  1. 检查场地时间段可用性
  2. 创建预约记录
  3. 确保playerId和matchId至少一个不为null

#### 2. 安排比赛 ⭐
```java
public boolean scheduleMatch(int matchId, int courtId, Timestamp startTime, Timestamp endTime)
```
- **参数**:
  - `matchId` - 比赛ID
  - `courtId` - 场地ID
  - `startTime` - 开始时间
  - `endTime` - 结束时间
- **返回值**: `boolean` - 安排成功返回true
- **功能**: 为比赛安排场地并创建比赛记录
- **业务逻辑**:
  1. 创建Match对象
  2. 通过reserveCourt预约场地
  3. 创建比赛记录

#### 3. 可用性检查方法

##### 检查开始时间可用性
```java
public boolean isStartTimeAvailable(int courtId, Timestamp startTime)
```
- **功能**: 检查指定场地在指定开始时间是否可用

##### 检查结束时间可用性
```java
public boolean isEndTimeAvailable(int courtId, Timestamp endTime)
```
- **功能**: 检查指定场地在指定结束时间是否可用

##### 检查时间段可用性
```java
public boolean isCourtAvailable(int courtId, Timestamp startTime, Timestamp endTime)
```
- **功能**: 检查指定场地在整个时间段是否可用

#### 4. 查询和统计方法

##### 获取最近预约记录
```java
public List<Reservation> getRecentReservations(int courtId)
```
- **参数**: `courtId` - 场地ID
- **返回值**: `List<Reservation>` - 最近10条预约记录
- **功能**: 获取指定场地的最近预约历史

##### 检查运动员当日预约限制
```java
public boolean canPlayerReserveOnDate(int playerId, LocalDate date)
```
- **参数**:
  - `playerId` - 运动员ID
  - `date` - 预约日期
- **返回值**: `boolean` - 是否可以预约
- **功能**: 检查运动员当日预约是否超过限制（最多2次）
- **业务规则**: 每个运动员每天最多只能预约2个时间段

### 业务特点
- **复杂业务**: 处理多种预约场景和业务规则
- **多DAO协调**: 同时操作预约和比赛两个实体
- **冲突检测**: 完善的时间冲突检测机制
- **业务规则**: 实现预约次数限制等业务约束

### 业务规则
1. **时间冲突**: 同一场地同一时间不能重复预约
2. **预约类型**: 支持个人训练预约和比赛预约
3. **次数限制**: 运动员每天最多预约2个时间段
4. **数据一致性**: 预约和比赛数据的一致性维护

---

## 🏆 MatchService - 比赛业务服务

### 类描述
负责处理比赛相关的复杂业务逻辑，包括比赛创建、选手安排、成绩记录等功能。是系统中最复杂的业务服务。

### 包路径
```java
package com.badmintonassociation.service;
```

### 依赖关系
```java
// 导入依赖
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.dao.MatchResultDAO;
import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.dao.PlayerMatchDAO;
import com.badmintonassociation.model.MatchResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import java.util.Random;
```

### 类定义
```java
public class MatchService
```

### 成员变量
| 变量名 | 类型 | 描述 | 作用 |
|--------|------|------|------|
| `matchDAO` | `MatchDAO` | 比赛数据访问对象 | 处理比赛数据操作 |
| `playerDAO` | `PlayerDAO` | 运动员数据访问对象 | 处理运动员数据操作 |
| `playerMatchDAO` | `PlayerMatchDAO` | 运动员比赛关联DAO | 处理参赛关联数据 |
| `matchResultDAO` | `MatchResultDAO` | 比赛成绩DAO | 处理成绩数据操作 |

### 构造方法
```java
public MatchService(MatchDAO matchDAO, PlayerDAO playerDAO, PlayerMatchDAO playerMatchDAO, MatchResultDAO matchResultDAO)
```
- **参数**: 四个DAO对象
- **作用**: 多DAO依赖注入，支持复杂的跨实体业务操作

### 核心业务方法

#### 1. 记录比赛成绩
```java
public boolean recordMatchResult(MatchResult matchResult)
```
- **参数**: `matchResult` - 比赛成绩对象
- **返回值**: `boolean` - 记录成功返回true
- **功能**: 记录单个运动员的比赛成绩
- **业务逻辑**: 
  - 调用DAO层创建成绩记录
  - 统一异常处理

#### 2. 获取比赛成绩
```java
public List<MatchResult> getMatchResults(int matchId)
```
- **参数**: `matchId` - 比赛ID
- **返回值**: `List<MatchResult>` - 比赛成绩列表
- **功能**: 获取指定比赛的所有成绩记录
- **业务逻辑**: 查询指定比赛的完整成绩信息

#### 3. 创建完整比赛 ⭐⭐⭐
```java
public boolean createMatchWithPlayers(LocalDate date, LocalTime startTime, LocalTime endTime)
```
- **参数**:
  - `date` - 比赛日期
  - `startTime` - 开始时间
  - `endTime` - 结束时间
- **返回值**: `boolean` - 创建成功返回true
- **功能**: 创建一场完整的比赛，包括场地安排、选手选择、成绩生成
- **复杂业务逻辑**:

##### 步骤1: 选择空闲场地
```java
int courtId = matchDAO.selectFreeCourt(Time.valueOf(startTime), Time.valueOf(endTime));
```

##### 步骤2: 创建比赛记录
```java
int matchId = matchDAO.createMatch(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), courtId);
```

##### 步骤3: 选择参赛选手
```java
List<Integer> eligiblePlayers = playerDAO.getEligiblePlayers(Time.valueOf(startTime), Time.valueOf(endTime));
if (eligiblePlayers.size() < 10) {
    System.out.println("没有足够的选手参与比赛。");
    return false;
}
List<Integer> selectedPlayers = selectRandomPlayers(eligiblePlayers, 10);
```

##### 步骤4: 建立参赛关联
```java
for (int playerId : selectedPlayers) {
    playerMatchDAO.insertPlayerMatch(matchId, playerId);
}
```

##### 步骤5: 生成比赛成绩
```java
Random rand = new Random();
for (int i = 0; i < selectedPlayers.size(); i++) {
    int playerId = selectedPlayers.get(i);
    int rankId = i + 1;
    int score = rand.nextInt(31); // 0-30分
    boolean recordBroken = score > 25; // 超过25分认为打破记录
    
    matchResultDAO.insertMatchResult(matchId, playerId, rankId, score, recordBroken);
}
```

#### 4. 辅助方法

##### 随机选择运动员
```java
private List<Integer> selectRandomPlayers(List<Integer> eligiblePlayers, int count)
```
- **参数**:
  - `eligiblePlayers` - 符合条件的运动员列表
  - `count` - 需要选择的数量
- **返回值**: `List<Integer>` - 随机选择的运动员ID列表
- **功能**: 从符合条件的运动员中随机选择指定数量的参赛者
- **算法**: 使用Collections.shuffle()进行随机洗牌

### 业务特点
- **高度复杂**: 协调多个实体的复杂业务流程
- **事务性强**: 多个数据操作需要保证一致性
- **自动化程度高**: 自动完成场地分配、选手选择、成绩生成
- **算法集成**: 包含随机选择算法和成绩生成算法

### 业务规则
1. **选手数量**: 每场比赛需要至少10名选手
2. **场地分配**: 自动选择空闲场地
3. **选手筛选**: 只选择在比赛时间段内空闲的选手
4. **成绩生成**: 自动生成排名和分数
5. **记录判定**: 分数超过25分认为打破记录

### 算法设计
- **随机选择算法**: 使用Fisher-Yates洗牌算法保证公平性
- **成绩生成算法**: 随机生成0-30分的成绩
- **排名算法**: 按选择顺序确定排名（可扩展为按成绩排名）

---

## 📊 Service层架构总结

### 类关系图
```
PlayerService
├── PlayerDAO
└── 处理运动员相关业务

CourtService  
├── CourtDAO
└── 处理场地相关业务

ReservationService
├── ReservationDAO
├── MatchDAO
└── 处理预约和比赛安排业务

MatchService
├── MatchDAO
├── PlayerDAO
├── PlayerMatchDAO
├── MatchResultDAO
└── 处理复杂比赛业务流程
```

### 复杂度分析
| Service类 | 复杂度 | DAO依赖数 | 主要功能 |
|----------|--------|-----------|----------|
| `PlayerService` | 🟢 简单 | 1个 | 运动员CRUD操作 |
| `CourtService` | 🟢 简单 | 1个 | 场地CRUD操作 |
| `ReservationService` | 🟡 中等 | 2个 | 预约管理和冲突检测 |
| `MatchService` | 🔴 复杂 | 4个 | 完整比赛流程管理 |

### 设计优势
1. **职责分离**: 每个Service专注特定业务领域
2. **异常封装**: 统一处理SQLException，提供友好接口
3. **事务协调**: 支持跨DAO的复杂业务操作
4. **扩展性**: 预留接口便于功能扩展

### 改进建议
1. **事务管理**: 引入声明式事务管理
2. **数据验证**: 增加业务数据验证逻辑
3. **缓存机制**: 对频繁查询的数据添加缓存
4. **日志记录**: 增加详细的业务操作日志
5. **配置管理**: 将业务规则参数化配置

---

*文档版本: 1.0*  
*最后更新: 2025年7月5日*  
*作者: 羽毛球协会管理系统开发团队*
