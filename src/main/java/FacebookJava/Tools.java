/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FacebookJava;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.auth.AccessToken;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Ulises Castañeda
 */
public class Tools {
    private InputStream inputStream = null;
    
    public InputStream getStream(String fileName)
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        return stream;
    }
    
    
    public static Facebook InstanceFacebook(Properties props) {
        Facebook fb = new FacebookFactory().getInstance();
        try
        {
            fb.setOAuthAppId(props.getProperty("oauth.appId"), props.getProperty("oauth.appSecret"));
            fb.setOAuthPermissions(props.getProperty("oauth.permissions"));
            fb.setOAuthAccessToken(new AccessToken(props.getProperty("oauth.accessToken"), null));
            return fb;
        }
        catch(Exception ex )
        {
            return fb;
        }
    }
    
    public static void printPost(Post p) {
        if(p.getStory() != null)
            System.out.println("Story: " + p.getStory());
        if(p.getMessage() != null)
            System.out.println("Mensaje: " + p.getMessage());
        System.out.println("--------------------------------");
    }
}
