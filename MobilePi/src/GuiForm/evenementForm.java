/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import Entity.Session;
import Entity.user;
import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import Entity.Event;
import Services.ServicesEvent;
import com.codename1.ui.layouts.FlowLayout;
import Entity.participant;

/**
 *
 * @author asus
 */
public class evenementForm extends Form{
        static Form currentForm;

    private EncodedImage placeHolder;
    user User = Session.getCurrentSession();
    public evenementForm( Resources theme) {

        currentForm = this;
        currentForm.setTitle("Event");
        currentForm.setLayout(BoxLayout.y());
       

        for (Event c : ServicesEvent.getInstance().getAllEvents()) {
            Container InfoContainer = new Container(BoxLayout.y());
            Label nomEvent = new Label(c.getNomEvent());
            Label AddEvent = new Label(c.getAdresse());
            Label typee = new Label(c.getType());
            Label prix = new Label(String.valueOf(c.getPrix()));
            Label Nombreplace = new Label(String.valueOf(c.getNbPlaces()));
            Label description = new Label(c.getDescription());
            //Label email = new Label(c.getEmail());
            //Label tel = new Label(String.valueOf(c.getTel()));
            //Label Date = new Label(c.getDate());

            InfoContainer.add(nomEvent);
            InfoContainer.add(AddEvent);
            InfoContainer.add(typee);
            InfoContainer.add(prix);
            InfoContainer.add(Nombreplace);
            InfoContainer.add(description);
            Container Container = new Container(BoxLayout.x());
                  nomEvent.getUnselectedStyle().setFgColor(0x000000);
        AddEvent.getUnselectedStyle().setFgColor(0x000000);
        Nombreplace.getUnselectedStyle().setFgColor(0x000000);
        prix.getUnselectedStyle().setFgColor(0x000000);
        typee.getUnselectedStyle().setFgColor(0x000000);
        description.getUnselectedStyle().setFgColor(0x000000);

            placeHolder = EncodedImage.createFromImage(theme.getImage("bla.jpg"), true);
            String url = "http://localhost/backt/web/" + c.getImage();
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl(url);
            URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

            ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));
            Container.add(img);
            Container.add(InfoContainer);
            currentForm.add(Container);

            img.addPointerReleasedListener(ev -> {
                EventDetail(c, theme).show();
            });
             currentForm.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new MenuForm(theme).showBack();
        });
        }

    

      
    }
 public Form EventDetail(Event c, Resources theme) {

        Form EventDetail = new Form(c.getNomEvent(), BoxLayout.y());

        placeHolder = EncodedImage.createFromImage(theme.getImage("bla.jpg"), true);
        String url = "http://localhost/backt/web/" + c.getImage();
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl(url);
        URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

        ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));

        Label nom = new Label("nom");
        Label address = new Label("adress");
        Label type = new Label("type");
        Label prix = new Label("prix");
        Label nombreplace = new Label("nombreplace");
        Label description = new Label("description");
        //Label image = new Label("image");

        //SpanLabel Message = new SpanLabel("Descrption: \n" + c.getDescription() + "\n" + "Created AT: " + c.getDate() + "\n" + "Members: " + c.getNombreplace());
        TextField EventNameField = new TextField(null, "Name");

        EventNameField.setText(c.getNomEvent());

        TextField nomeve = new TextField(null, "nom");
        nomeve.setText(c.getNomEvent());
        TextField addresseve = new TextField(null, "Adresse");
        addresseve.setText(String.valueOf(c.getAdresse()));
        TextField typeeve = new TextField(null, "Type");
        typeeve.setText(c.getType());
        TextField prixeve = new TextField(null, "Price");
        prixeve.setText(String.valueOf(c.getPrix()));
        TextField nombreplaceeve = new TextField(null, "places");
        nombreplaceeve.setText(String.valueOf(c.getNbPlaces()));
        TextField descriptioneve = new TextField(null, "description");
        descriptioneve.setText(c.getDescription());
           nom.getUnselectedStyle().setFgColor(0x000000);
        address.getUnselectedStyle().setFgColor(0x000000);
        nombreplace.getUnselectedStyle().setFgColor(0x000000);
        prix.getUnselectedStyle().setFgColor(0x000000);
        type.getUnselectedStyle().setFgColor(0x000000);
        description.getUnselectedStyle().setFgColor(0x000000);
        nomeve.getUnselectedStyle().setFgColor(0x000000);
        typeeve.getUnselectedStyle().setFgColor(0x000000);
        prixeve.getUnselectedStyle().setFgColor(0x000000);
        nombreplaceeve.getUnselectedStyle().setFgColor(0x000000);
        descriptioneve.getUnselectedStyle().setFgColor(0x000000);
        addresseve.getUnselectedStyle().setFgColor(0x000000);

        Container Container = new Container(new FlowLayout());
        Container.addAll(nom, nomeve, address, addresseve, type, typeeve, prix, prixeve, nombreplace, nombreplaceeve, description, descriptioneve);
        EventDetail.add(img);
        Button particper = new Button("participer");

        EventDetail.add(particper);
        
        EventDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        EventDetail.add(ButtonsContainer);
        
     
        particper.addActionListener(ev -> {

            participant p = new participant();
            p.setEvenement(c.getId());
            p.setNom(User.getUsername());
            p.setPrenom(User.getEmail());
            if(c.getNbPlaces()>0){
            if (ServicesEvent.getInstance().AjouterParticipant(p)) {
                Dialog.show("Success", "particpant ajouté", new Command("OK"));
                new evenementForm( theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }}
            else { Dialog.show("ERROR", "y'en a plus de place pour cet evenement", new Command("OK"));}
        });
        EventDetail.revalidate();

        EventDetail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new evenementForm( theme).show();
        });

        return EventDetail;
    }

   
    
    
}
