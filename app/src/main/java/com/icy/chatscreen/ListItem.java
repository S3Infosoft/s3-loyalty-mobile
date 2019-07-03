package com.icy.chatscreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.google.android.material.snackbar.Snackbar.*;
import java.util.Arrays;




public class ListItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {




    FirebaseAuth firebaseAuth;


    private DatePickerDialog.OnDateSetListener datePickerDialog,marriagedatepicker;
    public EditText fn,ln,phn,email,pass,cnfpass;
    public Button register;
    public TextView bddate,mrgdate;
    public Spinner city;
    public String tempbdate,tempmrgdate;
    public String selectedcity="";
    public String[] citiesofindia = new String[]{"Bhandup", "Mumbai", "Visakhapatnam", "Coimbatore", "Delhi", "Bangalore", "Pune", "Nagpur", "Lucknow", "Vadodara", "Indore", "Jalalpur", "Bhopal", "Kolkata", "Kanpur", "New Delhi", "Faridabad", "Rajkot", "Ghaziabad", "Chennai", "Meerut", "Agra", "Jaipur", "Jabalpur", "Varanasi", "Allahabad", "Hyderabad", "Noida", "Howrah", "Thane", "Patiala", "Chakan", "Ahmedabad", "Manipala", "Mangalore", "Panvel", "Udupi", "Rishikesh", "Gurgaon", "Mathura", "Shahjahanpur", "Bagpat", "Sriperumbudur", "Chandigarh", "Ludhiana", "Palakkad", "Kalyan", "Valsad", "Ulhasnagar", "Bhiwani", "Shimla", "Dehradun", "Patna", "Unnao", "Tiruvallur", "Kanchipuram", "Jamshedpur", "Gwalior", "Karur", "Erode", "Gorakhpur", "Ooty", "Haldwani", "Bikaner", "Puducherry", "Nalbari", "Bellary", "Vellore", "Naraina", "Mandi", "Rupnagar", "Jodhpur", "Roorkee", "Aligarh", "Indraprast", "Karnal", "Tanda", "Amritsar", "Raipur", "Pilani", "Bilaspur", "Srinagar", "Guntur", "Kakinada", "Warangal", "Tirumala - Tirupati", "Nizamabad", "Kadapa", "Kuppam", "Anantpur", "Nalgonda", "Potti", "Nellore", "Rajahmundry", "Bagalkot", "Kurnool", "Secunderabad", "Mahatma", "Bharuch", "Miraj", "Nanded", "Anand", "Gandhinagar", "Bhavnagar", "Morvi", "Aurangabad", "Modasa", "Patan", "Solapur", "Kolhapur", "Junagadh", "Akola", "Bhuj", "Karad", "Jalgaon Jamod", "Chandrapur", "Maharaj", "Dhule", "Ponda", "Dahod", "Navsari", "Panjim", "Patel", "Nashik", "Amravati", "Somnath", "Ganpat", "Karwar", "Davangere", "Raichur", "Nagara", "Kushalnagar", "Hassan", "Hubli", "Bidar", "Belgaum", "Mysore", "Dharwad", "Kolar", "TumkÅ«r", "Tiruchi", "Thiruvananthapuram", "Kozhikode", "Thrissur", "Madurai", "Thalassery", "Kannur", "Karaikudi", "Thanjavur", "Manor", "Idukki", "Thiruvarur", "Alappuzha", "Gandhigram", "Kochi", "Annamalainagar", "Amet", "Kottarakara", "Kottayam", "Tirunelveli", "Mohan", "Salem", "Attingal", "Chitra", "Chengannur", "Guwahati", "Kalam", "Ranchi", "Shillong", "Gangtok", "Srikakulam", "Tezpur", "Bhubaneswar", "Imphal", "Sundargarh", "Arunachal", "Manipur", "Bihar Sharif", "Mandal", "Dibrugarh", "Darbhanga", "Gaya", "Bhagalpur", "Kunwar", "Barddhaman", "Jadabpur", "Kalyani", "Cuttack", "Barpeta", "Jorhat", "Kharagpur", "Medinipur", "Agartala", "Saranga", "Machilipatnam", "Dhanbad", "Silchar", "Dumka", "Kokrajhar", "Bankura", "Jalpaiguri", "Durgapur", "Kalinga", "Palampur", "Jammu", "Dwarka", "Faridkot", "Udaipur", "Raigarh", "Hisar", "Solan", "Ajmer", "Lala", "Gurdaspur", "Sultanpur", "Jhansi", "Vidisha", "Jagdalpur", "Dipas", "Sawi", "Etawah", "Saharanpur", "Ujjain", "Kangra", "Bhilai", "Rohtak", "Haryana", "Ambala", "Bareilly", "Bhoj", "Kapurthala Town", "Sangrur", "Pusa", "Sagar", "Rewa", "Bhawan", "Rampur", "Bhadohi", "Cuddalore", "Khopoli", "Bali", "Bhiwandi", "Vasai", "Badlapur", "Sambalpur", "Raurkela", "Brahmapur", "Visnagar", "Surendranagar", "Ankleshwar", "Dahanu", "Silvassa", "Jamnagar", "Dhansura", "Muzaffarpur", "Wardha", "Bodhan", "Parappanangadi", "Malappuram", "Vizianagaram", "Mavelikara", "Pathanamthitta", "Satara", "Janjgir", "Gold", "Himatnagar", "Bodinayakkanur", "Gandhidham", "Mahabalipuram", "Nadiad", "Virar", "Bahadurgarh", "Kaithal", "Siliguri", "Tiruppur", "Ernakulam", "Jalandhar", "Barakpur", "Kavaratti", "Ratnagiri", "Moga", "Hansi", "Sonipat", "Bandra", "Aizawl", "Itanagar", "Nagar", "Ghatkopar", "Chen", "Powai", "Bhimavaram", "Bhongir", "Medak", "Karimnagar", "Narsapur", "Vijayawada", "Markapur", "Mancherial", "Sangli", "Moradabad", "Alipur", "Ichalkaranji", "Devgarh", "Yavatmal", "Hinganghat", "Madgaon", "Verna", "Katra", "Bilaspur", "Uttarkashi", "Muktsar", "Bhatinda", "Pathankot", "Khatauli", "Vikasnagar", "Kollam", "Kovilpatti", "Kovvur", "Paloncha", "Vasco", "Alwar", "Bijapur", "Tinsukia", "Ratlam", "Kalka", "Ladwa", "Rajpura", "Batala", "Hoshiarpur", "Katni", "Bhilwara", "Jhajjar", "Lohaghat", "Mohali", "Dhuri", "Thoothukudi", "Sivakasi", "Coonoor", "Shimoga", "Kayamkulam", "Namakkal", "Dharmapuri", "Aluva", "Antapur", "Tanuku", "Eluru", "Balasore", "Hingoli", "Quepem", "Assagao", "Betim", "Cuncolim", "Ahmednagar", "Goa", "Caranzalem", "Chopda", "Petlad", "Raipur", "Villupuram", "Shoranur", "Dasua", "Gonda", "Yadgir", "Palladam", "Nuzvid", "Kasaragod", "Paonta Sahib", "Sarangi", "Anantapur", "Kumar", "Kaul", "Panipat", "Uppal", "Teri", "Tiruvalla", "Jamal", "Chakra", "Narasaraopet", "Dharamsala", "Ranjan", "Garhshankar", "Haridwar", "Chinchvad", "Narela", "Aurangabad", "Sion", "Kalamboli", "Chittoor", "Wellington", "Nagapattinam", "Karaikal", "Pollachi", "Thenkasi", "Aranmula", "Koni", "Ariyalur", "Ranippettai", "Kundan", "Lamba Harisingh", "Surana", "Ghana", "Lanka", "Kataria", "Kotian", "Khan", "Salt Lake City", "Bala", "Vazhakulam", "Paravur", "Nabha", "Ongole", "Kaladi", "Jajpur", "Thenali", "Mohala", "Mylapore", "Bank", "Khammam", "Ring", "Maldah", "Kavali", "Andheri", "Baddi", "Mahesana", "Nila", "Gannavaram", "Cumbum", "Belapur", "Phagwara", "Rander", "Siuri", "Bulandshahr", "Bilimora", "Guindy", "Pitampura", "Baharampur", "Dadri", "Boisar", "Shiv", "Multi", "Bhadath", "Ulubari", "Palghar", "Puras", "Sikka", "Saha", "Godhra", "Dam Dam", "Ekkattuthangal", "Sahibabad", "Kalol", "Bardoli", "Wai", "Shirgaon", "Nehra", "Mangalagiri", "Latur", "Kottakkal", "Rewari", "Ponnani", "Narayangaon", "Hapur", "Kalpetta", "Khurja", "Ramnagar", "Neral", "Sendhwa", "Talegaon Dabhade", "Kargil", "Manali", "Jalalabad", "Palani", "Sirkazhi", "Krishnagiri", "Hiriyur", "Muzaffarnagar", "Kashipur", "Gampalagudem", "Siruseri", "Manjeri", "Raniganj", "Mahim", "Bhusawal", "Tirur", "Sattur", "Angul", "Puri", "Khurda", "Dharavi", "Ambur", "Vashi", "Arch", "Colaba", "Hosur", "Kota", "Hugli", "Anantnag", "Murshidabad", "Jharsuguda", "Jind", "Neyveli", "Vaniyambadi", "Srikalahasti", "Liluah", "Pali", "Bokaro", "Sidhi", "Asansol", "Darjeeling", "Kohima", "Shahdara", "Chandannagar", "Nadgaon", "Haripad", "Sitapur", "Vapi", "Bambolim", "Baidyabati", "Connaught Place", "Singtam", "Shyamnagar", "Sikar", "Choolai", "Mayapur", "Puruliya", "Habra", "Kanchrapara", "Goregaon", "Tiptur", "Kalpakkam", "Serampore", "Konnagar", "Port Blair", "Canning", "Mahad", "Alibag", "Pimpri", "Panchgani", "Karjat", "Vaikam", "Mhow", "Lakhimpur", "Madhoganj", "Kheri", "Gudivada", "Avanigadda", "Nayagarh", "Bemetara", "Bhatapara", "Ramgarh", "Dhubri", "Goshaingaon", "Bellare", "Puttur", "Narnaul", "Porbandar", "Keshod", "Dhrol", "Kailaras", "Morena", "Deolali", "Banda", "Orai", "Fatehpur", "Mirzapur", "Adilabad", "Pithapuram", "Ramavaram", "Amalapuram", "Champa", "Ambikapur", "Korba", "Pehowa", "Yamunanagar", "Shahabad", "Hamirpur", "Gulbarga", "Sagar", "Bhadravati", "Sirsi", "Honavar", "Siruguppa", "Koppal", "Gargoti", "Kankauli", "Jalna", "Parbhani", "Koraput", "Barpali", "Jaypur", "Banswara", "Tindivanam", "Mettur", "Srirangam", "Deoria", "Basti", "Padrauna", "Budaun", "Bolpur", "Gujrat", "Balurghat", "Binnaguri", "Guruvayur", "Chandauli", "Madikeri", "Piduguralla", "Vinukonda", "Berasia", "Sultans Battery", "Ramanagaram", "Angadipuram", "Mattanur", "Gobichettipalayam", "Banga", "Sibsagar", "Namrup", "North Lakhimpur", "Dhenkanal", "Karanja", "Cheyyar", "Vandavasi", "Arakkonam", "Tiruvannamalai", "Akividu", "Tadepallegudem", "Madanapalle", "Puttur", "Edavanna", "Kodungallur", "Marmagao", "Sanquelim", "Sakri", "Shahdol", "Satna", "Thasra", "Bundi", "Kishangarh", "Firozpur", "Kot Isa Khan", "Barnala", "Sunam", "Pithoragarh", "Jaspur", "Jhargram", "Dimapur", "Churachandpur", "Raxaul", "Motihari", "Munger", "Purnea", "Mannargudi", "Kumbakonam", "Eral", "Nagercoil", "Kanniyakumari", "Ramanathapuram", "Sivaganga", "Rajapalaiyam", "Srivilliputhur", "Suratgarh", "Gohana", "Sirsa", "Fatehabad", "Nurpur", "Chamba", "Khergam", "Dindigul", "Pudukkottai", "Kaimganj", "Tarn Taran", "Khanna", "Irinjalakuda", "Sehore", "Parra", "Dicholi", "Chicalim", "Saligao", "Changanacheri", "Igatpuri", "Sangamner", "Ganganagar", "Kanhangad", "Chidambaram", "Chittur", "Nilambur", "Arvi", "Jalesar", "Kasganj", "Chandausi", "Beawar", "Bharatpur", "Kathua", "Chalisgaon", "Karamsad", "Peranampattu", "Arani", "Payyanur", "Pattambi", "Pattukkottai", "Pakala", "Vikarabad", "Bhatkal", "Rupnarayanpur", "Kulti", "Koch Bihar", "Nongstoin", "Budbud", "Balangir", "Kharar", "Mukerian", "Mansa", "Punalur", "Mandya", "Nandyal", "Dhone", "Candolim", "Aldona", "Solim", "Daman", "Koothanallur", "Sojat", "Alanallur", "Kagal", "Jhunjhunun", "Sirhind", "Kurali", "Khinwara", "Machhiwara", "Talwandi Sabo", "Malpur", "Dhar", "Medarametla", "Pileru", "Yercaud", "Ottappalam", "Alangulam", "Palus", "Chiplun", "Durg", "Damoh", "Ambarnath", "Haveri", "Mundgod", "Mandvi", "Behala", "Fort", "Bela", "Balana", "Odhan", "Mawana", "Firozabad", "Bichpuri", "Almora", "Pauri", "Azamgarh", "Phaphamau", "Nongpoh", "Gangrar", "Jhalawar", "Nathdwara", "Jaisalmer", "Pushkar", "Sirohi", "Baroda", "Ambah", "Ambejogai", "Ambad", "Osmanabad", "Betalbatim", "Gangapur", "Dindori", "Yeola", "Pandharpur", "Neri", "Umred", "Patelguda", "Patancheru", "Singarayakonda", "Peddapuram", "Gadag", "ChikmagalÅ«r", "Chikodi", "Amer", "Chintamani", "Tambaram", "Palayam", "Karamadai", "Omalur", "Kuzhithurai", "Faizabad", "Thirumangalam", "Kodaikanal", "Devipattinam", "Dharapuram", "Rudrapur", "Talcher", "Haldia", "Karsiyang", "Sandur", "Bapatla", "Shamsabad", "Kandi", "Ramapuram", "Anchal", "Trimbak", "Calangute", "Arpora", "Khargone", "Mandla", "Kalan", "Pachmarhi", "Dhamtari", "Kumhari", "Aundh", "Tala", "Tuljapur", "Botad", "Sidhpur", "Sanand", "Nagwa", "Mussoorie", "Vadamadurai", "Sholavandan", "Pochampalli", "Perundurai", "Lalgudi", "Ponneri", "Mount Abu", "Vadner", "Shanti Grama", "Nalagarh", "Pahalgam", "Dinanagar", "Jatani", "Ganga", "Barmer", "Hoshangabad", "Khajuraho Group of Monuments", "Betul", "Sangola", "Tirumala", "Mirza Murad", "Attur", "Budha", "Pala", "Tonk", "Koni", "Rajpur", "Shrigonda", "Hazaribagh", "Nagaur", "Mandapeta", "Nabadwip", "Nandurbar", "Nazira", "Kasia", "Bargarh", "Kollegal", "Shahkot", "Jagraon", "Channapatna", "Madurantakam", "Kamalpur", "Ranaghat", "Mundra", "Mashobra", "Rama", "Chirala", "Bawana", "Dhaka", "Mahal", "Chitradurga", "Mandsaur", "Dewas", "Sachin", "Andra", "Kalkaji Devi", "Pilkhuwa", "Mehra", "Chhachhrauli", "Samastipur", "Bangaon", "Ghatal", "Jayanti", "Belgharia", "Kamat", "Dhariwal", "Morinda", "Kottagudem", "Suriapet", "Mahesh", "Sirwani", "Kanakpura", "Mahajan", "Sodhi", "Chand", "Nagal", "Hong", "Raju", "Tikamgarh", "Parel", "Jaynagar", "Mill", "Khambhat", "Ballabgarh", "Begusarai", "Shahapur", "Banka", "Golaghat", "Palwal", "Kalra", "Chandan", "Maru", "Nanda", "Chopra", "Kasal", "Rana", "Chetan", "Charu", "Arora", "Chhabra", "Bishnupur", "Manu", "Karimganj", "Ellora Caves", "Adwani", "Amreli", "Soni", "Sarwar", "Balu", "Rawal", "Darsi", "Nandigama", "Mathan", "Panchal", "Jha Jha", "Hira", "Manna", "Amal", "Kheda", "Abdul", "Roshan", "Bhandari", "Binavas", "Hari", "Nandi", "Rajapur", "Suman", "Sakri", "Khalapur", "Dangi", "Thiruthani", "Bawan", "Basu", "Kosamba", "Medchal", "Kakdwip", "Kamalpura", "Dogadda", "Charan", "Basirhat", "Nagari", "Kangayam", "Sopara", "Nadia", "Mahulia", "Alipur", "Hamirpur", "Fatehgarh", "Bagh", "Naini", "Karari", "Ajabpur", "Jaunpur", "Iglas", "Pantnagar", "Dwarahat", "Dasna", "Mithapur", "Bali", "Nilokheri", "Kolayat", "Haripur", "Dang", "Chhota Udepur", "Matar", "Sukma", "Guna", "Dona Paula", "Navelim", "Vainguinim", "Curchorem", "Balaghat", "Bhagwan", "Vijapur", "Sinnar", "Mangaon", "Hadadi", "Bobbili", "Yanam", "Udaigiri", "Balanagar", "Kanigiri", "Muddanuru", "Panruti", "Proddatur", "Puliyur", "Perambalur", "Turaiyur", "Tiruchchendur", "Shadnagar", "Markal", "Sultan", "Rayagada", "Kaniyambadi", "Vandalur", "Sangam", "Katoya", "Gudur", "Farakka", "Baramati", "Tohana"};
    public String fbemail,fbpass,fbphn,fbcnfpass,fbfn,fbln,fbseleccity,fbbdate,fbmrgdate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
       fn=findViewById(R.id.fnet);
        ln=findViewById(R.id.lnet);
        phn=findViewById(R.id.phet);
        pass=findViewById(R.id.pswet);
        cnfpass=findViewById(R.id.reet);
        bddate=findViewById(R.id.dobet);
        mrgdate = findViewById(R.id.annet);
        email=findViewById(R.id.emailet);
        city=(Spinner) findViewById(R.id.spinnercity);
        city.setOnItemSelectedListener(this);
        register=findViewById(R.id.registerbutton);

