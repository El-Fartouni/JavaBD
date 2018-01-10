/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabd;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrateur
 */
public class JavaBD {

    /**
     * @param args the command line arguments
     */
    //Appeller la fonction
//        testBD();
//        afficheEditeurs();
    public static void main(String[] args) {
        try {
//Récupération d'un connexion à la BD
            Connection cn = getDatabaseConnection();

            showSelectQuery("SELECT auteurs.nom, livres.titre FROM livres INNER JOIN auteurs ON auteurs.id = livres.auteur_id", cn);

            /* int affectedRows = addAuthor("Joseph", "Conrad");
             System.out.println("La requête concerne " + affectedRows + " lignes");
             */
 /*
            String[] noms = {"Ronsard", "Du Bellay", "Belleau", "Neruda", "Saramago"};
            String[] prenoms = {"Pierre", "Joaquim", "Remi", "Pablo", "José"};
            addAuthors(prenoms, noms);
             */
            deleteAuthor(20);

        } catch (SQLException ex) {
            Logger.getLogger(JavaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int addAuthor(String prenom, String nom) throws SQLException {
        //Récupération de la connexion
        Connection cn = getDatabaseConnection();

        //Requete SQL paramétrée
        String sql = "INSERT INTO auteurs(prenom, nom) VALUES (?,?)";

        //Création de l'objet PreparedStatement
        PreparedStatement pstm = cn.prepareStatement(sql);

        //Passage des valeurs
        pstm.setString(1, prenom);
        pstm.setString(2, nom);

        //Exécution de la requete
        return pstm.executeUpdate();

    }

    public static int deleteAuthor(int id) throws SQLException {
        //Récupération de la connexion
        Connection cn = getDatabaseConnection();

        //Requete SQL paramétrée
        String sql = "DELETE FROM auteurs WHERE id=?";

        //Création de l'objet PreparedStatement
        PreparedStatement pstm = cn.prepareStatement(sql);

        //Passage des valeurs
        pstm.setInt(1, id);

        //Exécution de la requete
        return pstm.executeUpdate();

    }

    public static void addAuthors(String[] firstNames, String[] names) throws SQLException {
        //Récupération de la connexion
        Connection cn = getDatabaseConnection();

        //Requete SQL paramétrée
        String sql = "INSERT INTO auteurs(prenom, nom) VALUES (?,?)";

        //Création de l'objet PreparedStatement
        PreparedStatement pstm = cn.prepareStatement(sql);

        //Nombre de noms
        int nbNames = names.length;

        //Boucle sur les noms
        for (int i = 0; i < nbNames; i++) {
            //Passage des valeurs
            pstm.setString(1, firstNames[i]);
            pstm.setString(2, names[i]);

            //Exécution de la requete
            pstm.executeUpdate();
        }

    }

    public static Connection getDatabaseConnection() throws SQLException {
        //Connexion à la base de données
        Connection cn = DriverManager.getConnection(
                "jdbc:mysql://localhost/bibliotheque",
                "root",
                ""
        );
        return cn;
    }

    public static void showSelectQuery(String sql, Connection cn) throws SQLException {
        //Création su statement
        Statement stm = cn.createStatement();
        //Exécution de la réqute
        ResultSet rs = stm.executeQuery(sql);

        //Obtention du nombre des colones de la requete
        //à partir des meta données de l'objet ResultSet
        int columnCount = rs.getMetaData().getColumnCount();

        StringBuilder sb = new StringBuilder();

        //Boucle sur les lignes du ResultSet
        while (rs.next()) {
            //Boucle sur les colonnes
            for (int i = 1; i <= columnCount; i++) {
                //Affichage de la valeur d'une colonne
                sb.append(rs.getString(i));
                sb.append("\t");
            }

            //Retour à la ligne
            sb.append(System.lineSeparator());
        }

        //Affichage du resultat
        System.out.println(sb.toString());
    }

    public static void testBD() {
        try {
            //Connexion à la base de données
            Connection cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bibliotheque",
                    "root",
                    ""
            );
            //Requête SQL à exécuter
            String sql = "SELECT prenom, nom FROM auteurs";
            //StringBuilder pour afficher le résultat*
            StringBuilder sb = new StringBuilder();
            //Création d'un objet statement
            //nécéssaire pour exécuter une réquete
            Statement stm = cn.createStatement();
            //Exécution de la réquete
            //Retourne un objet ResultSet
            //qui contient le resultat de la requete SELECT
            ResultSet rs = stm.executeQuery(sql);
            //Boucler sur tout le contenu du resultset
            //tant que rs.next() retourne true
            String prenom;
            while (rs.next()) {
                prenom = rs.getString("prenom");
                if (!prenom.isEmpty()) {
                    sb.append(prenom);
                    sb.append(" ");
                }
                sb.append(rs.getString("nom"));
                //Ajout du séparateur de ligne \n ou \r\n
                sb.append(System.lineSeparator());
            }
            //Affichage du résultat
            System.out.println(sb.toString());
        } catch (SQLException ex) {
            Logger.getLogger(JavaBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficheEditeurs() throws SQLException {
        try {
            //Connexion à la base de données
            Connection cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bibliotheque",
                    "root",
                    ""
            );

            //Requête SQL à exécuter
            String sql = "SELECT nom FROM editeurs";
            //StringBuilder pour afficher le résultat*
            StringBuilder sb = new StringBuilder();

            //Création d'un objet statement
            //nécéssaire pour exécuter une réquete
            Statement stm = cn.createStatement();
            //Exécution de la réquete
            //Retourne un objet ResultSet
            //qui contient le resultat de la requete SELECT
            ResultSet rs = stm.executeQuery(sql);

            //Boucler sur tout le contenu du resultset
            //tant que rs.next() retourne true
            String nom;
            while (rs.next()) {
                nom = rs.getString("nom");
                sb.append(nom);
                sb.append(" ");

                //Ajout du séparateur de ligne \n ou \r\n
                sb.append(System.lineSeparator());
            }

            //Affichage du résultat
            System.out.println(sb.toString());

        } catch (SQLException ex) {
            Logger.getLogger(JavaBD.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int deleteAuthor(String brin, String david) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
