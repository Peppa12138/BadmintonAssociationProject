# 羽毛球协会管理系统 - Model层类简介

## 📋 概述

Model层包含6个实体类和1个抽象基类，所有实体类都继承自BaseEntity，用于表示系统中的核心业务对象。

---

## 🏗️ BaseEntity (抽象基类)

**位置**: `com.badmintonassociation.model.BaseEntity`

### 📝 类描述
所有实体类的基类，提供统一的标识方法和通用功能。

### 🔧 抽象方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getId()` | `int` | 获取实体ID |
| `setId(int id)` | `void` | 设置实体ID |
| `getDisplayName()` | `String` | 获取显示名称 |

### 🛠️ 实例方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `equals(Object obj)` | `boolean` | 基于ID的相等性比较 |
| `hashCode()` | `int` | 基于ID的哈希码 |

---

## 🏃‍♂️ Player (选手类)

**位置**: `com.badmintonassociation.model.Player`

### 📝 类描述
表示羽毛球选手的信息，包含基本属性和等级。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `playerId` | `int` | 选手ID (主键) |
| `name` | `String` | 选手姓名 |
| `gender` | `String` | 性别 |
| `level` | `String` | 技能等级 |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `Player()` | 无参构造函数 |
| `Player(int playerId, String name, String gender, String level)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getPlayerId()` | `int` | 获取选手ID |
| `setPlayerId(int playerId)` | `void` | 设置选手ID |
| `getName()` | `String` | 获取姓名 |
| `setName(String name)` | `void` | 设置姓名 |
| `getGender()` | `String` | 获取性别 |
| `setGender(String gender)` | `void` | 设置性别 |
| `getLevel()` | `String` | 获取等级 |
| `setLevel(String level)` | `void` | 设置等级 |
| `toString()` | `String` | 字符串表示 |

---

## 🏟️ Court (场地类)

**位置**: `com.badmintonassociation.model.Court`

### 📝 类描述
表示羽毛球场地信息，包含位置和描述。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `courtId` | `int` | 场地ID (主键) |
| `location` | `String` | 场地位置 |
| `description` | `String` | 场地描述 |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `Court()` | 无参构造函数 |
| `Court(int courtId, String location, String description)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getCourtId()` | `int` | 获取场地ID |
| `setCourtId(int courtId)` | `void` | 设置场地ID |
| `getLocation()` | `String` | 获取位置 |
| `setLocation(String location)` | `void` | 设置位置 |
| `getDescription()` | `String` | 获取描述 |
| `setDescription(String description)` | `void` | 设置描述 |
| `toString()` | `String` | 字符串表示 |

---

## 🏆 Match (比赛类)

**位置**: `com.badmintonassociation.model.Match`

### 📝 类描述
表示羽毛球比赛的基本信息，包含时间安排。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `matchId` | `int` | 比赛ID (主键) |
| `date` | `java.sql.Date` | 比赛日期 |
| `startTime` | `java.sql.Timestamp` | 开始时间 |
| `endTime` | `java.sql.Timestamp` | 结束时间 |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `Match()` | 无参构造函数 |
| `Match(int matchId, Date date, Timestamp startTime, Timestamp endTime)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getMatchId()` | `int` | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | 设置比赛ID |
| `getDate()` | `Date` | 获取比赛日期 |
| `setDate(Date date)` | `void` | 设置比赛日期 |
| `getStartTime()` | `Timestamp` | 获取开始时间 |
| `setStartTime(Timestamp startTime)` | `void` | 设置开始时间 |
| `getEndTime()` | `Timestamp` | 获取结束时间 |
| `setEndTime(Timestamp endTime)` | `void` | 设置结束时间 |
| `toString()` | `String` | 字符串表示 |

---

## 📊 MatchResult (比赛成绩类)

**位置**: `com.badmintonassociation.model.MatchResult`

