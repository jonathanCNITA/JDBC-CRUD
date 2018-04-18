package game;


import java.sql.*;
import java.util.Scanner;

/* Test CRUD for the table personnage in BDD dungeon */

public class Game {

    private static Boolean play = true;
    private static Scanner scInt = new Scanner(System.in);
    private static Scanner scStr = new Scanner(System.in);
    private static Connection conn;

    public static void main(String[] args) 
    {


        System.out.println("test DB");

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:mysql://localhost:3306/dungeon";
            String user = "user";
            String passwd = "123";

            conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion effective !");

            while (play)
            {
                System.out.println("MENU\n1- CREATE \n2- READ \n3- UPDATE \n4- DELETE \n5- SHOW ALL");
                int choixUtilisateur = scInt.nextInt();
                switch (choixUtilisateur) {
                    case 1:
                        //-- creer Un personnage
                        createPersonnage();
                        break;
                    case 2:
                        //-- afficher un personnage
                        readPersonnage();
                        break;
                    case 3:
                        //-- Changer le nom du personnage
                        updatePersonnage();
                        break;
                    case 4:
                        deletePersonage();
                        break;
                    case 5:
                        showPersonage();
                        break;
                    default:
                        System.out.println("Make a valide choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    /*----------------------------------------*/
    public static void createPersonnage()
    {
         Scanner scInteger = new Scanner(System.in);
         Scanner scString = new Scanner(System.in);

         String name;
         int life, type;

         System.out.println("CREATE A PERSONNAGE:");
         System.out.println("Set a Name:");
         name = scString.nextLine();

         System.out.println("Set life:");
         life = scInteger.nextInt();

         System.out.println("Set Type (1- guerrier 2- Magicien:");
         type = scInteger.nextInt();


        try
        {
            PreparedStatement newPersonnage = conn.prepareStatement("INSERT  into personnage(name, life, type) VALUE (?,?,?)");
            newPersonnage.setString(1,name);
            newPersonnage.setInt(2,life);
            newPersonnage.setInt(3,type);
            newPersonnage.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void readPersonnage()
    {
        System.out.println("READ A PERSONNAGE:");
        System.out.println("Set an ID to read:");
        int id = scInt.nextInt();
        try
        {
            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM personnage WHERE id = " + id);
            ResultSetMetaData resultMeta = result.getMetaData();
            System.out.println("\n**********************************");

            for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

            System.out.println("\n**********************************");

            while(result.next())
            {
                for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + result.getObject(i).toString() + "\t |");

                System.out.println("\n---------------------------------");

            }
            result.close();
            state.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public static void updatePersonnage()
    {
        System.out.println("UPDATE A PERSONNAGE:");
        System.out.println("Set an ID to update:");
        int id = scInt.nextInt();
        try
        {
            System.out.println("Change name: ");
            String newName = scStr.nextLine();

            System.out.println("Change life: ");
            int newLife = scInt.nextInt();
            PreparedStatement newPersonnage = conn.prepareStatement("UPDATE personnage SET name=?, life = ? where id=?");
            newPersonnage.setString(1,newName);
            newPersonnage.setInt(2,newLife);
            newPersonnage.setInt(3,id);
            newPersonnage.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public static void deletePersonage()
    {
        System.out.println("DELETE A PERSONNAGE:");
        System.out.println("Set an ID to delete:");
        int id = scInt.nextInt();
        try
        {
            PreparedStatement deletePersonnage = conn.prepareStatement("DELETE FROM personnage WHERE personnage.id = ?");
            deletePersonnage.setInt(1,id);
            deletePersonnage.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void showPersonage()
    {
        System.out.println("SHOW PERSONNAGE TABLE:");
        try
        {

            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM personnage");
            ResultSetMetaData resultMeta = result.getMetaData();
            System.out.println("\n**********************************");

            for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

            System.out.println("\n**********************************");

            while(result.next())
            {
                for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + result.getObject(i).toString() + "\t |");

                System.out.println("\n---------------------------------");

            }
            result.close();
            state.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}