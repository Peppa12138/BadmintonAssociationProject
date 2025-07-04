# Model 类抽象基类改造指南（简化版）

## 已完成的改造

### 1. 创建了 BaseEntity 抽象基类（简化版）
- 位置：`src/main/java/com/badmintonassociation/model/BaseEntity.java`
- **只提供核心功能**：统一的标识方法
- 定义了三个抽象方法：getId(), setId(), getDisplayName()
- 提供了标准的 equals() 和 hashCode() 实现
- **不包含时间戳等额外功能**，保持最简设计

### 2. 已改造的类

#### Match 类
- ✅ 继承 BaseEntity
- ✅ 实现所有抽象方法
- ✅ **完全保留原有功能**（所有原始方法和属性）
- ✅ **没有添加任何新功能**
- ✅ **100% 向后兼容**

#### Court 类  
- ✅ 继承 BaseEntity
- ✅ 实现所有抽象方法
- ✅ **完全保留原有功能**
- ✅ **没有添加任何新功能**
- ✅ **100% 向后兼容**

## 简化的抽象基类设计

```java
public abstract class BaseEntity {
    
    // 抽象方法 - 子类必须实现
    public abstract int getId();
    public abstract void setId(int id);
    public abstract String getDisplayName();
    
    // 重写 equals 和 hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity that = (BaseEntity) obj;
        return getId() == that.getId();
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}
```

## 改造模板（最简版）

### 1. 修改类声明
```java
// 原来
public class ClassName {

// 改造后
public class ClassName extends BaseEntity {
```

### 2. 修改构造函数
```java
// 原来
public ClassName() {
}

public ClassName(参数...) {
    // 赋值
}

// 改造后
public ClassName() {
    super();
}

public ClassName(参数...) {
    super();
    // 赋值
}
```

### 3. 实现抽象方法
```java
@Override
public int getId() {
    return 主键字段;
}

@Override
public void setId(int id) {
    this.主键字段 = id;
}

@Override
public String getDisplayName() {
    return "有意义的显示名称";
}
```

### 4. 保持原有所有方法不变
```java
// 所有原有的 getter/setter 保持完全不变
// 所有原有的 toString 保持完全不变
// 不添加任何新方法
```

## 其他 Model 类改造示例

### Player 类改造示例

```java
public class Player extends BaseEntity {
    private int playerId;
    private String name;
    private String gender;
    private String level;

    // 构造函数
    public Player() {
        super();
    }

    public Player(int playerId, String name, String gender, String level) {
        super();
        this.playerId = playerId;
        this.name = name;
        this.gender = gender;
        this.level = level;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void setId(int id) {
        this.playerId = id;
    }

    @Override
    public String getDisplayName() {
        return name != null ? name + " (" + level + ")" : "选手-" + playerId;
    }

    // 所有原有方法保持完全不变
    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
```

## 改造的好处（最小化版本）

1. **统一的标识接口**：所有实体都有 getId() 方法
2. **标准化的对象比较**：统一的 equals() 和 hashCode()
3. **显示名称标准化**：getDisplayName() 提供友好的显示文本
4. **为未来扩展铺平道路**：基础架构已就位
5. **完全的向后兼容**：现有代码无需任何修改

## 使用示例

```java
// 原来的用法完全不变
Match match = new Match(1, date, startTime, endTime);
System.out.println(match.toString()); // 完全相同的输出

// 新增的统一接口
System.out.println(match.getDisplayName()); // "比赛-1 (2025-07-04)"
System.out.println(match.getId()); // 1

// 统一的对象比较
Match match1 = new Match(1, date1, start1, end1);
Match match2 = new Match(1, date2, start2, end2);
System.out.println(match1.equals(match2)); // true (相同ID)
```

## 注意事项

1. **零破坏性变更**：所有原有代码继续正常工作
2. **最小化设计**：只添加必要的抽象方法
3. **简单易懂**：新手也能快速理解
4. **ID 同步**：确保主键字段与基类的 getId() 保持一致
5. **保持一致性**：所有 Model 类都遵循相同的模式

## 完成状态

- ✅ BaseEntity 抽象类（简化版）
- ✅ Match 类（完全兼容）
- ✅ Court 类（完全兼容）
- ⭕ Player 类（待改造）
- ⭕ MatchResult 类（待改造）
- ⭕ Reservation 类（待改造）
- ⭕ PlayerMatch 类（待改造）

## 总结

这次改造采用了**最小化原则**：
- ✅ 提供统一的抽象接口
- ✅ 保持所有原有功能不变
- ✅ 不添加任何可能影响现有逻辑的新功能
- ✅ 为未来扩展提供基础架构
- ✅ 零学习成本，零破坏性变更

## 其他 Model 类改造模板

### Player 类改造示例

