package com.badmintonassociation.controller;

import com.badmintonassociation.service.PlayerService;
import com.badmintonassociation.service.CourtService;
import com.badmintonassociation.service.ReservationService;
import com.badmintonassociation.service.MatchService;
import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.dao.CourtDAO;
import com.badmintonassociation.dao.ReservationDAO;
import com.badmintonassociation.model.Reservation;
import com.badmintonassociation.dao.MatchDAO;
import com.badmintonassociation.dao.MatchResultDAO;
import com.badmintonassociation.util.DatabaseConnection;

import java.util.List;
import java.util.Scanner;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==== 羽毛球管理系统 ====");
            System.out.println("1. 查看所有选手");
            System.out.println("2. 查看所有场地");
            System.out.println("3. 预定场地");
            System.out.println("4. 安排比赛");
            System.out.println("5. 退出");
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
    }

    public static void main(String[] args) {
        // 示例初始化
        PlayerService playerService = new PlayerService(new PlayerDAO(DatabaseConnection.getConnection()));
        CourtService courtService = new CourtService(new CourtDAO(DatabaseConnection.getConnection()));
        ReservationService reservationService = new ReservationService(
                new ReservationDAO(DatabaseConnection.getConnection()),
                new MatchDAO(DatabaseConnection.getConnection()));
        MatchService matchService = new MatchService(new MatchDAO(DatabaseConnection.getConnection()),
                new MatchResultDAO(DatabaseConnection.getConnection()));

        ConsoleApplication app = new ConsoleApplication(playerService, courtService, reservationService, matchService);
        app.start();
    }
}
