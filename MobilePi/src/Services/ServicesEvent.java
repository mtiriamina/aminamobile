/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import Entity.Event;
import Entity.participant;
import utils.Statics;
import java.io.IOException;
//import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Entity.user;
import java.util.Map;

/**
 *
 * @author asus
 */
public class ServicesEvent {
    
      public ArrayList<Event> events;
    public  String  result="";
    public static ServicesEvent instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicesEvent() {
         req = new ConnectionRequest();
    }

    public static ServicesEvent getInstance() {
        if (instance == null) {
            instance = new ServicesEvent();
        }
        return instance;
    }
    
    public ArrayList<Event> parseevents(String jsonText){
        try {
            events=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Event c = new Event();
                float IdEvent = Float.parseFloat(obj.get("id").toString());
                  c.setId((int)IdEvent);
                  c.setNomEvent(obj.get("nomEvent").toString());
                  c.setAdresse(obj.get("adresse").toString()); 
                  c.setType(obj.get("type").toString());
                  float PriceEvent = Float.parseFloat(obj.get("prix").toString());
                  c.setPrix((int)PriceEvent);
                  float NbrPlace = Float.parseFloat(obj.get("nbPlaces").toString());
                  c.setNbPlaces((int)NbrPlace);
                  c.setDescription(obj.get("description").toString());
                  c.setImage(obj.get("image").toString());
                  //Timestamp DateD = Timestamp.parseTimestamp(obj.get("DateD").toString());
                 // c.setDateD((Timestamp)DateD);
                 // c.setDateD(obj.get("DateD").toString());
                 /* DateFormat formatter;
                 formatter = new SimpleDateFormat("dd/MM/yyyy");
                 Date date;
                try {
                    date = (Date) formatter.parse(obj.get("DateD").toString());
                     java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
               c.setDateD(timeStampDate);
                } catch (ParseException ex) {
                    ex.getMessage();
                    
                }
             
                try {
                    date = (Date) formatter.parse(obj.get("DateF").toString());
                     java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
               c.setDateF(timeStampDate);
                } catch (ParseException ex) {
                    ex.getMessage();
                    
                }*/
              
                  
                 
                 
                events.add(c);
            }
            
            
        } catch (IOException ex) {
            
        }
       
        return events;
    }
    
    public ArrayList<Event> getAllEvents(){
        String url = Statics.BASE_URL+"ListEventMobile";     
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseevents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);     
                return events;
    }
    
    public boolean AddEvent(Event c) {
       String latlng="amina";
        String url = Statics.BASE_URL + "AddEventMobile/?nomEvent=" + c.getNomEvent()+"&latlng=" + latlng+ "&adresse=" + c.getAdresse()+ "&type=" + c.getType()+ "&prix=" + c.getPrix()+ "&nbPlaces=" + c.getNbPlaces()+ "&description=" + c.getDescription()+ "&image=" + c.getImage(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
      public boolean AjouterParticipant(participant p) {
          boolean confirmation =true;
        String url = Statics.BASE_URL + "ajouterParticipantMobile/?idevent=" + p.getEvenement()+"&nom=" + p.getNom()+ "&prenom=" + p.getPrenom()+ "&confirmation=" + confirmation; //création de l'URL
          System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    public String DeleteEvent(Event c) {
          String url = Statics.BASE_URL + "deleteEventMobile/?id=" + c.getId();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        System.out.println(url);
                   

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    String data = new String(req.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> tasksListJson;
                    tasksListJson = j.parseJSON(new CharArrayReader(data.toCharArray()));
                   result=(String) tasksListJson.get("body");

                } catch (IOException ex) {
                    ex.getMessage();
                }
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    
    public boolean EditEvent(Event c) {
        String url = Statics.BASE_URL + "EditEventMobile/?id="+c.getId()+"&nomEvent=" + c.getNomEvent()+ "&adresse=" + c.getAdresse()+ "&type=" + c.getType()+ "&prix=" + c.getPrix()+ "&nbPlaces=" + c.getNbPlaces()+ "&description=" + c.getDescription(); //création de l'URL
            System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


public boolean envoiemail(user u) {
        String url = Statics.BASE_URL + "MailMobile/?mail="+u.getEmail(); //création de l'URL
            System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }





    
}
