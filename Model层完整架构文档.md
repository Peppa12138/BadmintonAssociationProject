# 羽毛球协会管理系统 - Model层完整文档

## 📋 概述

本文档详细介绍了羽毛球协会管理系统Model层的所有实体类。系统采用统一的继承架构，所有实体类都继承自`BaseEntity`抽象基类，实现了代码的标准化和可维护性。

## 🏗️ 架构设计

### 继承层次结构
```
BaseEntity (抽象基类)
├── Court (场地实体)
├── Match (比赛实体)
├── Player (运动员实体)
├── MatchResult (比赛成绩实体)
├── Reservation (预约实体)
└── PlayerMatch (运动员比赛关联实体)
```

### 设计原则
- **统一接口**: 所有实体都通过BaseEntity提供标准的ID访问和显示接口
- **向后兼容**: 保留所有原有功能，确保现有代码正常工作
- **业务语义**: 每个实体都有清晰的业务含义和职责
- **扩展性**: 基于抽象基类的设计便于后续功能扩展

---

## 🧱 BaseEntity - 抽象基类

### 类描述
所有实体类的抽象父类，定义了统一的标识接口和通用行为。

### 包路径
```java
package com.badmintonassociation.model;
```

### 类定义
```java
public abstract class BaseEntity
```

### 抽象方法
| 方法签名 | 返回类型 | 描述 | 用途 |
|---------|----------|------|------|
| `getId()` | `int` | 获取实体唯一标识符 | 通用ID访问接口 |
| `setId(int id)` | `void` | 设置实体唯一标识符 | 通用ID设置接口 |
| `getDisplayName()` | `String` | 获取用户友好的显示名称 | UI展示和日志记录 |

### 实现方法
| 方法签名 | 返回类型 | 描述 | 实现逻辑 |
|---------|----------|------|----------|
| `equals(Object obj)` | `boolean` | 基于ID的相等性比较 | 比较类型和ID值 |
| `hashCode()` | `int` | 基于ID的哈希值计算 | 使用ID的哈希值 |

### 设计优势
- **统一标识**: 所有实体都有统一的ID访问方式
- **类型安全**: 支持泛型编程和类型检查
- **代码复用**: 共享equals和hashCode实现
- **扩展友好**: 便于添加通用功能

---

## 🏟️ Court - 场地实体

### 类描述
表示羽毛球场地的实体类，管理场地的基本信息和位置描述。

### 包路径
```java
package com.badmintonassociation.model;
```

### 类定义
```java
public class Court extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `courtId` | `int` | 场地唯一标识符 | 主键，自动生成 |
| `location` | `String` | 场地位置 | 如"一号馆"、"室外场地A" |
| `description` | `String` | 场地描述 | 场地详细信息和特点 |

### 构造方法
```java
// 默认构造方法
public Court()

// 完整参数构造方法
public Court(int courtId, String location, String description)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return courtId; }

@Override
public void setId(int id) { this.courtId = id; }

