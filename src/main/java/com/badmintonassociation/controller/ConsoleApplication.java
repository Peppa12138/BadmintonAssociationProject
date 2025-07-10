
/**
 * 羽毛球协会管理系统控制台应用程序
 * 
 * 该类是整个羽毛球协会管理系统的控制台入口和用户交互界面。
 * 提供基于命令行的用户界面，支持选手管理、场地预订、比赛安排等核心功能。
 * 作为 MVC 架构中的控制器层，负责用户输入处理和业务流程协调。
 * 
 * 
 * 选手管理：查看所有注册选手信息
 * 场地管理：查看可用场地，支持场地预订
 * 预订系统：支持时间段预订，包含冲突检测和限制规则
 * 比赛系统：创建比赛，自动分配场地和选手
 * 成绩查询：查看选手历史比赛成绩
 * 
 * 
 * @author yuZhongShui，liSiHan，guoYifu
 * @since 2025-07-03
 */


package com.badmintonassociation.controller;

// 服务
import com.badmintonassociation.service.PlayerService;// 选手业务逻辑服务
import com.badmintonassociation.service.CourtService;// 场地业务逻辑服务
import com.badmintonassociation.service.ReservationService;//预约业务逻辑服务
import com.badmintonassociation.service.MatchService;// 比赛业务逻辑服务


// 数据访问对象
import com.badmintonassociation.dao.PlayerDAO;// 选手数据访问对象
import com.badmintonassociation.dao.PlayerMatchDAO; // 选手比赛数据访问对象
import com.badmintonassociation.dao.CourtDAO;// 场地数据访问对象
import com.badmintonassociation.dao.ReservationDAO;// 预约数据访问对象
import com.badmintonassociation.dao.MatchDAO;// 比赛数据访问对象
import com.badmintonassociation.dao.MatchResultDAO;// 比赛结果数据访问对象


// 模型
import com.badmintonassociation.model.MatchResult;// 比赛结果模型
import com.badmintonassociation.model.Reservation;// 预约模型

// 工具
import com.badmintonassociation.util.DatabaseConnection;// 数据库连接工具


