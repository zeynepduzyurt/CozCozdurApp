package com.example.zeynep.cozcozdurapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TestCoz extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    TextView tv_question, tv_total_true, tv_total_false, tv_totalQuestion, testName;
    RadioGroup rg_options;
    RadioButton rbA;
    RadioButton rbB;
    RadioButton rbC;
    RadioButton rbD;
    /*strings.xml içerisinde tanımladığımız iki ayrı diziyi aktaracağımız diziler*/
    String[] fileName, titles;
    /*Dosya başlıkları için hazırladığımız başlıkları spinner içinde göstereceğiz.*/
    Spinner spinner;
    /*XML verilerini okumak için, AsyncTask sınıfı oluşturduk.*/
    ReadXML readXML;
    /*Testin doğru cevabını saklayacağımız değişken*/
    String trueAnswer;
    /*Dosyada okunacak XML sorularının tutulacağı bir dizi.*/
    ArrayList<Question> qeuestions = new ArrayList<>();
    /*Toplam doğru sayısı, toplam yanlış sayısı, test sorularının bitip bitmediğini kontrol için sayaç, toplam soru sayısı ve spinner dan seçilen dosya index numarası*/
    int totalTrue = 0, totalFalse = 0, counter, questionsLength, file_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_coz);
        getControlViews();
        getArray();
        fillSpinner();
        setEventForView();
    }

    private void getControlViews() {
        /*Düzendekş tüm kontrollere erişim sağlanır.*/
        spinner = (Spinner) findViewById(R.id.spinner_konular);
        tv_question = (TextView) findViewById(R.id.tv_soru_metni);
        tv_totalQuestion = (TextView) findViewById(R.id.tv_totalQuestion);
        tv_total_true = (TextView) findViewById(R.id.tv_dogru_sayisi);
        tv_total_false = (TextView) findViewById(R.id.tv_yanlis_sayisi);
        testName = (TextView) findViewById(R.id.testName);
        rg_options = (RadioGroup) findViewById(R.id.rg_secenekler);
        rbA = (RadioButton) findViewById(R.id.rbA);
        rbB = (RadioButton) findViewById(R.id.rbB);
        rbC = (RadioButton) findViewById(R.id.rbC);
        rbD = (RadioButton) findViewById(R.id.rbD);
    }

    private void getArray() {
        /*Strings.xml dosyasındaki tüm verilere erişim sağlanır. Veriler dizilere aktarılır.*/
        fileName = getResources().getStringArray(R.array.file_name);
        titles = getResources().getStringArray(R.array.titles);
    }

    private void fillSpinner() {
        /*Spinner titles dizisi ile doldurulur.*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.titles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setEventForView() {
        /*RadioGroup ve spinner için uygun olay dinleyicileri tanımladık.*/
        rg_options.setOnCheckedChangeListener(this);
        spinner.setOnItemSelectedListener(this);
    }


    private void setTextForTextView() {
        /*Doğru sayısı, yanlış sayısı ve çözülen/toplam soru sayıları uygun kontrollere yazılır.*/
        tv_total_true.setText("Doğru Sayısı : " + totalTrue);
        tv_total_false.setText("Yanlış Sayısı : " + totalFalse);
        tv_totalQuestion.setText(counter + "/" + questionsLength);
    }

    public void getQuestionInfo(int j) {
        /*ArrayList içinde bulunan soru, a,b,c,d ve doğru cevap değerleri uygun alanlara set edilir. */
        tv_question.setText(qeuestions.get(j).getSoru_metni());
        rbA.setChecked(false);
        rbA.setText(qeuestions.get(j).getA());
        rbB.setChecked(false);
        rbB.setText(qeuestions.get(j).getB());
        rbC.setChecked(false);
        rbC.setText(qeuestions.get(j).getC());
        rbD.setChecked(false);
        rbD.setText(qeuestions.get(j).getD());
        trueAnswer = qeuestions.get(j).getCevap();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        /*Spinner dan bir veri seçildiğinde bu metot çalıştırılır.*/
        importantMethod(position);
    }

    private void importantMethod(int position) {
        /*XML dosyası bu metot ile yüklenir.*/
        /*Doğru sayısı = 0*/
        totalTrue = 0;
        /*Yanlış sayısı = 0*/
        totalFalse = 0;
        /*sayaç=1*/
        counter = 1;
        /*dosya index = 0*/
        file_index = position;
        /*Çözülen testin adı ekranda gösterilir.*/
        testName.setText("Test Adı: " + titles[file_index]);
        /*AsyncTask ile ismi alınan dosya sunucudan alınır*/
        readXML = new ReadXML(this, fileName[position]);
        readXML.execute();
        /*Doğru sayısı, yanlış sayısı ve çözülen/toplam soru sayıları uygun kontrollere yazılır.*/
        setTextForTextView();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        /*counter yani sayaç toplam soru sayısından büyük ise, işlem yapılır.
        Hangi radio button kontrolüne tıklanmışsa karşılaştırma işlemi yapılır:
        doğru ise doğru sayısı 1 arttırılır, yanlış ise yanlış sayısı 1 arttılır.*/
        if (counter < questionsLength) {
            switch (checkedId) {
                case R.id.rbA:
                    if (trueAnswer == "A") totalTrue++;
                    else totalFalse++;

                    break;
                case R.id.rbB:
                    if (trueAnswer == "B") totalTrue++;
                    else totalFalse++;

                    break;
                case R.id.rbC:
                    if (trueAnswer == "C") totalTrue++;
                    else totalFalse++;

                    break;
                case R.id.rbD:
                    if (trueAnswer == "D") totalTrue++;
                    else totalFalse++;
                    break;
            }
            getQuestionInfo(counter);
            counter++;
            setTextForTextView();
        } else {
            /*Sayaç toplam soru sayısına ulaştığında bu blok icra edilir.
            index numarsı 1 arttırılrı. index numarası toplam dosya sayısından küçük ise,
            bir sonraki dosya yüklenir.*/
            file_index++;
            if (file_index < fileName.length) {
                importantMethod(file_index);
            } else {
                /*index numarası eşit veya büyük ise, ilk dosya yüklenir.*/
                importantMethod(0);
            }
        }
    }

    public class ReadXML extends AsyncTask<Void, Void, ArrayList<Question>> {
        Context context;
        ProgressDialog progressDialog;
        String file_url;
        URL url;

        public ReadXML(Context context, String file_url) {
            this.context = context;
            /*Gelen dosya bilgisine göre yeni bir url oluşturulur.*/
            this.file_url = "http://10.0.2.2:80/sorubankasi/" + file_url + ".xml";
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Dosya Yükleniyor....");
        }

        @Override
        protected ArrayList<Question> doInBackground(Void... params) {
            return processParseXml(getDocument());
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Question> result) {
            progressDialog.dismiss();
            tv_question.setText(result.get(0).getSoru_metni());
            rbA.setText(result.get(0).getA());
            rbA.setChecked(false);
            rbB.setText(result.get(0).getB());
            rbB.setChecked(false);
            rbC.setText(result.get(0).getC());
            rbC.setChecked(false);
            rbD.setText(result.get(0).getD());
            rbD.setChecked(false);
            trueAnswer = result.get(0).getCevap();
            qeuestions = result;
            questionsLength = qeuestions.size();
            setTextForTextView();
            super.onPostExecute(result);
        }

        public Document getDocument() {
            try {
                /*URL bilgisi alınır*/
                url = new URL(file_url);
                /*URL ve uygulama arasında bağlantı kurulur.*/
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                /*URL için request metot belirlenir. Buraya GET,POST, HEAD,OPTIONS,PUT v.b.
                değerler verilebilir.*/
                connection.setRequestMethod("GET");
                /*Kuruluna bağlantının ucunda bulunan dosyanın veri akışı başlatılır.
                Bu kod ile veriler alınır. Fakat üzerinde işlem yapılacak düzende değildir.*/
                InputStream inputStream = connection.getInputStream();
                /*DocumentBuilderFactory:Parser(ayrıştırıcı) elde etmeyi sağlayan bir sınıftır.
                Bu sınıf XML dosyasından, DOM (Document Object Model – Belge Nesnesi Modeli)
                nesneleri üretmeyi sağlar. */
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                /*DocumentBuilder:XML belgesinden, DOM belge örnekleri oluşturmak için kullanılır. */
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                /*Document; DocumentBuilder ile elde edilen DOM belge örneklerinden XML dosyası
                oluşturmak için kullanılır. */
                Document xmlDoc = builder.parse(inputStream);
                return xmlDoc;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private ArrayList<Question> processParseXml(Document data) {
            if (data != null) {
                /*Tüm sorular için temel bir yapı oluşturduk.*/
                ArrayList<Question> question = new ArrayList<>();
                /*getDocumentElement(): XML dosyamızda bulunan <data> etiketine erişmeyi sağlar.*/
                Element root = data.getDocumentElement();
                /*getChildNodes(): <data> etiketinde bulunan tüm etiket ve özelliklere erişim sağlar.*/
                NodeList sorular = root.getChildNodes();
                /*getLength(): Toplam eleman sayısını verir.*/
                for (int i = 0; i < sorular.getLength(); i++) {
                    /*item(counter): dizi halinde bulunan etiketlerden birini almak için kullanılır.*/
                    Node soru = sorular.item(i);
                    /*soru.getNodeName().equalsIgnoreCase("soru"): Alınan etiketin ismi soru ise,*/
                    if (soru.getNodeName().equalsIgnoreCase("soru")) {
                        /*Tek bir soru için nesne oluştuduk*/
                        Question body = new Question();
                        /*soru etiketinde olan tüm etiketler seçilir.*/
                        NodeList soruYapisi = soru.getChildNodes();
                        /*döngü oluşturulur.*/
                        for (int j = 0; j < soruYapisi.getLength(); j++) {
                            /*soru etiketinde olan tüm etiketlere sırasıyla erişim sağlanır
                            ve gerekli karşılaştırma işlemleri yapılır.*/
                            Node soru_icerik = soruYapisi.item(j);
                            /*gelen etiket soru_metni ise işlem yapar, diğer etiketler
                            içinde aynı işlemler sırasıyla yapılır.*/
                            if (soru_icerik.getNodeName().equalsIgnoreCase("soru_metni")) {
                                body.setSoru_metni(soru_icerik.getTextContent());
                            } else if (soru_icerik.getNodeName().equalsIgnoreCase("A")) {
                                body.setA(soru_icerik.getTextContent());
                                if (soru_icerik.getAttributes().item(0).getTextContent().equalsIgnoreCase("1")) {
                                    body.setCevap("A");
                                }
                            } else if (soru_icerik.getNodeName().equalsIgnoreCase("B")) {
                                body.setB(soru_icerik.getTextContent());
                                if (soru_icerik.getAttributes().item(0).getTextContent().equalsIgnoreCase("1")) {
                                    body.setCevap("B");
                                }
                            } else if (soru_icerik.getNodeName().equalsIgnoreCase("C")) {
                                body.setC(soru_icerik.getTextContent());
                                if (soru_icerik.getAttributes().item(0).getTextContent().equalsIgnoreCase("1")) {
                                    body.setCevap("C");
                                }
                            } else if (soru_icerik.getNodeName().equalsIgnoreCase("D")) {
                                body.setD(soru_icerik.getTextContent());
                                if (soru_icerik.getAttributes().item(0).getTextContent().equalsIgnoreCase("1")) {
                                    body.setCevap("D");
                                }
                            }
                        }
                        question.add(body);
                        /*Log.counter("soru_metni", body.getSoru_metni());
                        Log.counter("A", body.getA());
                        Log.counter("B", body.getB());
                        Log.counter("C", body.getC());
                        Log.counter("D", body.getD());
                        Log.counter("Cevap", body.getCevap());*/
                    }
                }
                //Toast.makeText(context,""+question.size(),Toast.LENGTH_SHORT).show();
                return question;
            }
            return null;
        }
    }
}
