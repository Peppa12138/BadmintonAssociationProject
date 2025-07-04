# 羽毛球协会管理系统 - Model层文档

## 概述

本文档详细描述了羽毛球协会管理系统Model层的所有实体类，包括它们的结构、成员变量、方法和功能说明。

## 目录

1. [BaseEntity (基础实体抽象类)](#baseentity-基础实体抽象类)
2. [Court (场地实体)](#court-场地实体)
3. [Match (比赛实体)](#match-比赛实体)
4. [Player (运动员实体)](#player-运动员实体)
5. [MatchResult (比赛成绩实体)](#matchresult-比赛成绩实体)
6. [Reservation (预约实体)](#reservation-预约实体)
7. [PlayerMatch (运动员比赛关联实体)](#playermatch-运动员比赛关联实体)
8. [类关系图](#类关系图)

---

## BaseEntity (基础实体抽象类)

### 类说明
所有实体类的抽象基类，提供统一的标识接口和通用方法。

### 包路径
```java
package com.badmintonassociation.model;
```

### 抽象方法

| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getId()` | `int` | 无 | 获取实体的唯一标识符 |
| `setId(int id)` | `void` | `int id` | 设置实体的唯一标识符 |
| `getDisplayName()` | `String` | 无 | 获取实体的显示名称，用于UI展示 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `equals(Object obj)` | `boolean` | 基于ID的相等性比较 |
| `hashCode()` | `int` | 基于ID的哈希值计算 |

### 设计原则
- **统一接口**: 为所有实体提供统一的标识访问方式
- **最小化设计**: 只包含必要的抽象方法
- **向后兼容**: 不影响子类原有功能

---

## Court (场地实体)

### 类说明
表示羽毛球场地的实体类，继承自BaseEntity。

### 包路径
```java
package com.badmintonassociation.model;
```

### 继承关系
```java
public class Court extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 说明 |
|--------|------|------|
| `courtId` | `int` | 场地唯一标识符（主键） |
| `location` | `String` | 场地位置 |
| `description` | `String` | 场地描述信息 |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `Court()` | 无 | 默认构造方法 |
| `Court(int courtId, String location, String description)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `courtId` |
| `setId(int id)` | `void` | 设置 `courtId` |
| `getDisplayName()` | `String` | 返回 "位置 - 描述" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getCourtId()` | `int` | 无 | 获取场地ID |
| `setCourtId(int courtId)` | `void` | `int courtId` | 设置场地ID，同步更新基类ID |
| `getLocation()` | `String` | 无 | 获取场地位置 |
| `setLocation(String location)` | `void` | `String location` | 设置场地位置 |
| `getDescription()` | `String` | 无 | 获取场地描述 |
| `setDescription(String description)` | `void` | `String description` | 设置场地描述 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回场地的字符串表示 |

---

## Match (比赛实体)

### 类说明
表示羽毛球比赛的实体类，继承自BaseEntity。

### 包路径
```java
package com.badmintonassociation.model;
```

### 导入依赖
```java
import java.sql.Date;
import java.sql.Timestamp;
```

### 继承关系
```java
public class Match extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 说明 |
|--------|------|------|
| `matchId` | `int` | 比赛唯一标识符（主键） |
| `date` | `java.sql.Date` | 比赛日期 |
| `startTime` | `java.sql.Timestamp` | 比赛开始时间 |
| `endTime` | `java.sql.Timestamp` | 比赛结束时间 |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `Match()` | 无 | 默认构造方法 |
| `Match(int matchId, Date date, Timestamp startTime, Timestamp endTime)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `matchId` |
| `setId(int id)` | `void` | 设置 `matchId` |
| `getDisplayName()` | `String` | 返回 "比赛-ID (日期)" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getMatchId()` | `int` | 无 | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | `int matchId` | 设置比赛ID，同步更新基类ID |
| `getDate()` | `Date` | 无 | 获取比赛日期 |
| `setDate(Date date)` | `void` | `Date date` | 设置比赛日期 |
| `getStartTime()` | `Timestamp` | 无 | 获取开始时间 |
| `setStartTime(Timestamp startTime)` | `void` | `Timestamp startTime` | 设置开始时间 |
| `getEndTime()` | `Timestamp` | 无 | 获取结束时间 |
| `setEndTime(Timestamp endTime)` | `void` | `Timestamp endTime` | 设置结束时间 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回比赛的字符串表示 |

---

## Player (运动员实体)

### 类说明
表示羽毛球运动员的实体类，继承自BaseEntity。

### 包路径
```java
package com.badmintonassociation.model;
```

### 继承关系
```java
public class Player extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 说明 |
|--------|------|------|
| `playerId` | `int` | 运动员唯一标识符（主键） |
| `name` | `String` | 运动员姓名 |
| `gender` | `String` | 运动员性别 |
| `level` | `String` | 运动员技能等级 |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `Player()` | 无 | 默认构造方法 |
| `Player(int playerId, String name, String gender, String level)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `playerId` |
| `setId(int id)` | `void` | 设置 `playerId` |
| `getDisplayName()` | `String` | 返回 "姓名 (等级)" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getPlayerId()` | `int` | 无 | 获取运动员ID |
| `setPlayerId(int playerId)` | `void` | `int playerId` | 设置运动员ID，同步更新基类ID |
| `getName()` | `String` | 无 | 获取运动员姓名 |
| `setName(String name)` | `void` | `String name` | 设置运动员姓名 |
| `getGender()` | `String` | 无 | 获取运动员性别 |
| `setGender(String gender)` | `void` | `String gender` | 设置运动员性别 |
| `getLevel()` | `String` | 无 | 获取运动员等级 |
| `setLevel(String level)` | `void` | `String level` | 设置运动员等级 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回运动员的字符串表示 |

---

## MatchResult (比赛成绩实体)

### 类说明
表示比赛成绩的实体类，继承自BaseEntity。

### 包路径
```java
package com.badmintonassociation.model;
```

### 继承关系
```java
public class MatchResult extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 说明 |
|--------|------|------|
| `resultId` | `int` | 成绩记录唯一标识符（主键） |
| `matchId` | `int` | 关联的比赛ID（外键） |
| `playerId` | `int` | 关联的运动员ID（外键） |
| `rankId` | `int` | 排名ID |
| `score` | `int` | 比赛得分 |
| `recordBroken` | `boolean` | 是否打破记录 |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `MatchResult()` | 无 | 默认构造方法 |
| `MatchResult(int resultId, int matchId, int playerId, int rankId, int score, boolean recordBroken)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `resultId` |
| `setId(int id)` | `void` | 设置 `resultId` |
| `getDisplayName()` | `String` | 返回 "成绩-ID (排名:rankId)" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getResultId()` | `int` | 无 | 获取成绩ID |
| `setResultId(int resultId)` | `void` | `int resultId` | 设置成绩ID |
| `getMatchId()` | `int` | 无 | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | `int matchId` | 设置比赛ID |
| `getPlayerId()` | `int` | 无 | 获取运动员ID |
| `setPlayerId(int playerId)` | `void` | `int playerId` | 设置运动员ID |
| `getRankId()` | `int` | 无 | 获取排名ID |
| `setRankId(int rankId)` | `void` | `int rankId` | 设置排名ID |
| `getScore()` | `int` | 无 | 获取得分 |
| `setScore(int score)` | `void` | `int score` | 设置得分 |
| `isRecordBroken()` | `boolean` | 无 | 获取是否打破记录 |
| `setRecordBroken(boolean recordBroken)` | `void` | `boolean recordBroken` | 设置是否打破记录 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回比赛成绩的字符串表示 |

---

## Reservation (预约实体)

### 类说明
表示场地预约的实体类。**注意：此类尚未继承BaseEntity。**

### 包路径
```java
package com.badmintonassociation.model;
```

### 导入依赖
```java
import java.sql.Timestamp;
```

### 继承关系
```java
public class Reservation extends BaseEntity
```

### 成员变量
| 变量名 | 类型 | 说明 |
|--------|------|------|
| `reservationId` | `int` | 预约记录唯一标识符（主键） |
| `courtId` | `int` | 关联的场地ID（外键） |
| `playerId` | `Integer` | 关联的运动员ID（外键，可为null） |
| `matchId` | `Integer` | 关联的比赛ID（外键，可为null） |
| `startTime` | `java.sql.Timestamp` | 预约开始时间 |
| `endTime` | `java.sql.Timestamp` | 预约结束时间 |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `Reservation()` | 无 | 默认构造方法 |
| `Reservation(int reservationId, int courtId, Integer playerId, Integer matchId, Timestamp startTime, Timestamp endTime)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `reservationId` |
| `setId(int id)` | `void` | 设置 `reservationId` |
| `getDisplayName()` | `String` | 返回 "预约-ID (个人预约/比赛预约)" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getReservationId()` | `int` | 无 | 获取预约ID |
| `setReservationId(int reservationId)` | `void` | `int reservationId` | 设置预约ID |
| `getCourtId()` | `int` | 无 | 获取场地ID |
| `setCourtId(int courtId)` | `void` | `int courtId` | 设置场地ID |
| `getPlayerId()` | `Integer` | 无 | 获取运动员ID |
| `setPlayerId(Integer playerId)` | `void` | `Integer playerId` | 设置运动员ID |
| `getMatchId()` | `Integer` | 无 | 获取比赛ID |
| `setMatchId(Integer matchId)` | `void` | `Integer matchId` | 设置比赛ID |
| `getStartTime()` | `Timestamp` | 无 | 获取开始时间 |
| `setStartTime(Timestamp startTime)` | `void` | `Timestamp startTime` | 设置开始时间 |
| `getEndTime()` | `Timestamp` | 无 | 获取结束时间 |
| `setEndTime(Timestamp endTime)` | `void` | `Timestamp endTime` | 设置结束时间 |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回预约的字符串表示 |

### 特殊说明
- `playerId` 和 `matchId` 使用 `Integer` 类型，允许为 `null`
- 预约可能是个人预约（playerId有值，matchId为null）或比赛预约（matchId有值，playerId可能为null）

---

## PlayerMatch (运动员比赛关联实体)

### 类说明

表示运动员与比赛关联关系的实体类。**注意：此类尚未继承BaseEntity。**

### 包路径
```java
package com.badmintonassociation.model;
```

### 继承关系
```java
public class PlayerMatch extends BaseEntity
```

### 成员变量

| 变量名 | 类型 | 说明 |
|--------|------|------|
| `playerMatchId` | `int` | 关联记录唯一标识符（主键） |
| `matchId` | `int` | 关联的比赛ID（外键） |
| `playerId` | `int` | 关联的运动员ID（外键） |

### 构造方法
| 构造方法 | 参数 | 说明 |
|----------|------|------|
| `PlayerMatch()` | 无 | 默认构造方法 |
| `PlayerMatch(int playerMatchId, int matchId, int playerId)` | 完整参数 | 带参数的构造方法 |

### 实现的抽象方法
| 方法名 | 返回类型 | 实现说明 |
|--------|----------|----------|
| `getId()` | `int` | 返回 `playerMatchId` |
| `setId(int id)` | `void` | 设置 `playerMatchId` |
| `getDisplayName()` | `String` | 返回 "参赛记录-ID (选手:playerId-比赛:matchId)" 格式的字符串 |

### Getter/Setter方法
| 方法名 | 返回类型 | 参数 | 说明 |
|--------|----------|------|------|
| `getPlayerMatchId()` | `int` | 无 | 获取关联记录ID |
| `setPlayerMatchId(int playerMatchId)` | `void` | `int playerMatchId` | 设置关联记录ID |
| `getMatchId()` | `int` | 无 | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | `int matchId` | 设置比赛ID |
| `getPlayerId()` | `int` | 无 | 获取运动员ID |
| `setPlayerId(int playerId)` | `void` | `int playerId` | 设置运动员ID |

### 重写方法
| 方法名 | 返回类型 | 说明 |
|--------|----------|------|
| `toString()` | `String` | 返回关联关系的字符串表示 |

### 特殊说明
- 这是一个多对多关系的中间表实体
- 用于表示哪些运动员参加了哪些比赛

---

## 类关系图

```
BaseEntity (抽象类)
├── Court (场地)
├── Match (比赛)
├── Player (运动员)
├── MatchResult (比赛成绩)
├── Reservation (预约) - ✅ 已重构
└── PlayerMatch (运动员比赛关联) - ✅ 已重构
```

## 实体关系说明

### 主要关联关系
1. **Court ↔ Reservation**: 一个场地可以有多个预约记录
2. **Match ↔ Reservation**: 一个比赛可能需要预约场地
3. **Player ↔ Reservation**: 一个运动员可以有多个预约记录
4. **Match ↔ PlayerMatch ↔ Player**: 比赛和运动员的多对多关系
5. **Match ↔ MatchResult**: 一个比赛可以有多个成绩记录
6. **Player ↔ MatchResult**: 一个运动员可以有多个成绩记录

### 外键关系
- `Reservation.courtId` → `Court.courtId`
- `Reservation.playerId` → `Player.playerId` (可为null)
- `Reservation.matchId` → `Match.matchId` (可为null)
- `PlayerMatch.matchId` → `Match.matchId`
- `PlayerMatch.playerId` → `Player.playerId`
- `MatchResult.matchId` → `Match.matchId`
- `MatchResult.playerId` → `Player.playerId`

## 重构建议

### 待重构类
1. **Reservation**: 建议继承BaseEntity，实现统一接口
2. **PlayerMatch**: 建议继承BaseEntity，实现统一接口

### 重构收益
- **统一接口**: 所有实体类都有相同的标识访问方式
- **代码复用**: 共享equals和hashCode实现
- **类型安全**: 支持泛型编程
- **易于维护**: 统一的设计模式便于后续扩展

---

*文档版本: 1.0*  
*最后更新: 2025年7月5日*  
*作者: 羽毛球协会管理系统开发团队*
