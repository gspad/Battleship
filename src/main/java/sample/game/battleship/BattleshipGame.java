package sample.game.battleship;

import sample.game.battleship.gamerunner.GameRunner;
import sample.game.battleship.gamerunner.GameContext;

import java.io.IOException;
import java.util.Properties;

/**
 * The BattleshipGame class.  This class is responsible for loading the game properties and instantiating the CompositionRoot and GameRunner.
 */
public class BattleshipGame
{
    public static void main(String[] args)
    {
        try
        {
            Properties gameProperties = loadGameProperties("config.properties");
            CompositionRoot root = new CompositionRoot(gameProperties);
            GameContext gameContext = root.createGameContext();
            GameRunner gameRunner = GameRunner.newInstance(gameContext.getGameBoard(),gameContext.getPlayer1(),gameContext.getPlayer2());
            gameRunner.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * loads the game properties
     * @param configFileName the properties filename.
     */
    public static Properties loadGameProperties(String configFileName) throws IOException {
        Properties gameProperties = new Properties();
        gameProperties.load(BattleshipGame.class.getClassLoader().getResourceAsStream(configFileName));
        return gameProperties;
    }

}
