package com.badmintonassociation.service;

import com.badmintonassociation.dao.CourtDAO;
import com.badmintonassociation.model.Court;

import java.util.List;

/**
 * 场地业务服务类
 * 该类负责场地相关的业务逻辑处理，作为控制器层和数据访问层之间的桥梁。
 * 继承自 BaseService，具备统一的异常处理和日志记录能力。
 * 提供场地查询、管理等业务功能，支持场地预订和比赛调度系统。
 * 
 * @author huJunYang，huKeHan，yuZhongShui，liSiHan
 * @since 2025-07-03
 */
public class CourtService extends BaseService<Court, CourtDAO> {

    public CourtService(CourtDAO courtDAO) {
        super(courtDAO);
    }

    // 获取所有场地
    public List<Court> getAllCourts() {
        return executeWithExceptionHandling(
            () -> dao.getAllCourts(),
            null
        );
    }

    // Additional methods...
}
