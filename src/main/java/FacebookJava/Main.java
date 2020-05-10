/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookJava;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ulises Castañeda
 */
public class Main {
    private static final String CONFIG_FILE = "fbcmd4j.properties";
    private static Scanner scan = new Scanner(System.in);
    private static Properties props = new Properties();
    private static Facebook fb = null;
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException, FacebookException 
    {
        Main main = new Main();
        main.SetProperties();
        main.SetFacebook();
        
        
         while(true) {
            int option;
            System.out.println(":---------Ingresa la opcion que necesitas:---------\n");
            logger.info("App Iniciada");
            System.out.println("NewsFeed :   --------- 1\n");
            System.out.println("Wall:        --------- 2\n");
            System.out.println("Estado:      --------- 3\n");
            System.out.println("Link:        --------- 4\n");
            System.out.println("Salir:       --------- 5\n");
            
            option = scan.nextInt();
            scan.nextLine();
            switch (option) 
            {
                case 1:
                    try
                    {
                        System.out.println("Obteniendo NewsFeed");
                        ResponseList<Post> newsFeed = fb.getFeed("ucastaneda21");
                        for (Post p : newsFeed)
                        {
                            Tools.printPost(p);
                        }
                        logger.info("NewsFeed cargado correctamente.");
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error en NewsFeed:  " + ex.getMessage());
                    }
                    break;
                case 2:
                    try
                    {
                        System.out.println("Mostrando Wall...");
                        ResponseList<Post> wall = fb.getPosts();
                        for (Post p : wall) {
                            Tools.printPost(p);
                        }
                        logger.info("Wall Cargado correctamente.");
                        }
                    catch(Exception ex)
                    {
                        logger.info("Error en Wall: " + ex.getMessage());
                    }
                    break;
                case 3:
                    try
                    {
                        System.out.println("Ingrese el texto a publicar.");
                        String status = scan.nextLine();
                        fb.postStatusMessage(status);
                    }
                    catch(Exception ex)
                    {
                        logger.info("Error al hacer publicacion: " + ex.getMessage());
                    }
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("-----Saliendo del progrma-----");
                    System.exit(0);
            }
        }
    }
    
    
    void SetProperties() throws IOException
    {
        try
        {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            props.load(inStream);
            logger.info("Propiedades Correctas.");
        }
        catch(Exception ex)
        {
            logger.info("Error en las propiedades:  " + ex.getMessage());
        }
    }
    
    void SetFacebook()
    {
        try
        {
            fb = new FacebookFactory().getInstance();
            fb.setOAuthAppId(props.getProperty("oauth.appId"), props.getProperty("oauth.appSecret"));
            fb.setOAuthPermissions(props.getProperty("oauth.permissions"));
            if(props.getProperty("oauth.accessToken") != null)
            {
                fb.setOAuthAccessToken(new AccessToken(props.getProperty("oauth.accessToken"), null));
                logger.info("Facebook properties correctas");
            }
        }
        catch(Exception ex)
        {
            logger.info("Error al inicializar Facebook:  " + ex.getMessage());
        }
        
    }
}