// Java 标准库类
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ConsoleApplication {
    private PlayerService playerService;
    private CourtService courtService;
    private ReservationService reservationService;
    private MatchService matchService;

    public ConsoleApplication(PlayerService playerService, CourtService courtService,
            ReservationService reservationService, MatchService matchService) {
        this.playerService = playerService;
        this.courtService = courtService;
        this.reservationService = reservationService;
        this.matchService = matchService;
    }
    

    private void viewPlayerResults(Scanner scanner) {
        System.out.println("请输入选手编号:");
        int playerId = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline

        List<MatchResult> results = playerService.getLatestPlayerResults(playerId, 10);
        if (results == null || results.isEmpty()) {
            System.out.println("没有找到该选手的比赛成绩记录。");
            return;
        }

        System.out.println("选手最近的比赛成绩如下：");
        for (MatchResult result : results) {
            System.out.println("比赛ID: " + result.getMatchId() +
                    ", 名次: " + result.getRankId() +
                    ", 得分: " + result.getScore() +
                    ", 是否打破纪录: " + (result.isRecordBroken() ? "是" : "否"));
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=======================");
            System.out.println("║    羽毛球管理系统   ║");
            System.out.println("=======================");
            System.out.println("1. 查看所有选手");
            System.out.println("2. 查看所有场地");
            System.out.println("3. 预定场地");
            System.out.println("4. 安排比赛");
            System.out.println("5. 查看选手成绩");
            System.out.println("6. 退出");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    listAllPlayers();
                    break;
                case 2:
                    listAllCourts();
                    break;
                case 3:
                    reserveACourt(scanner);
                    break;
                case 4:
                    scheduleMatch(scanner);
                    break;
                case 5:
                    viewPlayerResults(scanner);
                    break;
                case 6:
                    System.out.println("退出系统.");
                    return;

                default:
                    System.out.println("无效选择，请重新选择.");
            }
        }
    }


    private void listAllPlayers() {
        playerService.getAllPlayers().forEach(System.out::println);
    }



    private void listAllCourts() {
        courtService.getAllCourts().forEach(System.out::println);
    }

    private void reserveACourt(Scanner scanner) {
        System.out.println("请输入您的用户ID:");
        int playerId = scanner.nextInt();
        System.out.println("请输入场地编号:");
        int courtId = scanner.nextInt();

        // Display recent reservation records for the court
        List<Reservation> recentReservations = reservationService.getRecentReservations(courtId);
        if (recentReservations != null && !recentReservations.isEmpty()) {
            System.out.println("该场地最近的预约记录如下：");
            for (Reservation reservation : recentReservations) {
                System.out.println("预约ID: " + reservation.getReservationId() +
                        ", 用户ID: " + reservation.getPlayerId() +
                        ", 开始时间: " + reservation.getStartTime() +
                        ", 结束时间: " + reservation.getEndTime());
            }
        } else {
            System.out.println("该场地无最近预约记录。");
        }

        System.out.println("请输入预订开始时间 (格式: yyyy-MM-dd HH:mm:ss):");
        scanner.nextLine(); // Consume newline
        // Convert input to Timestamp while respecting the time zone
        String startTimeInput = scanner.nextLine();
        LocalDateTime localDateTimeStart = LocalDateTime.parse(startTimeInput,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZoneId zoneId = ZoneId.of("Asia/Shanghai"); // Set your desired time zone
        Instant instantStart = localDateTimeStart.atZone(zoneId).toInstant();
        Timestamp startTime = Timestamp.from(instantStart);
        LocalDate reservationDate = localDateTimeStart.toLocalDate();
        // Check reservation count for the player on the same date
        if (!reservationService.canPlayerReserveOnDate(playerId, reservationDate)) {
            System.out.println("同一天最多只能预约两个时间段，该选手的预约次数已达上限。");
            return;
        }

        // Check court availability before further input
        if (!reservationService.isStartTimeAvailable(courtId, startTime)) {
            System.out.println("场地在该时间段不可用，预定失败。");
            return;
        }

        System.out.println("请输入预订结束时间 (格式: yyyy-MM-dd HH:mm:ss):");
        String endTimeInput = scanner.nextLine();
        LocalDateTime localDateTimeEnd = LocalDateTime.parse(endTimeInput,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Instant instantEnd = localDateTimeEnd.atZone(zoneId).toInstant();
        Timestamp endTime = Timestamp.from(instantEnd);

        // Check that the duration does not exceed 2 hours
        long milliseconds = endTime.getTime() - startTime.getTime();
        if (milliseconds < 0 || milliseconds > 2 * 60 * 60 * 1000) {
            System.out.println("预约时间无效，预约结束时间必须在开始时间之后，并且不超过2小时。");
            return;
        }

        // Check that the end time does not conflict with the start time of another
        // reservation
        if (!reservationService.isEndTimeAvailable(courtId, endTime)) {
            System.out.println("该结束时间与其他预约时间重叠，不可用。");
            return;
        }

        boolean success = reservationService.reserveCourt(courtId, startTime, endTime, playerId, null);
        System.out.println(success ? "场地预定成功！" : "预定失败，场地不可用。");
    }

    private void scheduleMatch(Scanner scanner) {
        // 示例方法: 根据业务逻辑类的接口调用进行实现
        // 提示用户输入比赛相关的信息，并调用相应的业务逻辑方法
        System.out.println("创建新的赛事...");
        // 清除前一次输入留下的换行符
        scanner.nextLine();
        // 要求用户输入比赛日期
        System.out.println("请输入比赛日期 (格式: yyyy-MM-dd):");
        String dateInput = scanner.nextLine();

        // 要求用户输入比赛开始时间
        System.out.println("请输入比赛开始时间 (格式: HH:mm:ss):");
        String startTimeInput = scanner.nextLine();

        // 要求用户输入比赛结束时间
        System.out.println("请输入比赛结束时间 (格式: HH:mm:ss):");
        String endTimeInput = scanner.nextLine();

        // 将输入转换为 LocalDate 和 LocalTime
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime startTime = LocalTime.parse(startTimeInput, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime endTime = LocalTime.parse(endTimeInput, DateTimeFormatter.ofPattern("HH:mm:ss"));

        // 创建比赛记录，比赛ID是数据库自动生成的
        boolean success = matchService.createMatchWithPlayers(date, startTime, endTime);
        if (success) {
            System.out.println("比赛创建成功！");
        } else {
            System.out.println("比赛创建失败。");
        }
    }


    public static void main(String[] args) {
    Connection connection = DatabaseConnection.getConnection();

    // 创建各个 DAO 实例
    PlayerDAO playerDAO = new PlayerDAO(connection);
    CourtDAO courtDAO = new CourtDAO(connection);
    ReservationDAO reservationDAO = new ReservationDAO(connection);
    MatchDAO matchDAO = new MatchDAO(connection);
    PlayerMatchDAO playerMatchDAO = new PlayerMatchDAO(connection);
    MatchResultDAO matchResultDAO = new MatchResultDAO(connection);

    // 使用 DAO 实例创建 Service 实例
    PlayerService playerService = new PlayerService(playerDAO);
    CourtService courtService = new CourtService(courtDAO);
    ReservationService reservationService = new ReservationService(reservationDAO, matchDAO);

    // Ensure MatchService receives all required DAO instances
    MatchService matchService = new MatchService(matchDAO, playerDAO, playerMatchDAO, matchResultDAO);

    // 将所有服务传入 ConsoleApplication，启动应用
    ConsoleApplication app = new ConsoleApplication(playerService, courtService, reservationService, matchService);
    app.start();
    }
}