@Override
public String getDisplayName() {
    return location != null ? location + " - " + description : "场地-" + courtId;
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getCourtId()` | 无 | `int` | 获取场地ID |
| `setCourtId(int)` | courtId | `void` | 设置场地ID，同步更新基类 |
| `getLocation()` | 无 | `String` | 获取场地位置 |
| `setLocation(String)` | location | `void` | 设置场地位置 |
| `getDescription()` | 无 | `String` | 获取场地描述 |
| `setDescription(String)` | description | `void` | 设置场地描述 |

### 业务特点
- **资源管理**: 作为可预约的资源，需要避免时间冲突
- **位置标识**: 通过location和description提供清晰的场地标识
- **关联实体**: 与Reservation和Match实体关联

---

## 🏆 Match - 比赛实体

### 类描述
表示羽毛球比赛的实体类，管理比赛的时间安排和基本信息。

### 包路径
```java
package com.badmintonassociation.model;
```

### 导入依赖
```java
import java.sql.Date;
import java.sql.Timestamp;
```

### 类定义
```java
public class Match extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `matchId` | `int` | 比赛唯一标识符 | 主键，自动生成 |
| `date` | `java.sql.Date` | 比赛日期 | 比赛举行的日期 |
| `startTime` | `java.sql.Timestamp` | 比赛开始时间 | 精确的开始时间 |
| `endTime` | `java.sql.Timestamp` | 比赛结束时间 | 精确的结束时间 |

### 构造方法
```java
// 默认构造方法
public Match()

// 完整参数构造方法
public Match(int matchId, Date date, Timestamp startTime, Timestamp endTime)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return matchId; }

@Override
public void setId(int id) { this.matchId = id; }

@Override
public String getDisplayName() {
    return "比赛-" + matchId + (date != null ? " (" + date + ")" : "");
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getMatchId()` | 无 | `int` | 获取比赛ID |
| `setMatchId(int)` | matchId | `void` | 设置比赛ID，同步更新基类 |
| `getDate()` | 无 | `Date` | 获取比赛日期 |
| `setDate(Date)` | date | `void` | 设置比赛日期 |
| `getStartTime()` | 无 | `Timestamp` | 获取开始时间 |
| `setStartTime(Timestamp)` | startTime | `void` | 设置开始时间 |
| `getEndTime()` | 无 | `Timestamp` | 获取结束时间 |
| `setEndTime(Timestamp)` | endTime | `void` | 设置结束时间 |

### 业务特点
- **时间管理**: 严格的开始和结束时间管理
- **资源占用**: 需要预约场地资源
- **多人参与**: 通过PlayerMatch关联多个参赛选手
- **成绩记录**: 通过MatchResult记录比赛结果

---

## 👤 Player - 运动员实体

### 类描述
表示羽毛球运动员的实体类，管理运动员的基本信息和技能等级。

### 包路径
```java
package com.badmintonassociation.model;
```

### 类定义
```java
public class Player extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `playerId` | `int` | 运动员唯一标识符 | 主键，自动生成 |
| `name` | `String` | 运动员姓名 | 运动员的真实姓名 |
| `gender` | `String` | 运动员性别 | 性别信息，用于分组比赛 |
| `level` | `String` | 技能等级 | 如"初级"、"中级"、"高级" |

### 构造方法
```java
// 默认构造方法
public Player()

// 完整参数构造方法
public Player(int playerId, String name, String gender, String level)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return playerId; }

@Override
public void setId(int id) { this.playerId = id; }

@Override
public String getDisplayName() {
    return name != null ? name + " (" + level + ")" : "选手-" + playerId;
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getPlayerId()` | 无 | `int` | 获取运动员ID |
| `setPlayerId(int)` | playerId | `void` | 设置运动员ID，同步更新基类 |
| `getName()` | 无 | `String` | 获取运动员姓名 |
| `setName(String)` | name | `void` | 设置运动员姓名 |
| `getGender()` | 无 | `String` | 获取运动员性别 |
| `setGender(String)` | gender | `void` | 设置运动员性别 |
| `getLevel()` | 无 | `String` | 获取技能等级 |
| `setLevel(String)` | level | `void` | 设置技能等级 |

### 业务特点
- **个人信息**: 管理运动员的基本个人信息
- **等级系统**: 通过level字段支持技能分级
- **参赛管理**: 通过PlayerMatch参与比赛
- **成绩追踪**: 通过MatchResult记录比赛成绩

---

## 🏅 MatchResult - 比赛成绩实体

### 类描述
表示比赛成绩的实体类，记录运动员在特定比赛中的表现和排名。

### 包路径
```java
package com.badmintonassociation.model;
```

### 类定义
```java
public class MatchResult extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `resultId` | `int` | 成绩记录唯一标识符 | 主键，自动生成 |
| `matchId` | `int` | 关联的比赛ID | 外键，指向Match表 |
| `playerId` | `int` | 关联的运动员ID | 外键，指向Player表 |
| `rankId` | `int` | 排名标识 | 运动员在比赛中的排名 |
| `score` | `int` | 比赛得分 | 运动员的具体得分 |
| `recordBroken` | `boolean` | 是否打破记录 | 标识是否创造新纪录 |

### 构造方法
```java
// 默认构造方法
public MatchResult()

// 完整参数构造方法
public MatchResult(int resultId, int matchId, int playerId, int rankId, int score, boolean recordBroken)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return resultId; }

@Override
public void setId(int id) { this.resultId = id; }

@Override
public String getDisplayName() {
    return "成绩-" + resultId + " (排名:" + rankId + ")";
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getResultId()` | 无 | `int` | 获取成绩记录ID |
| `setResultId(int)` | resultId | `void` | 设置成绩记录ID |
| `getMatchId()` | 无 | `int` | 获取关联比赛ID |
| `setMatchId(int)` | matchId | `void` | 设置关联比赛ID |
| `getPlayerId()` | 无 | `int` | 获取关联运动员ID |
| `setPlayerId(int)` | playerId | `void` | 设置关联运动员ID |
| `getRankId()` | 无 | `int` | 获取排名 |
| `setRankId(int)` | rankId | `void` | 设置排名 |
| `getScore()` | 无 | `int` | 获取得分 |
| `setScore(int)` | score | `void` | 设置得分 |
| `isRecordBroken()` | 无 | `boolean` | 是否打破记录 |
| `setRecordBroken(boolean)` | recordBroken | `void` | 设置记录标识 |

### 业务特点
- **成绩记录**: 详细记录比赛结果和运动员表现
- **排名系统**: 支持多种排名方式
- **记录追踪**: 自动识别和标记新纪录
- **数据分析**: 为运动员表现分析提供数据基础

---

## 📅 Reservation - 预约实体

### 类描述
表示场地预约的实体类，管理场地的预约信息，支持个人训练和比赛两种预约类型。

### 包路径
```java
package com.badmintonassociation.model;
```

### 导入依赖
```java
import java.sql.Timestamp;
```

### 类定义
```java
public class Reservation extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `reservationId` | `int` | 预约记录唯一标识符 | 主键，自动生成 |
| `courtId` | `int` | 关联的场地ID | 外键，指向Court表 |
| `playerId` | `Integer` | 关联的运动员ID | 外键，个人预约时有值 |
| `matchId` | `Integer` | 关联的比赛ID | 外键，比赛预约时有值 |
| `startTime` | `Timestamp` | 预约开始时间 | 精确的开始时间 |
| `endTime` | `Timestamp` | 预约结束时间 | 精确的结束时间 |

### 构造方法
```java
// 默认构造方法
public Reservation()

// 完整参数构造方法
public Reservation(int reservationId, int courtId, Integer playerId, Integer matchId, Timestamp startTime, Timestamp endTime)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return reservationId; }

