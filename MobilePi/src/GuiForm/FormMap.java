/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.ui.util.Resources;
import com.codename1.googlemaps.MapContainer;
import com.codename1.ui.Form;
import GuiForm.evenementForm;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import GuiForm.FormMap;

/**
 *
 * @author asus
 */
public class FormMap extends BaseForm{
Form f = new Form();
   static Form act;
   Resources res ; 
   Form tt= this ; 
    public FormMap(Resources theme) {
               act = this;
    act.getToolbar().addCommandToOverflowMenu("", null, ev -> {
    Listevent(res).show();
        });
         MapContainer cnt = new MapContainer("AIzaSyAvlZjEkXM2b-JwgAvHbq1ytphq2e96zJQ");
         Resources res = null ; 
      

        Button btnMoveCamera = new Button("enregistrer et continuer");
        btnMoveCamera.addActionListener((ActionEvent e)->{
       new evenementForm(res).show();
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

               cnt.addTapListener(e->{
    
            
                cnt.clearMapLayers();
                cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(e.getX(), e.getY()),
                        ""+cnt.getCameraPosition().toString(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+cnt.getName(), FontImage.MATERIAL_PLACE);
                        }
                );
             ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("http://maps.google.com/maps/api/geocode/json?latlng="+cnt.getCameraPosition().getLatitude()+","+cnt.getCameraPosition().getLongitude()+"&oe=utf8&sensor=false");
                    // NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
            String ch="";
          //  b.setRegion_chasse(ch);

            
        });
                    Button retour = new Button("Ajouter sur la carte  ");
            //    add(retour);
                retour.addActionListener(e -> {
                   Listevent(res).show();
                // Listevent mapf=new Listevent(res);
                 //tt.showBack();
                });
 
        Container root = new Container();
        f.setLayout(new BorderLayout());
         f.addComponent(BorderLayout.CENTER, cnt);
     f.addComponent(BorderLayout.SOUTH, btnMoveCamera);
f.show();

    
    }
    
        private Form Listevent(Resources res) {
        
         evenementForm t = new evenementForm(res);
      //  Form saisonn = t.execute();
    //  Class cls = testt.class;

        t.getToolbar().addMaterialCommandToLeftBar("retour", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new evenementForm(res).show();
        });

        return t;
    }
    
}
