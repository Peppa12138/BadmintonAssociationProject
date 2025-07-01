/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80038 (8.0.38)
 Source Host           : localhost:3306
 Source Schema         : badmintonassociation

 Target Server Type    : MySQL
 Target Server Version : 80038 (8.0.38)
 File Encoding         : 65001

 Date: 30/06/2025 17:34:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for courts
-- ----------------------------
DROP TABLE IF EXISTS `courts`;
CREATE TABLE `courts`  (
  `court_id` int NOT NULL AUTO_INCREMENT,
  `location` enum('East_South','West_North') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`court_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courts
-- ----------------------------
INSERT INTO `courts` VALUES (1, 'East_South', '东南面场地1');
INSERT INTO `courts` VALUES (2, 'East_South', '东南面场地2');
INSERT INTO `courts` VALUES (3, 'East_South', '东南面场地3');
INSERT INTO `courts` VALUES (4, 'West_North', '西北面场地1');
INSERT INTO `courts` VALUES (5, 'West_North', '西北面场地2');
INSERT INTO `courts` VALUES (6, 'West_North', '西北面场地3');
INSERT INTO `courts` VALUES (7, 'West_North', '西北面场地4');
INSERT INTO `courts` VALUES (8, 'West_North', '西北面场地5');
INSERT INTO `courts` VALUES (9, 'West_North', '西北面场地6');

-- ----------------------------
-- Table structure for matches
-- ----------------------------
DROP TABLE IF EXISTS `matches`;
CREATE TABLE `matches`  (
  `match_id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `court_id` int NOT NULL,
  PRIMARY KEY (`match_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matches
-- ----------------------------
INSERT INTO `matches` VALUES (1, '2025-12-12', '20:00:00', '23:30:30', 1);
INSERT INTO `matches` VALUES (2, '2025-12-12', '20:20:20', '21:30:30', 2);
INSERT INTO `matches` VALUES (3, '2025-12-12', '12:30:00', '16:30:00', 1);
INSERT INTO `matches` VALUES (4, '2025-12-12', '12:30:00', '16:30:00', 2);

-- ----------------------------
-- Table structure for matchresults
-- ----------------------------
DROP TABLE IF EXISTS `matchresults`;
CREATE TABLE `matchresults`  (
  `result_id` int NOT NULL AUTO_INCREMENT,
  `match_id` int NOT NULL,
  `player_id` int NOT NULL,
  `rank_id` int NOT NULL,
  `score` int NOT NULL,
  `record_broken` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`result_id`) USING BTREE,
  INDEX `match_id`(`match_id` ASC) USING BTREE,
  INDEX `player_id`(`player_id` ASC) USING BTREE,
  CONSTRAINT `matchresults_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `matches` (`match_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `matchresults_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matchresults
-- ----------------------------
INSERT INTO `matchresults` VALUES (1, 3, 28, 1, 17, 0);
INSERT INTO `matchresults` VALUES (2, 3, 26, 2, 13, 0);
INSERT INTO `matchresults` VALUES (3, 3, 19, 3, 20, 0);
INSERT INTO `matchresults` VALUES (4, 3, 49, 4, 14, 0);
INSERT INTO `matchresults` VALUES (5, 3, 27, 5, 20, 0);
INSERT INTO `matchresults` VALUES (6, 3, 24, 6, 10, 0);
INSERT INTO `matchresults` VALUES (7, 3, 3, 7, 4, 0);
INSERT INTO `matchresults` VALUES (8, 3, 9, 8, 10, 0);
INSERT INTO `matchresults` VALUES (9, 3, 46, 9, 28, 1);
INSERT INTO `matchresults` VALUES (10, 3, 30, 10, 11, 0);
INSERT INTO `matchresults` VALUES (11, 4, 31, 1, 30, 1);
INSERT INTO `matchresults` VALUES (12, 4, 22, 2, 12, 0);
INSERT INTO `matchresults` VALUES (13, 4, 38, 3, 11, 0);
INSERT INTO `matchresults` VALUES (14, 4, 10, 4, 8, 0);
INSERT INTO `matchresults` VALUES (15, 4, 2, 5, 13, 0);
INSERT INTO `matchresults` VALUES (16, 4, 13, 6, 5, 0);
INSERT INTO `matchresults` VALUES (17, 4, 34, 7, 29, 1);
INSERT INTO `matchresults` VALUES (18, 4, 15, 8, 3, 0);
INSERT INTO `matchresults` VALUES (19, 4, 44, 9, 15, 0);
INSERT INTO `matchresults` VALUES (20, 4, 50, 10, 15, 0);

-- ----------------------------
-- Table structure for playermatches
-- ----------------------------
DROP TABLE IF EXISTS `playermatches`;
CREATE TABLE `playermatches`  (
  `player_match_id` int NOT NULL AUTO_INCREMENT,
  `match_id` int NOT NULL,
  `player_id` int NOT NULL,
  PRIMARY KEY (`player_match_id`) USING BTREE,
  INDEX `match_id`(`match_id` ASC) USING BTREE,
  INDEX `player_id`(`player_id` ASC) USING BTREE,
  CONSTRAINT `playermatches_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `matches` (`match_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `playermatches_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of playermatches
-- ----------------------------
INSERT INTO `playermatches` VALUES (1, 3, 28);
INSERT INTO `playermatches` VALUES (2, 3, 26);
INSERT INTO `playermatches` VALUES (3, 3, 19);
INSERT INTO `playermatches` VALUES (4, 3, 49);
INSERT INTO `playermatches` VALUES (5, 3, 27);
INSERT INTO `playermatches` VALUES (6, 3, 24);
INSERT INTO `playermatches` VALUES (7, 3, 3);
INSERT INTO `playermatches` VALUES (8, 3, 9);
INSERT INTO `playermatches` VALUES (9, 3, 46);
INSERT INTO `playermatches` VALUES (10, 3, 30);
INSERT INTO `playermatches` VALUES (11, 4, 31);
INSERT INTO `playermatches` VALUES (12, 4, 22);
INSERT INTO `playermatches` VALUES (13, 4, 38);
INSERT INTO `playermatches` VALUES (14, 4, 10);
INSERT INTO `playermatches` VALUES (15, 4, 2);
INSERT INTO `playermatches` VALUES (16, 4, 13);
INSERT INTO `playermatches` VALUES (17, 4, 34);
INSERT INTO `playermatches` VALUES (18, 4, 15);
INSERT INTO `playermatches` VALUES (19, 4, 44);
INSERT INTO `playermatches` VALUES (20, 4, 50);

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players`  (
  `player_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` enum('Male','Female') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `level` enum('Beginner','Intermediate','Advanced') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of players
-- ----------------------------
INSERT INTO `players` VALUES (1, 'Player1', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (2, 'Player2', 'Male', 'Advanced');
INSERT INTO `players` VALUES (3, 'Player3', 'Female', 'Beginner');
INSERT INTO `players` VALUES (4, 'Player4', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (5, 'Player5', 'Female', 'Advanced');
INSERT INTO `players` VALUES (6, 'Player6', 'Male', 'Beginner');
INSERT INTO `players` VALUES (7, 'Player7', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (8, 'Player8', 'Male', 'Advanced');
INSERT INTO `players` VALUES (9, 'Player9', 'Female', 'Beginner');
INSERT INTO `players` VALUES (10, 'Player10', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (11, 'Player11', 'Female', 'Advanced');
INSERT INTO `players` VALUES (12, 'Player12', 'Male', 'Beginner');
INSERT INTO `players` VALUES (13, 'Player13', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (14, 'Player14', 'Male', 'Advanced');
INSERT INTO `players` VALUES (15, 'Player15', 'Female', 'Beginner');
INSERT INTO `players` VALUES (16, 'Player16', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (17, 'Player17', 'Female', 'Advanced');
INSERT INTO `players` VALUES (18, 'Player18', 'Male', 'Beginner');
INSERT INTO `players` VALUES (19, 'Player19', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (20, 'Player20', 'Male', 'Advanced');
INSERT INTO `players` VALUES (21, 'Player21', 'Female', 'Beginner');
INSERT INTO `players` VALUES (22, 'Player22', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (23, 'Player23', 'Female', 'Advanced');
INSERT INTO `players` VALUES (24, 'Player24', 'Male', 'Beginner');
INSERT INTO `players` VALUES (25, 'Player25', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (26, 'Player26', 'Male', 'Advanced');
INSERT INTO `players` VALUES (27, 'Player27', 'Female', 'Beginner');
INSERT INTO `players` VALUES (28, 'Player28', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (29, 'Player29', 'Female', 'Advanced');
INSERT INTO `players` VALUES (30, 'Player30', 'Male', 'Beginner');
INSERT INTO `players` VALUES (31, 'Player31', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (32, 'Player32', 'Male', 'Advanced');
INSERT INTO `players` VALUES (33, 'Player33', 'Female', 'Beginner');
INSERT INTO `players` VALUES (34, 'Player34', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (35, 'Player35', 'Female', 'Advanced');
INSERT INTO `players` VALUES (36, 'Player36', 'Male', 'Beginner');
INSERT INTO `players` VALUES (37, 'Player37', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (38, 'Player38', 'Male', 'Advanced');
INSERT INTO `players` VALUES (39, 'Player39', 'Female', 'Beginner');
INSERT INTO `players` VALUES (40, 'Player40', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (41, 'Player41', 'Female', 'Advanced');
INSERT INTO `players` VALUES (42, 'Player42', 'Male', 'Beginner');
INSERT INTO `players` VALUES (43, 'Player43', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (44, 'Player44', 'Male', 'Advanced');
INSERT INTO `players` VALUES (45, 'Player45', 'Female', 'Beginner');
INSERT INTO `players` VALUES (46, 'Player46', 'Male', 'Intermediate');
INSERT INTO `players` VALUES (47, 'Player47', 'Female', 'Advanced');
INSERT INTO `players` VALUES (48, 'Player48', 'Male', 'Beginner');
INSERT INTO `players` VALUES (49, 'Player49', 'Female', 'Intermediate');
INSERT INTO `players` VALUES (50, 'Player50', 'Male', 'Advanced');

-- ----------------------------
-- Table structure for reservations
-- ----------------------------
DROP TABLE IF EXISTS `reservations`;
CREATE TABLE `reservations`  (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `court_id` int NOT NULL,
  `player_id` int NULL DEFAULT NULL,
  `match_id` int NULL DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`reservation_id`) USING BTREE,
  UNIQUE INDEX `court_id`(`court_id` ASC, `start_time` ASC, `end_time` ASC) USING BTREE,
  INDEX `player_id`(`player_id` ASC) USING BTREE,
  INDEX `match_id`(`match_id` ASC) USING BTREE,
  CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`court_id`) REFERENCES `courts` (`court_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reservations_ibfk_3` FOREIGN KEY (`match_id`) REFERENCES `matches` (`match_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reservations_chk_1` CHECK ((`player_id` is not null) or (`match_id` is not null))
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservations
-- ----------------------------
INSERT INTO `reservations` VALUES (1, 3, 1, NULL, '2025-12-04 04:30:00', '2025-12-04 05:30:00');
INSERT INTO `reservations` VALUES (2, 5, 1, NULL, '2025-12-04 05:30:00', '2025-12-04 06:30:00');
INSERT INTO `reservations` VALUES (3, 5, 1, NULL, '2025-12-31 04:20:00', '2025-12-31 06:20:00');
INSERT INTO `reservations` VALUES (4, 7, 3, NULL, '2025-12-31 06:20:00', '2025-12-31 07:21:00');
INSERT INTO `reservations` VALUES (5, 7, 6, NULL, '2025-12-31 07:21:00', '2025-12-31 08:20:00');
INSERT INTO `reservations` VALUES (6, 3, 10, NULL, '2025-12-31 15:21:00', '2025-12-31 17:21:00');
INSERT INTO `reservations` VALUES (7, 7, 20, NULL, '2025-07-12 20:30:00', '2025-07-12 21:30:00');

SET FOREIGN_KEY_CHECKS = 1;


