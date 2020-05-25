/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

//import Entity.Membre;
//import Services.MembreService;
import Entity.user;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import Services.ServicesUsers;
import Entity.Session;


/**
 *
 * @author debba
 */
public class SignInForm_1 extends BaseForm{
     public static Form f;
      private static user User;
       

     
  //    static Membre connectedUser=new Membre();
//    MembreService auth = new MembreService();
    public static TextField username,password;
    public static Resources res,theme;  
        public SignInForm_1(Resources res) {
        super(new BorderLayout());
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
         
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");
        

        username = new TextField("", "username", 20, TextField.ANY);
        password = new TextField("", "Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(true);
        password.setSingleLineTextArea(true);
        
        username.getUnselectedStyle().setFgColor(0x000000);
        password.getUnselectedStyle().setFgColor(0x000000);
        Button signIn = new Button("Se connecter");
        Button signUp = new Button("S'inscrire");
      
       
        
        signIn.getUnselectedStyle().setFgColor(0xffffff);
        signUp.getUnselectedStyle().setFgColor(654111);
     
        
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
      
        Label doneHaveAnAccount = new Label("Vous n'avez pas de compte?");
         Label dokk = new Label("tr");
        doneHaveAnAccount.getUnselectedStyle().setFgColor(0xff0000);
        Validator val = new Validator();

 
       val.addConstraint(password, new LengthConstraint(4));
   

        Container content = BoxLayout.encloseY(
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
              
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
      

 
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
             User = ServicesUsers.getInstance().Login(username.getText(), password.getText());
                    if (User != null) {
                        
                        username.setText("");
                        password.setText("");
                        Session.start(User);
                        
                        if (!User.getRoles().equals("CLIENT")) {
                            new interfacGuide(res).show();
                            System.out.println("guide"); 
                       } else {
                         // new Client(current,theme).show();
                              new MenuForm(res).show();
                            System.out.println("client");

                        }
                    } else {
                        Dialog.show("Alert", "This is already your home", "OK", null);
                    }

            }
        });
    }
}
