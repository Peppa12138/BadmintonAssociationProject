package test;

import com.badmintonassociation.dao.PlayerDAO;
import com.badmintonassociation.model.Player;
import com.badmintonassociation.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 测试方法
 * 该类测试插入数据等
 * 
 * 
 * @author yuZhongShui
 * @since 2025-07-03
 */
public class InsertPlayersTest {

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        PlayerDAO playerDAO = new PlayerDAO(connection);

        String[] levels = { "Beginner", "Intermediate", "Advanced" };

        try {
            for (int i = 1; i <= 50; i++) {
                String name = "Player" + i;
                String gender = i % 2 == 0 ? "Male" : "Female";
                String level = levels[i % 3];

                Player player = new Player();
                player.setName(name);
                player.setGender(gender);
                player.setLevel(level);

                playerDAO.createPlayer(player);
            }
            System.out.println("Successfully inserted 50 players.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