```java
public class Player extends BaseEntity {
    private int playerId;
    private String name;
    private String gender;
    private String level;

    // 构造函数
    public Player() {
        super();
    }

    public Player(int playerId, String name, String gender, String level) {
        super();
        this.playerId = playerId;
        this.name = name;
        this.gender = gender;
        this.level = level;
    }

    // 实现抽象方法
    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void setId(int id) {
        this.playerId = id;
    }

    @Override
    public String getDisplayName() {
        return name != null ? name + " (" + level + ")" : "选手-" + playerId;
    }

    // 原有方法保持不变，但在 setter 中添加 updateTimestamp()
    public void setName(String name) {
        this.name = name;
        updateTimestamp();
    }

    public void setGender(String gender) {
        this.gender = gender;
        updateTimestamp();
    }

    public void setLevel(String level) {
        this.level = level;
        updateTimestamp();
    }

    // 新增业务方法
    public boolean canCompete() {
        return name != null && !name.trim().isEmpty() && level != null;
    }

    public boolean isAdvanced() {
        return "Advanced".equals(level);
    }
}
```

### MatchResult 类改造示例

```java
public class MatchResult extends BaseEntity {
    private int resultId;
    private int matchId;
    private int playerId;
    private int rankId;
    private int score;
    private boolean recordBroken;

    // 实现抽象方法
    @Override
    public int getId() {
        return resultId;
    }

    @Override
    public void setId(int id) {
        this.resultId = id;
    }

    @Override
    public String getDisplayName() {
        return "成绩-" + resultId + " (排名:" + rankId + ")";
    }

    // setter 方法添加 updateTimestamp()
    public void setScore(int score) {
        this.score = score;
        updateTimestamp();
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
        updateTimestamp();
    }

    public void setRecordBroken(boolean recordBroken) {
        this.recordBroken = recordBroken;
        updateTimestamp();
    }

    // 新增业务方法
    public boolean isWinner() {
        return rankId == 1;
    }

    public boolean isTopThree() {
        return rankId <= 3;
    }

    public String getPerformanceLevel() {
        if (recordBroken) return "破纪录";
        if (rankId == 1) return "冠军";
        if (rankId <= 3) return "前三名";
        if (rankId <= 10) return "前十名";
        return "参与";
    }
}
```

## 改造步骤

### 1. 修改类声明
```java
// 原来
public class ClassName {

// 改造后
public class ClassName extends BaseEntity {
```

### 2. 修改构造函数
```java
// 原来
public ClassName() {
}

public ClassName(参数...) {
    // 赋值
}

// 改造后
public ClassName() {
    super();
}

public ClassName(参数...) {
    super();
    // 赋值
}
```

### 3. 实现抽象方法
```java
@Override
public int getId() {
    return 主键字段;
}

@Override
public void setId(int id) {
    this.主键字段 = id;
}

@Override
public String getDisplayName() {
    return "有意义的显示名称";
}
```

### 4. 在重要的 setter 方法中添加时间戳更新
```java
public void setSomeField(Type value) {
    this.someField = value;
    updateTimestamp(); // 添加这行
}
```

### 5. 可选：添加业务方法
```java
public boolean isSomeCondition() {
    // 业务逻辑判断
}

public String getSomeDescription() {
    // 业务逻辑描述
}
```

### 6. 保持向后兼容
```java
// 保留原有的 toString 方法
@Override
public String toString() {
    // 原有实现
}

// 可选：提供基类的详细 toString
public String toDetailedString() {
    return super.toString();
}
```

## 使用新功能的示例

### 在 Service 层使用通用功能
```java
public class BaseService<T extends BaseEntity> {
    
    public boolean isEntityNew(T entity) {
        return entity.isNew();
    }
    
    public void prepareForSave(T entity) {
        entity.updateTimestamp();
    }
    
    public List<T> findRecentlyUpdated(List<T> entities, long hoursAgo) {
        long threshold = System.currentTimeMillis() - (hoursAgo * 60 * 60 * 1000);
        return entities.stream()
                .filter(entity -> entity.getUpdatedAt().getTime() > threshold)
                .collect(Collectors.toList());
    }
}
```

### 在 DAO 层使用
```java
public abstract class BaseDAO<T extends BaseEntity> {
    protected void setCommonFields(PreparedStatement stmt, T entity, int startIndex) throws SQLException {
        stmt.setTimestamp(startIndex, entity.getCreatedAt());
        stmt.setTimestamp(startIndex + 1, entity.getUpdatedAt());
    }
}
```

## 注意事项

1. **保持向后兼容**：所有原有的方法和属性必须保留
2. **时间戳更新**：在重要的数据变更方法中调用 updateTimestamp()
3. **ID 同步**：确保主键字段与基类的 id 保持同步
4. **业务方法**：可以添加有用的业务判断方法，但不强制
5. **测试**：改造后要测试现有功能是否正常工作

## 改造带来的好处

1. **统一的时间跟踪**：所有实体都有创建和更新时间
2. **通用的业务方法**：isNew(), updateTimestamp() 等
3. **标准化的标识**：统一的 getId() 方法
4. **更好的调试**：标准化的 toString() 和 equals()
5. **代码复用**：可以写通用的 Service 和 DAO 方法
6. **未来扩展**：容易添加更多通用功能

## 完成状态

- ✅ BaseEntity 抽象类
- ✅ Match 类
- ✅ Court 类
- ⭕ Player 类（待改造）
- ⭕ MatchResult 类（待改造）
- ⭕ Reservation 类（待改造）
- ⭕ PlayerMatch 类（待改造）