        firebaseAuth = FirebaseAuth.getInstance();

    bddate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal= Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month= cal.get(Calendar.MONTH);
            int date=cal.get(Calendar.DATE);
            DatePickerDialog dialog = new DatePickerDialog(ListItem.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datePickerDialog,year,month,date);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
dialog.show();
        }


    });
    mrgdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal=   Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month= cal.get(Calendar.MONTH);
            int date=cal.get(Calendar.DATE);
            DatePickerDialog dialog = new DatePickerDialog(ListItem.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,marriagedatepicker,year,month,date);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();}
    });

    datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
           int monthwrong=i1+1;
            tempbdate=(i)+" /"+monthwrong+" /"+i2;
            Toast.makeText(ListItem.this,tempbdate , Toast.LENGTH_SHORT).show();

        }
    };
marriagedatepicker = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int monthwrong=i1+1;
        tempmrgdate=i+" /"+monthwrong+" /"+i2;
        Toast.makeText(ListItem.this, tempmrgdate, Toast.LENGTH_SHORT).show();
    }
};

    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

               fbemail = email.getText().toString();
             fbpass=pass.getText().toString();
              fbphn=phn.getText().toString();
             fbcnfpass=cnfpass.getText().toString();
              fbfn=fn.getText().toString();
              fbln=ln.getText().toString();
              fbseleccity=selectedcity;
              fbbdate = tempbdate;
              fbmrgdate = tempmrgdate;

            if(fbemail.isEmpty() ||fbpass.isEmpty() ||fbcnfpass.isEmpty() ||fbfn.isEmpty() ||fbln.isEmpty() ){
                Snackbar.make(view,"Please Enter The required Fields" ,LENGTH_LONG).show();
            }else if(!fbpass.equals(fbcnfpass)){
                Snackbar.make(view,"Password and Conform password Should be same",LENGTH_LONG).show();
            }
            else {
                firebaseAuth.createUserWithEmailAndPassword(fbemail,fbpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            userregdetails user = new userregdetails(
                                    fbfn,
                                    fbln,
                                    fbphn,
                                    fbseleccity,
                                    fbbdate,
                                    fbemail,
                                    fbmrgdate

                            );
                            FirebaseDatabase.getInstance().getReference("Users").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(ListItem.this, "Succesful", Toast.LENGTH_SHORT).show();
                                        Snackbar.make(view,"Data Stored Succesfully",LENGTH_SHORT);
                                    }else{
                                        Toast.makeText(ListItem.this, "Data not stored succesfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });






                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Snackbar.make(view,"An Verification Link has been sent to your email , please check your email to proceed",LENGTH_LONG).show();
                                        Intent i = new Intent(ListItem.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(ListItem.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    });
















        Arrays.sort(citiesofindia);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,citiesofindia);
arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setPrompt("Select Your City");
city.setAdapter(arrayAdapter);


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Snackbar.make(adapterView,citiesofindia[i], LENGTH_LONG).show();
        selectedcity=citiesofindia[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