@Override
public void setId(int id) { this.reservationId = id; }

@Override
public String getDisplayName() {
    String type = (playerId != null) ? "个人预约" : "比赛预约";
    return "预约-" + reservationId + " (" + type + ")";
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getReservationId()` | 无 | `int` | 获取预约ID |
| `setReservationId(int)` | reservationId | `void` | 设置预约ID |
| `getCourtId()` | 无 | `int` | 获取场地ID |
| `setCourtId(int)` | courtId | `void` | 设置场地ID |
| `getPlayerId()` | 无 | `Integer` | 获取运动员ID |
| `setPlayerId(Integer)` | playerId | `void` | 设置运动员ID |
| `getMatchId()` | 无 | `Integer` | 获取比赛ID |
| `setMatchId(Integer)` | matchId | `void` | 设置比赛ID |
| `getStartTime()` | 无 | `Timestamp` | 获取开始时间 |
| `setStartTime(Timestamp)` | startTime | `void` | 设置开始时间 |
| `getEndTime()` | 无 | `Timestamp` | 获取结束时间 |
| `setEndTime(Timestamp)` | endTime | `void` | 设置结束时间 |

### 业务特点
- **双重用途**: 支持个人训练和比赛两种预约类型
- **时间管理**: 严格的时间冲突检查和管理
- **资源调度**: 合理分配场地资源
- **灵活设计**: playerId和matchId可选，支持不同预约场景

### 业务规则
- `playerId`和`matchId`至少一个不为null
- 同一场地同一时间段不能重复预约
- 个人预约有时间和次数限制

---

## 🔗 PlayerMatch - 运动员比赛关联实体

### 类描述
表示运动员与比赛关联关系的实体类，实现运动员和比赛之间的多对多关系。

### 包路径
```java
package com.badmintonassociation.model;
```

### 类定义
```java
public class PlayerMatch extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 描述 | 业务含义 |
|--------|------|------|----------|
| `playerMatchId` | `int` | 关联记录唯一标识符 | 主键，自动生成 |
| `matchId` | `int` | 关联的比赛ID | 外键，指向Match表 |
| `playerId` | `int` | 关联的运动员ID | 外键，指向Player表 |

### 构造方法
```java
// 默认构造方法
public PlayerMatch()

// 完整参数构造方法
public PlayerMatch(int playerMatchId, int matchId, int playerId)
```

### BaseEntity接口实现
```java
@Override
public int getId() { return playerMatchId; }

@Override
public void setId(int id) { this.playerMatchId = id; }

@Override
public String getDisplayName() {
    return "参赛记录-" + playerMatchId + " (选手:" + playerId + "-比赛:" + matchId + ")";
}
```

### 核心方法
| 方法名 | 参数 | 返回值 | 描述 |
|--------|------|--------|------|
| `getPlayerMatchId()` | 无 | `int` | 获取关联记录ID |
| `setPlayerMatchId(int)` | playerMatchId | `void` | 设置关联记录ID |
| `getMatchId()` | 无 | `int` | 获取比赛ID |
| `setMatchId(int)` | matchId | `void` | 设置比赛ID |
| `getPlayerId()` | 无 | `int` | 获取运动员ID |
| `setPlayerId(int)` | playerId | `void` | 设置运动员ID |

### 业务特点
- **多对多关系**: 实现Player和Match的多对多关联
- **参赛管理**: 管理运动员的参赛记录
- **数据完整性**: 确保关联数据的一致性
- **查询优化**: 便于查询特定比赛的参赛者或特定运动员的参赛记录

### 使用场景
- 比赛报名和参赛者管理
- 查询某场比赛的所有参赛选手
- 查询某位选手参加的所有比赛
- 生成参赛统计报告

---

## 🔄 实体关系图

### 核心关联关系
```
Court (场地)
├── 1:N → Reservation (预约)
└── 1:N → Match (比赛，通过预约)

Player (运动员)
├── 1:N → Reservation (预约)
├── M:N → Match (比赛，通过PlayerMatch)
└── 1:N → MatchResult (成绩)

Match (比赛)
├── 1:1 → Reservation (预约)
├── M:N → Player (运动员，通过PlayerMatch)
└── 1:N → MatchResult (成绩)

PlayerMatch (关联表)
├── N:1 → Player (运动员)
└── N:1 → Match (比赛)

MatchResult (成绩)
├── N:1 → Player (运动员)
└── N:1 → Match (比赛)
```

### 外键约束
| 子表 | 外键字段 | 父表 | 父表主键 | 约束说明 |
|------|----------|------|----------|----------|
| Reservation | `courtId` | Court | `courtId` | 预约必须关联有效场地 |
| Reservation | `playerId` | Player | `playerId` | 个人预约关联运动员（可选） |
| Reservation | `matchId` | Match | `matchId` | 比赛预约关联比赛（可选） |
| PlayerMatch | `playerId` | Player | `playerId` | 参赛记录关联运动员 |
| PlayerMatch | `matchId` | Match | `matchId` | 参赛记录关联比赛 |
| MatchResult | `playerId` | Player | `playerId` | 成绩记录关联运动员 |
| MatchResult | `matchId` | Match | `matchId` | 成绩记录关联比赛 |

---

## 📊 统计信息

### Model层概览
| 统计项 | 数值 | 说明 |
|--------|------|------|
| **总实体数** | 7个 | 包括1个抽象基类 + 6个具体实体 |
| **继承BaseEntity** | 6个 | 所有具体实体都继承基类 |
| **核心业务实体** | 4个 | Court, Player, Match, MatchResult |
| **关联实体** | 2个 | Reservation, PlayerMatch |
| **总字段数** | 23个 | 所有实体类的字段总数 |
| **外键关系** | 7个 | 实体间的引用关系 |

### 方法统计
| 方法类型 | 数量 | 说明 |
|----------|------|------|
| **抽象方法** | 3个 | BaseEntity定义的抽象接口 |
| **构造方法** | 12个 | 每个类2个构造方法 |
| **Getter方法** | 23个 | 所有字段的获取方法 |
| **Setter方法** | 23个 | 所有字段的设置方法 |
| **重写方法** | 24个 | toString + BaseEntity实现 |

---

## 🎯 使用建议

### 开发最佳实践
1. **统一接口**: 优先使用BaseEntity的getId()方法而非具体字段
2. **显示友好**: 在UI中使用getDisplayName()方法显示实体信息
3. **类型安全**: 利用继承关系编写泛型方法
4. **一致性**: 新增实体时遵循相同的设计模式

### 扩展指南
1. **新增实体**: 继承BaseEntity并实现三个抽象方法
2. **新增字段**: 添加对应的getter/setter方法
3. **业务方法**: 在具体实体类中添加业务相关的方法
4. **关联关系**: 通过外键字段建立实体间的关联

### 维护注意事项
1. **向后兼容**: 修改时确保不破坏现有接口
2. **数据完整性**: 维护外键约束和业务规则
3. **性能考虑**: 大量数据时注意查询优化
4. **文档同步**: 修改后及时更新文档

---

*文档版本: 2.0*  
*最后更新: 2025年7月5日*  
*创建者: 羽毛球协会管理系统开发团队*
