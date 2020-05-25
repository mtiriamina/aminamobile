/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;


//import Services.MembreService;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.spinner.DateTimeSpinner;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author debba
 */
public class SignUpForm extends BaseForm{
        public static TextField nom,telephone,adresse,email,surnom,motdepasse,grade,picker;
        public static Picker dPicker ;
    public static ComboBox box;
    //MembreService auth = new MembreService();
        
    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        nom = new TextField("", "Nom", 20, TextField.ANY);
     
                telephone = new TextField("", "Telephone", 20, TextField.PHONENUMBER);
                   adresse = new TextField("", "Adresse", 20, TextField.ANY);
        email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
           surnom = new TextField("", "Surnom", 20, TextField.ANY);
        motdepasse = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        picker = new TextField();
        dPicker = new Picker();
    
        box = new ComboBox();
        nom.setSingleLineTextArea(true);

               telephone.setSingleLineTextArea(true);
               adresse.setSingleLineTextArea(true);
        email.setSingleLineTextArea(true);
          surnom.setSingleLineTextArea(true);
            motdepasse.setSingleLineTextArea(true);
        
        Validator val = new Validator();
        
                val.addConstraint(nom, new LengthConstraint(4));
                String testusername="^\\(?([a-z]{3})\\)?";
               val.addConstraint(nom, new RegexConstraint(testusername, ""));
               
                 val.addConstraint(telephone, new LengthConstraint(11));
                 String testtel="^\\(?([216]{3})\\)?([ .-]?)([0-9]{8})";
               val.addConstraint(telephone, new RegexConstraint(testtel, "Must be valid phone number"));
               
                 val.addConstraint(adresse, new LengthConstraint(20));
               String testaddress="^\\(?([Tunisia ]{8})\\)?";
               val.addConstraint(adresse, new RegexConstraint(testaddress, "Must be valid adresse"));
               
                val.addConstraint(email, RegexConstraint.validEmail());
               
                 val.addConstraint(surnom, new LengthConstraint(20));
                String testsurnom="^\\(?([a-z]{3})\\)?";
               val.addConstraint(surnom, new RegexConstraint(testsurnom, ""));
               
                val.addConstraint(motdepasse, new LengthConstraint(4));
       
             
         
        Button next = new Button("Inscription");
        Button signIn = new Button("Se connecter");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Vous avez déjà un compte?");
        alreadHaveAnAccount.getUnselectedStyle().setFgColor(0xff0000);
      
        Container content = BoxLayout.encloseY(
                new Label("S'inscrire", "LogoLabel"),
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(telephone),
                createLineSeparator(),
                 new FloatingHint(adresse),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                dPicker,
                createLineSeparator(),
                 new FloatingHint(surnom),
                createLineSeparator(),
                new FloatingHint(motdepasse),
                createLineSeparator(),
                createLineSeparator()
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        //e -> new ActivateForm(res).show()
        next.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            //    auth.RegisterUser(res);
               
            }
        });
        
          dPicker.addActionListener(ev -> {
               picker.setText(pickerToString(dPicker));  
               System.out.println(picker.getText());
        });
    }
    public static String pickerToString(Picker p) {

        Date s = p.getDate(); //dPicker howa l picker
        //System.out.println(s);
        Calendar c = Calendar.getInstance();
        c.setTime(s);
        String m1 = Integer.toString(c.get(Calendar.MONTH) + 1);
        String y1 = Integer.toString(c.get(Calendar.YEAR));
        String d1 = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String date = y1 + "-" + m1 + "-" + d1;
        return date;
    }
}