### 📝 类描述
表示选手在比赛中的成绩和排名信息。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `resultId` | `int` | 成绩ID (主键) |
| `matchId` | `int` | 比赛ID (外键) |
| `playerId` | `int` | 选手ID (外键) |
| `rankId` | `int` | 排名 |
| `score` | `int` | 得分 |
| `recordBroken` | `boolean` | 是否破纪录 |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `MatchResult()` | 无参构造函数 |
| `MatchResult(int resultId, int matchId, int playerId, int rankId, int score, boolean recordBroken)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getResultId()` | `int` | 获取成绩ID |
| `setResultId(int resultId)` | `void` | 设置成绩ID |
| `getMatchId()` | `int` | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | 设置比赛ID |
| `getPlayerId()` | `int` | 获取选手ID |
| `setPlayerId(int playerId)` | `void` | 设置选手ID |
| `getRankId()` | `int` | 获取排名 |
| `setRankId(int rankId)` | `void` | 设置排名 |
| `getScore()` | `int` | 获取得分 |
| `setScore(int score)` | `void` | 设置得分 |
| `isRecordBroken()` | `boolean` | 获取破纪录状态 |
| `setRecordBroken(boolean recordBroken)` | `void` | 设置破纪录状态 |
| `toString()` | `String` | 字符串表示 |

---

## 📅 Reservation (预约类)

**位置**: `com.badmintonassociation.model.Reservation`

### 📝 类描述
表示场地预约信息，支持个人预约和比赛预约。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `reservationId` | `int` | 预约ID (主键) |
| `courtId` | `int` | 场地ID (外键) |
| `playerId` | `Integer` | 选手ID (外键，可为null) |
| `matchId` | `Integer` | 比赛ID (外键，可为null) |
| `startTime` | `java.sql.Timestamp` | 开始时间 |
| `endTime` | `java.sql.Timestamp` | 结束时间 |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `Reservation()` | 无参构造函数 |
| `Reservation(int reservationId, int courtId, Integer playerId, Integer matchId, Timestamp startTime, Timestamp endTime)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getReservationId()` | `int` | 获取预约ID |
| `setReservationId(int reservationId)` | `void` | 设置预约ID |
| `getCourtId()` | `int` | 获取场地ID |
| `setCourtId(int courtId)` | `void` | 设置场地ID |
| `getPlayerId()` | `Integer` | 获取选手ID |
| `setPlayerId(Integer playerId)` | `void` | 设置选手ID |
| `getMatchId()` | `Integer` | 获取比赛ID |
| `setMatchId(Integer matchId)` | `void` | 设置比赛ID |
| `getStartTime()` | `Timestamp` | 获取开始时间 |
| `setStartTime(Timestamp startTime)` | `void` | 设置开始时间 |
| `getEndTime()` | `Timestamp` | 获取结束时间 |
| `setEndTime(Timestamp endTime)` | `void` | 设置结束时间 |
| `toString()` | `String` | 字符串表示 |

---

## 🔗 PlayerMatch (选手参赛关联类)

**位置**: `com.badmintonassociation.model.PlayerMatch`

### 📝 类描述
表示选手与比赛的多对多关联关系。

### 📊 成员变量
| 字段名 | 类型 | 描述 |
|--------|------|------|
| `playerMatchId` | `int` | 关联ID (主键) |
| `matchId` | `int` | 比赛ID (外键) |
| `playerId` | `int` | 选手ID (外键) |

### 🛠️ 构造函数
| 构造函数 | 描述 |
|----------|------|
| `PlayerMatch()` | 无参构造函数 |
| `PlayerMatch(int playerMatchId, int matchId, int playerId)` | 全参数构造函数 |

### 🔧 成员方法
| 方法 | 返回类型 | 描述 |
|------|----------|------|
| `getPlayerMatchId()` | `int` | 获取关联ID |
| `setPlayerMatchId(int playerMatchId)` | `void` | 设置关联ID |
| `getMatchId()` | `int` | 获取比赛ID |
| `setMatchId(int matchId)` | `void` | 设置比赛ID |
| `getPlayerId()` | `int` | 获取选手ID |
| `setPlayerId(int playerId)` | `void` | 设置选手ID |
| `toString()` | `String` | 字符串表示 |

---

## 🏗️ 类关系图

```
BaseEntity (抽象类)
├── Player (选手)
├── Court (场地)  
├── Match (比赛)
├── MatchResult (比赛成绩)
├── Reservation (预约)
└── PlayerMatch (选手参赛关联)
```

## 📋 设计特点

- **统一继承**: 所有实体类继承BaseEntity，保证一致性
- **主键标准化**: 所有类都有对应的ID字段作为主键
- **外键关联**: 通过外键建立类之间的关联关系
- **空值处理**: Reservation类支持可空的playerId和matchId
- **类型安全**: 使用包装类型(Integer)处理可空字段

---

*文档版本: 1.0*  
*最后更新: 2025年7月7日*  
*作者: 羽毛球协会管理系统开发团队*
