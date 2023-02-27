package com.mikul17.footballsimulation.projekt;


import com.mikul17.footballsimulation.MainApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class DBmanager {

    public static boolean databaseExists = false;
    private static final String url = "jdbc:mysql://localhost:3306/?user=root&password=";
    private static final String databaseName = "simulation";
    private static Connection connection = null;

    //Table Players
    private static final String tablePlayers = "players";
    private static final String playersID = tablePlayers + ".playerID";
    private static final String playersTeamID = tablePlayers + ".teamID";
    private static final String playersFirstName = tablePlayers + ".firstName";
    private static final String playersLastName = tablePlayers + ".lastName";
    private static final String playersNationality = tablePlayers + ".nationality";
    private static final String playersPosition = tablePlayers + ".position";
    private static final String playersAge = tablePlayers + ".age";
    private static final String playersHeight = tablePlayers + ".height";
    private static final String playersOverall = tablePlayers + ".overall";
    private static final String playersIsInjured = tablePlayers + ".isInjured";
    private static final String playersInjuryTime = tablePlayers + ".injuryTime";
    private static final String playersIsSuspended = tablePlayers + ".isRedCarded";
    private static final String playersRedCards = tablePlayers + ".redCards";
    private static final String playersYellowCards = tablePlayers + ".yellowCards";
    private static final String playersGoals = tablePlayers + ".goals";
    private static final String playersAssists = tablePlayers + ".assists";

    //Table PlayerStats
    private static final String tablePlayerStats = "playerStats";
    private static final String playerStatsID = tablePlayerStats + ".playerStatsID";
    private static final String playerStatsPace = tablePlayerStats + ".pace";
    private static final String playerStatsStrength = tablePlayerStats + ".strength";
    private static final String playerStatsBlock = tablePlayerStats + ".block";
    private static final String playerStatsHeaderFight = tablePlayerStats + ".headerFight";
    private static final String playerStatsInterception = tablePlayerStats + ".interception";
    private static final String playerStatsPositioning = tablePlayerStats + ".positioning";
    private static final String playerStatsShortPass = tablePlayerStats + ".shortPass";
    private static final String playerStatsLongPass = tablePlayerStats + ".longPass";
    private static final String playerStatsCrossPass = tablePlayerStats + ".crossPass";
    private static final String playerStatsVision = tablePlayerStats + ".vision";
    private static final String playerStatsDribbling = tablePlayerStats + ".dribbling";
    private static final String playerStatsCloseRangeShot = tablePlayerStats + ".closeRangeShot";
    private static final String playerStatsLongShot = tablePlayerStats + ".longShot";
    private static final String playerStatsHeader = tablePlayerStats + ".header";
    private static final String playerStatsFreekicks = tablePlayerStats + ".freekicks";
    private static final String playerStatsPenalties = tablePlayerStats + ".penalties";
    private static final String playerStatsDiving = tablePlayerStats + ".diving";
    private static final String playerStatsReflexes = tablePlayerStats + ".reflexes";
    private static final String playerStatsPenaltySaving = tablePlayerStats + ".penaltySaving";
    private static final String playerStatsLongShotDefending = tablePlayerStats + ".longShotDefending";
    private static final String playerStatsCloseRangeDefending = tablePlayerStats + ".closeRangeDefending";


    //Table Teams
    private static final String tableTeams = "teams";
    private static final String teamsID = tableTeams + ".teamID";
    private static final String teamsName = tableTeams + ".teamName";
    private static final String teamWins = tableTeams + ".wins";
    private static final String teamDraws = tableTeams + ".draws";
    private static final String teamLosses = tableTeams + ".loses";
    private static final String teamGoalsScored = tableTeams + ".goalsScored";
    private static final String teamGoalsConceded = tableTeams + ".goalsConceded";
    private static final String teamGoalDifference = tableTeams + ".goalsDifference";
    private static final String teamsPoints = tableTeams + ".points";

    //Table Matches
    private static final String tableMatches = "matches";
    private static final String matchesID = tableMatches + ".matchID";
    private static final String matchesWeekNumber = tableMatches + ".weekNumber";
    private static final String matchesHomeTeamID = tableMatches + ".homeTeamID";
    private static final String matchesAwayTeamID = tableMatches + ".awayTeamID";
    private static final String matchesHomeTeamGoals = tableMatches + ".homeTeamGoals";
    private static final String matchesAwayTeamGoals = tableMatches + ".awayTeamGoals";

    //Table MatchStats
    private static final String tableMatchStats = "matchStats";
    private static final String matchStatsID = tableMatchStats + ".matchStatID";
    private static final String matchStatsTeamID = tableMatchStats + ".teamID";
    private static final String matchStatsShoots = tableMatchStats + ".shoots";
    private static final String matchStatsShootsOnTarget = tableMatchStats + ".shotsOnTarget";
    private static final String matchStatsPossession = tableMatchStats + ".possession";
    private static final String matchStatsPasses = tableMatchStats + ".passes";
    private static final String matchStatsPassesAccurate = tableMatchStats + ".passesAccuracy";
    private static final String matchStatsCorners = tableMatchStats + ".corners";
    private static final String matchStatsOffsides = tableMatchStats + ".offsides";
    private static final String matchStatsFouls = tableMatchStats + ".fouls";
    private static final String matchStatsYellowCards = tableMatchStats + ".yellowCards";
    private static final String matchStatsRedCards = tableMatchStats + ".redCards";
    private static final String matchStatsFreekicks = tableMatchStats + ".freekicksAmount";
    private static final String matchStatsPenalties = tableMatchStats + ".penaltiesAmount";
    private static final String matchStatsSaves = tableMatchStats + ".saves";
    private static final String matchStatsInterceptions = tableMatchStats + ".interceptions";
    private static final String matchStatsBlocks = tableMatchStats + ".blocks";
    private static final String matchStatsDribbles = tableMatchStats + ".dribbles";
    private static final String matchStatsDribblesWon = tableMatchStats + ".dribblesWon";

    //Table simulationInformation
    private static final String tableSimulationInformation = "simulationInformation";
    private static final String simulationInformationPlayerSelectedTeamID = tableSimulationInformation + ".playerSelectedTeamID";
    private static final String simulationInformationWeekNumber = tableSimulationInformation + ".weekNumber";
    private static final String simulationInformationSeasonsPlayed = tableSimulationInformation + ".seasonsPlayed";
    private static final String simulationInformationSeasonsWon = tableSimulationInformation + ".seasonsWon";
    private static final String simulationInformationPlayersCapitanName = tableSimulationInformation + ".playersCapitanName";
    private static final String simulationInformationPlayersFreekickTakerName = tableSimulationInformation + ".playersFreekickTakerName";
    private static final String simulationInformationPlayersPenaltyTakerName = tableSimulationInformation + ".playersPenaltyTakerName";


    //STATEMENTS

    //Preparing database
    private static final StringBuilder CREATE_DB = new StringBuilder("CREATE DATABASE IF NOT EXISTS " + databaseName);

    private static final String CREATE_TABLE_PLAYERS = """
            CREATE TABLE IF NOT EXISTS players(
              playerID smallint PRIMARY KEY AUTO_INCREMENT,
              teamID smallint,
              firstName varchar(20),
              lastName varchar(30),
              nationality varchar(35),
              position varchar(10) DEFAULT "none",
              age smallint NOT NULL,
              height int NOT NULL,
              overall smallint DEFAULT 0,
              isInjured boolean DEFAULT false,
              injuryTime smallint DEFAULT 0,
              isRedCarded boolean DEFAULT false,
              redCards smallint DEFAULT 0,
              yellowCards smallint DEFAULT 0,
              goals smallint DEFAULT 0,
              assists smallint DEFAULT 0
            );""";
    private static final String CREATE_TABLE_TEAMS = """
            CREATE TABLE IF NOT EXISTS teams(
              teamID smallint PRIMARY KEY AUTO_INCREMENT,
              teamName varchar(20) UNIQUE,
              wins smallint DEFAULT 0,
              draws smallint DEFAULT 0,
              loses smallint DEFAULT 0,
              goalsScored smallint DEFAULT 0,
              goalsConceded smallint DEFAULT 0,
              goalsDifference smallint DEFAULT 0,
              points smallint DEFAULT 0
            );""";
    private static final String CREATE_TABLE_PLAYER_STATS = """
            CREATE TABLE IF NOT EXISTS playerStats(
              playerStatsID smallint PRIMARY KEY,
              pace smallint(2),
              strength smallint(2),
              block smallint(2),
              headerFight smallint(2),
              interception smallint(2),
              positioning smallint(2),
              shortPass smallint(2),
              longPass smallint(2),
              crossPass smallint(2),
              vision smallint(2),
              dribbling smallint(2),
              closeRangeShot smallint(2),
              longShot smallint(2),
              header smallint(2),
              freekicks smallint(2),
              penalties smallint(2),
              diving smallint(2),
              reflexes smallint(2),
              penaltySaving smallint(2),
              longShotDefending smallint(2),
              closeRangeDefending smallint(2)
            );""";
    private static final String CREATE_TABLE_MATCHES = """
            CREATE TABLE IF NOT EXISTS matches(
              matchID smallint PRIMARY KEY AUTO_INCREMENT,
              weekNumber smallint DEFAULT 0,
              homeTeamGoals smallint DEFAULT 0,
              awayTeamGoals smallint DEFAULT 0,
              homeTeamID smallint NOT NULL,
              awayTeamID smallint NOT NULL
            );""";
    private static final String CREATE_TABLE_MATCH_STATS = """
              CREATE TABLE IF NOT EXISTS matchStats(
              PRIMARY KEY(matchStatID, teamID),
              matchStatID smallint,
              teamID smallint,
              shoots smallint,
              shotsOnTarget smallint,
              possession smallint,
              corners smallint,
              offsides smallint,
              fouls smallint,
              freekicksAmount smallint,
              penaltiesAmount smallint,
              yellowCards smallint,
              redCards smallint,
              saves smallint,
              interceptions smallint,
              blocks smallint,
              passes smallint,
              passesAccuracy smallint,
              dribbles smallint,
              dribblesWon smallint
            );""";

    private static final String CREATE_TABLE_SIMULATION_INFORMATION = """
            CREATE TABLE IF NOT EXISTS simulationInformation(
              playerSelectedTeamID smallint,
              weekNumber smallint DEFAULT 0,
              seasonsPlayed smallint DEFAULT 0,
              seasonsWon smallint DEFAULT 0,
              playersCapitanName varchar(50),
              playersFreekickTakerName varchar(50),
              playersPenaltyTakerName varchar(50)
            );""";


    //Querying database
    private static final String queryAllPlayerInfoStatement = "Select * FROM " + tablePlayers + " WHERE " + playersID + "= ?" + " ORDER BY " + playersID;
    private static final String queryTeamStatement = "SELECT * FROM " + tableTeams + " WHERE " + teamsID + " = ?";
    private static final String queryPlayerStats = "SELECT * FROM " + tablePlayerStats +
            " WHERE " + playerStatsID + " = (SELECT " + playersID + " FROM " + tablePlayers + " WHERE " + playersFirstName + " = ? AND " + playersLastName + " = ?)";
    private static final String querySelectedTeam = "SELECT " + teamsName + " FROM " + tableTeams + " WHERE "
            + teamsID + " = (SELECT " + simulationInformationPlayerSelectedTeamID + " FROM " + tableSimulationInformation + ")";
    private static final String queryCurrentWeek = "SELECT " + simulationInformationWeekNumber + " FROM " + tableSimulationInformation;
    private static final String querySeasonsPlayed = "SELECT " + simulationInformationSeasonsPlayed + " FROM " + tableSimulationInformation;
    private static final String querySeasonsWon = "SELECT " + simulationInformationSeasonsWon + " FROM " + tableSimulationInformation;
    private static final String queryPlayersTeamCapitanStatement = "SELECT " + simulationInformationPlayersCapitanName + " FROM " + tableSimulationInformation;
    private static final String queryPlayersTeamFreekickTakerStatement = "SELECT " + simulationInformationPlayersFreekickTakerName + " FROM " + tableSimulationInformation;
    private static final String queryPlayersTeamPenaltyTakerStatement = "SELECT " + simulationInformationPlayersPenaltyTakerName + " FROM " + tableSimulationInformation;
    private static final String queryTeamIDStatement = "SELECT " + teamsID + " FROM " + tableTeams + " WHERE " + teamsName + " = ?";
    private static final String queryMatchResultHomeStatement = "SELECT " + teamsName + ", " + matchesHomeTeamGoals + " FROM " + tableMatches + " INNER JOIN " + tableTeams + " ON " + matchesHomeTeamID + " = " + teamsID + " WHERE " + matchesWeekNumber + " = ?";
    private static final String queryMatchResultAwayStatement = "SELECT " + teamsName + ", " + matchesAwayTeamGoals + " FROM " + tableMatches + " INNER JOIN " + tableTeams + " ON " + matchesAwayTeamID + " = " + teamsID + " WHERE " + matchesWeekNumber + " = ?";
    private static final String queryMatchDetailsHomeStatement = "SELECT * FROM " + tableMatchStats +
            " WHERE " + matchStatsID + " IN ( SELECT " + matchesID + " FROM " + tableMatches +
            " WHERE " + matchesHomeTeamID + " = ? AND " + matchesWeekNumber + " = ?) AND " + matchStatsTeamID + " = " +
            " (SELECT " + matchesHomeTeamID + " FROM " + tableMatches + " WHERE " + matchesHomeTeamID + " = ? AND " + matchesWeekNumber + " = ?)";
    private static final String queryMatchDetailsAwayStatement = "SELECT * FROM " + tableMatchStats +
            " JOIN " + tableMatches + " ON " + matchStatsID + " = " + matchesID +
            " WHERE " + "(" + matchesAwayTeamID + " = ? AND " + matchesWeekNumber + " = ?)" +
            " AND " + matchStatsTeamID + " = " + matchesAwayTeamID;
    private static final String queryPlayerStatusInformationStatement = "SELECT " + playersAge + ", " + playersOverall + ", " + playersIsInjured + ", " + playersInjuryTime + ", " + playersIsSuspended
            + ", " + playersRedCards + ", " + playersYellowCards + ", " + playersGoals + ", " + playersAssists
            + " FROM " + tablePlayers + " WHERE " + playersFirstName + " = ? AND " + playersLastName + " = ? AND " + playersTeamID + " = ?";

    //Inserting into database
    private static final String insertSimulationProgress = "INSERT INTO " + tableSimulationInformation + " VALUES ((" +
            "SELECT " + teamsID + " FROM " + tableTeams + " WHERE " + teamsName + " = ?),?,?,?,?,?,?)";
    private static final String insertMatchStatement = "INSERT INTO " + tableMatches + "(" + matchesWeekNumber + ", "
            + matchesHomeTeamGoals + ", " + matchesAwayTeamGoals + ", " + matchesHomeTeamID + ", " + matchesAwayTeamID + ")" + " VALUES (?,?,?,(SELECT " + teamsID + " FROM " + tableTeams + " WHERE " + teamsName + " = ?),(SELECT " + teamsID + " FROM " + tableTeams + " WHERE " + teamsName + " = ?))";
    private static final String insertMatchStatsStatement = "INSERT INTO " + tableMatchStats + "("
            + matchStatsID + ", "
            + matchStatsTeamID + ", "
            + matchStatsShoots + ", "
            + matchStatsShootsOnTarget + ", "
            + matchStatsPossession + ", "
            + matchStatsPasses + ", "
            + matchStatsPassesAccurate + ", "
            + matchStatsCorners + ", "
            + matchStatsOffsides + ", "
            + matchStatsFouls + ", "
            + matchStatsYellowCards + ", "
            + matchStatsRedCards + ", "
            + matchStatsFreekicks + ", "
            + matchStatsPenalties + ", "
            + matchStatsSaves + ", "
            + matchStatsInterceptions + ", "
            + matchStatsBlocks + ", "
            + matchStatsDribbles + ", "
            + matchStatsDribblesWon
            + ")"
            + " VALUES (" + "(SELECT " + matchesID + " FROM " + tableMatches + " WHERE " + matchesHomeTeamID + " = ? AND " + matchesAwayTeamID + " = ?)," +
            "" + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //Updating database
    private static final String updateSimulationInformation = "Update " + tableSimulationInformation + " SET " + simulationInformationWeekNumber + " = ?," +
            simulationInformationSeasonsPlayed + " = ?," + simulationInformationSeasonsWon + " = ?," + simulationInformationPlayersCapitanName + " = ?," +
            simulationInformationPlayersFreekickTakerName + " = ?," + simulationInformationPlayersPenaltyTakerName + " = ?";
    private static final String updateTeamStatsStatement = "UPDATE " + tableTeams +
            " SET " + teamWins + " = ?, " + teamDraws + " = ?, " + teamLosses + " = ?, " + teamGoalsScored + " = ?, " + teamGoalsConceded + " = ?, " + teamGoalDifference + " = ?, " + teamsPoints + " = ?" +
            " WHERE " + teamsName + " = ?";
    private static final String updatePlayerInfoStatement = "UPDATE " + tablePlayers +
            " SET " + playersAge + " = ?, " + playersOverall + " = ?, " + playersIsInjured + " = ?, " + playersInjuryTime + " = ?, " + playersIsSuspended + " = ?, " + playersRedCards
            + " = ?, " + playersYellowCards + " = ?, " + playersGoals + " = ?, " + playersAssists + " = ? " +
            " WHERE " + playersFirstName + " = ? AND " + playersLastName + " = ?" + " AND " + playersTeamID + " = ?";
    private static final String updateNextSeasonTeamValuesStatement = "UPDATE " + tableTeams +
            " SET " + teamWins + " = 0, " + teamDraws + " = 0, " + teamLosses + " = 0, " + teamGoalsScored + " = 0, " + teamGoalsConceded + " = 0, " + teamGoalDifference + " = 0, " + teamsPoints + " = 0" +
            " WHERE " + teamsName + " = ?";

    //Deleting from database
    private static final String dropDatabase = "DROP DATABASE IF EXISTS " + databaseName;
    private static final String nextSeasonDropTables = "DROP TABLE " + tableMatches + ", " + tableMatchStats;

    //Database connection
    public static void openConnection () throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url);
                System.out.println("Connection to MySQL has been established.");
            } catch (SQLException e) {
                System.out.println("Couldn't connect to database " + e.getMessage());
            }
        }
    }
    public static void closeConnection () {
        try {

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection to MySQL has been closed.");
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection to database" + e.getMessage());
        }
    }

    //Database creation
    private static void createDatabase () {
        try {
            Statement statement = connection.createStatement();
            if (checkIfDatabaseExists()) {
                System.out.println("Database already exists");
                databaseExists = true;
                return;
            }
            statement.execute(CREATE_DB.toString());
            System.out.println("Database " + databaseName + " has been created.");
        } catch (SQLException e) {
            System.out.println("Couldn't create database" + e.getMessage());
        }
    }
    private static void createTables () {
        try (Statement statement = connection.createStatement()) {
            statement.execute("USE " + databaseName);
            statement.execute(CREATE_TABLE_PLAYERS);
            statement.execute(CREATE_TABLE_PLAYER_STATS);
            statement.execute(CREATE_TABLE_TEAMS);
            statement.execute(CREATE_TABLE_MATCHES);
            statement.execute(CREATE_TABLE_MATCH_STATS);
            statement.execute(CREATE_TABLE_SIMULATION_INFORMATION);

        } catch (SQLException e) {
            System.out.println("Couldn't create tables" + e.getMessage());
        }
    }
    public static void prepareDatabase () {
        try {
            createDatabase();
            createTables();
            addPlayers();
            addPlayerStats();
            addTeams();
            System.out.println("Database has been prepared successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't prepare database" + e.getMessage());
        }
    }
    private static void loopTroughInsertStatements (String path) throws FileNotFoundException, SQLException {
        File file = new File(path);
        Scanner newScanner = new Scanner(file);
        Iterator<String> iterator = newScanner.useDelimiter(";");
        while (iterator.hasNext()) {
            String query = iterator.next();
            Statement statement = connection.createStatement();
            statement.execute(query);
        }
    }
    private static void addPlayers () throws FileNotFoundException {
        try {
            String path = "src/main/resources/com/mikul17/footballsimulation/sqlResources/playersList.sql";
            loopTroughInsertStatements(path);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void addTeams () throws FileNotFoundException {
        try {
            String path = "src/main/resources/com/mikul17/footballsimulation/sqlResources/teamsList.sql";
            loopTroughInsertStatements(path);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void addPlayerStats () throws FileNotFoundException {
        try {
            String path = "src/main/resources/com/mikul17/footballsimulation/sqlResources/playerStatsList.sql";
            loopTroughInsertStatements(path);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean checkIfDatabaseExists () throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("USE " + databaseName);
            databaseExists = true;
            return true;
        } catch (SQLException e) {
            databaseExists = false;
            return false;
        }
    }
    public static void createNextSeasonTables () {
        try (Statement statement = connection.createStatement()) {
            statement.execute("USE " + databaseName);
            statement.execute(nextSeasonDropTables);
            statement.execute(CREATE_TABLE_MATCHES);
            statement.execute(CREATE_TABLE_MATCH_STATS);
        } catch (SQLException e) {
            System.out.println("Couldn't create tables" + e.getMessage());
        }
    }
    public static void dropDatabase () {
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropDatabase);
        } catch (SQLException e) {
            System.out.println("Couldn't drop database" + e.getMessage());
        }
    }
    public static void saveSimulation (String teamName, int weekNumber, int seasonsPlayed, int seasonsWon, String captainsName, String freekickName, String penaltyName) throws SQLException {
        String checker = "SELECT * from " + tableSimulationInformation;
        ResultSet resultSet = connection.createStatement().executeQuery(checker);
        if (resultSet.next()) {
            updateSimulationInformation(weekNumber, seasonsPlayed, seasonsWon, captainsName, freekickName, penaltyName);
        } else {
            try (PreparedStatement statement = connection.prepareStatement(insertSimulationProgress)) {
                statement.setString(1, teamName);
                statement.setInt(2, weekNumber);
                statement.setInt(3, seasonsPlayed);
                statement.setInt(4, seasonsWon);
                statement.setString(5, captainsName);
                statement.setString(6, freekickName);
                statement.setString(7, penaltyName);
                statement.execute();
            }
        }
    }

    //Database queries
    public static Player queryPlayer (int playerID) {
        try (PreparedStatement statement = connection.prepareStatement(queryAllPlayerInfoStatement)) {
            statement.setInt(1, playerID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String playerPosition = resultSet.getString(playersPosition);
            if (Objects.equals(playerPosition, "forward")) {
                return new Forward(resultSet.getInt(playersAge), resultSet.getInt(playersHeight), resultSet.getString(playersFirstName), resultSet.getString(playersLastName), resultSet.getString(playersNationality));
            } else if (Objects.equals(playerPosition, "midfielder")) {
                return new Midfielder(resultSet.getInt(playersAge), resultSet.getInt(playersHeight), resultSet.getString(playersFirstName), resultSet.getString(playersLastName), resultSet.getString(playersNationality));
            } else if (Objects.equals(playerPosition, "defender")) {
                return new Defender(resultSet.getInt(playersAge), resultSet.getInt(playersHeight), resultSet.getString(playersFirstName), resultSet.getString(playersLastName), resultSet.getString(playersNationality));
            } else if (Objects.equals(playerPosition, "goalkeeper")) {
                return new Goalkeeper(resultSet.getInt(playersAge), resultSet.getInt(playersHeight), resultSet.getString(playersFirstName), resultSet.getString(playersLastName), resultSet.getString(playersNationality));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static Team queryTeam (int ID) {
        try (PreparedStatement statement = connection.prepareStatement(queryTeamStatement)) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String teamName = resultSet.getString(teamsName);
            int wins = resultSet.getInt(teamWins);
            int draws = resultSet.getInt(teamDraws);
            int losses = resultSet.getInt(teamLosses);
            int goalsScored = resultSet.getInt(teamGoalsScored);
            int goalsConceded = resultSet.getInt(teamGoalsConceded);
            int goalsDifference = resultSet.getInt(teamGoalDifference);
            int points = resultSet.getInt(teamsPoints);

            return new Team(teamName, wins, draws, losses, goalsScored, goalsConceded, points);
        } catch (SQLException e) {
            System.out.println("Couldn't query teams" + e.getMessage());
        }
        return null;
    }
    public static String queryPlayerSelectedTeam (){
        try (PreparedStatement statement = connection.prepareStatement(querySelectedTeam)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(teamsName);
        } catch (SQLException e) {
            System.out.println("Couldn't query selected team " + e.getMessage());
        }
        return null;
    }
    public static int queryCurrentWeek (){
        try (PreparedStatement statement = connection.prepareStatement(queryCurrentWeek)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(simulationInformationWeekNumber);
        } catch (SQLException e) {
            System.out.println("Couldn't query current week " + e.getMessage());
        }
        return 0;
    }
    public static int querySeasonsPlayed (){
        try (PreparedStatement statement = connection.prepareStatement(querySeasonsPlayed)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(simulationInformationSeasonsPlayed);
        } catch (SQLException e) {
            System.out.println("Couldn't query seasons played " + e.getMessage());
        }
        return 0;
    }
    public static int querySeasonsWon (){
        try (PreparedStatement statement = connection.prepareStatement(querySeasonsWon)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(simulationInformationSeasonsWon);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Player queryCapitan (){
        try (PreparedStatement statement = connection.prepareStatement(queryPlayersTeamCapitanStatement)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String playerName = resultSet.getString(simulationInformationPlayersCapitanName);
            Player capitan;
            for (Player player : MainApplication.getSimulation().getPlayerTeam().getPlayerList()) {
                if (player.getName().equals(playerName)) {
                    capitan = player;
                    return capitan;
                }
            }

        } catch (SQLException e) {
            System.out.println("Couldn't query capitan " + e.getMessage());
        }
        return null;
    }
    public static Player queryFreekickTaker (){
        try (PreparedStatement statement = connection.prepareStatement(queryPlayersTeamFreekickTakerStatement)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String playerName = resultSet.getString(simulationInformationPlayersFreekickTakerName);
            Player freekickTaker;
            for (Player player : MainApplication.getSimulation().getPlayerTeam().getPlayerList()) {
                if (player.getName().equals(playerName)) {
                    freekickTaker = player;
                    return freekickTaker;
                }
            }

        } catch (SQLException e) {
            System.out.println("Couldn't query freekick taker " + e.getMessage());
        }
        return null;
    }
    public static Player queryPenaltyTaker (){
        try (PreparedStatement statement = connection.prepareStatement(queryPlayersTeamPenaltyTakerStatement)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String playerName = resultSet.getString(simulationInformationPlayersPenaltyTakerName);
            Player penaltyTaker;
            for (Player player : MainApplication.getSimulation().getPlayerTeam().getPlayerList()) {
                if (player.getName().equals(playerName)) {
                    penaltyTaker = player;
                    return penaltyTaker;
                }
            }

        } catch (SQLException e) {
            System.out.println("Couldn't query penalty taker " + e.getMessage());
        }
        return null;
    }
    private static int queryTeamID (String teamName) {
        try (PreparedStatement statement = connection.prepareStatement(queryTeamIDStatement)) {
            statement.setString(1, teamName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(teamsID);
        } catch (SQLException e) {
            System.out.println("Couldn't query team ID " + e.getMessage());
        }
        return 0;
    }
    public static List<Object[]> queryMatchResultAway (int currentWeek) {
        List<Object[]> matchResultAway = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(queryMatchResultAwayStatement)) {
            statement.setInt(1, currentWeek);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                matchResultAway.add(new Object[]{resultSet.getString(teamsName), resultSet.getInt(matchesAwayTeamGoals)});
            }
        } catch (SQLException e) {
            System.out.println("Couldn't query match result away " + e.getMessage());
        }
        return matchResultAway;
    }
    public static List<Object[]> queryMatchResultHome (int weekNumber) {
        List<Object[]> matchResultHome = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(queryMatchResultHomeStatement)) {
            statement.setInt(1, weekNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                matchResultHome.add(new Object[]{resultSet.getString(teamsName), resultSet.getInt(matchesHomeTeamGoals)});
            }
        } catch (SQLException e) {
            System.out.println("Couldn't query match result home " + e.getMessage());
        }
        return matchResultHome;
    }
    public static List<HashMap<String, Integer>> queryMatchDetails (int weekNumber, String homeTeamName, String awayTeamName) {
        List<HashMap<String, Integer>> matchDetails = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(queryMatchDetailsHomeStatement)) {
            int homeTeamID = queryTeamID(homeTeamName);
            statement.setInt(1, homeTeamID);
            statement.setInt(2, weekNumber);
            statement.setInt(3, homeTeamID);
            statement.setInt(4, weekNumber);
            ResultSet resultSet = statement.executeQuery();
            matchDetails.add(new HashMap<>());
            resultSet.next();
            matchDetails.get(0).put(("homeTeamShots"), resultSet.getInt("shoots"));
            matchDetails.get(0).put(("homeTeamShotsOnTarget"), resultSet.getInt("shotsOnTarget"));
            matchDetails.get(0).put(("homeTeamPossession"), resultSet.getInt("possession"));
            matchDetails.get(0).put(("homeTeamCorners"), resultSet.getInt("corners"));
            matchDetails.get(0).put(("homeTeamOffsides"), resultSet.getInt("offsides"));
            matchDetails.get(0).put(("homeTeamFouls"), resultSet.getInt("fouls"));
            matchDetails.get(0).put(("homeTeamFreekicks"), resultSet.getInt("freekicksAmount"));
            matchDetails.get(0).put(("homeTeamPenalties"), resultSet.getInt("penaltiesAmount"));
            matchDetails.get(0).put(("homeTeamYellowCards"), resultSet.getInt("yellowCards"));
            matchDetails.get(0).put(("homeTeamRedCards"), resultSet.getInt("redCards"));
            matchDetails.get(0).put(("homeTeamSaves"), resultSet.getInt("saves"));
            matchDetails.get(0).put(("homeTeamInterceptions"), resultSet.getInt("interceptions"));
            matchDetails.get(0).put(("homeTeamBlocks"), resultSet.getInt("blocks"));
            matchDetails.get(0).put(("homeTeamPasses"), resultSet.getInt("passes"));
            matchDetails.get(0).put(("homeTeamPassesAccuracy"), resultSet.getInt("passesAccuracy"));
            matchDetails.get(0).put(("homeTeamDribbles"), resultSet.getInt("dribbles"));
            matchDetails.get(0).put(("homeTeamDribblesWon"), resultSet.getInt("dribblesWon"));
        } catch (SQLException e) {
            System.out.println("Couldn't query match details " + e.getMessage());
        }

        try (PreparedStatement statement = connection.prepareStatement(queryMatchDetailsAwayStatement)) {
            int awayTeamID = queryTeamID(awayTeamName);
            statement.setInt(1, awayTeamID);
            statement.setInt(2, weekNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            matchDetails.add(new HashMap<>());
            matchDetails.get(1).put(("awayTeamShots"), resultSet.getInt("shoots"));
            matchDetails.get(1).put(("awayTeamShotsOnTarget"), resultSet.getInt("shotsOnTarget"));
            matchDetails.get(1).put(("awayTeamPossession"), resultSet.getInt("possession"));
            matchDetails.get(1).put(("awayTeamCorners"), resultSet.getInt("corners"));
            matchDetails.get(1).put(("awayTeamOffsides"), resultSet.getInt("offsides"));
            matchDetails.get(1).put(("awayTeamFouls"), resultSet.getInt("fouls"));
            matchDetails.get(1).put(("awayTeamFreekicks"), resultSet.getInt("freekicksAmount"));
            matchDetails.get(1).put(("awayTeamPenalties"), resultSet.getInt("penaltiesAmount"));
            matchDetails.get(1).put(("awayTeamYellowCards"), resultSet.getInt("yellowCards"));
            matchDetails.get(1).put(("awayTeamRedCards"), resultSet.getInt("redCards"));
            matchDetails.get(1).put(("awayTeamSaves"), resultSet.getInt("saves"));
            matchDetails.get(1).put(("awayTeamInterceptions"), resultSet.getInt("interceptions"));
            matchDetails.get(1).put(("awayTeamBlocks"), resultSet.getInt("blocks"));
            matchDetails.get(1).put(("awayTeamPasses"), resultSet.getInt("passes"));
            matchDetails.get(1).put(("awayTeamPassesAccuracy"), resultSet.getInt("passesAccuracy"));
            matchDetails.get(1).put(("awayTeamDribbles"), resultSet.getInt("dribbles"));
            matchDetails.get(1).put(("awayTeamDribblesWon"), resultSet.getInt("dribblesWon"));
        } catch (SQLException e) {
            System.out.println("Couldn't query match details " + e.getMessage());
        }
        return matchDetails;
    }
    public static HashMap<String,Integer> queryPlayerStatusInformation (String playersFirstName, String playersLastName, String playersTeamName) {
        HashMap<String, Integer> values = new HashMap<>();
        int teamID = queryTeamID(playersTeamName);
        try (PreparedStatement statement = connection.prepareStatement(queryPlayerStatusInformationStatement)) {
            statement.setString(1, playersFirstName);
            statement.setString(2, playersLastName);
            statement.setInt(3, teamID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                values.put("playerAge", resultSet.getInt("age"));
                values.put("playerOverall", resultSet.getInt("overall"));
                values.put("playerIsInjured", resultSet.getInt("isInjured"));
                values.put("playerInjuryTime", resultSet.getInt("injuryTime"));
                values.put("playerIsRedCarded", resultSet.getInt("isRedCarded"));
                values.put("playerRedCards", resultSet.getInt("redCards"));
                values.put("playerYellowCards", resultSet.getInt("yellowCards"));
                values.put("playerGoals", resultSet.getInt("goals"));
                values.put("playerAssists", resultSet.getInt("assists"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return values;
    }

    //Player stats
    public static void setPlayerStats (Forward player) {
        try (PreparedStatement statement = connection.prepareStatement(queryPlayerStats)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            player.setPace(resultSet.getInt(playerStatsPace));
            player.setInterception(resultSet.getInt(playerStatsInterception));
            player.setShortPass(resultSet.getInt(playerStatsShortPass));
            player.setLongPass(resultSet.getInt(playerStatsLongPass));
            player.setDribbling(resultSet.getInt(playerStatsDribbling));
            player.setLongShot(resultSet.getInt(playerStatsLongShot));
            player.setCloseRangeShot(resultSet.getInt(playerStatsCloseRangeShot));
            player.setHeader(resultSet.getInt(playerStatsHeader));
            player.setFreekicks(resultSet.getInt(playerStatsFreekicks));
            player.setPenalties(resultSet.getInt(playerStatsPenalties));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPlayerStats (Midfielder player) {
        try (PreparedStatement statement = connection.prepareStatement(queryPlayerStats)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            player.setPace(resultSet.getInt(playerStatsPace));
            player.setInterception(resultSet.getInt(playerStatsInterception));
            player.setShortPass(resultSet.getInt(playerStatsShortPass));
            player.setLongPass(resultSet.getInt(playerStatsLongPass));
            player.setCrossPass(resultSet.getInt(playerStatsCrossPass));
            player.setVision(resultSet.getInt(playerStatsVision));
            player.setDribbling(resultSet.getInt(playerStatsDribbling));
            player.setCloseRangeShot(resultSet.getInt(playerStatsCloseRangeShot));
            player.setLongShot(resultSet.getInt(playerStatsLongShot));
            player.setHeader(resultSet.getInt(playerStatsHeader));
            player.setFreekicks(resultSet.getInt(playerStatsFreekicks));
            player.setPenalties(resultSet.getInt(playerStatsPenalties));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPlayerStats (Defender player) {
        try (PreparedStatement statement = connection.prepareStatement(queryPlayerStats)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            player.setPace(resultSet.getInt(playerStatsPace));
            player.setStrength(resultSet.getInt(playerStatsStrength));
            player.setBlock(resultSet.getInt(playerStatsBlock));
            player.setHeaderFight(resultSet.getInt(playerStatsHeaderFight));
            player.setInterception(resultSet.getInt(playerStatsInterception));
            player.setPositioning(resultSet.getInt(playerStatsPositioning));
            player.setShortPass(resultSet.getInt(playerStatsShortPass));
            player.setLongPass(resultSet.getInt(playerStatsLongPass));
            player.setCloseRangeShot(resultSet.getInt(playerStatsCloseRangeShot));
            player.setHeader(resultSet.getInt(playerStatsHeader));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPlayerStats (Goalkeeper player) {
        try (PreparedStatement statement = connection.prepareStatement(queryPlayerStats)) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getLastName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            player.setShortPasses(resultSet.getInt(playerStatsShortPass));
            player.setLongPasses(resultSet.getInt(playerStatsLongPass));
            player.setDiving(resultSet.getInt(playerStatsDiving));
            player.setReflexes(resultSet.getInt(playerStatsReflexes));
            player.setPenaltySaving(resultSet.getInt(playerStatsPenaltySaving));
            player.setLongShotDefending(resultSet.getInt(playerStatsLongShotDefending));
            player.setCloseRangeDefending(resultSet.getInt(playerStatsCloseRangeDefending));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Inserts and updates
    public static void insertMatch (int weekNumber, int homeTeamGoals, int awayTeamGoals, String homeTeamName, String awayTeamName){
        try (PreparedStatement statement = connection.prepareStatement(insertMatchStatement)) {
            statement.setInt(1, weekNumber);
            statement.setInt(2, homeTeamGoals);
            statement.setInt(3, awayTeamGoals);
            statement.setString(4, homeTeamName);
            statement.setString(5, awayTeamName);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't insert match " + e.getMessage());
        }
    }

    public static void insertMatchStats (String homeTeamName, String awayTeamName, String teamID, int shoots, int shotsOnTarget,
                                         int possession, int corners, int offsides,
                                         int fouls, int freekicksAmount, int penaltiesAmount,
                                         int yellowCards, int redCards, int saves,
                                         int interceptions, int blocks, int passes,
                                         int passesAccuracy, int dribbles, int dribblesWon){
        try (PreparedStatement statement = connection.prepareStatement(insertMatchStatsStatement)) {
            statement.setInt(1, queryTeamID(homeTeamName));
            statement.setInt(2, queryTeamID(awayTeamName));
            statement.setInt(3, queryTeamID(teamID));
            statement.setInt(4, shoots);
            statement.setInt(5, shotsOnTarget);
            statement.setInt(6, possession);
            statement.setInt(7, passes);
            statement.setInt(8, passesAccuracy);
            statement.setInt(9, corners);
            statement.setInt(10, offsides);
            statement.setInt(11, fouls);
            statement.setInt(12, yellowCards);
            statement.setInt(13, redCards);
            statement.setInt(14, freekicksAmount);
            statement.setInt(15, penaltiesAmount);
            statement.setInt(16, saves);
            statement.setInt(17, interceptions);
            statement.setInt(18, blocks);
            statement.setInt(19, dribbles);
            statement.setInt(20, dribblesWon);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't insert match stats " + e.getMessage());
        }
    }
    public static void insertTeamStats (Team team) {
        try (PreparedStatement statement = connection.prepareStatement(updateTeamStatsStatement)) {
            statement.setInt(1, team.getWins());
            statement.setInt(2, team.getDraws());
            statement.setInt(3, team.getLosses());
            statement.setInt(4, team.getGoalsScored());
            statement.setInt(5, team.getGoalsConceded());
            statement.setInt(6, team.getGoalsDifference());
            statement.setInt(7, team.getPoints());
            statement.setString(8, team.getName());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't insert team stats " + e.getMessage());
        }
    }
    public static void updateSimulationInformation (int weekNumber, int seasonsPlayed, int seasonsWon, String captainsName, String freekickName, String penaltyName) {
        try (PreparedStatement statement = connection.prepareStatement(updateSimulationInformation)) {
            statement.setInt(1, weekNumber);
            statement.setInt(2, seasonsPlayed);
            statement.setInt(3, seasonsWon);
            statement.setString(4, captainsName);
            statement.setString(5, freekickName);
            statement.setString(6, penaltyName);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't update simulation information " + e.getMessage());
        }
    }
    public static void updatePlayerInfo (Player player, String teamName){
        try (PreparedStatement statement = connection.prepareStatement(updatePlayerInfoStatement)) {
            int teamID = queryTeamID(teamName);
            statement.setInt(1, player.getAge());
            statement.setInt(2, player.getOverall());
            statement.setBoolean(3, player.getIsInjured());
            statement.setInt(4, player.getInjuryTime());
            statement.setBoolean(5, player.isRedCard());
            statement.setInt(6, player.getRedCardCounter());
            statement.setInt(7, player.getYellowCardCounter());
            statement.setInt(8, player.getGoalCounter());
            statement.setInt(9, player.getAssistCounter());
            statement.setString(10, player.getName());
            statement.setString(11, player.getLastName());
            statement.setInt(12, teamID);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Couldn't update player info " + e.getMessage());
        }
    }
    public static void updateNextSeasonTeamValues (String teamName) {
        try (PreparedStatement statement = connection.prepareStatement(updateNextSeasonTeamValuesStatement)) {
            statement.setString(1, teamName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't update team values" + e.getMessage());
        }
    }
}